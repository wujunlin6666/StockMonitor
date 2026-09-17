
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


}
