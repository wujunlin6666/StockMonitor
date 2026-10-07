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

    public double calculateContribution(Stock stock){
        if(stock.getSymbol().trim().equalsIgnoreCase(symbol.trim())){
            return stock.getChangePercent()*weight;
        }else {
            throw new IllegalArgumentException(
                    "股票代码与板块成分股不匹配"
            );
        }
    }

}
