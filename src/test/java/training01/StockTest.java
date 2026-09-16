
package training01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockTest {

    @Test
    void testGetChange() {

        // 在这里完成测试

        Stock stock = new Stock("aa", 100.0, 120.0);

        assertEquals(20,stock.getChange());


    }

    @Test
    void testRisingStock(){
        Stock stock = new Stock("bb", 100.0, 120.0);

        assertEquals(20,stock.getChange());

        assertEquals("上涨",stock.getStatus());


    }

    @Test
    void testFallingStock(){
        Stock stock = new Stock("cc", 100.0, 80.0);

        assertEquals(-20,stock.getChange());

        assertEquals("下跌",stock.getStatus());


    }

    @Test
    void testFlatStock(){
        Stock stock = new Stock("bb", 100.0, 100.0);

        assertEquals(0,stock.getChange());

        assertEquals("平盘",stock.getStatus());


    }
}
