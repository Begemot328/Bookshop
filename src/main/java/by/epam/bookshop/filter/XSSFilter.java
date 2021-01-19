package by.epam.bookshop.filter;

import by.epam.bookshop.command.CommandEnum;
import by.epam.bookshop.command.RequestParameters;
import by.epam.bookshop.command.SessionParameters;
import by.epam.bookshop.controller.Controller;
import by.epam.bookshop.controller.dto.UserDTO;
import by.epam.bookshop.entity.user.UserStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = {"/ControllerURL"})
public class XSSFilter implements Filter {
    public static final String BLANK = "";

    ArrayList<Pattern> patterns = new ArrayList<>();

    public void init(FilterConfig filterConfig) throws ServletException {
        patterns.add(Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE));
        patterns.add(Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        patterns.add(Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        patterns.add(Pattern.compile("</script>", Pattern.CASE_INSENSITIVE));
        patterns.add(Pattern.compile("<script(.*?)>",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        patterns.add(Pattern.compile("eval\\((.*?)\\)",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        patterns.add(Pattern.compile("expression\\((.*?)\\)",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        patterns.add(Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE));
        patterns.add(Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE));
        patterns.add(Pattern.compile("onload(.*?)=",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        boolean filterWorked = false;
        HttpServletRequestSetParameterWrapper request = (new HttpServletRequestSetParameterWrapper(
                (HttpServletRequest) servletRequest));

        for (String key : (Set<String>) servletRequest.getParameterMap().keySet()) {
            for (Pattern pattern: patterns) {
                Matcher matcher = pattern.matcher(servletRequest.getParameter(key));
                if (matcher.matches()) {
                    filterWorked = true;
                    request.setParameter(key, matcher.replaceAll(BLANK));
                    Controller.getLoggerInstance().warn(matcher.group(0) +  " replaced");
                }
            }
        }

        if (filterWorked) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(request, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
