package FlowerShop.Blueprints;

import java.util.Objects;

public class Flower {
    private Seasons season;
    private String color;
    private double price;
    private double discount;
    private String flowerTypes;
    private boolean availableForDiscounts;

    public Flower(Seasons season, String color, double price, String flowerTypes, double discount) {
        this.season = season;
        this.color = color;
        this.price = price;
        this.flowerTypes = flowerTypes;
        this.discount = discount;
    }

    public Flower() {
    }

    public Flower(Seasons season, String color, String flowerTypes) {
        this();
        this.season = season;
        this.color = color;
        this.flowerTypes = flowerTypes;
    }


    public Seasons getSeason() {
        return season;
    }

    public void setAvailableForDiscounts(boolean availableForDiscounts) {
        this.availableForDiscounts = availableForDiscounts;
    }

    public String getColor() {
        return color;
    }

    public String getFlowerTypes() {
        return flowerTypes;
    }

    public double getPrice() {
        return price;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double totalPrice() {
        return availableForDiscounts ? price - price * discount / 100 : price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flower flower = (Flower) o;
        return season == flower.season && color.equals(flower.color) && flowerTypes.equals(flower.flowerTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(season, color, flowerTypes);
    }

    @Override
    public String toString() {
        return color + " " + flowerTypes;
    }
}
