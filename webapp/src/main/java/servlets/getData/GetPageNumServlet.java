package servlets.getData;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "getPageNum")
public class GetPageNumServlet extends HttpServlet{

    public void service(HttpServletRequest request, HttpServletResponse response) {
        String currentPage = request.getParameter("page");
        getPageNum(currentPage, request);
    }

    private void getPageNum(String currentPage, HttpServletRequest request) {
        currentPage = currentPage != null ? currentPage : Integer.toString(1);
        Integer pageNum = Integer.parseInt(currentPage);
        /*(pageNum - 1) * 10 -> 0  для 1-ой
                               -> 10 для 2-ой
                               -> 20 для 3-ей и т.д. страницы
            pageNum * 10 < pages.size() ? pageNum * 10 : pages.size()
            если pageNum * 10 < pages.size() наша страница не последняя
            то выводим все 10 элементов,
            иначе выводим оставшиеся элементы списка
            */
        request.setAttribute("pageNum", pageNum);
    }
}
