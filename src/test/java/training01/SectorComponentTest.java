package training01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SectorComponentTest {

    @Test
    void shouldCalculateContributionCorrectly() {

        SectorComponent component =
                new SectorComponent("NVDA", 0.5);

        // 100 -> 104，涨幅 = 4%
        Stock stock =
                new Stock("NVDA", 100.0, 104.0);

        double result =
                component.calculateContribution(stock);

        // 4% × 0.5 = 2%
        assertEquals(2.0, result, 0.00001);
    }

    @Test
    void shouldCalculateNegativeContributionCorrectly() {

        SectorComponent component =
                new SectorComponent("AVGO", 0.2);

        // 100 -> 95，跌幅 = -5%
        Stock stock =
                new Stock("AVGO", 100.0, 95.0);

        double result =
                component.calculateContribution(stock);

        // -5% × 0.2 = -1%
        assertEquals(-1.0, result, 0.00001);
    }

    @Test
    void shouldThrowExceptionWhenStockSymbolDoesNotMatch() {

        SectorComponent component =
                new SectorComponent("NVDA", 0.5);

        Stock stock =
                new Stock("AMD", 100.0, 104.0);

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> component.calculateContribution(stock)
                );

        assertEquals(
                "股票代码与板块成分股不匹配",
                exception.getMessage()
        );
    }
}