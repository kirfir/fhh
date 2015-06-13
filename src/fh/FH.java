/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fh;

import Data.Database;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author trevor.witjes
 */
public class FH {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException, FileNotFoundException, ClassNotFoundException {
        
//        Site neulion = new Site("https://ca.sports.yahoo.com/nhl/teams/ott/roster/");
//        neulion.connect();
//        System.out.println(neulion.read());

        Database rfhl = new Database ();
        rfhl.populate();
        rfhl.serialize();
        
        rfhl.deserialize();
        rfhl.listen();
        
    }
    
}
