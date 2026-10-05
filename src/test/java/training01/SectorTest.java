package training01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SectorTest {

    @Test
    void testTotalWeightAndWeightValid() {

        Sector sector = new Sector("AI算力");

        sector.addComponent("NVDA", 0.5);
        sector.addComponent("AMD", 0.3);
        sector.addComponent("AVGO", 0.2);

        assertEquals(
                1.0,
                sector.getTotalWeight(),
                0.00001
        );

        assertTrue(sector.isWeightValid());
    }


    @Test
    void testDuplicateComponent() {

        Sector sector = new Sector("AI算力");

        sector.addComponent("NVDA", 0.5);

        assertThrows(
                IllegalArgumentException.class,
                () -> sector.addComponent("NVDA", 0.3)
        );
    }


    @Test
    void testCalculateChangePercent() {

        Sector sector = new Sector("AI算力");

        sector.addComponent("NVDA", 0.5);
        sector.addComponent("AMD", 0.3);
        sector.addComponent("AVGO", 0.2);

        StockManager manager = new StockManager();

        manager.addStock(
                new Stock("NVDA", 100, 104)
        );

        manager.addStock(
                new Stock("AMD", 100, 102)
        );

        manager.addStock(
                new Stock("AVGO", 100, 99)
        );

        double result =
                sector.calculateChangePercent(manager);

        assertEquals(
                2.4,
                result,
                0.00001
        );
    }


    @Test
    void testMissingStockData() {

        Sector sector = new Sector("AI算力");

        sector.addComponent("NVDA", 0.5);
        sector.addComponent("AMD", 0.3);
        sector.addComponent("AVGO", 0.2);

        StockManager manager = new StockManager();

        manager.addStock(
                new Stock("NVDA", 100, 104)
        );

        manager.addStock(
                new Stock("AMD", 100, 102)
        );

        // 故意不添加 AVGO

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> sector.calculateChangePercent(manager)
                );

        assertEquals(
                "缺少股票数据：AVGO",
                exception.getMessage()
        );
    }
}