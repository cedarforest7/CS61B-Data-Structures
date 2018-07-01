import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByNum {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    //Uncomment this class once you've created your CharacterComparator interface and OffByOne class.
    @Test
    public void testEqualChars1(){
        //OffByOne offByOne = new OffByOne(); //Must instantiate first before calling any non-static method!!
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('r', 'q'));
        assertFalse(offByOne.equalChars('a', 'e'));
        assertFalse(offByOne.equalChars('z', 'a'));
        assertFalse(offByOne.equalChars('a', 'e'));
        assertTrue(offByOne.equalChars('&', '%'));
    }
    @Test
    public void testEqualCharsN(){
        OffByN offBy5 = new OffByN(5);
        assertTrue(offBy5.equalChars('a', 'f'));
        assertTrue(offBy5.equalChars('f', 'a'));
        assertFalse(offBy5.equalChars('f', 'h'));
        assertFalse(offBy5.equalChars('x', 'x'));
    }

}
