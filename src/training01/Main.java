package training01;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner =
                new Scanner(System.in);


        StockManager manager = new StockManager();

        StockFileManager fileManager = new StockFileManager("stock.txt");

        fileManager.loadStocks(manager);

        Menu menu = new Menu(scanner,manager);

        menu.start();

        fileManager.saveStocks(manager);


        scanner.close();
    }
}