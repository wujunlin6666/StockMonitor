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


        for (Stock stock : stocks) {

            System.out.println();
            System.out.println("========== 股票行情 ==========");

            System.out.println("股票：" + stock.getSymbol());
            System.out.println("涨跌额：" + stock.getChange());
            System.out.println("涨跌幅：" + stock.getChangePercent() + "%");
            System.out.println("状态：" + stock.getStatus());
        }


        scanner.close();
    }

}