package Model;

import java.io.*;
import java.util.Scanner;

//A ki és bemenet fájlba, illetve konzolra kiírásáért, beolvasásáért felelős osztály.
public class IO_Manager {

    private static FileWriter fw = null;
    private static Scanner scanner = null;
    private static Scanner stdScanner = null;

    public static boolean speak = true;

    //Kiírja a text-et
    public static void write(String text) {
        if (!speak)
            return;
        System.out.println("> " + text);
    }

    //Kiírja az erromessage-t
    public static void writeError(String errorMessage) {
        if (!speak)
            return;
        System.out.println("ERROR: \"" + errorMessage + "\"" );
    }

    //Kiírja a writeinfo-t
    public static void writeInfo(String info) {
        if (!speak)
            return;
        System.out.println("INFO: \"" + info + "\"" );
    }

    //Fájlba írja a text-et.
    public static void writeFile(String text) {
        if (!speak)
            return;
        try {
            fw.write(text + "\n");
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    public static void writeErrorFile(String errorMessage) {
        if (!speak)
            return;
        try {
            fw.write("ERROR: \"" + errorMessage + "\"" + "\n");
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    public static void writeInfoFile(String info) {
        if (!speak)
            return;
        try {
            fw.write("INFO: \"" + info + "\"" + "\n");
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    //Beolvas egy sort.
    public static String readLine() {
        if (stdScanner == null)
            stdScanner = new Scanner(System.in);
        return stdScanner.nextLine();
    }

    //Befejezi az olvasást.
    public static void endReading() {
        stdScanner.close();
    }

    //Beolvas egy sort a fájlból.
    public static String readLineFile() {
        if (scanner.hasNext())
            return scanner.nextLine();
        else
            return null;
    }

    //A FileWriter létrehozása.
    public static void openFileWrite(String filename) {
        try {
            fw = new FileWriter(filename);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    //A fájl becsukása.
    public static void closeFile() {
        try {
            if (fw != null) {
                fw.close();
                fw = null;
            }
            if (scanner != null) {
                scanner.close();
                scanner = null;
            }

        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    //Megnyitja a fájlt olvasásra.
    public static void openFileRead(String filename) {
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void write(String text, boolean toFile) {
        if (!speak)
            return;
        if (toFile)
            writeFile(text);
        else
            write(text);
    }

    public static void writeInfo(String info, boolean toFile) {
        if (!speak)
            return;
        if (toFile)
            writeInfoFile(info);
        else
            writeInfo(info);
    }

    public static void writeError(String errorMessage, boolean toFile) {
        if (!speak)
            return;
        if (toFile)
            writeErrorFile(errorMessage);
        else
            writeError(errorMessage);
    }

}
