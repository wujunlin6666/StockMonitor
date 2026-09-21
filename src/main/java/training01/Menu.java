package training01;

import java.io.IOException;
import java.util.Scanner;


public class Menu{

    private Scanner scanner;

    private StockManager manager;

    public Menu(Scanner scanner,StockManager manager){
        this.scanner=scanner;
        this.manager=manager;
    }

    public void displayStock(
            Stock stock
    ){

        System.out.println();

        System.out.println(
                "========== 股票行情 =========="
        );


        System.out.println(
                "股票：" + stock.getSymbol()
        );



        System.out.printf("涨跌额：%.2f%n", stock.getChange());

        System.out.printf("涨跌幅：%.2f%%%n", stock.getChangePercent());
        System.out.printf("前收盘价：%.2f%n", stock.getPreviousClose());
        System.out.printf("当前价格：%.2f%n", stock.getCurrentPrice());



        System.out.println(
                "状态：" + stock.getStatus()
        );
    }

    public void showAllStocks(){

        for(Stock stock : manager.getStocks()){

            displayStock(stock);

        }

    }

    public void start() {

        while (true) {

            System.out.println();

            System.out.println(
                    "========== 股票监控系统 =========="
            );

            System.out.println(
                    "1. 查看所有股票"
            );

            System.out.println(
                    "2. 查询股票"
            );

            System.out.println(
                    "3. 添加股票"
            );

            System.out.println(
                    "4. 删除股票"
            );

            System.out.println(
                    "5. 按股票代码排序"
            );

            System.out.println(
                    "6. 按当前价格从高到低排序"
            );

            System.out.println(
                    "7. 按涨跌幅从高到低排序"
            );

            System.out.println("8. 从 Alpaca 获取股票");
            System.out.println("9. 退出");
            System.out.println("10. 刷新指定股票行情");


            int choice = InputUtil.readInt(
                    scanner,
                    "请选择："
            );


            if (choice == 1) {

                showAllStocks();

            } else if (choice == 2) {

                System.out.print(
                        "请输入要查询的股票代码："
                );


                String search =
                        scanner.nextLine().trim();

                Stock stock=manager.searchStock(search);

                if(stock!=null){
                    displayStock(stock);
                }else {
                    System.out.println(
                            "未找到股票"
                    );
                }

            } else if (choice == 3) {

                String symbol=InputUtil.readStockSymbol(scanner,manager);

                double previousClose =
                        InputUtil.readPositiveDouble(
                                scanner,
                                "请输入昨日价格："
                        );


                double currentPrice =
                        InputUtil.readPositiveDouble(
                                scanner,
                                "请输入当前价格："
                        );

                manager.addStock(symbol,previousClose,currentPrice);

            } else if (choice == 4) {

                System.out.print("请输入股票代码：");
                String symbol = scanner.nextLine();

                boolean result =
                        manager.deleteStock(symbol);


                if(result){

                    System.out.println(
                            "删除成功"
                    );

                }else{

                    System.out.println(
                            "未找到股票"
                    );

                }
            } else if (choice == 5) {

                manager.sortBySymbol();


                System.out.println(
                        "已按股票代码排序"
                );

            } else if (choice == 6) {

                manager.sortByCurrentPrice();


                System.out.println(
                        "已按当前价格从高到低排序"
                );

            } else if (choice == 7) {

                manager.sortByChangePercent();


                System.out.println(
                        "已按涨跌幅从高到低排序"
                );

            } else if (choice == 8) {
                System.out.println("请输入股票代码：");
                String symbol = scanner.nextLine().trim().toUpperCase();

                Stock existingStock = manager.searchStock(symbol);


                if (existingStock == null) {

                    try {
                        StockApiClient client = new StockApiClient();


                        Stock stock = client.fetchStock(symbol);

                        manager.addStock(stock);
                        displayStock(stock);

                        System.out.println("已添加到自选列表");

                    } catch (IOException e) {
                        System.out.println("获取股票失败：" + e.getMessage());

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("股票请求被中断");
                        return;
                    }

                } else {

                    System.out.println("该股票已存在于自选列表");

                }
            }else if(choice==9){

                System.out.println("程序已退出");
                break;
            }else if(choice==10){
                System.out.print("请输入要刷新的股票代码：");

                String symbol=scanner.nextLine().trim().toUpperCase();
                Stock oldStock=manager.searchStock(symbol);
                if (oldStock == null) {
                    System.out.println("自选列表中没有这只股票");
                    continue;
                }

                try {
                    StockApiClient client=new StockApiClient();

                    Stock newStock=client.fetchStock(symbol);
                    boolean updated = manager.updateStock(newStock);
                    if (updated) {
                        displayStock(newStock);
                        System.out.println("刷新成功");
                    } else {
                        System.out.println("刷新失败：未找到原股票");
                    }
                }catch (IOException e){
                    System.out.println("刷新失败：" + e.getMessage());
                }catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("股票请求被中断");
                    return;
                }
            }
        }
    }

}
