public class RationalTestClient {
    public static void main(String[] args) {
        Rational a = new Rational(5, 16);
        Rational b = new Rational(3, 16);
        StdOut.println(a.plus(b) + "  Expect Answer: 1/2");
        StdOut.println(a.minus(b) + "  Expect Answer: 1/8");
        StdOut.println(a.times(b) + "  Expect Answer: 15/256");
        StdOut.println(a.divides(b) + "  Expect Answer: 5/3");
        StdOut.println(a.isEqual(b) + "  Expect Answer: false");
        StdOut.println("Rational a = " + a.toString());
        StdOut.println("Rational b = " + b.toString());
    }
}
