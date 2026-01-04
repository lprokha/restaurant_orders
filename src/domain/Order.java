package domain;

import util.NotImplementedException;

import java.util.List;
import java.util.Objects;



public class Order {
    // Этот блок кода менять нельзя! НАЧАЛО!
    private final Customer customer;
    private final List<Item> items;
    private final boolean homeDelivery;
    private transient double total = 0.0d;

    public Order(Customer customer, List<Item> orderedItems, boolean homeDelivery) {
        this.customer = customer;
        this.items = List.copyOf(orderedItems);
        this.homeDelivery = homeDelivery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return homeDelivery == order.homeDelivery &&
                Objects.equals(customer, order.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, homeDelivery);
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean isHomeDelivery() {
        return homeDelivery;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getTotal() {
        return total;
    }
    // Этот блок кода менять нельзя! КОНЕЦ!

    //----------------------------------------------------------------------
    //------   Реализация ваших методов должна быть ниже этой линии   ------
    //----------------------------------------------------------------------

    public void calculateTotal() {
        total = items.stream()
                .mapToDouble(item -> item.getPrice() * item.getAmount())
                .sum();
    }

    public String itemsToStr() {
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            sb.append(item.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("CUSTOMER:%n %-25s | %-40s %n%s%n%s%n%nITEMS:%n %-25s | %-5s | %-15s | %2s %n%s%n%s%nHOME DELIVERY: %s%n   TOTAL: %.2f%n"
                , "Full Name"
                , "Email"
                , "-".repeat(62)
                , customer
                , "Name"
                ,"Price"
                , "Type"
                , "Amount"
                , "-".repeat(62)
                ,itemsToStr()
                , homeDelivery
                , total);
    }

    public String toStrWithoutCustomer() {
        return String.format("ITEMS:%n %-25s | %-5s | %-15s | %2s %n%s%n%s%nHOME DELIVERY: %s%n   TOTAL: %.2f%n"
                , "Name"
                ,"Price"
                , "Type"
                , "Amount"
                , "-".repeat(62)
                ,itemsToStr()
                , homeDelivery
                , total);
    }
}
