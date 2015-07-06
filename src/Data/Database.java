    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import Parse.Trie;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
public class Database {
    
    Map <String, Player> players = new HashMap ();
    List<Year> years = new ArrayList<>();
    List<Team> fh_teams = new ArrayList<>();
    Trie lookup = new Trie();
    String [] teams = new String [] {"ANA", "ARI", "BOS", "BUF", "CGY", "CAR",
                                    "CHI", "COL", "CBJ", "DAL", "DET", "EDM",
                                    "FLA", "LAK", "MIN", "MTL", "NSH", "NJD",
                                    "NYI", "NYR", "OTT", "PHI", "PIT", "SJS",
                                    "STL", "TBL", "TOR", "VAN", "WSH", "WPG"};    
    String timestamp;
    
    public Database () throws IOException, InterruptedException {
        //populate();
    }
    
    public final void populate () throws IOException, InterruptedException {   
        System.out.println("Populating database...");
        
        for (int yr=2015; yr<=2015; yr++){
            Year y = new Year(yr);
            y.initiate(lookup);
            y.detail(lookup);
        
            years.add(y);
            System.out.println("Added year " + yr);
        }

        addTeams();
//        dumpExcel();
//        System.out.println("Excel dump done");
    }
   
    public void serialize () throws FileNotFoundException, IOException {        
//        Map <String, Player> test = new HashMap ();
//        test.put("test", players.get("jamie benn"));
//        
        FileOutputStream fos = new FileOutputStream("data.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(years);
        oos.close();
        
        fos = new FileOutputStream("lut.ser");
        oos = new ObjectOutputStream(fos);
        oos.writeObject(lookup);
        oos.close();
    }
    
    public void deserialize () throws FileNotFoundException, IOException, ClassNotFoundException  {
        FileInputStream fis = new FileInputStream("data.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        years = (List) ois.readObject();
        ois.close();
        
        fis = new FileInputStream("lut.ser");
        ois = new ObjectInputStream(fis);
        lookup = (Trie) ois.readObject();
        ois.close();
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
        data = data.substring(0, data.indexOf("</tbody>"));
        String [] temp = data.split("<td class=\"team leftCol\">");
        //System.out.println(temp[1]);
        
        for (int i=1; i<temp.length; i++){
            Team t = new Team();
            t.fillTeam(years, lookup, temp[i]);
            fh_teams.add(t);
            t.getTotals();
            t.printAverages();
        }
        
        firefoxDriver.quit();
    }
         
    public void dumpExcel () throws FileNotFoundException, IOException {
                //Workbook wb = new HSSFWorkbook();
        Workbook wb = new XSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("leaguedata");

        // set headers on excel sheet
        Row row = sheet.createRow((short)0);
        String headers [] = new String [] {
            "Team", "Player", "Position", "Age", "Yrs Played", "GP", "G", "A", 
            "PTS", "+/-", "STP", "SOG", "SH%", "Hits", "Blocks", "TOI", "G/60", 
            "A/60", "PTS/60", "STP/60", "SOG/60", "Hits/60", "Blocks/60"
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
        
//        // dump ALL players!!!!!
//        for (Map.Entry<String, Player> entry : players.entrySet()){
//            Row newrow = sheet.createRow((short)track);
//            entry.getValue().dumpExcel(newrow, "null");
//            track++;
//        }
//        
//                // Write the output to a file
//        FileOutputStream fileOut = new FileOutputStream("RFHL_allplayers.xlsx");
//        wb.write(fileOut);
//        fileOut.close();

        // dump fantasy teams!!!
        for (int i=0; i<fh_teams.size(); i++){
            track = fh_teams.get(i).dumpExcel(sheet, track);
            track++;
        }
        
        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("RFHL.xlsx");
        wb.write(fileOut);
        fileOut.close();

    }
    
    public void listen () throws IOException { 
        Scanner reader = new Scanner(System.in);
        List<Year> search_years = new ArrayList<>();
        boolean quit = false;
        
        while (!quit){
            System.out.println("List years: ");
            String req_years = reader.nextLine(); // CHECK FOR YEAR
            
            System.out.println("Enter player: ");
            String req = reader.nextLine();
            
            if (req.toLowerCase().equals("quit")) {
                quit = true;
            } else {       
                List<Player> res = search(req_years, req);
                
                if (res.isEmpty()) System.out.println("No player by that name found!");
                else               for (Player p : res) { p.printPlayer(); } 
            }
        }
    }
    
    public List<Player> search (String req_years, String srch) {
        List<Player> result = new ArrayList<>();
        
        for (int i=0; i<years.size(); i++){
            if (req_years.contains(String.valueOf(years.get(i).name))){
                result.addAll(years.get(i).search(lookup, srch));
            }
        }
        
        return result;
    }
        
}
