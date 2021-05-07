public class Exercise_126 {
    public static boolean isCircularShift(String s, String t) {
        int i = t.indexOf(s.charAt(0));
        if (i >= 0) {
            return s.equals(t.substring(i) + t.substring(0, i));
        }
        return false;
    }

    public static void main(String[] args) {
        String s1 = "abc";
        String t1 = "def";

        StdOut.println("Is circular Shift 1: " + isCircularShift(s1, t1) + " Expected: false");

        String s2 = "rene";
        String t2 = "nere";

        StdOut.println("");
        StdOut.println("Is circular Shift 2: " + isCircularShift(s2, t2) + " Expected: true");
    }
}
