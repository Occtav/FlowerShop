package FlowerShop.DataPersistence;

import FlowerShop.Models.FlowerShopOrder;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class FlowerShopOrderDAO {

    private final CustomerDAO customerDAO = new CustomerDAO();

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS FSO (" +
                " ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " CUSTOMER_ID INTEGER," +
                " RECEIPT TEXT," +
                " TOTAL_PRICE INTEGER," +
                " DATE DATE DEFAULT CURRENT_DATE," +
                " FOREIGN KEY(CUSTOMER_ID) REFERENCES CUSTOMERS(ID))";

        try (Connection con = Connector.getConnection(); Statement stm = con.createStatement()) {
            stm.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(FlowerShopOrder order) {
        String sql = "INSERT INTO FSO(CUSTOMER_ID, RECEIPT, TOTAL_PRICE) VALUES(?,?,?)";
        try (Connection con = Connector.getConnection(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setLong(1, customerDAO.getCustomerID(order.getCustomer().getName()));
            stm.setString(2, order.getReceipt(order));
            stm.setDouble(3, order.getTotalPriceofOrder());
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Map<Integer, Integer> getAllOrders() {
        Map<Integer, Integer> allOrders = new HashMap<>();
        String sql = "SELECT * FROM FSO";

        try (Connection con = Connector.getConnection();
             Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {

                allOrders.put(rs.getInt("ID"), rs.getInt("TOTAL_PRICE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allOrders;
    }
}
