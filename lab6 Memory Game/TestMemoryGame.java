package byog.lab6;

import org.junit.Test;
import org.junit.Assert;

public class TestMemoryGame {

 /**   @Test
    public void testflashSequence() {

        String[] exp = {"a", "b", "c", "d"};
        Assert.assertArrayEquals(exp, g.flashSequence("abcd"));
    }
 */
 public static void main(String[] args) {
     MemoryGame g = new MemoryGame(40, 40);
     g.solicitNCharsInput(3); //test solicit
 }

}
