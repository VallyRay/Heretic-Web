package servlets;

import beans.LanguageWorking;
import beans.UserWorking;
import util.LangConstant;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by iocz on 07/10/15.
 */
@WebServlet(urlPatterns = "/logInServlet")
public class LogInServlet extends HttpServlet {
    @EJB
    private UserWorking userBean;
    @EJB
    private LanguageWorking languageBean;

    @Override
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
            IOException{
        String user = request.getParameter("logField");
        String pass = request.getParameter("passField");
        try{
            logIn(request, response, userBean.isAuthenticate(user, pass), user);

        } catch (SQLException e) {
            languageBean.languageDetection(request.getParameter("language"),
                    LangConstant.LOGIN_EXCEPTION_EN,
                    LangConstant.LOGIN_EXCEPTION_RU);
        }
    }

    private void logIn(HttpServletRequest request,
            HttpServletResponse response,
                          Boolean result, String login) throws IOException, SQLException{
        if (result) {
            HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute("userId", userBean.getUserId(login));
            response.sendRedirect("index.jsp?news");
        } else {
            response.sendRedirect("index.jsp?wrong");
        }
    }
}
