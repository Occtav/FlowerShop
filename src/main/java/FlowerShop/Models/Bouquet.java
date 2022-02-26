package FlowerShop.Models;

import java.util.List;

public class Bouquet {
    private List<Flower> flowersList;
    private boolean packed;

    public Bouquet(List<Flower> flowersList) {
        this.flowersList = flowersList;
    }

    public Bouquet() {

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
