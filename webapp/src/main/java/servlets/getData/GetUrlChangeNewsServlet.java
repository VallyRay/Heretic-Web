package servlets.getData;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/getUrlChangeNews")
public class GetUrlChangeNewsServlet extends HttpServlet{
    public void service(HttpServletRequest request, HttpServletResponse response)
                                throws IOException, ServletException{
        //TODO используется ли.
        String country = request.getParameter("country");
        String description = request.getParameter("description");
        String id = request.getParameter("id");
        String title = request.getParameter("title");

        if (title != null) {
            StringBuilder changePageUrl = new StringBuilder("add_news.jsp?title=".concat(title).concat(
                    "&id=").concat(id).concat("&country=").concat(country).concat(
                    "&description=").concat(description)
            );
            request.setAttribute(changePageUrl.toString(), "url");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp?news=true");
            if (dispatcher != null) {
                dispatcher.forward(request, response);
            }
        }
    }


}
