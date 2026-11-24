import java.io.IOException;
import java.util.List;

public class SearchService {
    private final WikipediaApiClient apiClient;
    private final JsonParser jsonParser;

    public SearchService(WikipediaApiClient apiClient, JsonParser jsonParser){
        this.apiClient = apiClient;
        this.jsonParser = jsonParser;
    }

    public List<SearchResult> search(String query) throws IOException{
        String json = apiClient.search(query);
        return jsonParser.parseSearchResults(json);
    }
}
