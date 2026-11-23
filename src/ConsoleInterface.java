import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ConsoleInterface {
    private final SearchService searchService;
    private final BrowserOpener browserOpener;
    private final Scanner scanner;

    public ConsoleInterface(SearchService searchService, BrowserOpener browserOpener){
        this.searchService = searchService;
        this.browserOpener = browserOpener;
        this.scanner = new Scanner(System.in);
    }

    public void start(){
        System.out.println(" --- Поиск в википедии--- \n");

        boolean running = true;

        while(running){
            System.out.println("Введите поисковой запрос.");
            System.out.println("Для выхода из программы введите 0.\n");

            String query = scanner.nextLine().trim();

            if(query.equals("0")){
                running = false;
            } else if(query.isEmpty()){
                System.out.println("Ошибка: поисковый запрос не может быть пустым.");
            } else{
                performSearch(query);
            }
        }
        System.out.println("Работа программы завершена.");
    }

    private void performSearch(String query){
        try{
            System.out.println("");
            System.out.println("Выполняется поиск...");

            List<SearchResult> results = searchService.search(query);

            if(results.isEmpty()){
                System.out.println("По вашему запросу ничего не найдено.");
                return;
            }

            printResults(results);
            chooseArticle(results);

        } catch (IOException e){
            System.out.println("Не удалось выполнить запрос к Википедии.");
            System.out.println("Проверьте подключение к интернету и попробуйте снова.");
            System.out.println("Подробности:" + e.getMessage());
        } catch (IllegalArgumentException e){
            System.out.println("Ошибка обработки ответа: " + e.getMessage());
        }
    }

    private void printResults(List<SearchResult> results){
        System.out.println("\n Найденные статьи: ");

        for(int i = 0; i < results.size(); i++){
            SearchResult result = results.get(i);

            System.out.println((i+1) + ". " + result.getTitle());

            if(!result.getSnippet().isEmpty()){
                System.out.println("  " + result.getSnippet());
            }
        }
    }

    private void chooseArticle(List<SearchResult> results) {
        while (true) {
            System.out.println("\n Введите номер статьи для открытия.");
            System.out.println("Введите 0, чтобы вернуться к поиску.");

            String input = scanner.nextLine().trim();

            if (input.equals("0")) return;

            try {
                int choice = Integer.parseInt(input);

                if (choice < 1 || choice > results.size()) {
                    System.out.println("Ошибка: статьи с таким номером нет.");
                } else {
                    SearchResult selectedResult = results.get(choice - 1);

                    try {
                        browserOpener.openArticle(selectedResult.getPageId());
                        System.out.println("Статья открывается в браузере.");
                    } catch (IOException e) {
                        System.out.println("Не удалось открыть браузер.");
                        System.out.println("Ссылка на статью: https://ru.wikipedia.org/w/index.php?curid=" + selectedResult.getPageId());
                        System.out.println("Подробности: " + e.getMessage());
                    }

                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число из списка.");
            }
        }
    }
}
