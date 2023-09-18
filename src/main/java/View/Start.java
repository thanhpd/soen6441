package main.java.view;

import java.util.Scanner;

/**
 * @author Omnia Alam
 */

public class Start {
    String input;
    Scanner sc =new Scanner(System.in);
    public void welcomeDisplay()throws InterruptedException{
        System.out.println("####### Welcom to Risk Game#####");
        System.out.println("\n1.New Game");
        System.out.println("\n2.Load map");
        System.out.println("\n3.Exit");
        System.out.println();
        input=sc.nextLine();
        switch (input) {
            case "1":
                Logger lg= new Logger();

                lg.log();
                break;
        
            default:
                System.out.println("\nYou have entered wrong input. Please enter correct input");
			    welcomeDisplay();
                break;
        }
    }
    
}
