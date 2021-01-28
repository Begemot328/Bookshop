package by.epam.bookshop.filter;

import by.epam.bookshop.command.CommandEnum;
import by.epam.bookshop.command.RequestParameters;
import by.epam.bookshop.command.SessionParameters;
import by.epam.bookshop.controller.dto.UserDTO;
import by.epam.bookshop.entity.user.UserStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(urlPatterns = {"/ControllerURL"})
public class StatusFilter implements Filter {

    static CommandEnum[] SellerOnlyCommands = {
            CommandEnum.ADD_AUTHOR_COMMAND,
            CommandEnum.ADD_BOOK_COMMAND,
            CommandEnum.ADD_POSITION_COMMAND,
            CommandEnum.ADD_SHOP_COMMAND,
            CommandEnum.ADD_SHOP_MENU_COMMAND,
            CommandEnum.ADD_AUTHOR_MENU_COMMAND,
            CommandEnum.EDIT_AUTHOR_COMMAND,
            CommandEnum.EDIT_BOOK_COMMAND,
            CommandEnum.EDIT_SHOP_COMMAND,
            CommandEnum.EDIT_POSITION_COMMAND,
            CommandEnum.EDIT_BOOK_MENU_COMMAND,
            CommandEnum.EDIT_POSITION_MENU_COMMAND,
            CommandEnum.EDIT_AUTHOR_MENU_COMMAND,
            CommandEnum.GENRE_MENU_COMMAND,
            CommandEnum.MANAGE_GENRES_COMMAND,
            CommandEnum.ADD_BOOK_MENU_COMMAND,
            CommandEnum.ADD_POSITION_MENU_COMMAND,
            CommandEnum.RETURN_BOOK_COMMAND,
            CommandEnum.SELL_BOOK_COMMAND,
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        CommandEnum commandEnum;

        try {
            commandEnum = CommandEnum.valueOf(
                    servletRequest.getParameter(RequestParameters.COMMAND));
        } catch (IllegalArgumentException | NullPointerException e) {
            commandEnum = null;
        }
        if (Arrays.asList(SellerOnlyCommands).contains(commandEnum)) {

            Optional<Object> userObject = Optional.ofNullable(((HttpServletRequest) servletRequest).getSession()
                    .getAttribute(SessionParameters.CURRENT_USER));
            if (userObject.isPresent() && ((UserDTO) userObject.get()).getStatus().getId() > UserStatus.BUYER.getId()) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                HttpServletRequestSetParameterWrapper request = (new HttpServletRequestSetParameterWrapper(
                        (HttpServletRequest) servletRequest));
                request.setParameter(RequestParameters.COMMAND, CommandEnum.SEARCH_BOOKS_COMMAND.name());

                filterChain.doFilter(request, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
