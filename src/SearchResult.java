public class SearchResult {
    private final int pageId;
    private final String title;
    private final String snippet;

    public SearchResult(int pageId, String title, String snippet){
        this.pageId = pageId;
        this.title = title;
        this.snippet = snippet;
    }

    public int getPageId() {return pageId;}
    public String getTitle() {return title;}
    public String getSnippet() {return snippet;}

}
