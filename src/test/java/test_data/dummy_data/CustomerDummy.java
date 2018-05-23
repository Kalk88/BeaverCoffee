package test_data.dummy_data;

public class CustomerDummy {
    public static String dummy_data = "{\"clubmember\": {\"id\": \"78970117-3715-4f91-8b4f-c4f3342f5a83\", \"first\": \"Jens\", \"last\": \"Conny\", \"SSN\": \"098745028374\", \"homeAdress\": [{\"street\": \"Gejiersgatan 10\",  \"zip\": \"215 12\", \"city\": \"Malmö\", \"country\": { \"code\": \"SE\", \"currency\": \"SEK\", \"language\": \"SV\", \"name\": \"Sverige\" }}], \"occupation\": \"Youtube Influencer\", \"cards\": [{\"numOfPurchases\": \"10\", \"barcode\": \"12345678-3715-4f91-8b4f-c4f3342f5a83\", \"validCountry\": {\"code\": \"SE\", \"name\": \"Sverige\"}}]}}";
    public static String dummy_fail_data = "{\"clubmember\": {\"id\": \"78970117-3715-4f91-8b4f-c4f3342f5a83\", \"first\": \"Jens\", \"SSN\": \"098745028374\", \"homeAdress\": [{\"street\": \"Gejiersgatan 10\",  \"zip\": \"215 12\", \"city\": \"Malmö\", \"country\": { \"code\": \"SE\", \"currency\": \"SEK\", \"language\": \"SV\", \"name\": \"Sverige\" }}], \"occupation\": \"Youtube Influencer\", \"cards\": [{\"numOfPurchases\": \"10\", \"barcode\": \"12345678-3715-4f91-8b4f-c4f3342f5a83\", \"validCountry\": {\"code\": \"SE\", \"name\": \"Sverige\"}}]}}";
}
