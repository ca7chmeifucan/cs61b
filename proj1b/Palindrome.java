public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> res = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            res.addLast(c);
        }
        return res;
    }

    public boolean isPalindrome(String word) {
        //iterative method
        int i = 0;
        int j = word.length() - 1;
        while (i < j) {
            if (word.charAt(i) != word.charAt(j)) {
                return false;
            }
            i += 1;
            j -= 1;
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int i = 0, j = word.length() - 1;
        while (i < j) {
            if (!cc.equalChars(word.charAt(i), word.charAt(j))) {
                return false;
            }
            i += 1;
            j -= 1;
        }
        return true;
    }
}
