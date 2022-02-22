package FlowerShop.Service;

import FlowerShop.Blueprints.Flower;
import FlowerShop.Blueprints.Items.FlowerItem;
import FlowerShop.Blueprints.Seasons;
import FlowerShop.DataPersistence.FlowersDAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlowerLoader {

    public List<FlowerItem> loadFromDeposit(File fileName) {
        List<FlowerItem> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                FlowerItem flower = createFlower(values);
                records.add(flower);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void flowersToDB(List<FlowerItem> list) {
        for (FlowerItem f : list) {
            FlowersDAO.getInstance().insert(f);
        }
    }

    private FlowerItem createFlower(String[] att) {

        Seasons season = Seasons.valueOf(att[0]);
        String color = att[1];
        double price = Double.parseDouble(att[2]);
        String flowerTypes = att[3];
        double discount = Double.parseDouble(att[4]);
        int quantity = Integer.parseInt(att[5]);

        return new FlowerItem(new Flower(season, color, price, flowerTypes, discount), quantity);
    }
}
