package KlajdiNdoci;

import KlajdiNdoci.entities.Customer;
import KlajdiNdoci.entities.Order;
import KlajdiNdoci.entities.Product;
import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {

        List<Order> orders = new ArrayList<>();
        Faker faker = new Faker(Locale.ITALY);

        Supplier<Product> booksSupplier = () -> new Product(faker.book().title(), "books");

        Supplier<Product> babySupplier = () -> new Product(faker.commerce().productName(), "baby");

        Supplier<Product> boysSupplier = () -> new Product(faker.commerce().productName(), "boys");

        Supplier<Customer> customerSupplier = () -> new Customer(faker.name().name());

        Customer aldo = new Customer("aldo");


        Order order1 = new Order(Arrays.asList(booksSupplier.get(), booksSupplier.get()), customerSupplier.get());
        orders.add(order1);

        Order order2 = new Order(Arrays.asList(babySupplier.get(), babySupplier.get()), customerSupplier.get());
        orders.add(order2);

        Order order3 = new Order(Arrays.asList(boysSupplier.get(), boysSupplier.get()), customerSupplier.get());
        orders.add(order3);

        Order order4 = new Order(Arrays.asList(booksSupplier.get(), booksSupplier.get()), aldo);
        Order order5 = new Order(Arrays.asList(babySupplier.get(), babySupplier.get()), aldo);
        Order order6 = new Order(Arrays.asList(boysSupplier.get(), boysSupplier.get()), aldo);

        orders.add(order4);
        orders.add(order5);
        orders.add(order6);

//        orders.forEach(System.out::println);

        System.out.println();
        System.out.println("************************EXERCISE 1*****************************");
        System.out.println();

        Map<Customer, List<Order>> ordersByCustomer = orders.stream().collect(Collectors.groupingBy(Order::getCustomer));

        ordersByCustomer.forEach(((customer, customerOrders) -> {
            System.out.println("Cliente: " + customer.getName());
            customerOrders.forEach(order -> {
                System.out.println("Prodotti: " + order.getProducts());
                System.out.println();
            });
        }));

        System.out.println();
        System.out.println("************************EXERCISE 2*****************************");
        System.out.println();

        Map<Customer, Double> ordersTotalPrice = ordersByCustomer.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey, customerOrders -> customerOrders.getValue().stream()
                        .flatMap(order -> order.getProducts().stream())
                        .mapToDouble(Product::getPrice)
                        .sum()
        ));

        ordersTotalPrice.forEach(((customer, totalPrice) -> {
            System.out.println("Il totale del cliente " + customer.getName() + " é: " + totalPrice + "€");
        }));

        System.out.println();
        System.out.println("************************EXERCISE 3*****************************");
        System.out.println();

        orders.forEach(order -> {
            double highestPriceInOrder = order.getProducts().stream()
                    .mapToDouble(Product::getPrice)
                    .max()
                    .orElse(0);

            Product productWithHighestPrice = order.getProducts().stream()
                    .filter(product -> product.getPrice() == highestPriceInOrder)
                    .findFirst()
                    .orElse(null);

            System.out.println("Il prodotto con il prezzo piú alto di questa lista é " + productWithHighestPrice);

        });

        System.out.println();
        System.out.println("************************EXERCISE 4*****************************");
        System.out.println();

        AtomicInteger orderCount = new AtomicInteger();

        orders.forEach(order -> {

            int i = orderCount.incrementAndGet();

            double averagePriceInOrder = order.getProducts().stream()
                    .mapToDouble(Product::getPrice)
                    .average()
                    .orElse(0);
            System.out.println("La media dei prezzi dell'ordine numero " + i + " é di: " + averagePriceInOrder + "€");
        });

        System.out.println();
        System.out.println("************************EXERCISE 5*****************************");
        System.out.println();

        List<Product> productList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            productList.add(new Product(faker.book().title(), "books"));
            productList.add(new Product(faker.commerce().productName(), "baby"));
            productList.add(new Product(faker.commerce().productName(), "boys"));

        }

        Map<String, List<Product>> productByCategory = productList.stream().collect(Collectors.groupingBy(Product::getCategory));


        productByCategory.forEach(((category, products) -> {
            System.out.println("Categoria " + category);
            System.out.println(products);
            double categoryTotal = products.stream()
                    .mapToDouble(Product::getPrice)
                    .sum();
            System.out.println("Il totale della categoria " + category + " é di: " + categoryTotal + "€");
            System.out.println();
        }));

        System.out.println();
        System.out.println("************************EXERCISE 6*****************************");
        System.out.println();

        StringBuilder fileContent = new StringBuilder();

        for (Product product : productList) {
            String productData = String.format("%s@%s@%.2f@", product.getName(), product.getCategory(), product.getPrice());
            fileContent.append(productData);

        }
        File file = new File("src/output.txt");
        try {
            FileUtils.writeStringToFile(file, fileContent + System.lineSeparator(), StandardCharsets.UTF_8, true);

            String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            System.out.println("Nel file ho trovato: " + content);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
