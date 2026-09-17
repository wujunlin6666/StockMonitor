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

            System.out.println("8. 获取 IBM 演示行情");
            System.out.println("9. 退出");


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

                System.out.println(
                        "获取 IBM 演示行情"
                );




                Stock target=manager.searchStock("IBM");

                if(target != null){
                    System.out.println("IBM 已存在，暂不重复添加");
                }else {
                    try {
                        StockApiClient api = new StockApiClient();

                        Stock stock = api.fetchDemoIbm();

                        manager.addStock(stock);
                        System.out.println("IBM 添加成功");
                        displayStock(stock);

                    }catch (IOException e){
                        System.out.println("获取行情失败：" + e.getMessage());
                    }catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                        System.out.println("行情请求被中断");
                        break;
                    }
                }




            } else if(choice==9){

                System.out.println("程序已退出");
                break;
            }else {
                System.out.println("输入有误，请重新选择");
            }
        }
    }

}
