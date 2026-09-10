package training01;

import java.util.Scanner;

public class Main {

    public static double calculateChange(
            double currentPrice,
            double previousClose
    ){

        return currentPrice - previousClose;

    }


    public static double calculatePercent(
            double change,
            double previousClose
    ){

        return change / previousClose * 100;

    }


    public static String getStatus(
            double currentPrice,
            double previousClose
    ){

        if(currentPrice > previousClose){

            return "上涨";

        }else if(currentPrice < previousClose){

            return "下跌";

        }else{

            return "平盘";

        }

    }


    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);


        Stock stock = new Stock();


        System.out.print("请输入股票代码：");
        stock.symbol = scanner.nextLine();


        System.out.print("请输入昨日价格：");
        stock.previousClose = scanner.nextDouble();


        System.out.print("请输入当前价格：");
        stock.currentPrice = scanner.nextDouble();



        double change =
                calculateChange(
                        stock.currentPrice,
                        stock.previousClose
                );


        double percent =
                calculatePercent(
                        change,
                        stock.previousClose
                );


        System.out.println();

        System.out.println("========== 股票行情 ==========");

        System.out.println("股票：" + stock.symbol);

        System.out.println("涨跌额：" + change);

        System.out.println("涨跌幅：" + percent + "%");

        System.out.println(
                "状态：" +
                getStatus(
                        stock.currentPrice,
                        stock.previousClose
                )
        );


        scanner.close();

    }
}