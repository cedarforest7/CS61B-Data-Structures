public class OffByN implements CharacterComparator {
    public int N;
    public OffByN(int n) {
        N = n;
    }
    @Override
    public boolean equalChars(char a, char b){
        int diff = a - b;
        return (diff == N || diff == -N);
    }
}
