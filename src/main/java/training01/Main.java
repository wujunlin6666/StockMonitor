package training01;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {



        Scanner scanner =
                new Scanner(System.in);


        StockManager manager = new StockManager();

        SectorManager sectorManager = new SectorManager();

        StockFileManager fileManager = new StockFileManager("stock.txt");

        fileManager.loadStocks(manager);

        Sector aiSector = new Sector("AI算力");

        aiSector.addComponent("NVDA", 0.5);
        aiSector.addComponent("AMD", 0.3);
        aiSector.addComponent("AVGO", 0.2);

        sectorManager.addSector(aiSector);

        Menu menu = new Menu(scanner,manager,sectorManager);

        menu.start();

        fileManager.saveStocks(manager);

        scanner.close();
    }
}