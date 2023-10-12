package KlajdiNdoci;

import KlajdiNdoci.entities.Customer;
import KlajdiNdoci.entities.Order;
import KlajdiNdoci.entities.Product;
import com.github.javafaker.Faker;

import java.util.*;
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

    }
}
