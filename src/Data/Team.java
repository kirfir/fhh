/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import Parse.Trie;
import Utils.StringCmds;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author trevor.witjes
 */
public class Team {
    private String name;
    private List<Player> members = new ArrayList<>();
    int age;
    int gp, goals, assists, points, plusminus, pim, sog, hits, blocks,
        fow, fol, esp, stp, ppg, ppa, shg, sha, gwg;
    float sht_pcnt, toi, mins;
    float goals60, assists60, points60, sog60, hits60, blocks60, esp60, stp60;
    float onice_sht_pcnt;
    int rostered_spec_count, ir_count;
    
    public void fillTeam(List <Year> years, Trie lookup, String input){      
        name = input.substring(input.indexOf("\">") + 2, input.indexOf("</a>"));
        int count = 0;
        String [] temp = input.split("div class");

        for (int i=1; i<temp.length - 1; i++){  
            if (temp[i].contains("INJURED_RESERVE")) { ir_count++; }
            //System.out.println(i + " -- " + temp[i]);
            String pname = temp[i].substring(temp[i].indexOf("hand") + 7, temp[i].indexOf("</a>"));
            if (!temp[i].contains("MINORS") && count < 5) { // exclude minors players
                pname = pname.split(", ")[1].concat(" " + pname.split(",")[0]);
                int j;
                List poss = lookup.getWords(pname.toLowerCase());
                for (j=0; j<poss.size(); j++){
                    years.get(years.size()-1).players.get(poss.get(j).toString()).fh_team = name;
                    members.add(years.get(years.size()-1).players.get(poss.get(j).toString()));
                    //players.get(poss.get(j).toString()).printPlayer();
                }
                
                if (j==0) {
                    Player p = new Player(pname);
                    members.add(p);
                }
            }
            if (temp[i].contains("</td>")) { count++; }
        }
        
        
    }
    
    public void getTotals () {
        float total_toi = 0;
        float total_sht_pcnt = 0;
        float total_onice_sht_pcnt = 0;
        
        for (Player member : members) {
            age += member.age;
            gp += member.gp;
            goals += member.goals;
            assists += member.assists;
            points += member.points;
            plusminus += member.plusminus;
            pim += member.pim;
            sog += member.sog;
            hits += member.hits;
            blocks += member.blocks;
            fow += member.fow;
            fol += member.fol;
            esp += member.esp;
            stp += member.stp;
            ppg += member.ppg;
            ppa += member.ppa;
            shg += member.shg;
            sha += member.sha;
            gwg += member.gwg;
            total_toi += member.toi;
            total_sht_pcnt += member.sht_pcnt;
            total_onice_sht_pcnt += member.sht_pcnt;
            
            if (member.gp == 0) { rostered_spec_count++; } //create a count of zero gp, and a count of IR stashed players.!!!
        }
        
        for (int i=0; i<(rostered_spec_count-ir_count); i++){
            total_toi += 15;
            gp += 82;
        }
        
        toi = total_toi/members.size();
        sht_pcnt = total_sht_pcnt/members.size();
        onice_sht_pcnt = total_onice_sht_pcnt/members.size();
        
        float div = toi*gp/60;
        goals60 = StringCmds.round(goals/div, 3);
        assists60 = StringCmds.round(assists/div, 3);
        points60 = StringCmds.round(points/div, 3);
        sog60 = StringCmds.round(sog/div, 3);
        hits60 = StringCmds.round(hits/div, 3);
        blocks60 = StringCmds.round(blocks/div, 3);
        esp60 = StringCmds.round(esp/div, 3);
        stp60 = StringCmds.round(stp/div, 3);
        
    }
    
    public int dumpExcel(Sheet sheet, int start_row){
        int end_row = start_row;
        for (int i=0; i<members.size(); i++){
            Row row = sheet.createRow((short)end_row);
            members.get(i).dumpExcel(row, name);
            end_row++;
        }
        return end_row;
    }
    
    public void printTeam(){
        System.out.println(name);
        for (int i=0; i<members.size(); i++){
            members.get(i).printPlayer();
        }
    }
    
    public void printTotals(){
        
    }
    
    public void printAverages(){
        System.out.println( 
            "| " + name 
            + " | PlayerCount: " + members.size()
            + " | Goals/60: " + goals60 
            + " | Assists/60: " + assists60 
            + " | Points/60: " + points60 
            + " | SOG/60: " + sog60 
            + " | Hits/60: " + hits60 
            + " | Blocks/60: " + blocks60 
            + " | STP/60: " + stp60 
        );
    }
}
