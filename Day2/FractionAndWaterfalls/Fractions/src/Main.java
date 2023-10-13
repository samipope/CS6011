// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
//I tested that my exception would work and it does if you call main. it also prints out an error message saying that the denominator cannot be 0
        //i threw this exception into both of my contructors - I commented it out for the next assignment
       // Fraction f4 = new Fraction(1, 0);
        Fraction f1 = new Fraction(1,3);
        Fraction f2 = new Fraction(1,4);
        Fraction f3 = new Fraction(-1,4);
        Fraction f4 = new Fraction(1,2);
        Fraction f5 = new Fraction(2,8);
        Fraction f6 = new Fraction(7,12);
        Fraction f9 = new Fraction(1,2);
        Fraction f10 = new Fraction(1,3);

        ArrayList listOfFracs = new ArrayList<Fraction>(Arrays.asList(f1, f2,f3,f4,f5));

        Collections.sort(listOfFracs);



    }
}
