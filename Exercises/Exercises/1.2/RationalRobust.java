public class RationalRobust {
    private int[] arr = new int[2];
    private final static String ASSERT_AVOID_OVERFLOW_MESSAGE
            = "Operation would cause integer overflow.";

    public RationalRobust(int numerator, int denominator) {
        if (gcd(numerator, denominator) > 1) {
            throw new IllegalArgumentException(
                    "Input numerator and denominator have common factor.");
        }
        if (denominator == 0) {
            throw new IllegalArgumentException(
                    "Input denominator equals 0.");
        }
        arr[0] = numerator;
        arr[1] = denominator;
    }

    private int gcd(int p, int q) {
        // get common divider
        if (q == 0) {
            return p;
        }
        int r = p % q;
        return gcd(q, r);
    }

    private RationalRobust rcd(int p, int q) {
        // remove common divider
        while (gcd(p, q) != 1) {
            if (p == 0 || q == 0) {
                break;
            }
            int cd = gcd(p, q);
            p = p / cd;
            q = q / cd;
        }
        return new RationalRobust(p, q);
    }

    public RationalRobust plus(RationalRobust b) {
        assert (this.arr[0] * b.arr[1]) <= Integer.MAX_VALUE : ASSERT_AVOID_OVERFLOW_MESSAGE;
        assert (this.arr[1] * b.arr[0]) <= Integer.MAX_VALUE : ASSERT_AVOID_OVERFLOW_MESSAGE;
        assert (this.arr[0] * b.arr[1] + this.arr[1] * b.arr[0]) <= Integer.MAX_VALUE :
                ASSERT_AVOID_OVERFLOW_MESSAGE;
        assert (this.arr[1] * b.arr[1]) <= Integer.MAX_VALUE : ASSERT_AVOID_OVERFLOW_MESSAGE;
        int x = this.arr[0] * b.arr[1] + b.arr[0] * this.arr[1];
        int y = this.arr[1] * b.arr[1];
        RationalRobust c = rcd(x, y);
        return c;
    }

    public RationalRobust minus(RationalRobust b) {
        int x = this.arr[0] * b.arr[1] - b.arr[0] * this.arr[1];
        int y = this.arr[1] * b.arr[1];
        RationalRobust c = rcd(x, y);
        return c;
    }

    public RationalRobust times(RationalRobust b) {
        int x = this.arr[0] * b.arr[0];
        int y = this.arr[1] * b.arr[1];
        RationalRobust c = rcd(x, y);
        return c;
    }

    public RationalRobust divides(RationalRobust b) {
        int x = this.arr[0] * b.arr[1];
        int y = this.arr[1] * b.arr[0];
        RationalRobust c = rcd(x, y);
        return c;
    }

    public boolean isEqual(RationalRobust that) {
        if ((this.arr[0] == that.arr[0]) && (this.arr[1] == that.arr[1])) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this.arr[0] + "/" + this.arr[1];
    }
}
