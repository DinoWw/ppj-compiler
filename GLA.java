import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import structures.Rule;

public class GLA {

   private static final String PREFIX_STATE = "%X";
   private static final String PREFIX_LEX_UNIT = "%L";
   public static void main(String[] args) throws IOException {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));



      // REGEX 
      Map<String, String> regexes = new HashMap<String, String>();
      String line = reader.readLine();
      while(!line.startsWith(PREFIX_STATE)){

         String[] pair = line.split(" ");

         // regexes.put(pair[0].substring(1, pair[0].length()-1), pair[1]);
         regexes.put(pair[0], pair[1]);

         line = reader.readLine();
      }

      // STATES
      String[] states = line.split(" ", 0);  // sve osim prvog elementa

      // LEX UNITS
      if(!(line = reader.readLine()).startsWith(PREFIX_LEX_UNIT)){
         throw new IOException();
      };
      String[] lexUnits = line.split(" ", 0);  // sve osim prvog elementa


      // RULES
      ArrayList<Rule> rules = new ArrayList<Rule>();
      while(null != (line = reader.readLine()) && line.length() != 0){

         // line format : <State>regex
         String[] parts = line.split(">", 0);
         Rule rule = new Rule();
         rule.stateFrom = parts[0].substring(1);
         rule.regex = parts[1];
         
         line = reader.readLine();
         if(!line.startsWith("{")){
            throw new IOException();
         }

         line = reader.readLine();
         // line format: - or lexUnit
         rule.lexUnit = line;

         while(!(line = reader.readLine()).startsWith("}")){
            if(line.startsWith("NOVI_REDAK")){
               rule.newLine = true;
            }
            else if(line.startsWith("UDJI_U_STANJE")){
               rule.stateTo = line.split(" ", 2)[1];
            }
            else if(line.startsWith("VRATI_SE")){
               rule.goBack = Integer.parseInt(line.split(" ", 2)[1]);
            }
         }

         rules.add(rule);


      }

      // DISTRIBUTE REGEXES
      for(Map.Entry<String, String> r1 : regexes.entrySet()){
         for(Map.Entry<String, String> r2 : regexes.entrySet()){
            r2.setValue(r2.getValue().replace(r1.getKey(), '('+r1.getValue()+')'));
         }
      }
      for(Map.Entry<String, String> r1 : regexes.entrySet()){
         for(Rule rule : rules){
            rule.regex = rule.regex.replace(r1.getKey(), '('+r1.getValue()+')');
         }
      }
      System.err.println(rules.toString());
      System.err.println(regexes.entrySet().toString());
         
   }









 
 }

