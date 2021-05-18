package UniMartTeam.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@WebServlet(name = "Servlet", value = "/Servlet")
public class Servlet extends HttpServlet
{
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      /*ServletOutputStream os = response.getOutputStream();
      String path = getServletContext().getInitParameter("uploadpath") + "test_windows1.jpg";
      Files.copy(Paths.get(path), os);

      request.getRequestDispatcher("/WEB-INF/results/test.jsp").forward(request, response);*/
      request.getRequestDispatcher("/WEB-INF/results/creaUtente.jsp").forward(request, response);
   }
}
