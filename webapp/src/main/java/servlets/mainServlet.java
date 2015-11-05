package servlets;

import beans.UserWorking;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "main")
public class mainServlet extends HttpServlet {
    @EJB
    private UserWorking userBean;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String addNews = request.getParameter("addNews");
        String importXML = request.getParameter("importXML");
        String logout = request.getParameter("logout");
        String search = request.getParameter("topsearch");
        String traditionId = request.getParameter("traditionId");
        String faq = request.getParameter("faq");
        String xml = request.getParameter("xml");

        String resultURL = null;

        if (traditionId != null) {
            resultURL ="news/full_news.jsp";
        } else if (addNews != null) {
            //TODO Return to index.jsp after adding + unique check
            resultURL = request.getParameter("addNewsUrl") != null ?
                    request.getParameter("addNewsUrl") : "news/add_news.jsp";
        } else if (logout != null) {
            closeSession(request);
            response.sendRedirect("index.jsp");
        } else if (faq != null) {
            resultURL = "modules/faq.html";

        } else if (xml != null) {
            resultURL = "modules/XMLForm.jsp";
        } else if (importXML != null) {
            resultURL = "modules/load_data.html";
        } else if (search != null) {
            resultURL = "search/search.jsp?topsearch=" + search;
        } else if (userBean.getSession(request).getAttribute("userId") == null) {
            resultURL = "includes/welcome.html";
        }
        request.setAttribute("url", resultURL);
    }

    private void closeSession(HttpServletRequest request) {
        HttpSession httpSession = userBean.getSession(request);
        httpSession.setAttribute("userId", null);
    }
}
