public class RabinKarpAlgorithm {


    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {
        if (input.length() < pattern.length()) {
            return -1;
        }
        RollingString p = new RollingString(pattern, pattern.length());
        RollingString i = new RollingString(input.substring(0, pattern.length()), pattern.length());
        if (i.hashCode() == p.hashCode() && i.equals(p)) {
            return 0;
        }
        for (int k = pattern.length(); k < input.length(); k++) {
            i.addChar(input.charAt(k));
            if (i.hashCode() == p.hashCode() && i.equals(p)) {
                return k - i.length() + 1;
            }
        }
        return -1;
    }

}
