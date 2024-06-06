package classification;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MessageClient {
    private static WebDriver driver;

    public static void main(String[] args) {
        // Set up WebDriver (assuming you have ChromeDriver set up)
        System.setProperty("webdriver.chrome.driver",
                "C:/Users/bouss/Downloads/chromedriver-win64 (1)/chromedriver-win64/chromedriver.exe");
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(ops);
        String url = "http://localhost:3000";
        driver.get(url);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

        try {
            WebElement chatbotBox = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".chatbot-box")));
            WebElement header = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".chatbot-header")));
            WebElement closeBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#close")));

            // Ajouter l'écouteur d'événements pour détecter le clic sur le bouton
            ((JavascriptExecutor) driver)
                    .executeScript("document.querySelector('#close').addEventListener('click', function() {"
                            + "    window.boutonClique = true;" + "});");

            ((JavascriptExecutor) driver)
                    .executeScript("document.querySelector('#clic').addEventListener('click', function() {"
                            + "    window.boutonEnvoye = true;" + "});");
            ((JavascriptExecutor) driver)
                    .executeScript("document.querySelector('#chatbot-button').addEventListener('click', function() {"
                            + "    window.boutonOuverture = true;" + "});");

            WebElement sendBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#clic")));
            WebElement chatbotMessages = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".chatbot-messages")));
            WebElement messageUser = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".message.user")));

            while (true) {
                boolean envoyer = (boolean) ((JavascriptExecutor) driver)
                        .executeScript("return window.boutonEnvoye === true;");

                if (envoyer) {
                    WebElement messageValue = driver.findElement(By.cssSelector(".message.user"));

                    String message = messageValue.getText();
                    WebElement historiqueValue = driver.findElement(By.cssSelector(".chatbot-messages"));

                    String historique = historiqueValue.getText();

                    List<String> historyList = new ArrayList<>();
                    historyList.add(historique);

                    sendToMuleSoft(message, historyList);

                    // Reset the button status
                    ((JavascriptExecutor) driver).executeScript("window.boutonEnvoye = false;");
                }

                // Vérifiez si le bouton 'close' a été cliqué
                boolean boutonClique = (boolean) ((JavascriptExecutor) driver)
                        .executeScript("return window.boutonClique === true;");
                if (boutonClique) {
                    System.out.println("Le bouton 'close' a été cliqué !");
                    // Reset the close button status
                    ((JavascriptExecutor) driver).executeScript("window.boutonClique = false;");
                    // Vous pouvez ajouter d'autres actions ici si nécessaire
                }

                // Attendre un court instant avant de vérifier à nouveau
                Thread.sleep(500); // 500ms = 0.5s
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Ne pas fermer le navigateur ici
        }
    }

    private static void sendToMuleSoft(String message, List<String> history) {
        try {
            URL url = new URL("http://localhost:8082/test");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            // Créer le JSON contenant les données
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("message", message);
            jsonInput.put("history", history);

            // Envoyer les données
            OutputStream os = conn.getOutputStream();
            os.write(jsonInput.toString().getBytes());
            os.flush();

            System.out.println("Données envoyées à MuleSoft");

            // Lire la réponse de MuleSoft (optionnel)
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out
                        .println("Erreur lors de l'envoi des données à MuleSoft: HTTP code " + conn.getResponseCode());
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
