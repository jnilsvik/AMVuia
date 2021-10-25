package bacit.web.bew;

import java.io.*;
import java.util.Scanner;

public class FILEReaderPusher {
    //scans through entire file, line by line, returning a string and printing it
    public static void thingy(String filename, PrintWriter out) {
        File file = new File(filename);
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scan.hasNext()){
            out.println(scan.nextLine());
        }
    }
    //prints each inv. char to generate page
    public static void htmlText(PrintWriter out){
        try {
            FileReader reader = new FileReader("HTMLCSSJS/sidebar.html");
            int data = reader.read();
            while(data != -1) {
                out.print((char)data);
                data = reader.read();
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //concatinates the content to a single string then prints (in theory)
    public static void thingy2(String code, PrintWriter out){
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(code));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String content = contentBuilder.toString();
        out.println(content);
    }
}
