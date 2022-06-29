import java.util.Arrays; // import the ArrayList class

public class test {

    public static void main(String[] args) {
        int[][] arr = {{1, 2, 3}, {2, 3, 3}, {1, 2, 1}, {0, 0, 0}, {0, 0, 0}};
        int[][] done = test(3, arr);
        for(int i = 0; i < arr.length; i++){
            System.out.print("Jar "+ i + ": ");
            for(int j = 0; j < 3; j++) {
                System.out.print("[" + done[i][j] + "]");
            }
            System.out.println();
        }
    }

    static int[][] test (int n, int[][] arr) {
        int empty = 0;
        boolean[] full = new boolean[arr.length];
        boolean[] done = new boolean[arr.length];
        int[] letters = new int[n];

            for(int i = 0; i < arr.length; i++){
                int nl = 0;
                int cl = 0;
                 
                if(done[i] != true) {
                for(int j = 0; j < arr[i].length; j++){
                     if(arr[i][j] != 0) {
                        if(cl == 0)
                            cl = arr[i][j];
            
                        if(j == 0)
                            full[i] = true;

                        if(arr[i][j] == cl)
                            nl++;
                        else
                            break;
                     }
                }

                if(nl == 0)
                    empty++;
                else if(nl == n) {
                    done[i] = true;
                    letters[cl-1] = -1;
                }
                else
                    letters[cl-1] += nl;
            }
        }

        System.out.println("empty: " + empty + "\nfull: " + Arrays.toString(full)
        + "\ndone: " + Arrays.toString(done) + "\nletters: " + Arrays.toString(letters));

        return arr;
    }
}