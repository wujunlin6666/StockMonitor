package training01;

import java.util.ArrayList;
import java.util.List;

public class SectorManager {

    private List<Sector> sectors;

    public SectorManager() {
        this.sectors = new ArrayList<>();
    }

    public List<Sector> getSectors() {
        return sectors;
    }

    public void addSector(Sector sector) {

        if (sector == null) {
            throw new IllegalArgumentException("板块不能为空");
        }

        if (searchSector(sector.getName()) != null) {
            throw new IllegalArgumentException(
                    "板块已存在：" + sector.getName()
            );
        }

        sectors.add(sector);
    }

    public Sector searchSector(String name) {

        for (Sector sector : sectors) {
            if (name.trim().equalsIgnoreCase(sector.getName().trim())) {
                return sector;
            }
        }

        return null;
    }

    public boolean removeSector(String name){

        Sector sector = searchSector(name);

        if(sector == null){
            return false;
        }

        sectors.remove(sector);
        return true;

    }
}