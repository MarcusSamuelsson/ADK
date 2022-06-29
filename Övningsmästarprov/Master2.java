public class Master2 {

    public static void main(String[] args) {
        int x = 2;
        int u[] = {3, 3, 3};
        
    
        int answer = checkRec(x, u, u.length, 0);
        int[][] allC = new int[answer][u.length];
        
        for(int i = 0; i < u.length; i++)
            allC[i] = u.clone();
        
        String answerOrder = print_all_ways(x, u, u.length, 0, allC);
    
        System.out.println(answer);
        System.out.println(answerOrder);
    }

    static int checkRec(int x, int u[], int N, int current) {
        int b = x;

        if(current == 0) {
            int a = 0;
            
            for (int i = 0; i < N; ++i) {
                a += u[i];
            }
    
            b = a - x;
        }

    
        int combos = 0;
        for (int i = current; i < N; ++i) {
            for (int j = b; j > 0; --j) {
                if (u[i] >= j) {
                    if (j == b) {
                        combos++;
                    }
                    else if (j < b) {
                        combos += checkRec(b - j, u, N, i + 1);
                    }
                }
            }
        }

        return combos;
    }

    static String print_all_ways (int x, int u[], int N, int current, int[][] allCombos) {
        int b = x;
        boolean success = false;
        String output = "";

        if(current == 0) {
            int a = 0;
            
            for (int i = 0; i < N; ++i) {
                a += u[i];
            }
    
            b = a - x;
        }


        for (int i = current; i < N; ++i) {
            for (int j = b; j > 0; --j) {
                if (u[i] >= j) {
                    if (j == b) {
                        allCombos[current][i] -= j;
                        success = true;
                    }
                    else if (j < b) {
                        output += print_all_ways(b - j, u, N, i + 1, allCombos);
                    }
                }
            }
        }

        
        if(success || current == 0){
            for(int i = 0; i < allCombos[current].length; i++)
                output += allCombos[current][i] + " ";

            output += "\n";

            return output;
        } else
        return "";
    }
}