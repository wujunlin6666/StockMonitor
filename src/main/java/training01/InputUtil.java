package training01;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUtil {

    public static void inputInitialStocks(
            Scanner scanner,
            StockManager manager
    ) {

        int num = InputUtil.readPositiveInt(
                scanner,
                "请输入股票数量："
        );


        for (int i = 0; i < num; i++) {

            System.out.println();

            System.out.println(
                    "----- 第 " + (i + 1) + " 只股票 -----"
            );


            String symbol = InputUtil.readStockSymbol(scanner,manager);


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


            Stock stock = new Stock(
                    symbol,
                    previousClose,
                    currentPrice
            );


            manager.addStock(stock);
        }
    }

    public static int readInt(
            Scanner scanner,
            String prompt
    ) {

        while (true) {

            System.out.print(prompt);

            try {

                int value = scanner.nextInt();

                // 清除回车
                scanner.nextLine();

                return value;

            } catch (InputMismatchException e) {

                scanner.nextLine();

                System.out.println(
                        "请输入正确的整数"
                );
            }
        }
    }

    public static double readDouble(
            Scanner scanner,
            String prompt
    ) {

        while (true) {

            System.out.print(prompt);

            try {

                double value = scanner.nextDouble();

                // 吃掉输入数字后留下的回车
                scanner.nextLine();

                return value;

            } catch (InputMismatchException e) {

                // 清除错误输入，例如 hello
                scanner.nextLine();

                System.out.println("请输入正确的数字");
            }
        }
    }

    public static double readPositiveDouble(
            Scanner scanner,
            String prompt
    ) {

        while (true) {

            double value = readDouble(
                    scanner,
                    prompt
            );

            if (value > 0) {

                return value;
            }

            System.out.println(
                    "价格必须大于 0"
            );
        }
    }

    public static int readPositiveInt(
            Scanner scanner,
            String prompt
    ) {
        while (true){
            int value = readInt(scanner,prompt);

            if(value>0){
                return value;
            }
            System.out.println("股票数量必须大于 0");
        }


    }

    public static String readStockSymbol(
            Scanner scanner,
            StockManager manager
    ){

        while(true){

            System.out.print("请输入股票代码：");

            String symbol = scanner.nextLine().trim();


            if(symbol.isEmpty()){

                System.out.println(
                        "股票代码不能为空"
                );

                continue;
            }


            Stock stock =
                    manager.searchStock(symbol);


            if(stock != null){

                System.out.println(
                        "该股票已经存在"
                );

                continue;
            }


            return symbol;
        }
    }


}