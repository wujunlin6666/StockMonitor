package training01;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner =
                new Scanner(System.in);


        StockManager manager = new StockManager();

        Menu menu = new Menu(scanner,manager);

        InputUtil.inputInitialStocks(
                scanner,
                manager
        );


        menu.start();


        scanner.close();
    }
}