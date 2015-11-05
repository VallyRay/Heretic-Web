package servlets.saveData;

import beans.TraditionWorking;
import beans.UserWorking;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by iocz on 11/10/15.
 */
@WebServlet(urlPatterns = "saveTraditionsXML")
public class SaveTraditionsServlet extends HttpServlet {
    @EJB
    private TraditionWorking traditionBean;
    @EJB
    private UserWorking userBean;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        try {
            traditionBean.saveTradition(traditionBean.getUserTraditions((Integer) userBean.getSession(request).getAttribute("userId")),
                    //TODO string const.
                    "/Hereticlication/src/resources/xml/traditionSave.xml");
        } catch (SQLException e) {
            System.err.println("Ошибка при сохранении традиций");
        }
        response.sendRedirect("/Heretic/index.jsp?xml");
    }
}
