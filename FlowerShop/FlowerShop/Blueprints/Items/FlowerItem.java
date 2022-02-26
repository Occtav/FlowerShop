package FlowerShop.Models.Items;

import FlowerShop.Models.Flower;

public class FlowerItem implements ShoppingItem {

    private final Flower flower;
    private final int quantity;

    public FlowerItem(Flower flower, int quantity) {
        this.flower = flower;
        this.quantity = quantity;
    }

    public Flower getFlower() {
        return flower;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public double getUnitPrice() {
        return (flower.getPrice() - (flower.getPrice() * flower.getDiscount()) / 100) * quantity;
    }

    @Override
    public String receiptToString() {
        return quantity + " " + flower.getColor() + " " + flower.getFlowerTypes() + "\n";
    }

    @Override
    public String toString() {
        return quantity + " " + flower;
    }
}
