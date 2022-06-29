import java.io.*;
import java.util.*;
import java.nio.charset.*;

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

public class KonkConstr {

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

            BufferedReader in = new BufferedReader(new FileReader(indexFile, Charset.forName("ISO-8859-1")));
            BufferedWriter out = new BufferedWriter(new FileWriter("A", Charset.forName("ISO-8859-1")));

            int byteIndex = 0;
            int[] A = new int[29*900 + 29*30 + 30]; //array with all indices of the index file
            String lastWord = "";   //last found word
            String currentLine = ""; //current full line just read
            String currentWord = ""; //current word extracted from currentLine, to compare with lastWord

            // ----- test search in index file -----
            // RandomAccessFile testSearch = new RandomAccessFile(indexFile, "r");
            // testSearch.seek(1702056318); //<---- insert test byte index here
            // byte[] searchBytes = new byte[5];
            // searchBytes[0] = testSearch.readByte();
            // searchBytes[1] = testSearch.readByte();
            // searchBytes[2] = testSearch.readByte();
            // searchBytes[3] = testSearch.readByte();
            // searchBytes[4] = testSearch.readByte();
            // System.out.println(new String(searchBytes, "ISO-8859-1"));
            // ------------------------------------

            try {
                while ((currentLine = in.readLine()) != null) { //reads every line in indexFile

                    currentWord = currentLine.split(" ")[0]; //splits the line at first space, to extract word

                    //if the word is longer than 3 letters, then only extract first 3 letters
                    if (currentWord.length() > 3)
                        currentWord = currentLine.substring(0, 3);

                    //if the current word is new, add its index to calculated position in A
                    //and replace lastword
                    if (!currentWord.equals(lastWord)) {
                        A[indexCalc(currentWord)] = byteIndex;
                        lastWord = new String(currentWord);
                        // System.out.println("currentWord: " + currentWord + "\n index: " + byteIndex);
                    }
                    //add the line's length to the byte index counter,
                    //"+ 1" because readLine does not include newline char
                    byteIndex += currentLine.length() + 1;
                }

            } catch (IOException e) {
                System.out.println("EOF");
            }

            System.out.println("Done reading indices.");

            for (int i = 0; i < A.length; i++) {
                if(i == 900 || A[i] != 0)
                    out.write(A[i] + "\n");
                else
                    out.write(-1 + "\n");
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

    //method to calculate word index in A
    public static int indexCalc(String word) {
        int index = 0;
        char[] chars = word.toCharArray();

        // loop to replace "å ä ö" with correct value
        for (int i = 0; i < chars.length; i++) {
            if (Integer.valueOf(chars[i]).equals(229))
                chars[i] = '{';
            else if (Integer.valueOf(chars[i]).equals(228))
                chars[i] = '|';
            else if (Integer.valueOf(chars[i]).equals(246))
                chars[i] = '}';
        }

        //if conditions to calculate index based on word length
        if (word.length() == 3)
            index = (Integer.valueOf(chars[0])-96)*900 +
                    (Integer.valueOf(chars[1])-96)*30 +
                    (Integer.valueOf(chars[2])-96);

        else if (word.length() == 2)
            index = (Integer.valueOf(chars[0])-96)*900 +
                    (Integer.valueOf(chars[1])-96)*30;

        else
            index = (Integer.valueOf(chars[0])-96)*900;

        return index;
    }
}
