/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import java.math.BigDecimal;

/**
 *
 * @author trevor.witjes
 */
public class StringCmds {
   
    public static String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }   
    
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    
    public static float timeToFloat (String time) {
        String temp [] = time.split(":");
        float f = Float.parseFloat(temp[0]) + Float.parseFloat(temp[1])/60;        
        return f;
    }
    
    public static String timeToString (float time) {       
        int min = (int) time;
        float sec = (time - min) * 60;
        
        String s = min + ":" + String.format("%02d", (int) sec);    
        return s;
    }
   
    
}
