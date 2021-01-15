package by.epam.bookshop.command;

import java.net.URL;

/**
 * The Class Router.
 */
public class Router {

    /**
     * The Enum Type.
     */
    public enum Type {

        /** The forward. */
        FORWARD,
        /** The redirect. */
        REDIRECT
    }

    /** The type. */
    private Type type = Type.FORWARD;

    /** The page. */
    private JSPPages page;

    /** The page. */
    private URL url;

    /**
     * Instantiates a new router.
     */
    public Router() {
    }

    /**
     * Instantiates a new router.
     *
     * @param page the page
     */
    public Router(JSPPages page) {
        this.page = page;
    }

    /**
     * Instantiates a new router.
     *
     * @param url the command
     */
    public Router(URL url) {
        this.url = url;
        this.setRedirect();
    }

    /**
     * Sets the redirect.
     */
    public void setRedirect() {
        type = Type.REDIRECT;
    }

    /**
     * Sets the redirect.
     *
     * @param url the new redirect
     */
    public void setRedirect(URL url) {
        setRedirect();
        setURL(url);
    }

    /**
     * Sets the forward.
     */
    public void setForward() {
        type = Type.FORWARD;
    }

    /**
     * Sets the forward.
     *
     * @param page the new forward
     */
    public void setForward(JSPPages page) {
        setForward();
        setPage(page);
    }

    /**
     * Sets the page.
     *
     * @param page the new page
     */
    public void setPage(JSPPages page) {
        this.page = page;
    }



    /**
     * Gets the page.
     *
     * @return the page
     */
    public JSPPages getPage() {
        return page;
    }



    /**
     * Sets the page.
     *
     * @param url the new command
     */
    public void setURL(URL url) {
        this.url = url;
    }



    /**
     * Gets the page.
     *
     * @return the page
     */
    public URL getURL() {
        return url;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }
}
