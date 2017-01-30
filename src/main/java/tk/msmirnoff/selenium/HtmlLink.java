package tk.msmirnoff.selenium;

/**
 * Created by Dushman on 30.01.2017.
 */
public class HtmlLink {


    private int depth;
    private String url;
    private String parentUrl;
    boolean valid;
    boolean visited;

    public HtmlLink(int depth, String url, String parentUrl) {
        this.depth = depth;
        this.url = url;
        this.parentUrl = parentUrl;
        valid = false;
        visited=false;
    }

    public int getDepth() {

        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentUrl() {
        return parentUrl;
    }

    public void setParentUrl(String parentUrl) {
        this.parentUrl = parentUrl;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
