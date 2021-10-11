package bacit.web.bacit_headerFooter;
import java.io.PrintWriter;

public class HeaderFooter {

    public static void printHeader(String title, PrintWriter out){
        out.println("<html>");
        out.println("<head>");
        out.println("<title>"+title+"</title>");
        out.println("</head>");
        out.println("<body>");
    }

    public static void printFooter(PrintWriter out){
        out.println("</body>");
        out.println("</html>");
    }
}