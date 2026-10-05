package training01;

import java.util.ArrayList;
import java.util.List;

public class Sector {

    private String name;
    private List<SectorComponent> components;

    public Sector(String name) {
        this.name = name;
        this.components = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<SectorComponent> getComponents() {
        return components;
    }

    public void addComponent(String symbol,double weight){
        if (weight <= 0 || weight > 1) {
            throw new IllegalArgumentException("权重必须大于0且不超过1");
        }

        if (containsComponent(symbol)) {
            throw new IllegalArgumentException("该股票已经存在于板块中");
        }

        SectorComponent sectorComponent =
                new SectorComponent(symbol, weight);

        components.add(sectorComponent);

    }

    public boolean containsComponent(String symbol) {

        // 遍历 components
        // 如果某个 component.getSymbol()
        // 和 symbol 忽略大小写相同
        // return true
        for(SectorComponent sectorComponent:components){
            if(sectorComponent.getSymbol().trim().equalsIgnoreCase(symbol.trim())){
                return true;
            }

        }

        return false;

    }

    public double getTotalWeight(){

        double total = 0.0;

        for(SectorComponent sectorComponent:components){

            total = total + sectorComponent.getWeight();

        }

        return total;

    }

    public boolean isWeightValid() {
        // 判断总权重是否接近 1
        return Math.abs(getTotalWeight() - 1.0) < 0.00001;

    }

    public double calculateChangePercent(StockManager manager){
         double result = 0;
         for(SectorComponent sectorComponent : components){

             Stock stock=manager.searchStock(sectorComponent.getSymbol());

             if (stock == null) {
                 throw new IllegalStateException(
                         "缺少股票数据：" + sectorComponent.getSymbol()
                 );
             }
             result = result + stock.getChangePercent() * sectorComponent.getWeight();


             }

         return result;


         }
    }