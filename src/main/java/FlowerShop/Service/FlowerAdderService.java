package FlowerShop.Service;

import FlowerShop.Models.Flower;
import FlowerShop.Models.Items.FlowerItem;
import FlowerShop.DataPersistence.FlowersDAO;

import java.util.Locale;
import java.util.Optional;

public class FlowerAdderService {

    public Flower addFlowerToBouquet(String type, int quantity, String color) {
        Optional<Flower> flowerOfCertainType = FlowersDAO.getInstance().getAllFlowers().stream()
                .map(FlowerItem::getFlower)
                .filter(c -> c.getFlowerTypes().equalsIgnoreCase(type))
                .filter(c -> c.getColor().equalsIgnoreCase(color))
                .findFirst();

        if(flowerOfCertainType.isEmpty()){
            System.out.println("Flower " + color + " " + type + " is not available!");
            return null;
        }

        FlowersDAO.getInstance().deleteFlower(quantity, color, type.toUpperCase(Locale.ROOT));

        return flowerOfCertainType.get();
    }

    public FlowerItem addFlowerToFlowerItem(String type, String chosenColor, int quantity) {
        Optional<Flower> flowerOfCertainType = FlowersDAO.getInstance().getAllFlowers().stream()
                .map(FlowerItem::getFlower)
                .filter(c -> c.getFlowerTypes().equalsIgnoreCase(type))
                .filter(c -> c.getColor().equalsIgnoreCase(chosenColor))
                .findAny();

        if (flowerOfCertainType.isEmpty()) {
            System.out.println("We ran out of " + chosenColor + " " + type + "!");
            return null;
        }

        FlowersDAO.getInstance().deleteFlower(quantity, chosenColor, type.toUpperCase());

        return new FlowerItem(flowerOfCertainType.get(), quantity);
    }
}
