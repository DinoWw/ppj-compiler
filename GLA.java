import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;

import structures.GeneratorRule;

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
      ArrayList<GeneratorRule> rules = new ArrayList<GeneratorRule>();
      while(null != (line = reader.readLine()) && line.length() != 0){

         // line format : <State>regex
         String[] parts = line.split(">", 0);
         GeneratorRule rule = new GeneratorRule();
         rule.stateFrom = parts[0].substring(1);
         rule.regex = parts[1];
         
         line = reader.readLine();
         if(!line.startsWith("{")){
            throw new IOException();
         }

         line = reader.readLine();
         // line format: - or lexUnit
         rule.lexClass = line;

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
         for(GeneratorRule rule : rules){
            rule.regex = rule.regex.replace(r1.getKey(), '('+r1.getValue()+')');
         }
      }

      // System.err.println(rules.toString());
      // System.err.println(regexes.entrySet().toString());

      generateStateEnum(states);
      generateLexClassEnum(lexUnits);
      generateRules(rules);
      
   }


   // GENERATE FILE analizator/generated/State.java
   private static void generateStateEnum(String[] states){
      StringBuilder code = new StringBuilder()
      .append("package analizator.generated;\n")
      .append("public enum State{\n");

      for (int i = 1; i< states.length; i++){
          if (i!=1) code.append(",");
          code.append(states[i]);
      }
      code.append(";");
      code.append("\n}");

      try {
          FileWriter file = new FileWriter("analizator/generated/State.java", false); // false - overwrite
          BufferedWriter output = new BufferedWriter(file);
          output.write(code.toString());
          output.close();
      }

      catch (IOException e) {
          System.err.println("couldnt make State.java");
      } 
  }   

   // GENERATE FILE analizator/generated/LexClass.java
   private static void generateLexClassEnum(String[] lexUnits){
      StringBuilder code = new StringBuilder()
      .append("package analizator.generated;\n")
      .append("public enum LexClass{\n");

      for (int i = 1; i< lexUnits.length; i++){
          if (i!=1) code.append(",");
          code.append(lexUnits[i]);
      }
      code.append(";");
      code.append("\n}");

      try {
          FileWriter file = new FileWriter("analizator/generated/LexClass.java", false); // false - overwrite
          BufferedWriter output = new BufferedWriter(file);
          output.write(code.toString());
          output.close();
      }

      catch (IOException e) {
          System.err.println("couldnt make LexClass.java");
      } 
  }   

   // GENERATE FILE analizator/generated/Rules.java
   private static void generateRules(ArrayList<GeneratorRule> rules){
      StringBuilder code = new StringBuilder()
      .append("package analizator.generated;\n")
      .append("import analizator.generated.State;\n")
      .append("import analizator.generated.LexClass;\n")
      .append("import analizator.structures.Rule;\n")
      .append("import analizator.structures.Automat;\n")
      .append("import java.util.EnumMap;\n")
      .append("import java.util.Map;\n")
      .append("public class Rules{\n")
      .append("public static Map<State, Rule[]> getRules(){\n")
      .append("Map<State, Rule[]> tmp = new EnumMap<>(State.class);");
      
      

      // unsure if works
      Map<String, ArrayList<GeneratorRule>> ruleMap = new HashMap<>();
      for (GeneratorRule grule : rules){
          if(ruleMap.get(grule.stateFrom) == null){
            ruleMap.put(grule.stateFrom, new ArrayList<>());
          }
          ruleMap.get(grule.stateFrom).add(grule);
      }

      for(ArrayList<GeneratorRule> gruleArr : ruleMap.values()){
         code.append(String.format("tmp.put( State.%s, new Rule[] {", gruleArr.get(0).stateFrom));
         for(GeneratorRule grule : gruleArr){
            code.append(String.format("%s,\n", grule.toNewRule()));
         }
         code.append("});\n");
      }

      code.append("return tmp;\n};\n}");

      try {
          FileWriter file = new FileWriter("analizator/generated/Rules.java", false); // false - overwrite
          BufferedWriter output = new BufferedWriter(file);
          output.write(code.toString());
          output.close();
      }
      catch (IOException e) {
         System.err.println("Generator error: couldn't make State.java");;
      } 
  }   

  
 
 }
