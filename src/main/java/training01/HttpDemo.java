
package training01;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class HttpDemo {

    public static void main(String[] args)
            throws IOException, InterruptedException {

        // 1. 从环境变量读取密钥
        String apiKey = System.getenv("ALPACA_API_KEY_ID");
        String secretKey = System.getenv("ALPACA_API_SECRET_KEY");

        // 2. 检查密钥是否存在
        if (apiKey == null || apiKey.isBlank()
                || secretKey == null || secretKey.isBlank()) {

            System.out.println("请先配置 Alpaca 环境变量");
            return;
        }

        // 3. AAPL 行情快照接口
        String url =
                "https://data.alpaca.markets/v2/stocks/AAPL/snapshot?feed=iex";

        // 4. 发送带认证信息的请求
        StockApiClient api = new StockApiClient();

        Stock stock = api.fetchStock("AAPL");

        System.out.println(stock.getSymbol());
        System.out.println(stock.getPreviousClose());
        System.out.println(stock.getCurrentPrice());
        System.out.println(stock.getStatus());

    }
}
