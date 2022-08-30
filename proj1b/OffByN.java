public class OffByN implements CharacterComparator{
    int diff;

    public OffByN(int N) {
        this.diff = N;
    }
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs((int) (x - y)) == this.diff;
    }
}
