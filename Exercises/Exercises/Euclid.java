public class Euclid {
    public static int gcd(int p, int q) {
        if (q == 0) {
            return p;
        }
        int r = p % q;
        return gcd(q, r);
    }

    public static void main(String[] args) {
        int p1 = 5;
        int q1 = 25;
        StdOut.println(gcd(p1, q1) + "  Expect result: 5");

        int p2 = 16;
        int q2 = 0;
        StdOut.println(gcd(p2, q2) + "  Expect result: 16");

        int p3 = 16;
        int q3 = 25;
        StdOut.println(gcd(p3, q3) + "  Expect result: 1");

        int p4 = -15;
        int q4 = 25;
        StdOut.println(gcd(p4, q4) + "  Expect result: -5");
    }
}
