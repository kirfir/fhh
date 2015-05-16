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
public class Team {
    private String name;
    private List<Player> members = new ArrayList<>();
    
    public void fillTeam(String input){      
        name = input.substring(input.indexOf("\">") + 2, input.indexOf("</a>"));
        System.out.println(name);
        
        String [] temp = input.split("stopBubble(event)");

        for (int i=0; i<temp.length; i++){
            Player p = new Player();
            // NEED TO BE ABLE TO SEARCH HERE
        }
    }
    
 
}
