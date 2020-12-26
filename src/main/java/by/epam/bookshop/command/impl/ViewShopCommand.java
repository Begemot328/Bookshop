package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.dao.impl.position.PositionFinder;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.position.PositionService;
import by.epam.bookshop.service.shop.ShopService;

import javax.servlet.http.HttpServletRequest;

public class ViewShopCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        request.getParameter(RequestParameters.SHOP_ID);
        try {
            Shop shop = ShopService.getInstance().read(
                    Integer.parseInt(request.getParameter(RequestParameters.SHOP_ID)));
            request.getSession().setAttribute(SessionParameters.SHOP, shop);

            Position[] positions = PositionService.getInstance().findBy(
                    new PositionFinder().findByShop(shop.getId())).stream()
                    .filter(position -> position.getStatus() == PositionStatus.READY ||
                            ((User) request.getSession()
                                    .getAttribute(SessionParameters.CURRENT_USER)).getStatus()
                                    != UserStatus.BUYER)
                    .toArray(Position[]::new);
            request.getSession().setAttribute(SessionParameters.POSITIONS, positions);
        } catch (ServiceException e) {
            return new Router(JSPPages.ERROR_PAGE);
        }

        return new Router(JSPPages.VIEW_SHOP_PAGE);
    }
}
