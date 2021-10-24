package bacit.web.bew;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FILEReaderPusher {
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
    public static void thingy2(String filename) {
        File file = new File(filename);
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scan.hasNext()){
            System.out.println(scan.nextLine());
        }
    }
}
