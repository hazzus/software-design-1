package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.HTMLBuilder;
import ru.akirakozov.sd.refactoring.Product;
import ru.akirakozov.sd.refactoring.ProductDB;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final ProductDB db = new ProductDB();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        HTMLBuilder page = new HTMLBuilder(response.getWriter());
        try {
            if ("max".equals(command)) {
                List<Product> list = db.getMax();
                page.addHeader("Product with max price: ");
                for (Product product : list) {
                    page.addProduct(product);
                }
                page.addEnding();
            } else if ("min".equals(command)) {
                List<Product> list = db.getMin();
                page.addHeader("Product with min price: ");
                for (Product product : list) {
                    page.addProduct(product);
                }
                page.addEnding();
            } else if ("sum".equals(command)) {
                page.addLine("Summary price: ");
                page.addLine(Long.toString(db.getSum()));
                page.addEnding();
            } else if ("count".equals(command)) {
                page.addLine("Number of products: ");
                page.addLine(Long.toString(db.getCount()));
                page.addEnding();
            } else {
                response.getWriter().println("Unknown command: " + command);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
