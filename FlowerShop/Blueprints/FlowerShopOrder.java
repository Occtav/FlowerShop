package FlowerShop.Blueprints;

import FlowerShop.Blueprints.Items.ShoppingItem;

import java.util.List;

public class FlowerShopOrder {

    private Customer customer;
    private List<ShoppingItem> shoppingItemList;

    public FlowerShopOrder(Customer customer, List<ShoppingItem> shoppingItemList) {
        this.customer = customer;
        this.shoppingItemList = shoppingItemList;
    }

    public FlowerShopOrder() {
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setShoppingItemList(List<ShoppingItem> shoppingItemList) {
        this.shoppingItemList = shoppingItemList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getTotalPriceofOrder() {
        return shoppingItemList.stream()
                .mapToDouble(ShoppingItem::getUnitPrice)
                .sum();
    }

    public List<ShoppingItem> getShoppingItemList() {
        return shoppingItemList;
    }

    public String getReceipt(FlowerShopOrder order) {
        StringBuilder receipt = new StringBuilder();
        for (ShoppingItem s : order.getShoppingItemList()) {
            receipt.append(s.receiptToString());
        }
        return receipt + " " + "with the total price of " + order.getTotalPriceofOrder();
    }
}
