import java.util.LinkedList;
import java.util.List;

public class something {
    public static class  Grann {
        List<Integer> n = new LinkedList<Integer>();
    }

    public static void main(String[] args) {
        int v = 6;
        Grann[] flowChart = new Grann[3];

        flowChart[0].n.add(1);

        System.out.println(flowChart[0].n.get(0));
        System.out.println(flowChart[0].n.size());
    }
}
