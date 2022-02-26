package FlowerShop.DataPersistence;

import FlowerShop.Models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDAO {

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS CUSTOMERS (" +
                " ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " NAME TEXT," +
                " ADDRESS TEXT," +
                " NO_OF_ORDERS INTEGER)";
        try (Connection con = Connector.getConnection(); Statement stm = con.createStatement()) {
            stm.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer insert(Customer customer) {
        String sql = "INSERT INTO CUSTOMERS(NAME, ADDRESS, no_of_orders) VALUES(?,?,?)";
        long id = System.currentTimeMillis();
        customer.setId(id);
        try (Connection con = Connector.getConnection(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, customer.getName());
            stm.setString(2, customer.getAddress());
            stm.setInt(3, 1);

            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public List<Customer> getAll() {
        List<Customer> allCustomers = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMERS";
        try (Connection con = Connector.getConnection();
             Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                allCustomers.add(extractCustomer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    private Customer extractCustomer(ResultSet rs) throws SQLException {
        return new Customer(rs.getString("NAME"), rs.getString("ADDRESS"));
    }

    public int getCustomerID(String name) {
        int id = 0;
        try (Connection con = Connector.getConnection();
             PreparedStatement pstm = con.prepareStatement("SELECT ID FROM CUSTOMERS WHERE name = ?")) {
            pstm.setString(1, name);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                id = rs.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public Map<Integer, String> getAllCustomers() {
        Map<Integer, String> allCustomers = new HashMap<>();
        try (Connection con = Connector.getConnection();
             PreparedStatement pstm = con.prepareStatement("SELECT ID, NAME FROM CUSTOMERS")) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                allCustomers.put(rs.getInt("ID"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    public void updateNoOfOrders(String name) {
        try (Connection con = Connector.getConnection();
             PreparedStatement pstm = con.prepareStatement("UPDATE Customers SET NO_OF_ORDERS = NO_OF_ORDERS + 1 WHERE name = ?")) {
            pstm.setString(1, name);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getAddress(String nameC) {
        String address = "";
        try (Connection con = Connector.getConnection();
             PreparedStatement pstm = con.prepareStatement("SELECT ADDRESS FROM Customers WHERE name = ?")) {
            pstm.setString(1, nameC);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                address = rs.getString("ADDRESS");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }
}
