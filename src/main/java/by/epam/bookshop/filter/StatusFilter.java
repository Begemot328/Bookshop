package by.epam.bookshop.filter;

import by.epam.bookshop.command.CommandEnum;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.RequestParameters;
import by.epam.bookshop.command.SessionParameters;
import by.epam.bookshop.command.impl.AddShopCommand;
import by.epam.bookshop.command.impl.ForwardCommand;
import by.epam.bookshop.controller.dto.UserDTO;
import by.epam.bookshop.entity.user.UserStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.*;

@WebFilter(urlPatterns = {"/ControllerURL"})
public class StatusFilter implements Filter {

    static CommandEnum[] SellerOnlyCommands = {
            //CommandEnum.ADD_AUTHOR_COMMAND,
            //CommandEnum.ADD_BOOK_COMMAND,
            CommandEnum.ADD_POSITION_COMMAND,
            //CommandEnum.ADD_SHOP_COMMAND,
            //CommandEnum.ADD_SHOP_MENU_COMMAND,
            //CommandEnum.ADD_AUTHOR_MENU_COMMAND,
            //CommandEnum.ADD_BOOK_MENU_COMMAND,
            CommandEnum.ADD_POSITION_MENU_COMMAND,
            CommandEnum.PROCESS_POSITION_COMMAND,
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
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
