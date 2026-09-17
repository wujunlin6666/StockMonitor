
package training01;

import java.io.IOException;

public class HttpDemo {

    public static void main(String[] args)
            throws IOException, InterruptedException {

        // 1. 从 API 获取 Stock 对象
        StockApiClient api = new StockApiClient();
        Stock stock = api.fetchDemoIbm();

        // 2. 打印股票信息
        System.out.println(stock.getSymbol());
        System.out.println(stock.getPreviousClose());
        System.out.println(stock.getCurrentPrice());
        System.out.println(stock.getStatus());

        // 3. 加入股票管理器
        StockManager manager = new StockManager();
        manager.addStock(stock);

        // 4. 查询股票并使用返回值
        Stock found = manager.searchStock("IBM");

        if (found != null) {
            System.out.println("查询成功：" + found.getSymbol());
            System.out.println("股票状态：" + found.getStatus());
        } else {
            System.out.println("未找到 IBM");
        }
    }
}
