package Model;

import java.io.*;
import java.util.Scanner;

//A ki és bemenet fájlba, illetve konzolra kiírásáért, beolvasásáért felelős osztály.
public class IO_Manager {

    static private FileWriter fw = null;
    static private Scanner scanner = null;
    static private Scanner stdScanner = null;

    static public boolean speak = true;

    //Kiírja a text-et
    static public void write(String text) {
        if (!speak)
            return;
        System.out.println("> " + text);
    }

    //Kiírja az erromessage-t
    static public void writeError(String errorMessage) {
        if (!speak)
            return;
        System.out.println("ERROR: \"" + errorMessage + "\"" );
    }

    //Kiírja a writeinfo-t
    static public void writeInfo(String info) {
        if (!speak)
            return;
        System.out.println("INFO: \"" + info + "\"" );
    }

    //Fájlba írja a text-et.
    static public void writeFile(String text) {
        if (!speak)
            return;
        try {
            fw.write(text + "\n");
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    static public void writeErrorFile(String errorMessage) {
        if (!speak)
            return;
        try {
            fw.write("ERROR: \"" + errorMessage + "\"" + "\n");
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    static public void writeInfoFile(String info) {
        if (!speak)
            return;
        try {
            fw.write("INFO: \"" + info + "\"" + "\n");
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    //Beolvas egy sort.
    static public String readLine() {
        if (stdScanner == null)
            stdScanner = new Scanner(System.in);
        return stdScanner.nextLine();
    }

    //Befejezi az olvasást.
    static public void endReading() {
        stdScanner.close();
    }

    //Beolvas egy sort a fájlból.
    static public String readLineFile() {
        if (scanner.hasNext())
            return scanner.nextLine();
        else
            return null;
    }

    //A FileWriter létrehozása.
    static public void openFileWrite(String filename) {
        try {
            fw = new FileWriter(filename);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    //A fájl becsukása.
    static public void closeFile() {
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
    static public void openFileRead(String filename) {
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }

    static public void write(String text, boolean toFile) {
        if (!speak)
            return;
        if (toFile)
            writeFile(text);
        else
            write(text);
    }

    static public void writeInfo(String info, boolean toFile) {
        if (!speak)
            return;
        if (toFile)
            writeInfoFile(info);
        else
            writeInfo(info);
    }

    static public void writeError(String errorMessage, boolean toFile) {
        if (!speak)
            return;
        if (toFile)
            writeErrorFile(errorMessage);
        else
            writeError(errorMessage);
    }

}
