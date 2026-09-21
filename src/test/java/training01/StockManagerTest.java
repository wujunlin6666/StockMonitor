
package training01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StockManagerTest {

    @Test
    void testAddStock() {
        StockManager manager = new StockManager();

        manager.addStock("BB", 100.0, 120.0);

        assertEquals(1, manager.getStocks().size());
    }

    @Test
    void testSearchStock() {
        StockManager manager = new StockManager();

        manager.addStock("AA", 100.0, 120.0);

        assertNotNull(manager.searchStock("AA"));
    }

    @Test
    void testDeleteStock() {
        StockManager manager = new StockManager();

        manager.addStock("CC", 100.0, 120.0);

        assertTrue(manager.deleteStock("CC"));
        assertEquals(0, manager.getStocks().size());
    }

    @Test
    void testSearchNotFound() {
        StockManager manager = new StockManager();

        manager.addStock("CC", 100.0, 120.0);

        assertNull(manager.searchStock("TSLA"));
    }

    @Test
    void testDeleteNotFound() {
        StockManager manager = new StockManager();

        manager.addStock("CC", 100.0, 120.0);

        assertFalse(manager.deleteStock("TSLA"));
        assertEquals(1, manager.getStocks().size());
    }

    @Test
    void testUpdateStock() {
        StockManager manager = new StockManager();

        Stock oldStock = new Stock("AAPL", 100, 120);
        Stock newStock = new Stock("AAPL", 337.09, 335.73);

        manager.addStock(oldStock);

        boolean updated = manager.updateStock(newStock);

        assertTrue(updated);
        assertEquals(1, manager.getStocks().size());
        assertSame(newStock, manager.searchStock("AAPL"));

        Stock missing = new Stock("TSLA", 300, 310);

        assertFalse(manager.updateStock(missing));
        assertEquals(1, manager.getStocks().size());
        assertSame(newStock, manager.searchStock("AAPL"));
    }


}
