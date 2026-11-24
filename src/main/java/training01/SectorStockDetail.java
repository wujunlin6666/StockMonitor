package training01;

public class SectorStockDetail {
    private Stock stock;
    private double weight;

    public SectorStockDetail(
            Stock stock,
            double weight
    ){
        this.stock=stock;
        this.weight=weight;
    }

    public Stock getStock(){
        return stock;
    }

    public double getWeight(){
        return weight;
    }
}
