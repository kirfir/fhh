/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author trevor.witjes
 */
public class Player {
    
    int jersey;
    String name;
    String position;
    String team;
    int gp, goals, assists, points, plusminus, pim, sog, hits, blocks,
        fow, fol, stp, ppg, ppa, shg, sha, gwg;
    float sht_pcnt, toi;
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
            + " | PIM: " + pim 
            + " | Hits: " + hits 
            + " | Blocks: " + blocks 
            + " | STP: " + stp      
            + " | SOG: " + sog 
            + " | S%: " + sht_pcnt   
            + " | TOI: " + toi 
            + " |"
        );
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
        toi = (toi + s.toi)/2;
    }
    
    public void printSplits (){
        for (Split split : splits) {
            split.printSplit();
        }
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