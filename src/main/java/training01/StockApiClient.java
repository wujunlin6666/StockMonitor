
package training01;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class StockApiClient {

    private static final String DEMO_URL =
            "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=IBM&apikey=demo";

    private final HttpJsonClient client = new HttpJsonClient();

    public Stock fetchDemoIbm() throws IOException, InterruptedException {

        JsonNode root = client.getJson(DEMO_URL);

        return parseQuote(root);
    }


    Stock parseQuote(JsonNode root) throws IOException {

        // 把原来 fetchDemoIbm() 中
        // 获取 Global Quote、检查字段、
        // 转换价格、创建 Stock 的代码移到这里
        JsonNode quote = root.get("Global Quote");

        if (quote == null || quote.isNull()) {
            throw new IOException("API 未返回 Global Quote");
        }

        // 3. 提取股票代码
        JsonNode symbolNode = quote.get("01. symbol");

        if (symbolNode == null || symbolNode.isNull()) {
            throw new IOException("缺少股票代码");
        }

        String symbol = symbolNode.asText();

        // 4. 提取前收盘价
        JsonNode previousCloseNode = quote.get("08. previous close");

        if (previousCloseNode == null || previousCloseNode.isNull()) {
            throw new IOException("缺少前收盘价");
        }

        // 5. 提取当前价格
        JsonNode currentPriceNode = quote.get("05. price");

        if (currentPriceNode == null || currentPriceNode.isNull()) {
            throw new IOException("缺少当前价格");
        }

        // 6. 将字符串价格转换为 double
        double previousClose;
        double currentPrice;

        try {
            previousClose = Double.parseDouble(
                    previousCloseNode.asText()
            );

            currentPrice = Double.parseDouble(
                    currentPriceNode.asText()
            );
        } catch (NumberFormatException e) {
            throw new IOException("API 返回的股票价格格式不正确", e);
        }

        // 7. 检查价格是否合法
        if (!Double.isFinite(previousClose)
                || !Double.isFinite(currentPrice)
                || previousClose <= 0
                || currentPrice <= 0) {

            throw new IOException("API 返回了无效的股票价格");
        }

        return new Stock(symbol,previousClose,currentPrice);


    }

}
