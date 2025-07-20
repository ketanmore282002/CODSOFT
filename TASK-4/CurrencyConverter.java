import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Currency Converter ===");
        

        System.out.print("Enter base currency (e.g., USD, INR, EUR): ");
        String fromCurrency = sc.nextLine().trim().toUpperCase();

        System.out.print("Enter target currency (e.g., USD, INR, EUR): ");
        String toCurrency = sc.nextLine().trim().toUpperCase();

        System.out.print("Enter amount to convert: ");
        double amount = sc.nextDouble();

        convertCurrency(fromCurrency, toCurrency, amount);
    }

    public static void convertCurrency(String from, String to, double amount) {
        try {
            String apiUrl = "https://api.exchangerate.host/convert?from=" + from + "&to=" + to + "&amount=" + amount;

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("HTTP Error: " + responseCode);
                return;
            }

            Scanner scanner = new Scanner(url.openStream());
            StringBuilder inline = new StringBuilder();
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();

            // Manually extract the "result" value from the JSON
            String json = inline.toString();
            double result = extractResultFromJson(json);

            if (result != -1) {
                System.out.printf("Converted Amount: %.2f %s\n", result, to);
            } else {
                System.out.println("Conversion failed. Invalid response.");
            }

        } catch (Exception e) {
            System.out.println("Error during conversion: " + e.getMessage());
        }
    }

    // Simple method to extract "result" field from JSON string
    private static double extractResultFromJson(String json) {
        try {
            int index = json.indexOf("\"result\":");
            if (index == -1) return -1;

            int start = index + 9;
            int end = json.indexOf(",", start);
            if (end == -1) {
                end = json.indexOf("}", start);
            }

            String resultStr = json.substring(start, end).trim();
            return Double.parseDouble(resultStr);

        } catch (Exception e) {
            return -1;
        }
    }
}
