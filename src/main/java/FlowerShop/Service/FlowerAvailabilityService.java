package FlowerShop.Service;

import FlowerShop.Models.Flower;
import FlowerShop.Models.Items.FlowerItem;
import FlowerShop.Models.Seasons;
import FlowerShop.DataPersistence.FlowersDAO;

import java.util.List;
import java.util.stream.Collectors;

public class FlowerAvailabilityService {

    FlowersDAO flowersDAO = FlowersDAO.getInstance();

    public List<FlowerItem> getAllAvailableFlowers(Flower flower) {
        return flowersDAO.getAllFlowers()
                .stream()
                .filter(c -> c.getFlower().equals(flower))
                .collect(Collectors.toList());
    }

    List<FlowerItem> getAvailableSeasonalFlowers(Seasons seasons) {
        return flowersDAO.getAllFlowers()
                .stream()
                .filter(c -> c.getFlower().getSeason().equals(seasons))
                .collect(Collectors.toList());
    }

    List<String> getAvailableTypes() {
        return flowersDAO.getAllFlowers().stream()
                .map(FlowerItem::getFlower)
                .map(Flower::getFlowerTypes)
                .distinct()
                .collect(Collectors.toList());
    }
}
