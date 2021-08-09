import java.util.*;

public class MinK {

    static int getMinK(List<Integer> list, int k) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < list.size(); i++) {
            Integer value = list.get(i);
            priorityQueue.add(value);

        }
        for (int i = 0; i < priorityQueue.size(); i++) {
            Integer value = priorityQueue.poll();
            if(i == k - 1) {
                return value;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            list.add(in.nextInt());
        }
        int minK = getMinK(list, 2);
        System.out.println(minK);
    }
}
