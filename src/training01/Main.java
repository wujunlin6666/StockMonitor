package training01;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static double calculateChange(
            double currentPrice,
            double previousClose
    ) {

        return currentPrice - previousClose;

    }


    public static double calculatePercent(
            double change,
            double previousClose
    ) {

        return change / previousClose * 100;

    }


    public static String getStatus(
            double currentPrice,
            double previousClose
    ) {

        if (currentPrice > previousClose) {

            return "上涨";

        } else if (currentPrice < previousClose) {

            return "下跌";

        } else {

            return "平盘";

        }

    }


    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);


        // 创建股票列表
        ArrayList<Stock> stocks = new ArrayList<>();


        System.out.print("请输入股票数量：");
        int num = scanner.nextInt();

        // 清除nextInt留下的换行
        scanner.nextLine();



        // 第一阶段：输入并保存股票
        for (int i = 0; i < num; i++) {


            System.out.print("请输入股票代码：");
            String symbol = scanner.nextLine();


            System.out.print("请输入昨日价格：");
            double previousClose = scanner.nextDouble();


            System.out.print("请输入当前价格：");
            double currentPrice = scanner.nextDouble();


            // 创建股票对象
            Stock stock = new Stock(
                    symbol,
                    previousClose,
                    currentPrice
            );


            // 加入列表
            stocks.add(stock);


            scanner.nextLine();

        }



        // 第二阶段：遍历股票并输出
        for (Stock stock : stocks) {


            double change = calculateChange(
                    stock.getCurrentPrice(),
                    stock.getPreviousClose()
            );


            double percent = calculatePercent(
                    change,
                    stock.getPreviousClose()
            );


            System.out.println();

            System.out.println("========== 股票行情 ==========");


            System.out.println(
                    "股票：" + stock.getSymbol()
            );


            System.out.println(
                    "涨跌额：" + change
            );


            System.out.println(
                    "涨跌幅：" + percent + "%"
            );


            System.out.println(
                    "状态：" +
                            getStatus(
                                    stock.getCurrentPrice(),
                                    stock.getPreviousClose()
                            )
            );

        }


        scanner.close();

    }

}