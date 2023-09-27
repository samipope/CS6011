import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("hello world!");


        ArrayList<Integer> myArray = new ArrayList<Integer>();
      for(int i=0; i <= 10;i++ ){
          myArray.add(1);
      }
      System.out.println(myArray);

        int sum = 0;

      for(int i=0; i<myArray.size();i++ ){
          sum += myArray.get(i);
      }

      System.out.println(sum);

        System.out.print("Put in your age");
        Scanner s = new Scanner(System.in);
        int userAge = s.nextInt();

        if (userAge >=80){
            System.out.print("You are part of the greatest generation");
        }
        else if (userAge >=60) {
            System.out.print("You are part of the baby boomer generation");
        }
        else if (userAge >=40){
            System.out.print("You are part of generation x");
        }
        else if (userAge >=20){
            System.out.print("You are part of the millenial generation");
        }
        else {
            System.out.print("You are an iKid");
        }






    }
}