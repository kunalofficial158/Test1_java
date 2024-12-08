package ques1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Read cars from the JSON file and store them in a map
        Map<String, Map<String, Double>> carInfoMap = JsonCar.readCarsFromJson("C:/Users/KunalGoswami/Downloads/Test1_JAVA/src/ques1/cars.json");

        Scanner scanner = new Scanner(System.in);

        // Step 1: Prompt user to enter their budget
        System.out.print("\nEnter your budget in Lakhs: ");
        double budget = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        // Step 2: Display available cars within the budget
        List<String> availableCars = new ArrayList<>();
        for (String carName : carInfoMap.keySet()) {
            Map<String, Double> carVariants = carInfoMap.get(carName);
            for (Double price : carVariants.values()) {
                double onRoadPrice = calculateOnRoadPrice(price);
                if (onRoadPrice <= budget) {
                    availableCars.add(carName);
                    break;
                }
            }
        }

        if (availableCars.isEmpty()) {
            System.out.println("No cars available within your budget.");
            return;
        }

        System.out.println("\nAvailable cars within your budget:");
        for (int i = 0; i < availableCars.size(); i++) {
            System.out.println((i + 1) + ". " + availableCars.get(i));
        }

        // Take input from user to select a car
        System.out.print("\nSelect a car by entering its number: ");
        int carChoice = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline
        if (carChoice < 0 || carChoice >= availableCars.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        // Take selected car and find its variants from carInfoMap
        String selectedCar = availableCars.get(carChoice);
        Map<String, Double> selectedCarData = carInfoMap.get(selectedCar);
        List<String> variants = new ArrayList<>(selectedCarData.keySet());

        // Display the variants
        System.out.println("Variants available for " + selectedCar + ":");
        for (int i = 0; i < variants.size(); i++) {
            double variantPrice = selectedCarData.get(variants.get(i));
            double onRoadPrice = calculateOnRoadPrice(variantPrice);
            if (onRoadPrice <= budget) {
                System.out.printf("%d. %s (₹%.2f Lakhs, On-road: ₹%.2f Lakhs)%n", i + 1, variants.get(i), variantPrice, onRoadPrice);
            }
        }

        // Select a variant
        System.out.print("\nSelect a variant by entering its number: ");
        int variantChoice = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline
        if (variantChoice < 0 || variantChoice >= variants.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        String selectedVariant = variants.get(variantChoice);
        double carPrice = selectedCarData.get(selectedVariant);
        double onRoadPrice = calculateOnRoadPrice(carPrice);



        //-------------------------------------------- Time for accessories



        // Read accessories from the JSON file
        Map<String, Map<String, String>> accessories = JsonAccessories.readAccessoriesFromJson("C:/Users/KunalGoswami/Downloads/Test1_JAVA/src/ques1/accessories.json");

        // List to hold chosen accessories and their price ranges
        List<String> chosenAccessories = new ArrayList<>();
        double totalAccessoriesPrice = 0.0;

        while (true) {
            // Display accessory categories
            System.out.println("\nAccessory Categories:");
            int index = 1;
            List<String> categories = new ArrayList<>(accessories.keySet());
            for (String category : categories) {
                System.out.println(index + ". " + category);
                index++;
            }

            //Take input from user to select a category
            System.out.print("\nSelect a category by entering its number (0 to finish): ");
            int categoryChoice = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume newline
            if (categoryChoice == -1) {
                break;
            }
            if (categoryChoice < 0 || categoryChoice >= categories.size()) {
                System.out.println("Invalid choice.");
                continue;
            }

            //Extract selected Category and accessories related to it
            String selectedCategory = categories.get(categoryChoice);
            Map<String, String> categoryAccessories = accessories.get(selectedCategory);

            // Display accessories in the selected category
            System.out.println("Accessories in " + selectedCategory + ":");
            index = 1;
            List<String> accessoryNames = new ArrayList<>(categoryAccessories.keySet());
            for (String accessory : accessoryNames) {
                System.out.println(index + ". " + accessory + " (" + categoryAccessories.get(accessory) + ")");
                index++;
            }

            // Take input from user to select an accessory
            System.out.print("\nSelect an accessory by entering its number (0 to finish): ");
            int accessoryChoice = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume newline
            if (accessoryChoice == -1) {
                break;
            }
            if (accessoryChoice < 0 || accessoryChoice >= accessoryNames.size()) {
                System.out.println("Invalid choice.");
                continue;
            }

            // Find selected accessory and then price range
            String selectedAccessory = accessoryNames.get(accessoryChoice);
            String priceRange = categoryAccessories.get(selectedAccessory);
            double accessoryPrice = getPriceFromRange(priceRange);

            // Check if adding this accessory exceeds the budget
            // divide by 1 lakh because we are getting in thousands, to show it in lakh we have to divide
            if (onRoadPrice + totalAccessoriesPrice / 100000 + accessoryPrice / 100000 > budget) {
                System.out.printf("Adding this accessory exceeds your budget. Your current budget is ₹%.2f Lakhs and the total cost will be ₹%.2f Lakhs.%n", budget, onRoadPrice + (totalAccessoriesPrice / 100000) + (accessoryPrice / 100000));
                continue;
            }

            // Add selected accessory and its price range to the list
            chosenAccessories.add(selectedAccessory + " (" + priceRange + ")");
            totalAccessoriesPrice += accessoryPrice;

            // Display the current total accessories cost
            System.out.printf("Total accessories cost (Average): ₹%.2f %n", totalAccessoriesPrice);

            // Ask user if they want to add more accessories
            System.out.print("\nDo you want to add more accessories? (1 for yes, 0 for no): ");
            int addMore = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (addMore == 0) {
                break;
            }
        }

        // Display final on-road price
        System.out.println("\n-------------------------------------------");
        System.out.printf("Final on-road price (including accessories) of %s (%s): ₹%.2f Lakhs%n", selectedCar, selectedVariant, onRoadPrice + totalAccessoriesPrice / 100000);

        // Display selected accessories and their price ranges
        System.out.println("Selected Accessories:");
        for (String accessory : chosenAccessories) {
            System.out.println("  " + accessory);
        }
        System.out.printf("Total cost of accessories: ₹%.2f Lakhs%n", totalAccessoriesPrice / 100000);

        scanner.close();
    }

    //on road price calculator
    private static double calculateOnRoadPrice(double carPrice) {
        double registrationFee = carPrice * 0.07;
        double insuranceFee = carPrice * 0.12;
        double handlingFee = carPrice * 0.02;
        double onRoadPrice = carPrice + registrationFee + insuranceFee + handlingFee;
        return Math.round(onRoadPrice * 100.0) / 100.0; // round to two decimal places
    }

    //Finding average price
    private static double getPriceFromRange(String priceRange) {
        // Remove ₹ symbol and split the range
        String[] prices = priceRange.replace("₹", "").split(" - ");
        double lowPrice = Double.parseDouble(prices[0].replace(",", ""));
        double highPrice = Double.parseDouble(prices[1].replace(",", ""));

        // Return the average price
        return (lowPrice + highPrice) / 2;
    }
}
