package training01;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockRepository {
    public Stock findBySymbol(Connection connection, String target)
            throws SQLException {
        String sql = """
        SELECT symbol, previous_close, current_price
        FROM stocks
        where symbol=?
        ORDER BY symbol
        """;
        try (PreparedStatement statement = connection.prepareStatement(sql);
        ) {

            statement.setString(1,target);
            try(ResultSet resultSet=statement.executeQuery()){
                while (resultSet.next()) {
                    String symbol = resultSet.getString("symbol");
                    double previousClose=resultSet.getDouble("previous_close");
                    double currentPrice=resultSet.getDouble("current_price");

                    Stock stock=new Stock(symbol,previousClose,currentPrice);
                    return stock;

                }

                return null;

            }
        }
    }
    public int insert(Connection connection, String sym, double pre, double cur)
            throws SQLException{
        String insertSql = """
        INSERT INTO stocks (symbol, previous_close, current_price)
        VALUES (?,?,?)
        """;
        try (PreparedStatement insertStatement =
                     connection.prepareStatement(insertSql)){
            insertStatement.setString(1,sym);
            insertStatement.setDouble(2,pre);
            insertStatement.setDouble(3,cur);

            int affectedRows = insertStatement.executeUpdate();
            return affectedRows;
        }

    }

    public int update(Connection connection, String sym, double cur)
            throws SQLException{

        String updateSql = """
        UPDATE stocks
        SET current_price = ?
        WHERE symbol = ?
        """;
        try (PreparedStatement updateStatement =
                     connection.prepareStatement(updateSql)) {

            updateStatement.setDouble(1,cur);
            updateStatement.setString(2,sym);

            int affectedRows=updateStatement.executeUpdate();
            return affectedRows;
        }
    }

    public int delete(Connection connection, String sym)
            throws SQLException{

        String deleteSql= """
        DELETE FROM stocks
        where symbol=?         
        """;
        try (PreparedStatement deleteStatement =
                     connection.prepareStatement(deleteSql)) {
            deleteStatement.setString(1,sym);
            int affectedRows= deleteStatement.executeUpdate();
            return affectedRows;

        }

    }

    public List<Stock> findAll(Connection connection,int choice) throws SQLException{

        List<Stock> stocks=new ArrayList<>();

        String orderBy="symbol";

        if(choice==6){
            orderBy="current_price DESC";
        }

        if(choice==7){
            orderBy="(current_price - previous_close) / previous_close DESC";
        }

        String sql = """
            SELECT symbol, previous_close, current_price
            FROM stocks
            """ + "ORDER BY " + orderBy;
            try (PreparedStatement findStatement = connection.prepareStatement(sql)){
                ResultSet resultSet=findStatement.executeQuery();
                while (resultSet.next()){
                    String symbol=resultSet.getString("symbol");
                    Double previous_close=resultSet.getDouble("previous_close");
                    Double current_price=resultSet.getDouble("current_price");

                    Stock newStock=new Stock(symbol,previous_close,current_price);
                    stocks.add(newStock);
                }
                return stocks;
            }

    }
    public int updateQuote(Connection connection, Stock stock) throws SQLException{
        String sql= """
                UPDATE stocks
                SET previous_close=?, current_price=?
                WHERE symbol=?
                """;

        try(PreparedStatement statement=connection.prepareStatement(sql)){
            statement.setDouble(1,stock.getPreviousClose());
            statement.setDouble(2,stock.getCurrentPrice());
            statement.setString(3,stock.getSymbol());

            int affectedRows=statement.executeUpdate();
            return affectedRows;

        }

    }




}
