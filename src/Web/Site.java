/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author trevor.witjes
 */
public class Site { 
    
    private URL link;
    public String sitename;
    
    public Site (String sitename) {
        this.sitename = sitename;
    }
    
    public int connect () {
        try { link = new URL (sitename); }
        catch (MalformedURLException mue) { 
            System.out.println("URL Error: " + mue);
            return 1; 
        }
        return 0;
    }
    
    public int connect (String sname) {
        try { link = new URL (sitename); }
        catch (MalformedURLException mue) { 
            System.out.println("URL Error: " + mue);
            return 1; 
        }
        return 0;
    }
    
    //URL myURL = new URL("http://example.com/pages/");
    //URL page1URL = new URL(myURL, "page1.html");
    
    
    public String read () throws IOException {
        URLConnection con = link.openConnection();
        InputStream is = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            builder.append(line);
        }
        
        return builder.toString();    
    } 
    
    public String read (String stop) throws IOException {
        URLConnection con = link.openConnection();
        InputStream is = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        StringBuilder builder = new StringBuilder();
        String line;
        while (((line = br.readLine()) != null) && !(line.contains(stop))) { //
            builder.append(line);
        }
        
        return builder.toString();    
    } 
    
}
