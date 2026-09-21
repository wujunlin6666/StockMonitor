package training01;

import java.util.ArrayList;
import java.util.List;

public class StockManager {

    private List<Stock> stocks;

    public StockManager(){

        stocks = new ArrayList<>();
    }

    public void addStock(
            String symbol,
            double previousClose,
            double currentPrice
    ){

        Stock stock =
                new Stock(
                        symbol,
                        previousClose,
                        currentPrice
                );

        stocks.add(stock);

    }

    public void addStock(Stock stock){

        stocks.add(stock);

    }

    public List<Stock> getStocks(){

        return stocks;

    }

    public boolean deleteStock(String symbol
            ) {



        for (int i = 0; i < stocks.size(); i++) {

            Stock stock =
                    stocks.get(i);


            if (
                    stock.getSymbol()
                            .equalsIgnoreCase(symbol)
            ) {

                stocks.remove(i);


                return true;

            }
        }

        return false;
    }


    public Stock searchStock(String target){

        for(Stock stock: stocks){

            if(stock.getSymbol()
                    .equalsIgnoreCase(target)){

                return stock;

            }
        }

        return null;
    }

    public void sortBySymbol(){

        stocks.sort((stock1,stock2)->{

            return stock1
                    .getSymbol()
                    .compareToIgnoreCase(
                            stock2.getSymbol()
                    );
        });
    }


    public void sortByCurrentPrice(){

        stocks.sort((stock1,stock2)->{

            return Double.compare(
                    stock2.getCurrentPrice(),
                    stock1.getCurrentPrice()
            );

        });
    }

    public void sortByChangePercent(){

        stocks.sort((stock1,stock2)->{

            return Double.compare(
                    stock2.getChangePercent(),
                    stock1.getChangePercent()
            );

        });
    }

    public boolean updateStock(Stock newStock) {

        Stock targetstock = searchStock(newStock.getSymbol().trim());
        if(targetstock==null){
            return false;
        }else {
            int index=stocks.indexOf(targetstock);
            stocks.set(index,newStock);
            return true;
        }




    }
}