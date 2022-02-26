package FlowerShop.Models.Items;

import FlowerShop.Models.Bouquet;
import FlowerShop.Models.Flower;

public class BouquetItem implements ShoppingItem {

    private final Bouquet bouquet;
    private final int quantity;

    public BouquetItem(Bouquet bouquet, int quantity) {
        this.bouquet = bouquet;
        this.quantity = quantity;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public double getUnitPrice() {
        return bouquet.isPacked() ? (bouquet.getFlowersList().stream()
                .mapToDouble(Flower::totalPrice).sum()) * quantity + 10 : (bouquet.getFlowersList().stream().mapToDouble(Flower::totalPrice).sum()) * quantity;
    }

    @Override
    public String receiptToString() {
        return quantity + " " + bouquet.toString();
    }
}
