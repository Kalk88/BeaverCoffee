package test_data.dummy_data;

import app.order.Order;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import java.util.Map;

public class OrderDummy {
    private static Map<String, String[]> params = ImmutableMap.of(
            "status", new String[]{"Finished"}
    );

    public static String data = "{ \"id\": \"78970117-3715-4f91-8b4f\", \"timestamp\": 1526633863, \"discount\": \"%10\", \"customer\":\n" +
            "        { \"id\": \"78970117-3715-4f91-8b4f-c4f3342f5a83\", \"clubmember\":\n" +
            "            {\"id\": \"78970117-3715-4f91-8b4f-c4f3342f5a83\", \"first\": \"Jens\", \"last\": \"Conny\", \"SSN\": \"098745028374\", \"homeAdress\":\n" +
            "                [{\"street\": \"Bogdan Street 10\", \"zip\": \"911 1337\", \"city\": \"Coimbra\", \"country\":\n" +
            "                    {\"code\": \"SE\", \"currency\": \"SEK\", \"language\": \"SV\", \"name\": \"Sverige\"}}], \"occupation\": \"Youtube Influencer\", \"validCountry\":\n" +
            "        {\"code\": \"SE\", \"currency\": \"SEK\", \"language\": \"SV\", \"name\": \"Sverige\"}}}, \"employeeID\": \"12345678-3715-4f91-8b4f-c4f3342f5a83\", \"storeID\": \"69999998-3715-4f91-8b4f-c4f3342f5a83\", \"products\": [ { \"productID\": \"12345678-3715-4f91-8b4f-c4f3342f5a8\", \"quantity\": \"1\"}, { \"productID\": \"12345678-3715-4f91-8b4f-c4f3342f5a83\", \"quantity\": \"1\"} ], \"status\": \"Finished\"}";

    public static Order GetDummyOrder() {
        return new Gson().fromJson(data, Order.class);
    }
    public static Map<String, String[]> statusMap() { return params;}
}