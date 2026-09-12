package training01;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Stock> stocks = new ArrayList<>();


        System.out.print("请输入股票数量：");
        int num = scanner.nextInt();

        scanner.nextLine();


        // 输入初始股票
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


        // 股票监控系统菜单
        while (true) {

            System.out.println();
            System.out.println("========== 股票监控系统 ==========");
            System.out.println("1. 查看所有股票");
            System.out.println("2. 查询股票");
            System.out.println("3. 添加股票");
            System.out.println("4. 删除股票");
            System.out.println("5. 退出");

            System.out.print("请选择：");

            int choice = scanner.nextInt();

            scanner.nextLine();


            if (choice == 1) {

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

            } else if (choice == 2) {

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

            } else if (choice == 3) {

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

            } else if (choice == 4) {

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

            } else if (choice == 5) {

                System.out.println("程序已退出");

                break;

            } else {

                System.out.println(
                        "输入有误，请重新选择"
                );
            }
        }


        scanner.close();
    }

}