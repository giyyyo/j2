import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WikipediaApiClient {
    private static final String API_URL = "https://ru.wikipedia.org/w/api.php";

    public String search(String query) throws IOException {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.name());

        String requestUrl = API_URL + "?action=query&list=search&utf8=1&format=json&srlimit=10&srsearch="
                    + encodedQuery;

        HttpURLConnection connection = null;

        try{
            URL url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("User-Agent", "WikipediaSearchStudentApp/1.0 (Java)");

            int responseCode = connection.getResponseCode();

            InputStream inputStream;
            if(responseCode >= 200 && responseCode < 300) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
                if(inputStream == null){
                    throw new IOException("Сервер вернул HTTP-код " + responseCode);
                }
            }

            StringBuilder response = new StringBuilder();

            try (BufferedReader reader = new BufferedReader( new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
                String line;

                while((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            if (responseCode < 200 || responseCode >= 300){
                throw new IOException("Ошибка сервера. HTTP-код: " + responseCode);
            }

            return response.toString();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }
}

