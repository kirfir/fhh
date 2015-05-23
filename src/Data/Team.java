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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author trevor.witjes
 */
public class Team {
    private String name;
    private List<Player> members = new ArrayList<>();
    
    public void fillTeam(Map <String, Player> players, Trie lookup, String input){      
        name = input.substring(input.indexOf("\">") + 2, input.indexOf("</a>"));
        System.out.println(name);
        
        String [] temp = input.split("class=\\\"hand");

        for (int i=1; i<temp.length; i++){
            Player p = new Player();
            String pname = temp[i].substring(3, temp[i].indexOf("</"));
            if (!pname.contains("<")){
                pname = pname.split(", ")[1].concat(" " + pname.split(",")[0]);
                int j;
                List poss = lookup.getWords(pname.toLowerCase());
                for (j=0; j<poss.size(); j++){
                    players.get(poss.get(j).toString()).fh_team = name;
                    players.get(poss.get(j).toString()).printPlayer();
                }
                
                if (j==0) p.printEmptyPlayer(pname);
            }
            
            // NEED TO BE ABLE TO SEARCH HERE
        }
    }
    
    public int dumpExcel(Sheet sheet, int start_row){
        int end_row = start_row;
        for (int i=0; i<members.size(); i++){
            Row row = sheet.createRow((short)start_row);
            row.createCell(2).setCellValue("test"); 
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
}
