package training01;

public class Stock {

    private String symbol;
    private double previousClose;
    private double currentPrice;


    public Stock(
            String symbol,
            double previousClose,
            double currentPrice
    ) {
        this.symbol = symbol;
        this.previousClose = previousClose;
        this.currentPrice = currentPrice;
    }


    public String getSymbol() {
        return symbol;
    }


    public double getPreviousClose() {
        return previousClose;
    }


    public double getCurrentPrice() {
        return currentPrice;
    }


    public double getChange() {
        return currentPrice - previousClose;
    }


    public double getChangePercent() {
        return getChange() / previousClose * 100;
    }


    public String getStatus() {

        if (currentPrice > previousClose) {
            return "上涨";
        } else if (currentPrice < previousClose) {
            return "下跌";
        } else {
            return "平盘";
        }

    }

}