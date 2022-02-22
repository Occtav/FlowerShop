package FlowerShop.Service;

import FlowerShop.Blueprints.Flower;
import FlowerShop.Blueprints.Items.FlowerItem;
import FlowerShop.DataPersistence.FlowersDAO;

import java.util.List;
import java.util.stream.Collectors;

public class FlowerAdder {

    public Flower addFlowerToBucket(String type, int quantity, String color) {

        List<Flower> flowerOfCertainType = FlowersDAO.getInstance().getAllFlowers().stream()
                .map(FlowerItem::getFlower)
                .filter(c -> c.getFlowerTypes().equalsIgnoreCase(type))
                .filter(c -> c.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());

        FlowersDAO.getInstance().deleteFlower(quantity, flowerOfCertainType.get(0).getColor(), flowerOfCertainType.get(0).getFlowerTypes());

        return flowerOfCertainType.get(0);
    }

    public FlowerItem addFlowerToFlowerItem(String type, String chosenC, int quantity) {
        List<Flower> flowerOfCertainType = FlowersDAO.getInstance().getAllFlowers().stream()
                .map(FlowerItem::getFlower)
                .filter(c -> c.getFlowerTypes().equalsIgnoreCase(type))
                .filter(c -> c.getColor().equalsIgnoreCase(chosenC))
                .collect(Collectors.toList());

        FlowersDAO.getInstance().deleteFlower(quantity, chosenC, type.toUpperCase());

        if (flowerOfCertainType.get(0) == null) {
            System.out.println("We ran out of " + type + "!");
        }
        return new FlowerItem(flowerOfCertainType.get(0), quantity);
    }
}
