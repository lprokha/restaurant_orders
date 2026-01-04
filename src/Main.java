import domain.Order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        // это для домашки
        // выберите любое количество заказов, какое вам нравится.

        var orders = RestaurantOrders.read("orders_100.json").getOrders();
        printOrders(orders);
        System.out.println("=".repeat(70));
        printOrders(getOrdersWithMaxTotal(orders, 5));

        System.out.println("=".repeat(70));
        printOrders(getOrdersWithMinTotal(orders, 3));

        System.out.println("=".repeat(70));
        printOrders(getOrdersWithHomeDelivery(orders));

        //var orders = RestaurantOrders.read("orders_1000.json").getOrders();
        //var orders = RestaurantOrders.read("orders_10_000.json").getOrders();

        // протестировать ваши методы вы можете как раз в этом файле (или в любом другом, в котором вам будет удобно)
    }

    private static void printOrders(List<Order> orders) {
        orders.stream()
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
}
