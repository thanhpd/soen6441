package com.w10.risk_game.view;

import java.util.ArrayList;
import java.util.Scanner;

public class Logger {
    Scanner sc;
    ShowMap mapviewer;
    String input;
    String fileName;
    ArrayList<String> inputLogList;

    public Logger() {
        sc = new Scanner(System.in);
        mapviewer = new ShowMap();
    }

    public void log() {
        System.out.println("\n Enter the commands");
        input = sc.nextLine();

    }
}
