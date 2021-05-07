public class Rational {

    private int[] arr = new int[2];

    public Rational(int numerator, int denominator) {
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

    private Rational rcd(int p, int q) {
        // remove common divider
        while (gcd(p, q) != 1) {
            if (p == 0 || q == 0) {
                break;
            }
            int cd = gcd(p, q);
            p = p / cd;
            q = q / cd;
        }
        return new Rational(p, q);
    }

    /* private int getElementInArray(int index) {
        return arr[index];
    } */

    public Rational plus(Rational b) {
        int x = b.arr[0] + this.arr[0];
        int y = this.arr[1];
        Rational c = rcd(x, y);
        return c;
    }

    public Rational minus(Rational b) {
        int x = this.arr[0] - b.arr[0];
        int y = this.arr[1];
        Rational c = rcd(x, y);
        return c;
    }

    public Rational times(Rational b) {
        int x = this.arr[0] * b.arr[0];
        int y = this.arr[1] * b.arr[1];
        Rational c = rcd(x, y);
        return c;
    }

    public Rational divides(Rational b) {
        int x = this.arr[0] * b.arr[1];
        int y = this.arr[1] * b.arr[0];
        Rational c = rcd(x, y);
        return c;
    }

    public boolean isEqual(Rational that) {
        if ((this.arr[0] == that.arr[0]) && (this.arr[1] == that.arr[1])) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this.arr[0] + "/" + this.arr[1];
    }
}
