package FlowerShop.Service;

public interface Discounts {
    double priceAfterDiscounts(double price);
}

class FixedDiscount implements Discounts {

    double discount;

    public FixedDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public double priceAfterDiscounts(double price) {
        return price - discount < 0 ? price : price - discount;
    }
}

class PercentageDiscount implements Discounts {

    double percentage;

    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double priceAfterDiscounts(double price) {
        return price - (price * percentage) / 100;
    }
}