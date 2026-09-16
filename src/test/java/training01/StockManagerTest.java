
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


}
