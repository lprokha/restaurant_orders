import com.google.gson.internal.bind.util.ISO8601Utils;
import domain.Customer;
import domain.Order;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

public class Main {

    public static void main(String[] args) {

        // это для домашки
        // выберите любое количество заказов, какое вам нравится.

        var orders = RestaurantOrders.read("orders_100.json").getOrders();
        //var orders = RestaurantOrders.read("orders_1000.json").getOrders();
        //var orders = RestaurantOrders.read("orders_10_000.json").getOrders();

//        runFirstTaskMethods(orders);

        runSecondTaskMethods(orders);


        // протестировать ваши методы вы можете как раз в этом файле (или в любом другом, в котором вам будет удобно)
    }

    private static void runFirstTaskMethods(List<Order> orders) {
        printOrders(orders);

        System.out.println("=".repeat(70));
        int n = 5;
        System.out.println(n + " ORDERS WITH MAXIMAL TOTAL:\n");
        printOrders(getOrdersWithMaxTotal(orders, n));

        System.out.println("=".repeat(70));
        int m = 3;
        System.out.println(m + " ORDERS WITH MINIMAL TOTAL:\n");
        printOrders(getOrdersWithMinTotal(orders, m));

        System.out.println("=".repeat(70));
        System.out.println("HOME DELIVERY ORDERS:\n");
        printOrders(getOrdersWithHomeDelivery(orders));

        System.out.println("=".repeat(70));
        System.out.println("HOME DELIVERY ORDERS WITH MAXIMAL AND MINIMAL TOTAL:\n");
        printOrders(getMaxAndMinTotalFromHomeDeliveryOrders(orders));

        System.out.println("=".repeat(70));
        double minOrderTotal = 70;
        double maxOrderTotal = 90;
        System.out.println("ORDERS WITH TOTALS BETWEEN " + minOrderTotal + " AND " + maxOrderTotal + ":\n"  );
        printOrders(getAboveMinAndBelowMaxTotalOrders(orders, minOrderTotal, maxOrderTotal));

        System.out.println("=".repeat(70));
        System.out.println("TOTAL OF ALL ORDERS: " + sumOfAllOrdersTotals(orders));

        System.out.println("=".repeat(70));
        System.out.println("CUSTOMERS' EMAILS:\n");
        printEmails(getAllCustomersUniqueEmails(orders));
    }

    private static void runSecondTaskMethods(List<Order> orders) {
        printOrders(getOrdersGroupedByCustomers(orders));
        getCustomersWithTheirTotals(orders).forEach((k, v) -> System.out.printf("CUSTOMER: %-20s | TOTAL: %4.2f%n", k, v));
    }

    private static Map<String, List<Order>> getOrdersGroupedByCustomers(List<Order> orders) {
        return orders.stream()
                .collect(groupingBy(order -> order.getCustomer().getFullName()));
    }

    private static Map<String, Double> getCustomersWithTheirTotals(List<Order> orders) {
        return orders.stream()
                .collect(groupingBy(order -> order.getCustomer().getFullName(), summingDouble(Order::getTotal)));
    }

    private static void printOrders(Map<String, List<Order>> orders) {
        orders.entrySet().stream()
                .forEach(customerListEntry -> {
                    System.out.println("=".repeat(70));
                    System.out.println("CUSTOMER: " + customerListEntry.getKey() + "\n--- ORDERS: ---");

                    customerListEntry.getValue().forEach(order -> System.out.println(order.toStrWithoutCustomer()));
                    System.out.println();
                });
    }

    private static void printOrders(List<Order> orders) {
        orders.stream()
                .forEach(System.out::println);
    }

    private static void printEmails(Set<String> emails) {
        emails.stream()
                .forEach(System.out::println);
    }

    private static List<Order> getOrdersWithMaxTotal(List<Order> orders,int n) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getTotal).reversed())
                .limit(n)
                .toList();
    }

    private static List<Order> getOrdersWithMinTotal(List<Order> orders,int n) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getTotal))
                .limit(n)
                .toList();
    }

    private static List<Order> getOrdersWithHomeDelivery(List<Order> orders) {
        return orders.stream()
                .filter(Order::isHomeDelivery)
                .toList();
    }

    private static List<Order> getMaxAndMinTotalFromHomeDeliveryOrders(List<Order> orders) {
        List<Order> ordersWithHD = getOrdersWithHomeDelivery(orders);
        List<Order> maxAndMinOrders = new ArrayList<>();
        maxAndMinOrders.add(ordersWithHD.stream()
                .max(Comparator.comparingDouble(Order::getTotal))
                .get());
        maxAndMinOrders.add(ordersWithHD.stream()
                .min(Comparator.comparingDouble(Order::getTotal))
                .get());

        return maxAndMinOrders;
    }

    private static List<Order> getAboveMinAndBelowMaxTotalOrders(List<Order> orders, double minOrderTotal, double maxOrderTotal) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getTotal))
                .dropWhile(order -> order.getTotal() < minOrderTotal)
                .takeWhile(order -> order.getTotal() < maxOrderTotal)
                .toList();
    }

    private static double sumOfAllOrdersTotals(List<Order> orders) {
        return orders.stream()
                .mapToDouble(Order::getTotal)
                .sum();
    }

    private static Set<String> getAllCustomersUniqueEmails(List<Order> orders) {
        return orders.stream()
                .map(order -> order.getCustomer().getEmail())
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
