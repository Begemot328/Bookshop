package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
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

    private static final int ELEMENTS_PER_PAGE = 30;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        int page;
        try {
            page = Integer.parseInt(request.getParameter(RequestParameters.PAGE));
            if (page <= 0) {
                page = 1;
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        try {
            Shop shop = ShopService.getInstance().read(
                    Integer.parseInt(request.getParameter(RequestParameters.SHOP_ID)));
            request.setAttribute(RequestParameters.SHOP, shop);
            Object currentUserObj = request.getSession()
                    .getAttribute(SessionParameters.CURRENT_USER);

            PositionFinder finder =
                    new PositionFinder().findByShop(shop.getId());

            int pageQuantity = CommandUtil.pageQuantity(PositionService.getInstance().countBy(finder),
                    ELEMENTS_PER_PAGE);

            Position[] positions = PositionService.getInstance().findBy(finder).stream()
                    .filter(position -> position.getStatus() == PositionStatus.READY
                            || ((position.getStatus() == PositionStatus.RESERVED
                            || position.getStatus() == PositionStatus.SOLD)
                            && currentUserObj != null
                            && ((User) currentUserObj).getStatus()
                            != UserStatus.BUYER))
                    .toArray(Position[]::new);

            request.setAttribute(RequestParameters.POSITIONS, positions);
            request.setAttribute(RequestParameters.PAGE_QUANTITY, pageQuantity);
            request.setAttribute(RequestParameters.CURRENT_PAGE, page);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new Router(JSPPages.VIEW_SHOP_PAGE);
    }
}
