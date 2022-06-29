/**
 * Maximum flow algorithm
 *
 * ADK21 - Labb 3
 *
 * @author: Markus Newton Hedelin, Marcus Samuelsson
 */

 import java.util.Queue;
 import java.util.ArrayList;
 import java.util.LinkedList;

public class MaxFlow {
    static int v;

    public static boolean bfs(int s, int t, ArrayList<LinkedList<Integer>> flowGraph, int[][] restCapGraph, int[] prev) {
        boolean[] visited = new boolean[v+1];
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(s);

        while (!queue.isEmpty()) {
            int currentV = queue.poll();
            visited[currentV] = true;

            if (currentV == t)
                return true;

            for (Integer toV : flowGraph.get(currentV)) {
                if (!visited[toV] && restCapGraph[currentV][toV] > 0) {
                    prev[toV] = currentV;
                    queue.add(toV);
                }
            }
        }
        return false;
    }


    public static void main(String[] args) {
        // long endTime = 0;
        // long startTime = 0;
        // long duration = 0;
        // startTime = System.nanoTime();

        Kattio io = new Kattio(System.in, System.out);

        LinkedList<Integer> edgeOutput = new LinkedList<Integer>();
        int maxFlow = 0;
        int positiveEdges = 0;

        v = io.getInt();
        int s = io.getInt();
        int t = io.getInt();
        int e = io.getInt();

        ArrayList<LinkedList<Integer>> flowGraph = new ArrayList<LinkedList<Integer>>();
        int[][] capGraph = new int[v+1][v+1];
        int[][] restCapGraph = new int[v+1][v+1];
        int[][] maxFlowGraph = new int[v+1][v+1];

        //init array with empty lists
        for (int i = 0; i <= v; i++)
            flowGraph.add(new LinkedList<Integer>());

        for (int edge = 1; edge <= e; edge++) {
            int fromV = io.getInt();
            int toV = io.getInt();
            int c = io.getInt();

            flowGraph.get(fromV).add(toV);
            capGraph[fromV][toV] = c;
            restCapGraph[fromV][toV] = c;
        }

        int[] prev = new int[v+1];
        while (bfs(s, t, flowGraph, restCapGraph, prev)) {

            int currentMin = Integer.MAX_VALUE;
            for (int vertex = t; vertex != s; vertex = prev[vertex]) {
                int from = prev[vertex];
                int to = vertex;

                if (restCapGraph[from][to] < currentMin)
                    currentMin = restCapGraph[from][to];
            }

            for (int vertex = t; vertex != s; vertex = prev[vertex]) {
                int from = prev[vertex];
                int to = vertex;

                if(maxFlowGraph[from][to] == 0) {
                    positiveEdges++;
                    edgeOutput.add(from);
                    edgeOutput.add(to);
                }

                maxFlowGraph[from][to] += currentMin;
                maxFlowGraph[to][from] = -maxFlowGraph[from][to];

                if (restCapGraph[to][from] == 0)
                    flowGraph.get(to).add(from);

                restCapGraph[from][to] = capGraph[from][to] - maxFlowGraph[from][to];
                restCapGraph[to][from] = capGraph[to][from] - maxFlowGraph[to][from];

            }

            maxFlow += currentMin;
        }


        io.println(v);
        io.println(s + " " + t + " " + maxFlow);
        io.println(positiveEdges);

        while(!edgeOutput.isEmpty()) {
            int from = edgeOutput.removeFirst();
            int to = edgeOutput.removeFirst();

            io.println(from + " " + to + " " + maxFlowGraph[from][to]);
        }

        io.flush();
        io.close();

    }

}




// if (currentV == t) {
//     startTime = System.nanoTime();
//     currentPath += " " + currentV;
//     Scanner scanner = new Scanner(currentPath);
//     int from = scanner.nextInt();
//     int to = scanner.nextInt();
//     while(scanner.hasNextInt()) {
//         edgeOutput += from + " " + to + " " + currentMin + "\n";
//
//         restCapGraph[from][to] -= currentMin;
//         restCapGraph[to][from] += currentMin;
//
//         positiveEdges++;
//         from = to;
//         to = scanner.nextInt();
//     }
//     edgeOutput += from + " " + to + " " + currentMin + "\n";
//
//     restCapGraph[from][to] -= currentMin;
//     restCapGraph[to][from] += currentMin;
//
//     positiveEdges++;
//
//     maxFlow += currentMin;
//     hasPath = true;
//
//     endTime = System.nanoTime();
//     duration += (endTime - startTime);
//     break;
// }
