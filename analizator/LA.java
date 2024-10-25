package analizator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import analizator.generated.State;
import analizator.structures.Automat;
import analizator.structures.LexUnit;
import analizator.structures.Rule;



public class LA {

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    Lexer lexer = new Lexer(reader);

    Automat a = new Automat("#\\|");

    System.out.println(a.doTransition('#'));
    System.out.println(a.isAccepted());
    System.out.println(a.doTransition('|'));
    System.out.println(a.isAccepted());

    //ArrayList<LexUnit> lexUnitList = lexer.analyse();

    // for(LexUnit lexUnit : lexUnitList){
    //   System.out.println(lexUnit.toString());
    // }
  }
}