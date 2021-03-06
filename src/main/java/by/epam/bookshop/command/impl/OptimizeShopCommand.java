package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.position.PositionFinder;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.position.PositionService;
import by.epam.bookshop.service.shop.ShopService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public class OptimizeShopCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Shop shop;
        User admin = (User) request.getSession().getAttribute(SessionParameters.CURRENT_USER);

        try {
            shop = ShopService.getInstance().read(Integer.parseInt(request.getParameter(RequestParameters.SHOP_ID)));
            Collection<Position> collection =
            PositionService.getInstance().findBy(new PositionFinder().findByShop(shop.getId()));
            PositionService.getInstance().optimizePositions(collection, admin);
            Position[] positions = PositionService.getInstance().findBy(
                    new PositionFinder().findByShop(shop.getId())).stream()
                    .filter(position -> position.getStatus() == PositionStatus.READY
                            || position.getStatus() == PositionStatus.RESERVED
                            || position.getStatus() == PositionStatus.SOLD)
                    .toArray(Position[]::new);
            request.setAttribute(RequestParameters.POSITIONS, positions);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.VIEW_SHOP_PAGE);
    }
}
