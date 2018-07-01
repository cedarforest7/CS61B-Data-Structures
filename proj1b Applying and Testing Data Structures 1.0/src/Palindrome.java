public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordList = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            wordList.addLast(c);
        }
        return wordList;
    }
    /**unnecessary method
    private Deque<Character> shorten(Deque<Character> L){
        L.removeFirst();
        L.removeLast();
        return L;
    }
     */
    /** public class OffByOne implements CharacterComparator {
        @Override
        public boolean equalChars(char a, char b){
            int diff = a - b;
            return (diff == 1 || diff == -1);
        }
    }
    */
    private boolean isPalindrome(Deque<Character> wordList){
        if(wordList.size() == 0 || wordList.size() == 1) {
            return true;
        }
        return(wordList.removeFirst().equals(wordList.removeLast()) && isPalindrome(wordList));
    }
    private boolean isPalindrome(Deque<Character> wordList, CharacterComparator cc){
        //CharacterComparator x = new cc(); // to be modified
        //cc obo = new cc(); // to be modified

        if(wordList.size() == 0 || wordList.size() == 1) {
            return true;
        }
        return(cc.equalChars(wordList.removeFirst(), wordList.removeLast()) && isPalindrome(wordList, cc)); // to be modified
    }
    public boolean isPalindrome(String word) {
        Deque<Character> wordList = wordToDeque(word);
        return isPalindrome(wordList);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordList = wordToDeque(word);
        return isPalindrome(wordList, cc);
    }
}
