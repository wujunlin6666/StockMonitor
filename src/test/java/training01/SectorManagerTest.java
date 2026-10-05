package training01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SectorManagerTest {

    @Test
    void testAddSearchAndRemoveSector() {

        SectorManager manager = new SectorManager();

        Sector aiSector = new Sector("AI算力");

        manager.addSector(aiSector);

        // 添加后应该能找到
        assertSame(
                aiSector,
                manager.searchSector("AI算力")
        );

        // 应该只有一个板块
        assertEquals(
                1,
                manager.getSectors().size()
        );

        // 删除应该成功
        assertTrue(
                manager.removeSector("AI算力")
        );

        // 删除后应该找不到
        assertNull(
                manager.searchSector("AI算力")
        );

        // 再删一次，因为已经不存在，所以应该失败
        assertFalse(
                manager.removeSector("AI算力")
        );
    }

    @Test
    void testDuplicateSector() {

        SectorManager manager = new SectorManager();

        manager.addSector(new Sector("AI算力"));

        assertThrows(
                IllegalArgumentException.class,
                () -> manager.addSector(new Sector("AI算力"))
        );
    }
}