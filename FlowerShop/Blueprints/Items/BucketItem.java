package FlowerShop.Blueprints.Items;

import FlowerShop.Blueprints.Bucket;
import FlowerShop.Blueprints.Flower;

public class BucketItem implements ShoppingItem {

    Bucket bucket;
    int quantity;

    public BucketItem(Bucket bucket, int quantity) {
        this.bucket = bucket;
        this.quantity = quantity;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public double getUnitPrice() {
        return bucket.isPacked() ? (bucket.getFlowersList().stream().mapToDouble(Flower::totalPrice).sum()) * quantity + 10 : (bucket.getFlowersList().stream().mapToDouble(Flower::totalPrice).sum()) * quantity;
    }

    @Override
    public String receiptToString() {
        return quantity + " " + bucket.toString();
    }
}
