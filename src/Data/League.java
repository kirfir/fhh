/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import Parse.Trie;
import Utils.StringCmds;
import Web.Site;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 *
 * @author trevor.witjes
 */
public class League {
    
    Map <String, Player> players = new HashMap ();
    List<Team> fh_teams = new ArrayList<>();
    Trie lookup = new Trie();
    String [] teams = new String [] {"ANA", "ARI", "BOS", "BUF", "CGY", "CAR",
                                    "CHI", "COL", "CBJ", "DAL", "DET", "EDM",
                                    "FLA", "LAK", "MIN", "MTL", "NSH", "NJD",
                                    "NYI", "NYR", "OTT", "PHI", "PIT", "SJS",
                                    "STL", "TBL", "TOR", "VAN", "WSH", "WPG"};    
    String timestamp;
    
    public League () throws IOException, InterruptedException {
        System.out.println("Populating league...");
        populate();
    }
    
    public final void populate () throws IOException, InterruptedException {   

        String alt;
        for (String team : teams) {
            switch (team) {
                case "CBJ": alt = "cob"; break;
                case "LAK": alt = "los"; break;
                case "MTL": alt = "mon"; break;
                case "SJS": alt = "san"; break;
                case "TBL": alt = "tam"; break;
                case "WSH": alt = "was"; break;
                default: alt = team; break;
            }
            
            addYahooData("https://ca.sports.yahoo.com/nhl/teams/" + alt.toLowerCase() + "/stats/", team);
        }
        for (String team : teams) {
            addGsonData("http://nhlwc.cdnak.neulion.com/fs1/nhl/league/playerstatsline/20142015/2/" + team + "/iphone/playerstatsline.json", team);
        }
        
        addTeams();
        dumpExcel();
    }
    
    public void addGsonData(String site, String team) throws IOException {
        Site neulion = new Site(site);
        neulion.connect();
        String input = neulion.read();
        String [] split;

        try(Reader reader = new StringReader(input)){
            Gson gson = new GsonBuilder().create();
            Add add = gson.fromJson(reader, Add.class);
            add.Gson(players, lookup, team);
            split = add.toString().split("\\(");             
        }
        timestamp = split[0]; // Store timestamp for verification purposes
    }
    
    public void addYahooData(String site, String team) throws IOException {
        Site yahoo = new Site(site);
        yahoo.connect();
        String input = yahoo.read();
        
        String temp = input.substring(input.indexOf("<colgroup><col><col><col><col><col><col><col><col><col><col><col><col><col><col><col><col><col><col></colgroup>"));
        temp = temp.substring(temp.indexOf("<tbody>"), temp.indexOf("</tbody>"));
        Add add = new Add();
        add.Yahoo(temp, players, lookup, team);
    }
    
    public void addTeams() throws InterruptedException, FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("login.txt"));
        String username = fileScanner.nextLine();
        String password = fileScanner.nextLine();
        
        WebDriver firefoxDriver = new FirefoxDriver();
       
        //Open the url which we want in firefox
        firefoxDriver.get("http://www.fantrax.com/fantasy/teamRosterChart.go?leagueId=ntokropui8nbbjt1");
        WebElement link;
        link = firefoxDriver.findElement(By.id("loginZone"));
        link.click();
        WebElement input;
        input = firefoxDriver.findElement(By.id("j_username"));
        input.sendKeys(username);
        input = firefoxDriver.findElement(By.id("realPassword"));
        input.sendKeys(password);
        input.sendKeys(Keys.RETURN);
        
        Thread.sleep(2000);
        firefoxDriver.get("http://www.fantrax.com/fantasy/teamRosterChart.go?leagueId=ntokropui8nbbjt1");
        String data = firefoxDriver.getPageSource();
        
        data = data.substring(data.indexOf("<table class=\"fantTable rosterChart\">"));
        String [] temp = data.split("<td class=\"team leftCol\">");
        //System.out.println(temp[1]);
        
        Team t = new Team();
        t.fillTeam(players, lookup, temp[1]);
        fh_teams.add(t);
        
//        for (int i=1; i<temp.length; i++){
//            Team t = new Team();
//            t.fillTeam(temp[i]);
//            fh_teams.add(t);
//        }
        
        firefoxDriver.quit();
        
        //System.out.println(input);   
    }
         
    public void dumpExcel () throws FileNotFoundException, IOException {
                //Workbook wb = new HSSFWorkbook();
        Workbook wb = new XSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("leaguedata");

        // set headers on excel sheet
        Row row = sheet.createRow((short)0);
        String headers [] = new String [] {
            "Team", "Player", "Position", "GP", "G", "A", "PTS", "+/-", "STP", "SOG",
            "Hits", "Blocks", "TOI", "G/60", "A/60", "PTS/60", "STP/60",
            "SOG/60", "Hits/60", "Blocks/60"
        };

        for (int i=0; i<headers.length; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(createHelper.createRichTextString(headers[i]));
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyle);
        }

        // add player data
        int track = 2;
        for (int i=0; i<fh_teams.size(); i++){
            track = fh_teams.get(i).dumpExcel(sheet, track);
        }
        
        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("RFHL.xlsx");
        wb.write(fileOut);
        fileOut.close();
    }
    
    public void listen () throws IOException { 
        Scanner reader = new Scanner(System.in);
        boolean quit = false;
        
        while (!quit){
            System.out.println("Enter player: ");

            String req = reader.nextLine();
            if (req.toLowerCase().equals("quit")) {
                quit = true;
            } else {       
                int i;
                List poss = lookup.getWords(req.toLowerCase());
                for (i=0; i<poss.size(); i++){
                    players.get(poss.get(i).toString()).printPlayer();
                }
                
                if (i==0) System.out.println("No player by that name found!");
            }
        }
    }
        
}
