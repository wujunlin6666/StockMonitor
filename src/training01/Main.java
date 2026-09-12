package training01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void inputInitialStocks(
            Scanner scanner,
            List<Stock> stocks
    ) {

        System.out.print("请输入股票数量：");
        int num = scanner.nextInt();

        scanner.nextLine();

        for (int i = 0; i < num; i++) {

            System.out.print("请输入股票代码：");
            String symbol = scanner.nextLine();

            System.out.print("请输入昨日价格：");
            double previousClose = scanner.nextDouble();

            System.out.print("请输入当前价格：");
            double currentPrice = scanner.nextDouble();

            Stock stock = new Stock(
                    symbol,
                    previousClose,
                    currentPrice
            );

            stocks.add(stock);

            scanner.nextLine();
        }
    }


    public static void showAllStocks(
            List<Stock> stocks
    ) {

        for (Stock stock : stocks) {

            System.out.println();
            System.out.println("========== 股票行情 ==========");

            System.out.println(
                    "股票：" + stock.getSymbol()
            );

            System.out.println(
                    "涨跌额：" + stock.getChange()
            );

            System.out.println(
                    "涨跌幅：" +
                            stock.getChangePercent() +
                            "%"
            );

            System.out.println(
                    "状态：" + stock.getStatus()
            );
        }
    }


    public static void searchStock(
            Scanner scanner,
            List<Stock> stocks
    ) {

        System.out.print(
                "请输入要查询的股票代码："
        );

        String search = scanner.nextLine();

        boolean found = false;

        for (Stock stock : stocks) {

            if (
                    stock.getSymbol()
                            .equalsIgnoreCase(search)
            ) {

                System.out.println();
                System.out.println(
                        "========== 查询结果 =========="
                );

                System.out.println(
                        "股票：" + stock.getSymbol()
                );

                System.out.println(
                        "涨跌额：" + stock.getChange()
                );

                System.out.println(
                        "涨跌幅：" +
                                stock.getChangePercent() +
                                "%"
                );

                System.out.println(
                        "状态：" + stock.getStatus()
                );

                found = true;

                break;
            }
        }

        if (!found) {
            System.out.println("未找到该股票");
        }
    }


    public static void addStock(
            Scanner scanner,
            List<Stock> stocks
    ) {

        System.out.print("请输入股票代码：");
        String symbol = scanner.nextLine();

        System.out.print("请输入昨日价格：");
        double previousClose = scanner.nextDouble();

        System.out.print("请输入当前价格：");
        double currentPrice = scanner.nextDouble();

        Stock stock = new Stock(
                symbol,
                previousClose,
                currentPrice
        );

        stocks.add(stock);

        scanner.nextLine();

        System.out.println(
                "股票 " +
                        stock.getSymbol() +
                        " 添加成功！"
        );
    }


    public static void deleteStock(
            Scanner scanner,
            List<Stock> stocks
    ) {

        System.out.print(
                "请输入要删除的股票代码："
        );

        String target = scanner.nextLine();

        boolean found = false;

        for (int i = 0; i < stocks.size(); i++) {

            Stock stock = stocks.get(i);

            if (
                    stock.getSymbol()
                            .equalsIgnoreCase(target)
            ) {

                stocks.remove(i);

                System.out.println(
                        "股票 " +
                                stock.getSymbol() +
                                " 删除成功！"
                );

                found = true;

                break;
            }
        }

        if (!found) {
            System.out.println("未找到该股票");
        }
    }


    public static void sortBySymbol(
            List<Stock> stocks
    ) {

        stocks.sort((stock1, stock2) -> {
            return stock1.getSymbol()
                    .compareToIgnoreCase(
                            stock2.getSymbol()
                    );
        });
    }


    public static void sortByCurrentPrice(
            List<Stock> stocks
    ) {

        stocks.sort((stock1, stock2) -> {
            return Double.compare(
                    stock2.getCurrentPrice(),
                    stock1.getCurrentPrice()
            );
        });
    }


    public static void sortByChangePercent(
            List<Stock> stocks
    ) {

        stocks.sort((stock1, stock2) -> {
            return Double.compare(
                    stock2.getChangePercent(),
                    stock1.getChangePercent()
            );
        });
    }


    public static void runMenu(
            Scanner scanner,
            List<Stock> stocks
    ) {

        while (true) {

            System.out.println();
            System.out.println("========== 股票监控系统 ==========");
            System.out.println("1. 查看所有股票");
            System.out.println("2. 查询股票");
            System.out.println("3. 添加股票");
            System.out.println("4. 删除股票");
            System.out.println("5. 按股票代码排序");
            System.out.println("6. 按当前价格从高到低排序");
            System.out.println("7. 按涨跌幅从高到低排序");
            System.out.println("8. 退出");

            System.out.print("请选择：");

            int choice = scanner.nextInt();

            scanner.nextLine();

            if (choice == 1) {

                showAllStocks(stocks);

            } else if (choice == 2) {

                searchStock(scanner, stocks);

            } else if (choice == 3) {

                addStock(scanner, stocks);

            } else if (choice == 4) {

                deleteStock(scanner, stocks);

            } else if (choice == 5) {

                sortBySymbol(stocks);

                System.out.println(
                        "已按股票代码排序"
                );

            } else if (choice == 6) {

                sortByCurrentPrice(stocks);

                System.out.println(
                        "已按当前价格从高到低排序"
                );

            } else if (choice == 7) {

                sortByChangePercent(stocks);

                System.out.println(
                        "已按涨跌幅从高到低排序"
                );

            } else if (choice == 8) {

                System.out.println("程序已退出");

                break;

            } else {

                System.out.println(
                        "输入有误，请重新选择"
                );
            }
        }
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        List<Stock> stocks = new ArrayList<>();

        inputInitialStocks(scanner, stocks);

        runMenu(scanner, stocks);

        scanner.close();
    }
}