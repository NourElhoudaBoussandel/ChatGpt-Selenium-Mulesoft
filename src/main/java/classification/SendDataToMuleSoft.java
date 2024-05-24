//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL; 
//
//public class SendDataToMuleSoft {
//
//    public static void main(String[] args) {
//        // URL de l'API REST dans MuleSoft
//        String apiUrl = "http://localhost:8081/api/data"; // Remplacez par l'URL réelle de votre API REST
//
//        // Données à envoyer
//        String jsonData = "{\"product\": \"example\", \"price\": 100.00}"; // Remplacez par les données réelles
//
//        try {
//            URL url = new URL(apiUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setDoOutput(true);
//
//            OutputStream os = conn.getOutputStream();
//            os.write(jsonData.getBytes());
//            os.flush();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//
//            String output;
//            while ((output = br.readLine()) != null) {
//                System.out.println(output);
//            }
//
//            conn.disconnect();
//
//            System.out.println("Données envoyées avec succès à l'API REST.");
//        } catch (Exception e) {
//            System.err.println("Erreur lors de l'envoi des données à l'API REST: " + e.getMessage());
//        }
//    }
//}
