package ua.alexander.sqlcmd.view;

import java.util.Scanner;

public class Console implements View{
    public void type(String message) {
        System.out.println(message);
    }

    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
