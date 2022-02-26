package FlowerShop.Service;

import FlowerShop.Models.Flower;
import FlowerShop.Models.Items.FlowerItem;
import FlowerShop.Models.Seasons;
import FlowerShop.DataPersistence.FlowersDAO;

import java.util.List;
import java.util.stream.Collectors;

public class FlowerAvailability {

    FlowersDAO flowersDAO = FlowersDAO.getInstance();

    public List<FlowerItem> flowerAvailable(Flower flower) {
        return flowersDAO.getAllFlowers()
                .stream()
                .filter(c -> c.getFlower().equals(flower))
                .collect(Collectors.toList());
    }

    List<FlowerItem> seasonalFlowersAvailable(Seasons seasons) {
        return flowersDAO.getAllFlowers()
                .stream()
                .filter(c -> c.getFlower().getSeason().equals(seasons))
                .collect(Collectors.toList());
    }

    List<String> typesAvailable() {
        return flowersDAO.getAllFlowers().stream()
                .map(FlowerItem::getFlower)
                .map(Flower::getFlowerTypes)
                .distinct()
                .collect(Collectors.toList());
    }
}
