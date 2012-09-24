package kdrama;

import java.io.*;
import java.util.*;

public class Sort {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/Azira/new1.txt"));
        Map<String, String> map=new TreeMap<String, String>();
        String line="";
        while((line=reader.readLine())!=null){
                map.put(getField(line),line);
        }
        reader.close();
        FileWriter writer = new FileWriter("/Users/Azira/sort.txt");
        for(String val : map.values()){
                writer.write(val);      
                writer.write('\n');
        }
        writer.close();
    }

    private static String getField(String line) {
        return line.split(" ")[0];//extract value you want to sort on
    }
}