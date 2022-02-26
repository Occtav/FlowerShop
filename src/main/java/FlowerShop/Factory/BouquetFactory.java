package FlowerShop.Factory;

import FlowerShop.Models.Bouquet;
import FlowerShop.Models.Flower;
import FlowerShop.Factory.Models.FlowerColors;
import FlowerShop.Factory.Models.FlowerTypes;
import FlowerShop.Service.FlowerAdderService;

import java.util.ArrayList;
import java.util.List;

public class BouquetFactory {

    private final FlowerAdderService flowerAdderService = new FlowerAdderService();

    public Bouquet createPredefinedBouquet(String type, int quantity) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case "SPRING":
                return createSpringBouquet(quantity);
            case "FUNERAL":
                return createFuneralBouquet(quantity);
            case "WEDDING":
                return createWeddingBouquet(quantity);
            case "FIRST_MARCH":
                return createFirstMarchBouquet(quantity);
            case "FORGIVENESS":
                return createForgivenessBouquet(quantity);
            default:
                return new Bouquet();
        }
    }

    private Bouquet createSpringBouquet(int quantity) {
        List<Flower> flowerList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            flowerList.add(flowerAdderService.addFlowerToBouquet(FlowerTypes.SNOWDROP, quantity, FlowerColors.BLUE));
        }
        for (int j = 0; j < 3; j++) {
            flowerList.add(flowerAdderService.addFlowerToBouquet(FlowerTypes.LULLABY, quantity, FlowerColors.YELLOW));
        }
        return new Bouquet(flowerList);
    }

    private Bouquet createFuneralBouquet(int quantity) {
        List<Flower> flowerList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            flowerList.add(flowerAdderService.addFlowerToBouquet(FlowerTypes.TULIP, quantity, FlowerColors.BLACK));
        }
        return new Bouquet(flowerList);
    }

    private Bouquet createWeddingBouquet(int quantity) {
        List<Flower> flowerList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            flowerList.add(flowerAdderService.addFlowerToBouquet(FlowerTypes.TULIP, quantity, FlowerColors.WHITE));
        }
        return new Bouquet(flowerList);
    }

    private Bouquet createFirstMarchBouquet(int quantity) {
        List<Flower> flowerList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            flowerList.add(flowerAdderService.addFlowerToBouquet(FlowerTypes.SNOWDROP, quantity, FlowerColors.BLUE));
        }
        return new Bouquet(flowerList);
    }

    private Bouquet createForgivenessBouquet(int quantity) {
        List<Flower> flowerList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            flowerList.add(flowerAdderService.addFlowerToBouquet(FlowerTypes.ROSE, quantity, FlowerColors.RED));
        }
        return new Bouquet(flowerList);
    }

}
