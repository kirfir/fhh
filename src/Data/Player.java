/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import static Utils.StringCmds.round;
import static Utils.StringCmds.timeToString;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author trevor.witjes
 */
public class Player {
    
    int jersey;
    String name, fh_team;
    String position;
    String team;
    int gp, goals, assists, points, plusminus, pim, sog, hits, blocks,
        fow, fol, stp, ppg, ppa, shg, sha, gwg;
    float sht_pcnt, toi, mins;
    float goals60, assists60, points60, sog60, hits60, blocks60, stp60;
    
    List<Split> splits = new ArrayList<>();
    
    //int TOI; library
    
    public void printPlayer (){
        System.out.println(
            "| " + name 
            + " | " + position 
            + " #" + jersey 
            + " | " + team 
            + " | GP: " + gp
            + " | G: " + goals 
            + " | A: " + assists 
            + " | PTS: " + points 
            + " | +/-: " + plusminus 
            + " | STP: " + stp   
            //+ " | PIM: " + pim 
            + " | SOG: " + sog 
            + " | S%: " + sht_pcnt  
            + " | Hits: " + hits 
            + " | Blocks: " + blocks     
            + " | TOI: " + timeToString(toi) 
            + " | G/60: " + goals60 
            + " | A/60: " + assists60 
            + " | PTS/60: " + points60 
            + " | STP/60: " + stp60 
            + " | SOG/60: " + sog60      
            + " | Hits/60: " + hits60 
            + " | Blocks/60: " + blocks60   
            + " |"
        );
    }
    
    public void printEmptyPlayer (String n_name){   
        System.out.println(
            "| " + n_name 
            + " | " + "0" 
            + " #" + "0" 
            + " | " + "0" 
            + " | GP: " + "0" 
            + " | G: " + "0"  
            + " | A: " + "0"  
            + " | PTS: " + "0"  
            + " | +/-: " + "0"  
            + " | STP: " + "0"  
            + " | SOG: " + "0"  
            + " | S%: " + "0.0"   
            //+ " | PIM: " + "0"  
            + " | Hits: " + "0"  
            + " | Blocks: " + "0"        
            + " | TOI: " + "0.0"  
            + " | G/60: " + "0.0" 
            + " | A/60: " + "0.0" 
            + " | PTS/60: " + "0.0" 
            + " | STP/60: " + "0.0" 
            + " | SOG/60: " + "0.0"      
            + " | Hits/60: " + "0.0" 
            + " | Blocks/60: " + "0.0"   
            + " |"
        );
    }
    
    public void add_advStats(){
        float secs = toi*60*gp;
        float div = secs/360;
        
        goals60 = round(goals/div, 3);
        assists60 = round(assists/div, 3);
        points60 = round(points/div, 3);
        stp60 = round(stp/div, 3);
        sog60 = round(sog/div, 3);
        hits60 = round(hits/div, 3);
        blocks60 = round(blocks/div, 3);
    }
    
    public void addTOI(Split s){
        if (toi < 1){
            toi = s.toi;
        } else {
            toi = round((toi + s.toi)/2, 2);
        }
        add_advStats();
    }
    
    public void addSplit(Split s){
        // add split to List<Split>
        if (splits.isEmpty()){ // if this is first split, add split for existing stats
            Split n = new Split(team, this);
            splits.add(n);
        }
        
        splits.add(s);
        
        // add to stat totals
        gp += s.gp;
        goals += s.goals;
        assists += s.assists;
        points += s.points;
        plusminus += s.plusminus;
        pim += s.pim;
        sog += s.sog;
        hits += s.hits;
        blocks += s.blocks;
        fow += s.fow;
        fol += s.fol;
        stp += s.stp;
        ppg += s.ppg;
        ppa += s.ppa;
        shg += s.shg;
        sha += s.sha;
        gwg += s.gwg;
        sht_pcnt = (sht_pcnt + s.sht_pcnt)/2;
        //toi = (toi + s.toi)/2;
    }
    
    public void printSplits (){
        System.out.println(name);
        for (Split split : splits) {
            split.printSplit();
        }
    }
    
    public void dumpExcel (Row row, String fh_name){
        row.createCell(1).setCellValue(fh_name); 
        row.createCell(2).setCellValue(name); 
        row.createCell(3).setCellValue(position); 
        row.createCell(4).setCellValue(gp); 
        row.createCell(5).setCellValue(goals); 
        row.createCell(6).setCellValue(assists); 
        row.createCell(7).setCellValue(points); 
        row.createCell(8).setCellValue(plusminus); 
        row.createCell(9).setCellValue(stp); 
        row.createCell(10).setCellValue(sog); 
        row.createCell(11).setCellValue(sht_pcnt); 
        row.createCell(12).setCellValue(hits); 
        row.createCell(13).setCellValue(blocks); 
        row.createCell(14).setCellValue(timeToString(toi)); 
        row.createCell(15).setCellValue(goals60); 
        row.createCell(16).setCellValue(assists60); 
        row.createCell(17).setCellValue(points60); 
        row.createCell(18).setCellValue(stp60); 
        row.createCell(19).setCellValue(sog60); 
        row.createCell(20).setCellValue(hits60); 
        row.createCell(21).setCellValue(blocks60);         
    }
    
    public boolean matches(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        if (!name.contains(other.name.substring(3)) || this.goals != other.goals || this.sog != this.sog)
            return false;
        return true;
    }
    
    
//    public int getHashCode (String name_in) {
//        int hash = 1;
//        hash = 13 * hash + name_in.hashCode();
//        return hash;
//    }
//    
//    @Override
//    public int hashCode() {
//        int hash = 1;
//        hash = 13 * hash + name.hashCode();
//        return hash;
//    }
//    
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        Player other = (Player) obj;
//        if (!name.equals(other.name))
//            return false;
//        return true;
//    }

}