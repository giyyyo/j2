
public class Main {

    public static void main(String[] args){
        WikipediaApiClient apiClient = new WikipediaApiClient();
        JsonParser jsonParser = new JsonParser();
        SearchService searchService = new SearchService(apiClient, jsonParser);
        BrowserOpener browserOpener = new BrowserOpener();
        ConsoleInterface consoleInterface = new ConsoleInterface(searchService, browserOpener);

        consoleInterface.start();

    }
}
