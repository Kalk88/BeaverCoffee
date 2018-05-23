package test_data.dummy_data;

import app.employee.Employee;
import app.order.Order;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by ep on 2018-05-23.
 */
public class EmployeeDummy {
    public static String data = "{ \"id\": \"78970117-3715-4f91-8b4f-c4f3342fabcd\", " +
            "\"details\": { \"first\": \"Jens\", \"last\": \"Conny\", \"SSN\": \"098745028374\", " +
            "\"homeAdress\": [ {\"street\": \"Bogdan Street 10\", \"zip\": \"911 1337\", \"city\": \"Coimbra\", " +
            "\"country\": { \"code\": \"SE\",\"currency\": \"SEK\",\"language\": \"SV\", \"name\": \"Sverige\"}}]," +
            "\"phoneNumbers\": {\"mobile\": [\"+4612738592\",\"+4644434593\"],\"home\": [\"0401315023\",\"040150532\"]}," +
            "\"employmentHistory\": [{\"postion\": \"Beanmaker\"," +
            "\"startTimestamp\": 1526633863,\"endTimestamp\": 1526688000, \"type\": \"Full\"}]}," +
            "\"comments\": [ { \"id\": \"78970117-3715-4f91-8b4f-c4f3342f5a83\", \"timestamp\": 1526633863, " +
            "\"employer\": \"12345678-3715-4f91-8b4f-c4f3342f5a83\", \"comment\": \"You're my favorite guy, pal!\" } ] }";

    public static String updatedData = "{ \"id\": \"78970117-3715-4f91-8b4f-c4f3342f5a83\", " +
            "\"details\": { \"first\": \"Hens\", \"last\": \"Conny\", \"SSN\": \"098745028374\", " +
            "\"homeAdress\": [ {\"street\": \"Zlatan Street 10\", \"zip\": \"911 1337\", \"city\": \"Coimbra\", " +
            "\"country\": { \"code\": \"SE\",\"currency\": \"SEK\",\"language\": \"SV\", \"name\": \"Sverige\"}}]," +
            "\"phoneNumbers\": {\"mobile\": [\"+4612738592\",\"+4644434593\"],\"home\": [\"0401315023\",\"040150532\"]}," +
            "\"employmentHistory\": [{\"postion\": \"Beanmaker\"," +
            "\"startTimestamp\": 1526633863,\"endTimestamp\": 1526688000, \"type\": \"Full\"}]}," +
            "\"comments\": [ { \"id\": \"78970117-3715-4f91-8b4f-c4f3342f5a83\", \"timestamp\": 1526633863, " +
            "\"employer\": \"12345678-3715-4f91-8b4f-c4f3342f5a83\", \"comment\": \"You're my favorite guy, pal!\" } ] }";;

    public static Employee GetDummyEmployee() {
        return new Gson().fromJson(data, Employee.class);
    }
}
