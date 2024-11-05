package analizator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import analizator.structures.LexUnit;



public class LA {

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    Lexer lexer = new Lexer(reader);

    ArrayList<LexUnit> lexUnitList = lexer.analyse();

    for(LexUnit lexUnit : lexUnitList){
      System.out.println(lexUnit.toString());
    }
  }
}