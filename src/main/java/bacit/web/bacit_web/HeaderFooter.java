package bacit.web.bacit_web;

import java.io.PrintWriter;

public class HeaderFooter {

    public static void printHeader(PrintWriter out){
        out.println("<html>");
        out.println("<body>");
    }

    public static void printFooter(PrintWriter out){
        out.println("</body>");
        out.println("</html>");
    }
}
