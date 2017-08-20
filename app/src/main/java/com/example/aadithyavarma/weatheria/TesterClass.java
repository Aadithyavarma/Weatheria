package com.example.aadithyavarma.weatheria;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aadithya Varma on 8/9/2017.
 */

public class TesterClass {

    /**
     * Checks if the location contains only alphabets.
     * @param loc
     * @return
     */
    public boolean isValidLocation(String loc){
        Pattern p = Pattern.compile("[a-zA-Z]+\\s+[a-zA-Z]+");
        Matcher m = p.matcher(loc);
        if(m.matches())
        return true;
        return false;
    }

    /**
     * Check if the place name is valid;
     * @param name
     * @return
     */
    public boolean isValidName(String name){
        Pattern p = Pattern.compile("[a-zA-Z]+\\s+[a-zA-Z]+");
        Matcher m = p.matcher(name);
        if(m.matches())
            return true;
        return false;
    }
}
