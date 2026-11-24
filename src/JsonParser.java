import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public List<SearchResult> parseSearchResults(String json) {
        List<SearchResult> results = new ArrayList<>();

        try {
            JsonObject root = com.google.gson.JsonParser.parseString(json).getAsJsonObject();

            if (!root.has("query") || !root.getAsJsonObject("query").has("search")) {
                return results;
            }

            JsonArray searchArray = root.getAsJsonObject("query").getAsJsonArray("search");

            for (JsonElement element : searchArray) {
                JsonObject article = element.getAsJsonObject();

                int pageId = article.get("pageid").getAsInt();
                String title = article.get("title").getAsString();
                String snippet = " ";

                if (article.has("snippet")) {
                    snippet = cleanSnippet(article.get("snippet").getAsString());
                }
                results.add(new SearchResult(pageId, title, snippet));
            }
        } catch (JsonParseException | IllegalStateException | NullPointerException e) {
            throw new IllegalArgumentException("Не удалось обработать ответ Википедии.", e);
        }

        return results;
    }

    private String cleanSnippet(String snippet) {
        return snippet.replaceAll("<[^>]*>", "")
                .replace("&quot;", "\"")
                .replace("&#039;", "'")
                .replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">");
    }
}
