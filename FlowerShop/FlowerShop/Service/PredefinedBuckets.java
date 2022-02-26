package FlowerShop.Service;

import FlowerShop.Models.Bucket;
import FlowerShop.Models.Flower;

import java.util.ArrayList;
import java.util.List;


public class PredefinedBuckets {

    FlowerAdder flowerAdder = new FlowerAdder();

    public Bucket promiseIWont(int c) {
        List<Flower> flowers = new ArrayList<>();
        flowers.add(flowerAdder.addFlowerToBucket("rose", c, "red"));
        flowers.add(flowerAdder.addFlowerToBucket("rose", c, "red"));
        flowers.add(flowerAdder.addFlowerToBucket("rose", c, "red"));
        return new Bucket(flowers);
    }

    public Bucket firstOfMarch(int c) {
        List<Flower> flowers = new ArrayList<>();
        flowers.add(flowerAdder.addFlowerToBucket("snowdrop", c, "blue"));
        flowers.add(flowerAdder.addFlowerToBucket("snowdrop", c, "blue"));
        flowers.add(flowerAdder.addFlowerToBucket("snowdrop", c, "blue"));
        return new Bucket(flowers);
    }

    public Bucket springPack(int c) {
        List<Flower> flowers = new ArrayList<>();
        flowers.add(flowerAdder.addFlowerToBucket("snowdrop", c, "blue"));
        flowers.add(flowerAdder.addFlowerToBucket("snowdrop", c, "blue"));
        flowers.add(flowerAdder.addFlowerToBucket("snowdrop", c, "blue"));
        flowers.add(flowerAdder.addFlowerToBucket("lullaby", c, "yellow"));
        flowers.add(flowerAdder.addFlowerToBucket("lullaby", c, "yellow"));
        flowers.add(flowerAdder.addFlowerToBucket("lullaby", c, "yellow"));
        flowers.add(flowerAdder.addFlowerToBucket("lullaby", c, "yellow"));
        return new Bucket(flowers);
    }

    public Bucket funeralPack(int c) {
        List<Flower> flowers = new ArrayList<>();
        flowers.add(flowerAdder.addFlowerToBucket("tulip", c, "black"));
        flowers.add(flowerAdder.addFlowerToBucket("tulip", c, "black"));
        flowers.add(flowerAdder.addFlowerToBucket("tulip", c, "black"));
        return new Bucket(flowers);
    }
}
