/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import static Utils.StringCmds.round;
import static Utils.StringCmds.timeToString;
import java.io.Serializable;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author trevor.witjes
 */
public class Player implements Serializable{
    
    int jersey;
    String name, fh_team, year;
    String position;
    String team;
    int age, exp;
    int gp, goals, assists, points, plusminus, pim, sog, hits, blocks,
        fow, fol, esp, stp, ppg, ppa, shg, sha, gwg;
    float sht_pcnt, toi, mins;
    float goals60, assists60, points60, sog60, hits60, blocks60, esp60, stp60;
    float onice_sht_pcnt;
    
    String BTN_index; // behindthenet index
    
    //List<Split> splits = new ArrayList<>();
    String temp[];
    
    //int TOI; library
    
    public Player(){
        
    }
    
    public Player (String pname){
        name = pname;
    }
       
    // -- PARSING FUNCTIONS --
    public void nhlnumbers (String input){
        temp = input.split("<td>");
        
        String pdata[] = temp[0].split(":");        
        name = pdata[4].substring(pdata[4].indexOf(">")+1, pdata[4].indexOf("</")).split(", ")[1] 
             + " " 
             + pdata[4].substring(pdata[4].indexOf(">")+1, pdata[4].indexOf("</")).split(", ")[0];
        fixname();
        BTN_index = name.toLowerCase().replaceAll("[^A-Za-z]", "");
                
        jersey = NumberUtils.toInt(pdata[1].substring(1, pdata[1].indexOf("&")));
        
        position = pdata[3].substring(1, 2);
        age = NumberUtils.toInt(pdata[4].substring(1, pdata[4].indexOf("&")));
        
        if (!temp[1].contains("unknown")) {
            team = temp[1].substring(temp[1].indexOf(">")+1, temp[1].indexOf("</"));
        }            
        
        gp = NumberUtils.toInt(temp[2].substring(1, temp[2].indexOf("<")-1));
        goals = NumberUtils.toInt(temp[3].substring(1, temp[3].indexOf("<")-1));
        assists = NumberUtils.toInt(temp[4].substring(1, temp[4].indexOf("<")-1));
        points = NumberUtils.toInt(temp[5].substring(1, temp[5].indexOf("<")-1));
        plusminus = NumberUtils.toInt(temp[6].substring(1, temp[6].indexOf("<")-1));
        pim = NumberUtils.toInt(temp[7].substring(1, temp[7].indexOf("<")-1));
        hits = NumberUtils.toInt(temp[8].substring(1, temp[8].indexOf("<")-1));
        blocks = NumberUtils.toInt(temp[9].substring(1, temp[9].indexOf("<")-1));
        ppg = NumberUtils.toInt(temp[10].substring(1, temp[10].indexOf("<")-1));
        ppa = NumberUtils.toInt(temp[11].substring(1, temp[11].indexOf("<")-1));
        shg = NumberUtils.toInt(temp[12].substring(1, temp[12].indexOf("<")-1));
        sha = NumberUtils.toInt(temp[13].substring(1, temp[13].indexOf("<")-1));
        stp = ppg + ppa + shg + sha;
        esp = points - stp;
        gwg = NumberUtils.toInt(temp[14].substring(1, temp[14].indexOf("<")-1));
        sog = NumberUtils.toInt(temp[15].substring(1, temp[15].indexOf("<")-1));
        sht_pcnt = round(Float.parseFloat(temp[16].substring(1, temp[16].indexOf("<")-1))*100, 2);
    }
    
    public void sportingcharts (String input){
        temp = input.split("\">");
        
        //div = Float.parseFloat(temp[5].substring(0, temp[5].indexOf("<")).replace(",", ""));
        toi = Float.parseFloat(temp[6].substring(0, temp[6].indexOf("<")));
 
        float div = toi*gp/60;
        
        goals60 = round(goals/div, 3);
        assists60 = round(assists/div, 3);
        points60 = round(points/div, 3);
        stp60 = round(stp/div, 3);
        esp60 = round(esp/div, 3);
        sog60 = round(sog/div, 3);
        hits60 = round(hits/div, 3);
        blocks60 = round(blocks/div, 3);
    }
     
    public void behindthenet (String input){
        temp = input.split("\">");
        //System.out.println(name + " -- " + temp[9].substring(0, temp[9].indexOf("<")-1));
        
        if (temp[9].substring(0, temp[9].indexOf("<")-1).contains("-") || temp[9].substring(0, temp[9].indexOf("<")-1).isEmpty()){
            onice_sht_pcnt = 0;
        } else {
            onice_sht_pcnt = Float.parseFloat(temp[9].substring(0, temp[9].indexOf("<")-1));
        }
        
//        if (Integer.parseInt(year) == 2015 && age < 28 && gp > 20 && points60 > 2.0 && onice_sht_pcnt < 7.5){
//            printPlayer();
//        }
    }
    
    public void fixname (){ // Silfverberg, Flynn, 
        switch (name) {
            case "Daniel Girardi":
                name = "Dan Girardi";
                break;
            case "Tobias Enstrom":
                name = "Toby Enstrom";
                break;
            case "Nikolai Kulemin":
                name = "Nikolay Kulemin";
                break;
            case "Maxime Talbot":
                name = "Max Talbot";
                break;
            case "Mathew Dumba":
                name = "Matt Dumba";
                break;
            case "Dan Paille":
                name = "Daniel Paille";
                break;
            case "Alexander Khokhlachev":
                name = "Alex Khokhlachev";
                break;
        }
    }
    
    // -----------------------
    
     
    // -- PRINTING FUNCTIONS --
    public void printPlayer (){
        System.out.println(
            "| " + year 
            + " | " + name 
            + " | " + position 
            + " #" + jersey 
            + " | " + team 
            + " | Age: " + age 
            //+ " | Exp: " + exp 
            + " | GP: " + gp
            + " | G: " + goals 
            + " | A: " + assists 
            + " | PTS: " + points 
            + " | +/-: " + plusminus 
            + " | STP: " + stp   
            //+ " | PIM: " + pim 
            + " | SOG: " + sog 
            + " | S%: " + sht_pcnt  
            + " | OI S%: " + onice_sht_pcnt
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
            + " | Age: " + "0" 
            //+ " | Exp: " + "0" 
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
    // ------------------------
    
    public void add_advStats(){
        float secs = toi*60*gp/3600;
        float div = secs/3600;
        
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
    
//    public void addSplit(Split s){
//        // add split to List<Split>
//        if (splits.isEmpty()){ // if this is first split, add split for existing stats
//            Split n = new Split(team, this);
//            splits.add(n);
//        }
//        
//        splits.add(s);
//        
//        // add to stat totals
//        gp += s.gp;
//        goals += s.goals;
//        assists += s.assists;
//        points += s.points;
//        plusminus += s.plusminus;
//        pim += s.pim;
//        sog += s.sog;
//        hits += s.hits;
//        blocks += s.blocks;
//        fow += s.fow;
//        fol += s.fol;
//        stp += s.stp;
//        ppg += s.ppg;
//        ppa += s.ppa;
//        shg += s.shg;
//        sha += s.sha;
//        gwg += s.gwg;
//        sht_pcnt = (sht_pcnt + s.sht_pcnt)/2;
//        //toi = (toi + s.toi)/2;
//    }
//    
//    public void printSplits (){
//        System.out.println(name);
//        for (Split split : splits) {
//            split.printSplit();
//        }
//    }
    
    public void dumpExcel (Row row, String fh_name){
        row.createCell(0).setCellValue(fh_name); 
        row.createCell(1).setCellValue(name); 
        row.createCell(2).setCellValue(position); 
        row.createCell(3).setCellValue(age); 
        row.createCell(4).setCellValue(exp); 
        row.createCell(5).setCellValue(gp); 
        row.createCell(6).setCellValue(goals); 
        row.createCell(7).setCellValue(assists); 
        row.createCell(8).setCellValue(points); 
        row.createCell(9).setCellValue(plusminus); 
        row.createCell(10).setCellValue(stp); 
        row.createCell(11).setCellValue(sog); 
        row.createCell(12).setCellValue(sht_pcnt); 
        row.createCell(13).setCellValue(hits); 
        row.createCell(14).setCellValue(blocks); 
        row.createCell(15).setCellValue(timeToString(toi)); 
        row.createCell(16).setCellValue(goals60); 
        row.createCell(17).setCellValue(assists60); 
        row.createCell(18).setCellValue(points60); 
        row.createCell(19).setCellValue(stp60); 
        row.createCell(20).setCellValue(sog60); 
        row.createCell(21).setCellValue(hits60); 
        row.createCell(22).setCellValue(blocks60);         
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
    
}