package FlowerShop;

import FlowerShop.Models.*;
import FlowerShop.Models.Items.BouquetItem;
import FlowerShop.Models.Items.FlowerItem;
import FlowerShop.Models.Items.ShoppingItem;
import FlowerShop.DataPersistence.CustomerDAO;
import FlowerShop.DataPersistence.FlowerShopOrderDAO;
import FlowerShop.DataPersistence.FlowersDAO;
import FlowerShop.Factory.BouquetFactory;
import FlowerShop.Factory.Models.PredefinedBouquetTypes;
import FlowerShop.Service.*;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class FlowerShopApp {


    private final FlowerLoaderService flowerService;
    private final FlowerAvailabilityService flowerAvailabilityService;
    private final FlowerAdderService flowerAdderService;
    private final FlowerShopOrderDAO flowerShopOrderDAO;
    private final CustomerDAO customerDAO;
    private final CustomerService customerService;

    public FlowerShopApp() {
        this.flowerService = new FlowerLoaderService();
        this.flowerAvailabilityService = new FlowerAvailabilityService();
        this.flowerAdderService = new FlowerAdderService();
        this.flowerShopOrderDAO = new FlowerShopOrderDAO();
        this.customerDAO = new CustomerDAO();
        this.customerService = new CustomerService();
        //creates tables
        customerDAO.createTable();
        FlowersDAO.getInstance().createTable();
        flowerShopOrderDAO.createTable();
    }


    public void start() {

        //initialize shopping item list, which will be populated during the order
        List<ShoppingItem> shoppingItemsCommand = new ArrayList<>();

        System.out.println("Please type the action wanted : for creating a new order press 1: \n" +
                "to see the number of flowers of a certain type press 2: \n" +
                "to set discount for a certain type of flower press 3: \n" +
                "to add new flowers to online deposit press 4: \n" +
                "to see all the orders till now press 5: ");

        Scanner getInfos = new Scanner(System.in);
        int choice = Integer.parseInt(getInfos.next());
        switch (choice) {

            case 1:
                //flowerShopOrder contains the list of shoppingItems (flowers and buckets of flowers) and the customer
                //when we'll know the customer, it will be added to order
                //then once it's done we'll add also the shoppingItems
                FlowerShopOrder finalOrder = new FlowerShopOrder();

                Scanner nameOfCustomer = new Scanner(System.in);
                System.out.println("Please enter name of the customer, if random please enter r : ");
                String nameCustomer = nameOfCustomer.nextLine();

                //verify the existence (by name) of client before introducing it in DB
                //if exists, just update his number of orders +1
                if (!customerService.verifyCustomerByName(nameCustomer)) {

                    System.out.println("Please enter the address of the customer : ");
                    Scanner addressOfCustomer = new Scanner(System.in);

                    Customer cus = new Customer(nameCustomer, addressOfCustomer.nextLine());
                    //we insert the new customer into database
                    customerDAO.insert(cus);
                    //here we set the customer into the finalOrder
                    finalOrder.setCustomer(cus);
                } else {
                    Customer cus = new Customer(nameCustomer, customerDAO.getAddress(nameCustomer));
                    finalOrder.setCustomer(cus);
                    customerDAO.updateNoOfOrders(nameCustomer);
                    System.out.println(customerService.verifyCustomerByName(nameCustomer));
                }

                System.out.println("For single flowers press 1, for buckets press 2");
                Scanner order = new Scanner(System.in);
                int bucketsOrSingle = order.nextInt();

                switch (bucketsOrSingle) {
                    case 1:
                        System.out.println("We have this stockpile of flowers: " + FlowersDAO.getInstance().getAllFlowers());

                        Scanner enterFlower = new Scanner(System.in);
                        System.out.println("Please enter the flowers wanted : ");
                        String flowerName = enterFlower.next();

                        Scanner colorOfFlower = new Scanner(System.in);
                        System.out.println("Please enter the wanted color from the list : ");
                        System.out.println(FlowersDAO.getInstance().getColors(flowerName));

                        String chosenColor = colorOfFlower.next();

                        System.out.println("Please enter the quantity of flowers: ");
                        Scanner enterQuantityOfFlowers = new Scanner(System.in);

                        int flowersQuantity = Integer.parseInt(enterQuantityOfFlowers.next());

                        //FlowerItem is the result client's choice and will be added next to shoppingItemsCommand
                        //chosen flowers will be added to the order and removed from the database using addFlowerToFlowerItem method
                        FlowerItem flowerItem = flowerAdderService.addFlowerToFlowerItem(flowerName, chosenColor, flowersQuantity);
                        shoppingItemsCommand.add(flowerItem);
                        break;

                    case 2:
                        System.out.println("Do you want a predefined bucket? For yes press 1: ");
                        Scanner predefined = new Scanner(System.in);
                        int predef = Integer.parseInt(predefined.next());
                        if (predef == 1) {
                            System.out.println("We have these predefined buckets : promiseIWont (3 red roses)" + "\n" +
                                    " firstOfMarch(3 blue snowdrops)" + "\n" +
                                    " springPack(3 blue snowdrops and 4 yellow lullabies)" + "\n" +
                                    " funeralPack(3 black tulips)" + "\n" +
                                    " weddingPack(3 white tulips");
                            System.out.println("For promiseIWont press 1, for firstOfMatch press 2, for springPack press 3, for funeralPack press 4, for weddingPack press 5");
                            Scanner predefBucket = new Scanner(System.in);
                            int predefBuck = Integer.parseInt(predefBucket.next());

                            System.out.println("Please enter the quantity: ");
                            Scanner predefQuantity = new Scanner(System.in);
                            int predefQ = Integer.parseInt(predefQuantity.next());

                            BouquetFactory bouquetFactory = new BouquetFactory();

                            switch (predefBuck) {
                                case 1:
                                    Bouquet forgiveness = bouquetFactory.createPredefinedBouquet(PredefinedBouquetTypes.FORGIVENESS, predefQ);
                                    BouquetItem bouquetItem = new BouquetItem(forgiveness, predefQ);
                                    shoppingItemsCommand.add(bouquetItem);
                                    break;
                                case 2:
                                    Bouquet firstOfMarch = bouquetFactory.createPredefinedBouquet(PredefinedBouquetTypes.FIRST_MARCH, predefQ);
                                    BouquetItem firstMarch = new BouquetItem(firstOfMarch, predefQ);
                                    shoppingItemsCommand.add(firstMarch);
                                    break;
                                case 3:
                                    Bouquet springBouquet = bouquetFactory.createPredefinedBouquet(PredefinedBouquetTypes.SPRING, predefQ);
                                    BouquetItem spring = new BouquetItem(springBouquet, predefQ);
                                    shoppingItemsCommand.add(spring);
                                    break;
                                case 4:
                                    Bouquet funeralBouquet = bouquetFactory.createPredefinedBouquet(PredefinedBouquetTypes.FUNERAL, predefQ);
                                    BouquetItem funeral = new BouquetItem(funeralBouquet, predefQ);
                                    shoppingItemsCommand.add(funeral);
                                case 5:
                                    Bouquet weddingBouquet = bouquetFactory.createPredefinedBouquet(PredefinedBouquetTypes.WEDDING, predefQ);
                                    BouquetItem wedding = new BouquetItem(weddingBouquet, predefQ);
                                    shoppingItemsCommand.add(wedding);
                                    break;
                            }
                        }

                        System.out.println("Do you also want custom-made buckets? For yes press 1: ");
                        Scanner choiceB = new Scanner(System.in);

                        if (Integer.parseInt(choiceB.next()) == 1) {

                            System.out.println("We have this stockpile of flowers: " + FlowersDAO.getInstance().getAllFlowers());

                            System.out.println("Please enter the quantity of buckets: ");
                            Scanner quantity = new Scanner(System.in);
                            int c = Integer.parseInt(quantity.next());

                            System.out.println("Please enter flower of buckets or DONE if the bucket is complete");
                            Scanner flower1 = new Scanner(System.in);
                            String f = flower1.next();

                            List<Flower> flowersOfBucket = new ArrayList<>();

                            while (!f.equalsIgnoreCase("done")) {
                                System.out.println("Please enter the color wanted: ");
                                Scanner colorBucket = new Scanner(System.in);
                                String colorB = colorBucket.next();
                                if (f.equalsIgnoreCase("done")) {
                                    break;
                                }
                                flowersOfBucket.add(flowerAdderService.addFlowerToBouquet(f, c, colorB));
                                f = flower1.next();
                            }

                            //firstly we make a bucket (a list of flowers), then we add it to a BucketItem
                            //lastly we add the BucketItem to shoppingItemsCommand (a list of ShoppingItems)
                            Bouquet bouquet = new Bouquet(flowersOfBucket);
                            BouquetItem bouquetItem = new BouquetItem(bouquet, c);
                            shoppingItemsCommand.add(bouquetItem);
                            break;
                        }
                }

                //finalOrder contains the list shoppingItems, basically flowers and buckets of flowers
                //and the customer who ordered them
                finalOrder.setShoppingItemList(shoppingItemsCommand);
                flowerShopOrderDAO.insert(finalOrder);
                System.out.println(finalOrder.getReceipt(finalOrder));
                break;

            case 2:
                System.out.println("Please enter the season of flower : ");
                Scanner season = new Scanner(System.in);

                System.out.println("Please enter the color of flower : ");
                Scanner color = new Scanner(System.in);

                System.out.println("Please enter the type of flower : ");
                Scanner type = new Scanner(System.in);

                Flower flowerAvailable = new Flower(Seasons.valueOf(season.next().toUpperCase(Locale.ROOT)), color.next().toUpperCase(Locale.ROOT), type.next().toUpperCase(Locale.ROOT));

                System.out.println(FlowersDAO.getInstance().getQuantity(flowerAvailable));
                System.out.println(flowerAvailabilityService.getAllAvailableFlowers(flowerAvailable));
                break;

            case 3:
                System.out.println("Please enter the season of flower you want to set the discount on :");
                Scanner seasonDiscount = new Scanner(System.in);
                String s = seasonDiscount.next().toUpperCase(Locale.ROOT);
                System.out.println("Please enter the color of flower you want to set the discount on :");
                Scanner colorDiscount = new Scanner(System.in);

                System.out.println("Please enter the type of flower you want to set the discount on :");
                Scanner typeDiscount = new Scanner(System.in);

                System.out.println("Please enter the percentage");
                Scanner percentage = new Scanner(System.in);

                FlowersDAO.getInstance().setDiscounts(new Flower(Seasons.valueOf(s), colorDiscount.next().toUpperCase(Locale.ROOT), typeDiscount.next().toUpperCase(Locale.ROOT)), Integer.parseInt(percentage.next()));
                System.out.println("The discount is set!");
                break;

            case 4:
                System.out.println("Please enter the path of file: ");
                Scanner path = new Scanner(System.in);
                File f = new File(path.next());
                flowerService.persistFlowersInDatabase(flowerService.loadFromDeposit(f));
                break;

            case 5:
                System.out.println("Until now we have " + (long) flowerShopOrderDAO.getAllOrders().keySet().size() + " orders \n" +
                        "with a total value of " + flowerShopOrderDAO.getAllOrders()
                        .values()
                        .stream()
                        .mapToDouble(v -> v)
                        .sum() + "\n" +
                        "average sum of orders " + flowerShopOrderDAO.getAllOrders()
                        .entrySet()
                        .stream()
                        .collect(Collectors.averagingInt(Map.Entry::getValue)));
        }
    }
}
