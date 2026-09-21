
package training01;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockApiClientTest {

    @Test
    void testParseQuoteSuccess() throws IOException {

        String json = """
                {
                  "Global Quote": {
                    "01. symbol": "IBM",
                    "05. price": "237.4900",
                    "08. previous close": "248.3700"
                  }
                }
                """;

        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(json);

        StockApiClient api = new StockApiClient();

        // TODO 1：调用 api.parseQuote(root)，接收 Stock

        Stock stock = api.parseQuote(root);

        // TODO 2：检查 symbol 是否等于 "IBM"

        assertEquals("IBM", stock.getSymbol());


        // TODO 3：检查 previousClose 是否等于 248.37
        assertEquals(248.37, stock.getPreviousClose(), 0.0001);

        // TODO 4：检查 currentPrice 是否等于 237.49
        assertEquals(237.49, stock.getCurrentPrice(), 0.0001);
    }


    @Test
    void testParseQuoteMissingQuote() throws IOException {

        String json = "{}";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        StockApiClient api = new StockApiClient();

        // TODO：使用 assertThrows 验证
        assertThrows(IOException.class,()->api.parseQuote(root));
        // api.parseQuote(root) 会抛出 IOException
    }

    @Test
    void testParseQuoteMissingPrice() throws IOException{


        String json = """
        {
          "Global Quote": {
            "01. symbol": "IBM",
            "08. previous close": "248.3700"
          }
        }
        """;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        StockApiClient api = new StockApiClient();

        IOException exception=assertThrows(IOException.class,()->api.parseQuote(root));

        assertEquals("缺少当前价格",exception.getMessage());

    }

    @Test
    void testParseQuoteInvalidPrice() throws IOException{


        String json = """
        {
          "Global Quote": {
            "01. symbol": "IBM",
            "05. price": "abc",
            "08. previous close": "248.3700"
          }
        }
        """;
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(json);

        StockApiClient api = new StockApiClient();

        IOException exception = assertThrows(IOException.class,()->api.parseQuote(root));

        assertEquals("API 返回的股票价格格式不正确",exception.getMessage());

    }

    @Test
    void testParseSnapshotMissingTrade() throws IOException{

        String json = """
        {
          "symbol": "AAPL",
          "prevDailyBar": {
            "c": 332.49
          }
        }
        """;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode=mapper.readTree(json);
        StockApiClient client =new StockApiClient();

        IOException exception = assertThrows(
                IOException.class,
                () -> client.parseSnapshot(jsonNode)
        );

        assertEquals("API 缺少最新成交数据", exception.getMessage());    }

    @Test
    void testParseSnapshotMissingPreviousBar() throws IOException{

        String json = """
        {
          "symbol": "AAPL",
          "latestTrade": {
            "p": 337.09
          }
        }
        """;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);
        StockApiClient client =new StockApiClient();

        IOException exception = assertThrows(IOException.class,()->client.parseSnapshot(jsonNode));
        assertEquals("API 缺少前一交易日行情", exception.getMessage());
    }


    // 测试1：正常 JSON 能否正确转换成 Stock
    @Test
    void testParseSnapshotSuccess() throws IOException {

        String json = """
            {
              "symbol": "AAPL",
              "latestTrade": {
                "p": 337.09
              },
              "prevDailyBar": {
                "c": 332.49
              }
            }
            """;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        StockApiClient api = new StockApiClient();
        Stock stock = api.parseSnapshot(root);

        assertEquals("AAPL", stock.getSymbol());
        assertEquals(332.49, stock.getPreviousClose(), 0.0001);
        assertEquals(337.09, stock.getCurrentPrice(), 0.0001);
    }


    // 测试2：缺少当前价格 p
    @Test
    void testParseSnapshotMissingCurrentPrice() throws IOException {

        String json = """
            {
              "symbol": "AAPL",
              "latestTrade": {},
              "prevDailyBar": {
                "c": 332.49
              }
            }
            """;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        StockApiClient api = new StockApiClient();

        IOException exception = assertThrows(
                IOException.class,
                () -> api.parseSnapshot(root)
        );

        assertEquals("API 当前价格格式不正确", exception.getMessage());
    }


    // 测试3：当前价格不是数字
    @Test
    void testParseSnapshotInvalidCurrentPrice() throws IOException {

        String json = """
            {
              "symbol": "AAPL",
              "latestTrade": {
                "p": "abc"
              },
              "prevDailyBar": {
                "c": 332.49
              }
            }
            """;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        StockApiClient api = new StockApiClient();

        IOException exception = assertThrows(
                IOException.class,
                () -> api.parseSnapshot(root)
        );

        assertEquals("API 当前价格格式不正确", exception.getMessage());
    }


    // 测试4：前收盘价为负数
    @Test
    void testParseSnapshotNegativePreviousClose() throws IOException {

        String json = """
            {
              "symbol": "AAPL",
              "latestTrade": {
                "p": 337.09
              },
              "prevDailyBar": {
                "c": -10
              }
            }
            """;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        StockApiClient api = new StockApiClient();

        IOException exception = assertThrows(
                IOException.class,
                () -> api.parseSnapshot(root)
        );

        assertEquals("API 返回了无效的股票价格", exception.getMessage());
    }
}
