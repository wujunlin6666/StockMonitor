package training01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectorRepository
{
    public List<String> findAllNames(Connection connection) throws SQLException {
        String sql= """
                select sector_name from sectors
                """;
        List<String> names=new ArrayList<>();

        try(PreparedStatement statement=connection.prepareStatement(sql);
            ResultSet resultSet=statement.executeQuery()){
            while (resultSet.next()){
                String name=resultSet.getString("sector_name");
                names.add(name);
            }
            return names;
        }

    }

    public List<SectorStockDetail> findComponentsBySectorName(Connection connection,String sectorName) throws SQLException{
        String sql= """
                select sector_component.symbol,sector_component.weight,current_price,previous_close,(stocks.current_price - stocks.previous_close)
                    / stocks.previous_close * 100 AS change_percent,(stocks.current_price - stocks.previous_close)
                    / stocks.previous_close * 100 * sector_component.weight as contribution
                from sector_component
                join sectors
                on sector_component.sector_id = sectors.sector_id
                join stocks
                on stocks.symbol=sector_component.symbol
                where sector_name=?;
                """;

        List<SectorStockDetail> sectorStockDetails=new ArrayList<>();
        try(PreparedStatement statement=connection.prepareStatement(sql)){
            statement.setString(1,sectorName);
            ResultSet resultSet=statement.executeQuery();
            while(resultSet.next()){

                Stock stock=new Stock(resultSet.getString("symbol"),
                        resultSet.getDouble("previous_close"),
                        resultSet.getDouble("current_price") );

                SectorStockDetail sectorStockDetail=new SectorStockDetail(stock,resultSet.getDouble("weight"));

                sectorStockDetails.add(sectorStockDetail);
            }
            return sectorStockDetails;
        }
    }


}
