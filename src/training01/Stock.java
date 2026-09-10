package training01;


public class Stock {


    private String symbol;

    private double previousClose;

    private double currentPrice;



    public Stock(String symbol,
                 double previousClose,
                 double currentPrice){

        this.symbol = symbol;

        this.previousClose = previousClose;

        this.currentPrice = currentPrice;

    }

    public double getCurrentPrice(){

        return currentPrice;

    }
    public double getPreviousClose(){

        return previousClose;

    }
    public String getSymbol(){

        return symbol;

    }

}