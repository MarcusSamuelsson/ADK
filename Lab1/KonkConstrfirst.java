import java.io.*;
import java.util.*;

/**
 * Lab 1 - Condordance hash array program
 *
 * @author Markus Newton Hedelin, Marcus Samuelsson
 * @since date
 */


/**
 * 1. Skapa indexfil mha. tokenizer och sort
 * 2. Skapa hasharray utifrån indexfil
 */

public class KonkConstrfirst {

    // -------- 1. -------


    // -------- 2. -------
    /**
     * 1. sök efter "a" ("aa" --> "aaa" osv.) i indexfil och räkna bytenummer
     * 2. vid träff:
     *         - spara bytenummer (position i indexfil) i a["a"] = 101203
     *         - fortsätt till nästa, "aa"
     *
     * a 1000
     * aa 1202
     *
     * sök från a["a"] -> a["aa"]
     *
     */

    public static void main(String[] args) {

        // out.writeBytes(new String(b, "ISO-8859-1")); <----- skiver till A

        try {
            long startTime = System.nanoTime();

            File indexFile = new File("B:/Dokument/KTH/files/index");

            DataInputStream in = new DataInputStream(new FileInputStream(indexFile));
            DataOutputStream out = new DataOutputStream(new FileOutputStream("A"));

            int byteIndex = 0;
            int[] A = new int[29*900 + 29*30 + 29]; //array med alla index från indexfil
            // String lastWord = "";
            // String currentWord;
            byte[] lastBytes = new byte[3];
            byte[] currentBytes = new byte[3];
            boolean findNextLine = false;



            // ----- test search in index file -----
            // RandomAccessFile testSearch = new RandomAccessFile(indexFile, "r");
            // testSearch.seek(58802078); //<---- insert test byte index here
            // byte[] searchBytes = new byte[3];
            // searchBytes[0] = testSearch.readByte();
            // searchBytes[1] = testSearch.readByte();
            // searchBytes[2] = testSearch.readByte();
            // System.out.println(new String(searchBytes, "ISO-8859-1"));
            // ------------------------------------

            // test with line number
            int lineNr = 0;
            while (lineNr < 50_000_000) {
                try {
                    if (!findNextLine) {
                        currentBytes[0] = in.readByte();
                        currentBytes[1] = in.readByte();
                        currentBytes[2] = in.readByte();

                        if (currentBytes[1] == 0x20) { // if 2nd byte == space
                            currentBytes[1] = 96;
                            currentBytes[2] = 96;
                        }
                        else if (currentBytes[2] == 0x20)
                            currentBytes[2] = 96;

                        // currentWord = new String(currentBytes, "ISO-8859-1");
                        // Byt ut detta till en jämförelse med en byte[] för att snabba upp
                        if (!Arrays.equals(currentBytes, lastBytes)) {
                        // if (currentBytes[0] != lastBytes[0] && currentBytes[1] != lastBytes[1] && currentBytes[2] != lastBytes[2]) {
                            A[indexCalc(currentBytes)] = byteIndex;
                            lastBytes = Arrays.copyOf(currentBytes, 3);
                            // System.out.println("currentWord: " + currentWord + "\n indexCalc: " + indexCalc(currentBytes)
                            //                     + "\n index: " + byteIndex);
                            // System.out.println("currentBytes: " + currentBytes[0] + "\n lastBytes: " + lastBytes[0]);
                        }

                        byteIndex += 3;
                        findNextLine = true;
                    }
                    else {
                        byte nextByte = in.readByte();
                        if (nextByte == 0xA) {
                            findNextLine = false;
                            lineNr++;
                        }
                        byteIndex++;
                    }

                } catch (EOFException e) {
                    System.out.println("fel");
                }
            }

            in.close();
            out.close();

            long endTime = System.nanoTime();
            System.out.println("Time elapsed: " + ((endTime-startTime)/1000000000) + "seconds");
        }
        catch (IOException e) {
            System.out.println(e);
        }

    }

    public static int indexCalc(byte[] word) {

        int index = (word[0]-96)*900 + (word[1]-96)*30 + (word[2]-96);

        return index;
    }
}
