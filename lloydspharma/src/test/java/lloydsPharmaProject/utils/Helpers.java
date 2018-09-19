package lloydsPharmaProject.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lloydsPharmaProject.Models.Order;
import net.serenitybdd.core.Serenity;

import java.util.ArrayList;
import java.util.List;

public class Helpers {
    public static Order getOrderFromCreatedOrderListJsonBy(String testID) {
        Gson gson = new Gson();
        Order order = new Order();
        List<String> orderListJson = new ArrayList<>();
        orderListJson = (List<String>) Serenity.getCurrentSession().get("CreatedOrderListJson");
        for (String orderJson : orderListJson) {
            order = gson.fromJson(orderJson, new TypeToken<Order>() {
            }.getType());
            if (order.getTestNumber().equals(testID)) {
                return order;
            }
        }
        return null;
    }

}
