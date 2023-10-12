package KlajdiNdoci.entities;

import java.util.Random;

public class Customer {
    private long id;
    private String name;
    private int tier;

    public Customer(String name) {
        Random rand = new Random();
        this.id = rand.nextLong();
        this.name = name;
        this.tier = rand.nextInt(1, 3);
    }

    public String getName() {
        return name;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tier=" + tier +
                '}';
    }
}
