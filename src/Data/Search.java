/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import Parse.Trie;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author trevor.witjes
 */
public class Search {
    
    public List<Player> queryPlayers (Map <String, Player> players, Trie lookup, String query){
        List<Player> result = new ArrayList <>();                      
        List poss = lookup.getWords(query.toLowerCase());

        for (int i=0; i<poss.size(); i++){
            result.add(players.get(poss.get(i).toString()));
        }
        
        return result;
    }
    
    public Player getPlayer (Map <String, Player> players, Trie lookup, String query, boolean combine) {
        Player p = new Player();
        
        List poss = lookup.getWords(query.toLowerCase());
        
                    
        return p;  
                    
                    
         
            
//                    samePlayer = false;
//                    if (j > 0){
//                        samePlayer = players.get(srch).get(j).name.equals(players.get(srch).get(j-1).name)
//                                && (players.get(srch).get(j).gp + players.get(srch).get(j-1).gp <= 82);
//                    }
//                    // if samePlayer, print name only once
//                    if (samePlayer) { players.get(srch).get(j).printStats();}
//                        else { players.get(srch).get(j).printPlayer();}
              
    }
    
    
}
