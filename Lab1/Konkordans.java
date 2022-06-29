import java.io.*;
import java.util.*;
import java.nio.charset.*;

/**
 * Lab 1 - Condordance search program
 *
 * @author Markus Newton Hedelin, Marcus Samuelsson
 * @since date
 */


// ska använda latmanshashning
// ska inte läsa hela txt-filen, utan bara ta hjälp av index i konstruktionsprogrammet

public class Konkordans {
    public static void main(String[] args) {

        try {
            File AFile = new File("A");
            //File indexFile = new File("B:/Dokument/KTH/files/index");
            //File korpusFile = new File("B:/Dokument/KTH/files/korpus");


            BufferedReader AReader = new BufferedReader(new FileReader(AFile, Charset.forName("ISO-8859-1")));
            //BufferedReader indexReader = new BufferedReader(new FileReader(indexFile, Charset.forName("ISO-8859-1")));
            //BufferedReader korpusReader = new BufferedReader(new FileReader(korpusFile, Charset.forName("ISO-8859-1")));

            int[] A = new int[29*900 + 29*30 + 30];
            String currentIndex;

            int i = 0;
            while ((currentIndex = AReader.readLine()) != null) {
                A[i] = Integer.valueOf(currentIndex);
                i++;
            }
            
            AReader.close();

            String a = "åäö";
            System.out.println(A[indexCalc(a)]);


        } catch (IOException e) {
            System.out.println(e);
        }

    }

    //method to calculate word index in A
    public static int indexCalc(String word) {
        int[] charValues = new int[3];


        // loop to replace "å ä ö" with correct value
        for (int i = 0; i < 3 && i < word.length(); i++) {
            charValues[i] = Integer.valueOf(word.charAt(i))-96;

            System.out.println("Before: " + charValues[i]);
            if (charValues[i] == 133)
                charValues[i] = 27;
            else if (charValues[i] == 132)
                charValues[i] = 28;
            else if (charValues[i] == 150)
                charValues[i] = 29;

            System.out.println("After: " + charValues[i]);
        }

        int index = (charValues[0])*900 +
                    (charValues[1])*30 +
                    (charValues[2]);

        return index;
    }

}
