package by.epam.bookshop.command;

// TODO: Auto-generated Javadoc
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
    private String page;

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
    public Router(String page) {
        this.page = page;
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
     * @param page the new redirect
     */
    public void setRedirect(String page) {
        setRedirect();
        setPage(page);
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
    public void setForward(String page) {
        setForward();
        setPage(page);
    }

    /**
     * Sets the page.
     *
     * @param page the new page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Gets the page.
     *
     * @return the page
     */
    public String getPage() {
        return page;
    }
}
