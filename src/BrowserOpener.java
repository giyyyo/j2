import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

//окрывает веб страницу в браузере на ноутбуке

public class BrowserOpener {

    public void openArticle(int pageId) throws IOException {
        String articleUrl = "https://ru.wikipedia.org/w/index.php?curid=" + pageId;

        if(!Desktop.isDesktopSupported())
            throw new IOException("На этом компьютере открытие браузера не поддерживается.");

        Desktop desktop = Desktop.getDesktop();

        if(!desktop.isSupported(Desktop.Action.BROWSE))
            throw new IOException("Открытие ссылок через браузер не поддерживается.");

        desktop.browse(URI.create(articleUrl));
    }
}
