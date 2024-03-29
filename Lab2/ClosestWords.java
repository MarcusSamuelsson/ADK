/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */

import java.util.LinkedList;
import java.util.List;

public class ClosestWords {
    LinkedList<String> closestWords = null;

    int closestDistance = -1;

    public static void main(String[] args) {
        for (int x = 0; x <= 4; x++) {
            System.out.print("|");
            for (int y = 0; y <= 4; y++) {
                //System.out.println("This (" + x + ", " + y + "): " + partDist("labd", "blad", x, y));
                System.out.print(" " + partDist("labd", "blad", x, y) + " ");

            }
            System.out.print("|");
            System.out.println();
        }


    }

    static int partDist(String w1, String w2, int w1len, int w2len) {

        if (w1len == 0)
            return w2len;

        if (w2len == 0)
            return w1len;

        int res = partDist(w1, w2, w1len - 1, w2len - 1) +
                (w1.charAt(w1len - 1) == w2.charAt(w2len - 1) ? 0 : 1);

        int addLetter = partDist(w1, w2, w1len - 1, w2len) + 1;

        if (addLetter < res)
            res = addLetter;


        int deleteLetter = partDist(w1, w2, w1len, w2len - 1) + 1;
        if (deleteLetter < res)
            res = deleteLetter;

        return res;
    }

    int distance(String w1, String w2) {
        return partDist(w1, w2, w1.length(), w2.length());
    }

    public ClosestWords(String w, List<String> wordList) {
        for (String s : wordList) {
            int dist = distance(w, s);
            // System.out.println("d(" + w + "," + s + ")=" + dist);
            if (dist < closestDistance || closestDistance == -1) {
                closestDistance = dist;
                closestWords = new LinkedList<String>();
                closestWords.add(s);
            } else if (dist == closestDistance)
                closestWords.add(s);
        }
    }

    int getMinDistance() {
        return closestDistance;
    }

    List<String> getClosestWords() {
        return closestWords;
    }
}
