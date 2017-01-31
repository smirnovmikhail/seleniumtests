package tk.msmirnoff.selenium;

import org.openqa.selenium.Point;

/**
 * Created by Dushman on 30.01.2017.
 */
public class HtmlLink {


    private int depth;
    private String name;
    private String url;
    private String parentUrl;
    boolean valid;
    boolean visited;
    private Point location;

    public HtmlLink(String name, String url, String parentUrl, int depth, Point location) {
        if (name != null) {
            this.name = name;
        } else this.name = "";

        if (url != null) {
            this.url = url;
        } else this.url = "";

        if (parentUrl != null) {
            this.parentUrl = parentUrl;
        } else this.parentUrl = "";

        if (location != null) {
            this.location = location;
        } else {
            this.location = new Point(0,0);
        }

        this.depth=depth;
        this.location=location;
        visited=false;
        valid=false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public HtmlLink(int depth, String url, String parentUrl) {
        this.depth = depth;
        this.url = url;
        this.parentUrl = parentUrl;
        valid = false;
        visited = false;
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

    public void print() {

        System.out.println("Name:     " + name);
        System.out.println("url:      " + url);
        System.out.println("parentUrl:" + parentUrl);
        System.out.println("depth:    " + depth);
        System.out.println("valid:    " + valid);
        System.out.println("visited:  " + visited);
        System.out.println("location: " + location.toString());
        System.out.println("---");
    }
}
