import java.util.*;

public class OnlineStoreSystem {

    public static void main(String[] args) {
        // Original order data (Customer, Product, Quantity, Price)
        String[] orderData = {
                "John,Laptop,1,899.99", "Sarah,Mouse,2,25.50", "Mike,Keyboard,1,75.00",
                "John,Monitor,1,299.99", "Sarah,Laptop,1,899.99", "Lisa,Mouse,3,25.50",
                "Mike,Headphones,1,150.00", "John,Mouse,1,25.50", "Lisa,Keyboard,2,75.00",
                "Sarah,Monitor,2,299.99", "Mike,Monitor,1,299.99", "Lisa,Headphones,1,150.00"
        };

        System.out.println("=== ONLINE STORE ORDER PROCESSING SYSTEM ===\n");

        // Store all orders
        ArrayList<String> orderList = new ArrayList<>(Arrays.asList(orderData));

        // Output
        // STEP 1
        System.out.println("STEP 1: Managing orders with ArrayList");
        System.out.println("Total orders: " + orderList.size());
        System.out.println("First 3 orders:");
        int n = Math.min(3, orderList.size());
        for (int i = 0; i < n; i++) {
            System.out.println("  " + orderList.get(i));
        }
        System.out.println();

        // STEP 2
        System.out.println("STEP 2: Finding customers with HashSet");

        HashSet<String> customers = new HashSet<>();

        for (String order : orderList) {
            String[] parts = order.split(",");
            String customerName = parts[0];
            customers.add(customerName);
        }

        System.out.println("Unique customers: " + customers);
        System.out.println("Total customers: " + customers.size());
        System.out.println();

        // STEP 3
        System.out.println("STEP 3: Sorting products with TreeSet");

        TreeSet<String> products = new TreeSet<>();

        for (String order : orderList) {
            String[] parts = order.split(",");
            String productName = parts[1];
            products.add(productName);
        }

        System.out.println("Sorted products: " + products);
        System.out.println("Total products: " + products.size());
        System.out.println();

        // STEP 4
        System.out.println("STEP 4: Calculating totals with HashMap");

        HashMap<String, Double> customerTotal = new HashMap<>();
        HashMap<String, Integer> productTotal = new HashMap<>();

        for (String order : orderList) {
            String[] parts = order.split(",");
            String customer = parts[0];
            String product = parts[1];
            int quantity = Integer.parseInt(parts[2]);
            double price = Double.parseDouble(parts[3]);

            // Calculate total spent by each customer
            double totalPrice = quantity * price;
            customerTotal.put(customer, customerTotal.getOrDefault(customer, 0.0) + totalPrice);

            // Calculate total quantity sold per product
            productTotal.put(product, productTotal.getOrDefault(product, 0) + quantity);
        }

        System.out.println("Total spent by each customer: " + customerTotal);
        System.out.println("Total quantity sold per product: " + productTotal);
        System.out.println();

        // STEP 5
        System.out.println("STEP 5: Processing big orders with Queue");

        Queue<String> bigOrders = new LinkedList<>();

        for (String order : orderList) {
            String[] parts = order.split(",");
            int quantity = Integer.parseInt(parts[2]);
            double price = Double.parseDouble(parts[3]);
            double total = quantity * price;

            if (total >= 200) {
                bigOrders.add(order);
            }
        }

        System.out.println("Processing orders worth $200 or more:");
        while (!bigOrders.isEmpty()) {
            String nextOrder = bigOrders.poll();
            System.out.println("  Processing: " + nextOrder);
        }
        System.out.println();

        // STEP 6: Handling returns with Stack
        System.out.println("STEP 6: Handling returns with Stack");

        Stack<String> returns = new Stack<>();

        returns.push("John returns Mouse");
        returns.push("Lisa returns Keyboard");
        returns.push("Mike returns Monitor");

        System.out.println("Created returns (push order):");
        for (String r : returns) {
            System.out.println("  " + r);
        }
        System.out.println();

        System.out.println("Processing returns in LIFO order:");
        while (!returns.isEmpty()) {
            String nextReturn = returns.pop();
            System.out.println("  Processing: " + nextReturn);
        }

        System.out.println();
        
    }
}
