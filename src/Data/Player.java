/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import java.util.Arrays;

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
    float sht_pcnt;
    String toi;

    
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
    
    public void printStats (){
        System.out.println(
            "               " 
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