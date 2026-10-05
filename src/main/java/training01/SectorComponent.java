package training01;

public class SectorComponent {

    private String symbol;
    private double weight;

    public SectorComponent(String symbol, double weight){
        this.symbol=symbol;
        this.weight=weight;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getWeight() {
        return weight;
    }


}
