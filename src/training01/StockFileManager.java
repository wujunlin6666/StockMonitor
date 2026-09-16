package training01;

import java.io.*;


public class StockFileManager {

    private String filename;

    public StockFileManager(String filename){

        this.filename = filename;

    }

    public void saveStocks(
            StockManager manager
    ){

        try(            BufferedWriter writer = new BufferedWriter(new FileWriter(filename))
        ) {


            for(Stock stock:manager.getStocks()){


                String line = stock.getSymbol()+","+stock.getPreviousClose()+","+stock.getCurrentPrice();

                writer.write(line);

                writer.newLine();

            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void loadStocks(StockManager manager) {

        try (
                BufferedReader reader =
                        new BufferedReader(new FileReader(filename))
        ) {

            String line = reader.readLine();

            while (line != null) {

                String[] parts = line.split(",");

                if (parts.length != 3) {
                    System.out.println("数据格式错误，已跳过：" + line);

                    line = reader.readLine();
                    continue;
                }

                String symbol = parts[0].trim();
                String previousCloseText = parts[1];
                String currentPriceText = parts[2];

                if (symbol.isEmpty()) {
                    System.out.println("股票代码为空，已跳过：" + line);

                    line = reader.readLine();
                    continue;
                }

                try {
                    double previousClose =
                            Double.parseDouble(previousCloseText);

                    double currentPrice =
                            Double.parseDouble(currentPriceText);

                    if (previousClose <= 0 || currentPrice <= 0) {
                        System.out.println("股票价格非法，已跳过：" + line);

                        line = reader.readLine();
                        continue;
                    }

                    manager.addStock(
                            symbol,
                            previousClose,
                            currentPrice
                    );

                } catch (NumberFormatException e) {
                    System.out.println(
                            "价格格式错误，已跳过：" + line
                    );
                }

                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println(
                    "未找到历史数据文件，将以空数据启动。"
            );

        } catch (IOException e) {
            System.out.println(
                    "读取文件失败：" + e.getMessage()
            );
        }
    }


}