package FlowerShop.Service;

import FlowerShop.DataPersistence.CustomerDAO;

import java.util.Map;

public class CustomerService {

    private final CustomerDAO customerDAO = new CustomerDAO();

    public boolean verifyIfCustomerExistsByID(int id) {
        Map<Integer, String> allCustomers = customerDAO.getAllCustomers();
        return allCustomers.keySet().stream()
                .anyMatch(p -> p == id);
    }

    public boolean verifyCustomerByName(String name) {
        Map<Integer, String> allCustomers = customerDAO.getAllCustomers();
        return allCustomers.containsValue(name);
    }
}
