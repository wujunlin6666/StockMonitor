package training01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StockSortingTest{

    private StockManager  manager;

    @BeforeEach
    void setup(){

        manager = new StockManager();

        manager.addStock("TSLA", 200.0, 180.0);
        manager.addStock("NVDA", 50.0, 75.0);
        manager.addStock("AAPL", 100.0, 110.0);
    }

    @Test
    void testSortBySymbol() {

        manager.sortBySymbol();

        assertEquals("AAPL", manager.getStocks().get(0).getSymbol());
        assertEquals("NVDA", manager.getStocks().get(1).getSymbol());
        assertEquals("TSLA", manager.getStocks().get(2).getSymbol());
    }

    @Test
    void testSortByCurrentPrice() {

        manager.sortByCurrentPrice();

        assertEquals("TSLA", manager.getStocks().get(0).getSymbol());
        assertEquals("AAPL", manager.getStocks().get(1).getSymbol());
        assertEquals("NVDA", manager.getStocks().get(2).getSymbol());
    }

    @Test
    void testSortByChangePercent() {

        manager.sortByChangePercent();

        assertEquals("NVDA", manager.getStocks().get(0).getSymbol());
        assertEquals("AAPL", manager.getStocks().get(1).getSymbol());
        assertEquals("TSLA", manager.getStocks().get(2).getSymbol());
    }
}