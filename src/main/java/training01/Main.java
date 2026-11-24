package training01;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {



        Scanner scanner =
                new Scanner(System.in);

        String url = "jdbc:mysql://127.0.0.1:3306/stock_monitor";
        String user = "stock_app";

        System.out.print("请输入 MySQL 密码：");
        String password = scanner.nextLine();

        try (Connection connection =
                     DriverManager.getConnection(url, user, password)) {

            Menu menu = new Menu(scanner, connection);

            menu.start();
        }
        scanner.close();
    }
}