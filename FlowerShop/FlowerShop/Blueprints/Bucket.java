package FlowerShop.Models;

import java.util.List;

public class Bucket {

    private final List<Flower> flowersList;
    private boolean packed;

    public Bucket(List<Flower> flowersList) {
        this.flowersList = flowersList;
    }

    public void setPacked(boolean packed) {
        this.packed = packed;
    }

    public List<Flower> getFlowersList() {
        return flowersList;
    }

    public boolean isPacked() {
        return packed;
    }

    @Override
    public String toString() {
        return "Buckets of "
                + flowersList;
    }
}
