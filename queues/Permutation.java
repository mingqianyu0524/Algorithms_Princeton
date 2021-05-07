/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int n = 0;
        int k;
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        try {
            k = Integer.parseInt(args[0]);
            if (k < 0) {
                throw new IllegalArgumentException("K must be positive");
            }
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("K must be an integer");
        }

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            rq.enqueue(s);
            n++;
        }

        if (k > n) {
            throw new IllegalArgumentException("K is greater than n");
        }
        else {
            for (int i = 0; i < k; i++) {
                StdOut.println(rq.dequeue());
            }
        }
    }
}
