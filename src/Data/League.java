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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 *
 * @author trevor.witjes
 */
public class League {
    
    Map <String, List<Player>> players = new HashMap ();    
    Trie lookup = new Trie();
    String [] teams = new String [] {"ANA", "ARI", "BOS", "BUF", "CGY", "CAR",
                                    "CHI", "COL", "CBJ", "DAL", "DET", "EDM",
                                    "FLA", "LAK", "MIN", "MTL", "NSH", "NJD",
                                    "NYI", "NYR", "OTT", "PHI", "PIT", "SJS",
                                    "STL", "TBL", "TOR", "VAN", "WSH", "WPG"};    
    String timestamp;
    
    public League () throws IOException {
        System.out.println("Populating league...");
        populate();
    }
    
    // var=1:   gson files
    // var=2:   yahoo html
    public final void populate () throws IOException {   
        addYahooData("https://ca.sports.yahoo.com/nhl/teams/ott/stats/", "OTT");
        
//        for (String team : teams) {
//            addYahooData("https://ca.sports.yahoo.com/nhl/teams/" + team.toLowerCase() + "/roster/", team);
//        }
//        for (String team : teams) {
//            addGsonData("http://nhlwc.cdnak.neulion.com/fs1/nhl/league/playerstatsline/20142015/2/" + team + "/iphone/playerstatsline.json", team);
//        }
    }
    
    public void addGsonData(String site, String team) throws IOException {
        Site neulion = new Site(site);
        neulion.connect();
        String input = neulion.read();
        String [] split;

        try(Reader reader = new StringReader(input)){
            Gson gson = new GsonBuilder().create();
            Team t = gson.fromJson(reader, Team.class);
            t.addGson(players, lookup, team);
            split = t.toString().split("\\(");             
        }
        timestamp = split[0]; // Store timestamp for verification purposes
    }
    
    public void addYahooData(String site, String team) throws IOException {
        Site yahoo = new Site(site);
        yahoo.connect();
        String input = yahoo.read();
        
        String temp = input.substring(input.indexOf("<colgroup><col><col><col><col><col><col><col><col><col><col><col><col><col><col><col><col><col><col></colgroup>"));
        temp = temp.substring(temp.indexOf("<tbody>"), temp.indexOf("</tbody>"));
        Team t = new Team();
        t.addYahoo(temp, players, lookup, team);
    }
    
    public void listen () {
        Scanner reader = new Scanner(System.in);
        boolean quit = false;
        
        //System.out.println(Arrays.toString(players.entrySet().toArray()));
        
        while (!quit){
            System.out.println("Enter player: ");

            String req = reader.nextLine();
            if (req.toLowerCase().equals("quit")) {
                quit = true;
            } else {          
                List poss = lookup.getWords(req.toLowerCase());
                String srch;  
                int i, j;
                boolean samePlayer;

                for (i=0; i<poss.size(); i++){
                    srch = poss.get(i).toString();
                    for (j=0; j<players.get(srch).size(); j++){
                        samePlayer = false;
                        if (j > 0){
                            samePlayer = players.get(srch).get(j).name.equals(players.get(srch).get(j-1).name)
                                    && (players.get(srch).get(j).gp + players.get(srch).get(j-1).gp <= 82);
                        }
                        // if samePlayer, print name only once
                        if (samePlayer) { players.get(srch).get(j).printStats();}
                            else { players.get(srch).get(j).printPlayer();}
                    }
                }

                if (i==0) System.out.println("No player by that name found!");
            }
        }
    }
        
}
