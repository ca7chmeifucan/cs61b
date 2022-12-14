import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;

/**
 * A String-like class that allows users to add and remove characters in the String
 * in constant time and have a constant-time hash function. Used for the Rabin-Karp
 * string-matching algorithm.
 */
class RollingString{

    /**
     * Number of total possible int values a character can take on.
     * DO NOT CHANGE THIS.
     */
    static final int UNIQUECHARS = 128;

    /**
     * The prime base that we are using as our mod space. Happens to be 61B. :)
     * DO NOT CHANGE THIS.
     */
    static final int PRIMEBASE = 6113;

    private int hashvalue;
    private List<Character> char_ls = new ArrayList<>();

    /**
     * Initializes a RollingString with a current value of String s.
     * s must be the same length as the maximum length.
     */
    public RollingString(String s, int length) {
        assert(s.length() == length);
        /* FIX ME */
        for (int i = 0; i < s.length(); i++) {
            char_ls.add(s.charAt(i));
            int single_hash = single_hash_helper(s.charAt(i), i, length);
            hashvalue = (hashvalue + single_hash) % PRIMEBASE;
        }
    }

    public int single_hash_helper(char c, int ind, int length) {
        int single_hash = (int) c;
        for (int i = 0; i < length - ind - 1; i++) {
            single_hash = (single_hash * UNIQUECHARS) % PRIMEBASE;
        }
        return single_hash % PRIMEBASE;
    }

    /**
     * Adds a character to the back of the stored "string" and 
     * removes the first character of the "string". 
     * Should be a constant-time operation.
     */
    public void addChar(char c) {
        /* FIX ME */
        int old_hash = hashvalue + PRIMEBASE;
        char first_char = char_ls.get(0);
        int new_hash = old_hash - single_hash_helper(first_char, 0, char_ls.size());
        new_hash = (new_hash * UNIQUECHARS ) % PRIMEBASE;
        new_hash = (new_hash + (int) c) % PRIMEBASE;
        this.hashvalue = new_hash;
        char_ls.remove(0);
        char_ls.add(c);
    }


    /**
     * Returns the "string" stored in this RollingString, i.e. materializes
     * the String. Should take linear time in the number of characters in
     * the string.
     */
    public String toString() {
        StringBuilder strb = new StringBuilder();
        /* FIX ME */
        for (char c : char_ls) {
            strb.append(c);
        }
        return strb.toString();
    }

    /**
     * Returns the fixed length of the stored "string".
     * Should be a constant-time operation.
     */
    public int length() {
        /* FIX ME */
        return char_ls.size();
    }


    /**
     * Checks if two RollingStrings are equal.
     * Two RollingStrings are equal if they have the same characters in the same
     * order, i.e. their materialized strings are the same.
     */
    @Override
    public boolean equals(Object o) {
        /* FIX ME */
        assertTrue(o instanceof RollingString);
        return this.toString().equals(o.toString());
    }

    /**
     * Returns the hashcode of the stored "string".
     * Should take constant time.
     */
    @Override
    public int hashCode() {
        /* FIX ME */
        return hashvalue;
    }
}
