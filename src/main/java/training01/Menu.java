package training01;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;


public class Menu{

    private Scanner scanner;
    private Connection connection;

    public Menu(Scanner scanner,Connection connection){
        this.scanner=scanner;
        this.connection=connection;
    }



    private boolean refreshSectorStocks(List<SectorStockDetail> details) {

        StockApiClient client = new StockApiClient();

        boolean allSuccess = true;

        StockRepository repository=new StockRepository();

        for (SectorStockDetail detail : details) {
            String symbol=detail.getStock().getSymbol();
            try{
                Stock latestStock = client.fetchStock(symbol);
                int updatedRows = repository.updateQuote(connection, latestStock);
                if(updatedRows==0){
                    allSuccess=false;
                    System.out.println(symbol + " 未能保存最新行情。");
                }
            }catch (IOException | SQLException e){
                System.out.println(symbol + " 刷新失败：" + e.getMessage());
                allSuccess=false;
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(symbol + " 刷新被中断。");
                return false;
            }
        }
        return allSuccess;
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

    public void showAllStocks(List<Stock> stocks){

        for(Stock stock : stocks){

            displayStock(stock);

        }

    }

    public void start() throws SQLException {

        while (true) {

            System.out.println();

            System.out.println(
                    "========== 股票监控系统 =========="
            );

            System.out.println("1. 查看所有股票");
            System.out.println("2. 查询股票");
            System.out.println("3. 添加股票");
            System.out.println("4. 删除股票");
            System.out.println("5. 按股票代码排序");
            System.out.println("6. 按当前价格从高到低排序");
            System.out.println("7. 按涨跌幅从高到低排序");
            System.out.println("8. 从 Alpaca 获取股票");
            System.out.println("9. 退出");
            System.out.println("10. 刷新指定股票行情");
            System.out.println("11. 查看全部板块");
            System.out.println("12. 刷新板块行情");
            System.out.println("13. 查看板块详情");
            System.out.println("14. 更正已保存的股票价格");

            int choice = InputUtil.readInt(
                    scanner,
                    "请选择："
            );


            if (choice == 1) {
                StockRepository repository=new StockRepository();
                List<Stock> stocks=repository.findAll(connection,choice);
                showAllStocks(stocks);

            } else if (choice == 2) {

                System.out.print(
                        "请输入要查询的股票代码："
                );


                String search =
                        scanner.nextLine().trim();

                StockRepository repository= new StockRepository();
                Stock stock=repository.findBySymbol(connection,search);

                if(stock!=null){
                    displayStock(stock);
                }else {
                    System.out.println(
                            "未找到股票"
                    );
                }

            } else if (choice == 3) {

                System.out.print("请输入要添加的股票代码（例如 AMD、NVDA）：");
                String symbol=scanner.nextLine().trim();
                StockRepository repository=new StockRepository();
                Stock stock=repository.findBySymbol(connection,symbol);
                if(stock==null){
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
                    int insertedRows=repository.insert(connection,symbol,previousClose,currentPrice);
                    if(insertedRows==1){
                        System.out.println("添加成功，已保存到自选列表。");
                    }
                }else {
                    System.out.println("该股票已在自选列表中，无需重复添加。");
                }

            } else if (choice == 4) {

                System.out.print("请输入股票代码：");
                String symbol = scanner.nextLine();

                StockRepository repository=new StockRepository();
                try {
                    int deleteRows = repository.delete(connection, symbol);
                    if(deleteRows==1){
                        System.out.println("删除成功");
                    }else{
                        System.out.println("未找到股票");
                    }
                } catch (SQLException e) {
                    if(e.getErrorCode()==1451){
                        System.out.println("该股票仍属于某个板块，请先将它移出板块再删除。");

                    }else {
                        System.out.println("删除失败，请稍后重试。");
                    }
                }

            } else if (choice == 5) {

                StockRepository repository=new StockRepository();
                List<Stock> stocks= repository.findAll(connection,choice);
                showAllStocks(stocks);

                System.out.println(
                        "已按股票代码排序"
                );

            } else if (choice == 6) {

                StockRepository repository=new StockRepository();
                List<Stock> stocks= repository.findAll(connection,choice);
                showAllStocks(stocks);
                System.out.println(
                        "已按当前价格从高到低排序"
                );

            } else if (choice == 7) {

                StockRepository repository=new StockRepository();
                List<Stock> stocks= repository.findAll(connection,choice);
                showAllStocks(stocks);

                System.out.println(
                        "已按涨跌幅从高到低排序"
                );

            } else if (choice == 8) {
                System.out.println("请输入股票代码：");
                String symbol = scanner.nextLine().trim().toUpperCase();

                StockRepository repository=new StockRepository();
                Stock existingStock=repository.findBySymbol(connection,symbol);
                if(existingStock==null){

                        try {
                            StockApiClient client = new StockApiClient();


                            Stock stock = client.fetchStock(symbol);

                            int addedRows=repository.insert(connection,stock.getSymbol(),stock.getPreviousClose(),stock.getCurrentPrice());
                            if(addedRows==1){
                                displayStock(stock);
                                System.out.println("已添加到自选列表");

                            }


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
                StockRepository repository=new StockRepository();
                try {
                    StockApiClient client=new StockApiClient();

                    Stock newStock=client.fetchStock(symbol);
                    int updatedRows=repository.updateQuote(connection,newStock);
                    if (updatedRows == 0) {
                        System.out.println("自选列表中没有这只股票");
                        continue;
                    }
                    displayStock(newStock);
                    System.out.println("行情刷新成功");
                }catch (IOException e){
                    System.out.println("刷新失败：" + e.getMessage());
                }catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("股票请求被中断");
                    return;
                }
            } else if (choice == 11) {

                SectorRepository sectorRepository=new SectorRepository();
                List<String> names=sectorRepository.findAllNames(connection);
                for (String name:names){
                    System.out.println("板块：" + name);
                }
            } else if (choice == 12) {

                System.out.print("请输入板块名称：");
                String sectorName = scanner.nextLine().trim();

                SectorRepository repository=new SectorRepository();
                List<SectorStockDetail> details =
                        repository.findComponentsBySectorName(connection, sectorName);

                if (details.isEmpty()) {
                    System.out.println("没有找到可刷新的板块成分。");
                } else {
                    double totalWeight=0.0;
                    for (SectorStockDetail detail:details){
                        totalWeight=totalWeight+detail.getWeight();
                    }
                    if (Math.abs(totalWeight - 1.0) > 0.000001) {
                        System.out.println("板块成分权重合计不是 100%，无法刷新。");
                        continue;
                    }
                    boolean refresh = refreshSectorStocks(details);

                    if (!refresh) {
                        System.out.println("板块数据刷新不完整");

                    } else {
                        List<SectorStockDetail> updatedDetails =
                                repository.findComponentsBySectorName(connection, sectorName);

                        double changePercent=0.0;
                        for(SectorStockDetail detail:updatedDetails){
                            double contribution = detail.getStock().getChangePercent() * detail.getWeight();
                            changePercent=changePercent+contribution;
                        }
                        System.out.printf(
                                "板块 %s 涨跌幅：%.2f%%%n",
                                sectorName,
                                changePercent
                        );
                    }
                }
            } else if (choice == 13) {

                System.out.print("请输入板块名称：");
                double totalChangePercent = 0.0;
                String name = scanner.nextLine().trim();
                SectorRepository repository=new SectorRepository();
                List<SectorStockDetail> details=repository.findComponentsBySectorName(connection,name);

                for(SectorStockDetail detail:details){
                    displayStock(detail.getStock());
                    System.out.printf("权重：%.0f%%%n", detail.getWeight() * 100);
                    double contribution = detail.getStock().getChangePercent() * detail.getWeight();
                    totalChangePercent=totalChangePercent+contribution;
                    System.out.printf("对板块的贡献：%+.2f 个百分点%n", contribution);
                }
                System.out.printf("板块 %s 总涨跌幅：%+.2f%%%n", name, totalChangePercent);

            }

            else if(choice==14){
                System.out.print("请输入要更正的股票代码：");
                String symbol=scanner.nextLine().trim();

                double cur = InputUtil.readPositiveDouble(scanner, "请输入新的当前价格：");
                StockRepository repository=new StockRepository();
                int updatedRows=repository.update(connection,symbol,cur);
                if(updatedRows==1){
                    System.out.println("价格更正成功，已保存到数据库。");
                }else {
                    System.out.println("没有更新到股票记录，请检查股票代码。");
                }
            }
        }
    }

}
