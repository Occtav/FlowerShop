package FlowerShop.DataPersistence;

import FlowerShop.Models.Flower;
import FlowerShop.Models.Items.FlowerItem;
import FlowerShop.Models.Seasons;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class FlowersDAO {

    private static FlowersDAO flowersDAO = null;

    public static FlowersDAO getInstance(){
        if(flowersDAO == null){
            flowersDAO = new FlowersDAO();
        }
        return flowersDAO;
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS FLOWERS (" +
                " ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " SEASON TEXT," +
                " COLOR TEXT," +
                " PRICE INTEGER," +
                " TYPE TEXT," +
                " DISCOUNT INTEGER," +
                " QUANTITY INTEGER)";

        try (Connection con = Connector.getConnection(); Statement stm = con.createStatement()) {
            stm.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(FlowerItem flower) {
        String sql = "INSERT INTO FLOWERS(SEASON, COLOR, PRICE, TYPE, DISCOUNT, QUANTITY) VALUES(?,?,?,?,?,?)";
        try (Connection con = Connector.getConnection(); PreparedStatement stm = con.prepareStatement(sql)) {
            Optional<Flower> updatedQuantityOfFlowers = getAllFlowers().stream()
                    .map(FlowerItem::getFlower)
                    .filter(c -> c.equals(flower.getFlower()))
                    .findAny();

            if (updatedQuantityOfFlowers.isPresent()) {
                updateQuantity(flower.getQuantity(), updatedQuantityOfFlowers.get());
            } else {
                stm.setString(1, String.valueOf(flower.getFlower().getSeason()));
                stm.setString(2, flower.getFlower().getColor());
                stm.setDouble(3, flower.getFlower().getPrice());
                stm.setString(4, flower.getFlower().getFlowerTypes());
                stm.setDouble(5, flower.getFlower().getDiscount());
                stm.setInt(6, flower.getQuantity());

                stm.execute();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }




    public  List<FlowerItem> getAllFlowers() {
        List<FlowerItem> allFlowersAvailable = new ArrayList<>();
        String sql = "SELECT * FROM FLOWERS";
        try (Connection con = Connector.getConnection();
             Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                allFlowersAvailable.add(extractFlower(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allFlowersAvailable;
    }

    private FlowerItem extractFlower(ResultSet rs) throws SQLException {
        return new FlowerItem(new Flower(Seasons.valueOf(rs.getString("SEASON")), rs.getString("COLOR"), rs.getDouble("PRICE"), rs.getString("TYPE"), rs.getDouble("DISCOUNT")), rs.getInt("QUANTITY"));
    }

    public void deleteFlower(int quantity, String chosenC, String type) {
        String sql = "UPDATE FLOWERS SET QUANTITY = QUANTITY - ? WHERE TYPE = ? AND COLOR = ?";
        try (Connection con = Connector.getConnection();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, quantity);
            stm.setString(2, type);
            stm.setString(3,chosenC.toUpperCase(Locale.ROOT));
            stm.executeUpdate();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

     public void updateQuantity(int quantity, Flower flower) {
        String sql = "UPDATE FLOWERS SET QUANTITY = QUANTITY + ? WHERE TYPE = ? AND COLOR = ? AND SEASON = ?";
        try (Connection con = Connector.getConnection();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, quantity);
            stm.setString(2, flower.getFlowerTypes());
            stm.setString(3, flower.getColor());
            stm.setString(4, String.valueOf(flower.getSeason()));
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDiscounts(Flower flower, int discount){
        String sql = "UPDATE FLOWERS SET DISCOUNT = ? WHERE TYPE = ? AND COLOR = ? AND SEASON = ?";
        try (Connection con = Connector.getConnection();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, discount);
            stm.setString(2, flower.getFlowerTypes());
            stm.setString(3, flower.getColor());
            stm.setString(4, String.valueOf(flower.getSeason()));
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getColors(String type){
        List<String> colors = new ArrayList<>();
        try(Connection con = Connector.getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT COLOR FROM FLOWERS WHERE TYPE = ?")){
            pstm.setString(1, type.toUpperCase(Locale.ROOT));
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                colors.add(rs.getString("COLOR"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return colors;
    }

    public int getQuantity(Flower flower){
        int quantity = 0;
        try(Connection con = Connector.getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT QUANTITY FROM FLOWERS WHERE SEASON = ? AND COLOR = ? AND TYPE = ?")){
            pstm.setString(1, flower.getSeason().toString());
            pstm.setString(2, flower.getColor());
            pstm.setString(3, flower.getFlowerTypes());

            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                quantity = rs.getInt("QUANTITY");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return quantity;
    }
}
