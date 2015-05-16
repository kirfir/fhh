/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

/**
 *
 * @author trevor.witjes
 */
public class Split {
    String name, playername;
    int gp, goals, assists, points, plusminus, pim, sog, hits, blocks,
        fow, fol, stp, ppg, ppa, shg, sha, gwg;
    float sht_pcnt, toi;
    
    public Split (String s_name, Player p) {
        name = s_name;
        playername = p.name;
        gp = p.gp;
        goals = p.goals;
        assists = p.assists;
        points = p.points;
        plusminus = p.plusminus;
        pim = p.pim;
        sog = p.sog;
        hits = p.hits;
        blocks = p.blocks;
        fow = p.fow;
        fol = p.fol;
        stp = p.stp;
        ppg = p.ppg;
        ppa = p.ppa;
        shg = p.shg;
        sha = p.sha;
        gwg = p.gwg;
        sht_pcnt = p.sht_pcnt;
        toi = p.toi;
    }
    
    public void printSplit (){
        System.out.println(
            "| " + name 
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
        Player other = (Player) obj;
        if (!playername.contains(other.name.substring(3)) || this.goals != other.goals || this.sog != other.sog)
            return false;
        return true;
    }
}
