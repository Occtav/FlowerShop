package FlowerShop.DataPersistence;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    static Connection getConnection() throws SQLException{
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        return DriverManager.getConnection("jdbc:sqlite:C:\\Users\\ovidi\\Desktop\\octavDB\\FlowerShop.db", config.toProperties());
    }
}

