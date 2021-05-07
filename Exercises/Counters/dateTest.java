/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.Date;

@SuppressWarnings("checkstyle:TypeName")
public class dateTest {
    public static void main(String[] args) {
        Date a = new Date(12, 31, 1999);
        Date b = new Date(1, 1, 2011);
        a = b;
        StdOut.println(a);
        StdOut.println(b);
    }
}
