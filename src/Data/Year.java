/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import Parse.Trie;
import static Utils.StringCmds.round;
import Web.Site;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**s
 *
 * @author trevor.witjes
 */
public class Year implements Serializable{
    int name;
    Map <String, Player> players = new HashMap ();
    String temp[];
    
    public Year (int year){
        name = year;
    }
    
    public void initiate(Trie lookup) throws IOException {
        // nhlnumbers.com for inital player list and basic stats
        String input = getSource("http://stats.nhlnumbers.com/player_stats/position/skater/year/" + String.valueOf(name), "MM");
        input = input.substring(input.indexOf("/thead")).replaceAll("\\s+"," ");
        temp = input.split("Jersey");

        for (int i=1; i<temp.length; i++){
            Player p = new Player();
            p.nhlnumbers(temp[i]);
            p.year = String.valueOf(name);
            
            if (lookup.getWords(p.name.toLowerCase()).isEmpty()){ // Add player to lookup
                lookup.addWord(p.name.toLowerCase()); 
            } 
            players.put(p.name.toLowerCase(), p); // Add player to hashmap
        }
    }
    
    public void detail(Trie lookup) throws IOException {
        String input = getSource("http://www.sportingcharts.com/nhl/stats/time-on-ice-statistics/" + String.valueOf(name - 1) + "/", ">-<");
        input = input.substring(input.indexOf("</thead>"));
        temp = input.split("text-align: left");
        
        for (int i=1; i<temp.length; i++){ //temp.length
            String pname = fixname(temp[i].substring(temp[i].indexOf("/\'>")+3, temp[i].indexOf("</a>")));
            Player p = players.get(pname.toLowerCase());
            if (p != null) { p.sportingcharts(temp[i]); }
            // else           { System.out.println(pname); }
        }
        
        input = getSource("http://www.behindthenet.ca/nhl_statistics.php?ds=34&s=3&f1=" + String.valueOf(name - 1) + "_s&f2=5v5&c=0+1+3+5+29+30+31+32+33+34#snip=f");
        input = input.substring(input.indexOf(">PDO<"));
        temp = input.split("<tr id=");
        
        for (int i=1; i<temp.length; i++){
            String pname = temp[i].substring(temp[i].indexOf("col-2"));
            pname = pname.substring(pname.indexOf("\">")+2, pname.indexOf("<")).toLowerCase();            
            
            Iterator it = players.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry <String, Player> pair = (Map.Entry)it.next();
                Player p = pair.getValue();
                if (p != null && p.BTN_index.contains(pname)){
                    p.behindthenet(temp[i]);
                }
            }
        }    
    }
    
    public List <Player> search (Trie lookup, String srch){
        List<Player> result = new ArrayList<>();
        
        List poss = lookup.getWords(srch.toLowerCase());
        int i;    
        for (i=0; i<poss.size(); i++){
            if (!((players.get(poss.get(i).toString())) == null)){
                result.add(players.get(poss.get(i).toString()));
            }
        }
        
        return result;
    }
    
    public String fixname (String name){
        switch (name){
            case "Michael Cammalleri":
                name = "Mike Cammalleri";
                break;
            case "Pierre-Alexandr Parenteau":
                name = "Pierre-Alexandre Parenteau";
                break;
             
             
        }
        
        return name;
    }
    
    public void fh_teams(){
        
    }  
    
    public String getSource(String url, String stop) throws IOException{
        Site site = new Site(url);
        site.connect();
        return site.read(stop);
    }
    
    public String getSource(String url) throws IOException{
        Site site = new Site(url);
        site.connect();
        return site.read();
    }
    
    
    
    
    
    
    
    
    public void Yahoo(String in_stats, String in_data, Map <String, Player> players, Trie lookup, String team){
        String [] temp_s;
        String [] stats;
        temp_s = in_stats.split(";\">");
        
        String srch;
        String [] temp_d;
        String [] data;
        temp_d = in_data.split(";\">");
        
        for (int i=1; i<temp_s.length; i++){
            Player p = new Player();
            p.team = team;
            p.name = temp_s[i].substring(0, temp_s[i].indexOf("<"));
            stats = temp_s[i].split("title=\"");
            
            p.gp = Integer.parseInt(stats[1].substring((stats[1].indexOf(">")+1), stats[1].indexOf("<")));
            p.goals = Integer.parseInt(stats[2].substring((stats[2].indexOf(">")+1), stats[2].indexOf("<")));
            p.assists = Integer.parseInt(stats[3].substring((stats[3].indexOf(">")+1), stats[3].indexOf("<")));
            p.points = Integer.parseInt(stats[4].substring((stats[4].indexOf(">")+1), stats[4].indexOf("<")));
            p.plusminus = Integer.parseInt(stats[5].substring((stats[5].indexOf(">")+1), stats[5].indexOf("<")));
            p.pim = Integer.parseInt(stats[6].substring((stats[6].indexOf(">")+1), stats[6].indexOf("<")));
            p.hits = Integer.parseInt(stats[7].substring((stats[7].indexOf(">")+1), stats[7].indexOf("<")));
            p.blocks = Integer.parseInt(stats[8].substring((stats[8].indexOf(">")+1), stats[8].indexOf("<")));
            p.fow = Integer.parseInt(stats[9].substring((stats[9].indexOf(">")+1), stats[9].indexOf("<")));
            p.fol = Integer.parseInt(stats[10].substring((stats[10].indexOf(">")+1), stats[10].indexOf("<")));
            p.ppg = Integer.parseInt(stats[11].substring((stats[11].indexOf(">")+1), stats[11].indexOf("<")));
            p.ppa = Integer.parseInt(stats[12].substring((stats[12].indexOf(">")+1), stats[12].indexOf("<")));
            p.shg = Integer.parseInt(stats[13].substring((stats[13].indexOf(">")+1), stats[13].indexOf("<")));
            p.sha = Integer.parseInt(stats[14].substring((stats[14].indexOf(">")+1), stats[14].indexOf("<")));
            p.stp = p.ppg + p.ppa + p.shg + p.sha;
            p.gwg = Integer.parseInt(stats[15].substring((stats[15].indexOf(">")+1), stats[15].indexOf("<")));
            p.sog = Integer.parseInt(stats[16].substring((stats[16].indexOf(">")+1), stats[16].indexOf("<")));
            p.sht_pcnt = round(Float.parseFloat(stats[17].substring((stats[17].indexOf(">")+1), stats[17].indexOf("<")))*100, 2);
                         
            // Add player to lookup
            if (lookup.getWords(p.name.toLowerCase()).isEmpty()){
                lookup.addWord(p.name.toLowerCase());
            } 
            
            // Add player to hashmap
            String key = p.name.toLowerCase(); //.replace(" ", "")
            if (players.get(key) != null){ // if player name exists, add split
                Split s = new Split(team, p);
                //players.get(key).addSplit(s);
            } else {
                players.put(key, p);
            }     
        }
        
        for (int i=1; i<temp_d.length; i++){
            srch = temp_d[i].substring(0, temp_d[i].indexOf("<"));
            data = temp_d[i].split("class=\"");
            
            List poss = lookup.getWords(srch.toLowerCase());
            for (int j=0; j<poss.size(); j++){
                players.get(poss.get(j).toString()).age = Integer.parseInt(data[6].substring((data[6].indexOf(">")+1), data[6].indexOf("<")));
                players.get(poss.get(j).toString()).exp = Integer.parseInt(data[7].substring((data[7].indexOf(">")+1), data[7].indexOf("<")));
                //players.get(poss.get(j).toString()).printPlayer();
            }
        }
            
    }

    
}
