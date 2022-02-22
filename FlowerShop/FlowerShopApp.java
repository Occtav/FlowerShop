package FlowerShop;

import FlowerShop.Blueprints.*;
import FlowerShop.Blueprints.Items.BucketItem;
import FlowerShop.Blueprints.Items.FlowerItem;
import FlowerShop.Blueprints.Items.ShoppingItem;
import FlowerShop.DataPersistence.CustomerDAO;
import FlowerShop.DataPersistence.FlowerShopOrderDAO;
import FlowerShop.DataPersistence.FlowersDAO;
import FlowerShop.Service.*;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class FlowerShopApp {

    public static void main(String[] args) {

        FlowerLoader flowerService = new FlowerLoader();
        FlowerAvailability flowerAvailability = new FlowerAvailability();
        FlowerAdder flowerAdder = new FlowerAdder();
        FlowerShopOrderDAO flowerShopOrderDAO = new FlowerShopOrderDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        CustomerService customerService = new CustomerService();
        PredefinedBuckets predefinedBuckets = new PredefinedBuckets();

        //creates tables
        customerDAO.createTable();
        FlowersDAO.getInstance().createTable();
        flowerShopOrderDAO.createTable();

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
                        FlowerItem flowerItem = flowerAdder.addFlowerToFlowerItem(flowerName, chosenColor, flowersQuantity);
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
                                    " funeralPack(3 black tulips)");
                            System.out.println("For promiseIWont press 1, for firstOfMatch press 2, for springPack press 3, for funeralPack press 4");
                            Scanner predefBucket = new Scanner(System.in);
                            int predefBuck = Integer.parseInt(predefBucket.next());

                            System.out.println("Please enter the quantity: ");
                            Scanner predefQuantity = new Scanner(System.in);
                            int predefQ = Integer.parseInt(predefQuantity.next());

                            switch (predefBuck) {
                                case 1:
                                    Bucket b = predefinedBuckets.promiseIWont(predefQ);
                                    BucketItem bucketItem = new BucketItem(b, predefQ);
                                    shoppingItemsCommand.add(bucketItem);
                                    break;
                                case 2:
                                    Bucket firstMarch = predefinedBuckets.firstOfMarch(predefQ);
                                    BucketItem firstM = new BucketItem(firstMarch, predefQ);
                                    shoppingItemsCommand.add(firstM);
                                    break;
                                case 3:
                                    Bucket spring = predefinedBuckets.springPack(predefQ);
                                    BucketItem springB = new BucketItem(spring, predefQ);
                                    shoppingItemsCommand.add(springB);
                                    break;
                                case 4:
                                    Bucket funeral = predefinedBuckets.funeralPack(predefQ);
                                    BucketItem funeralB = new BucketItem(funeral, predefQ);
                                    shoppingItemsCommand.add(funeralB);
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
                                flowersOfBucket.add(flowerAdder.addFlowerToBucket(f, c, colorB));
                                f = flower1.next();
                            }

                            //firstly we make a bucket (a list of flowers), then we add it to a BucketItem
                            //lastly we add the BucketItem to shoppingItemsCommand (a list of ShoppingItems)
                            Bucket bucket = new Bucket(flowersOfBucket);
                            BucketItem bucketItem = new BucketItem(bucket, c);
                            shoppingItemsCommand.add(bucketItem);
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
                System.out.println(flowerAvailability.flowerAvailable(flowerAvailable));
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
                flowerService.flowersToDB(flowerService.loadFromDeposit(f));
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
