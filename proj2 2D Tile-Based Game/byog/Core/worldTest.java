package byog.Core;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.Core.worldGenerator;

import java.util.Random;

public class worldTest {
    @Test
    public void testOverlap() {
        int w = 78;
        int h = 39;

        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(78, 39);

        // initialize raw world with nothing
        TETile[][] world = new TETile[w][h];
        for (int x = 0; x < w; x += 1) {
            for (int y = 0; y < h; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // fills in a with rooms
        worldGenerator.room r2 = new worldGenerator.room(1,5,20,5);
        worldGenerator.room r3 = new worldGenerator.room(5,2,19,5);
        worldGenerator.addRoom(r2, world);
        // draws the world to the screen
        //ter.renderFrame(world);
        /** int[] x = new int[2];
        x[0] = worldGenerator.roomsCoord[1][0];
        x[1] = worldGenerator.roomsCoord[1][1];
        System.out.print(x[0] + " " + x[1]);
         */
        assertTrue(worldGenerator.room.overlapRoom(r3));
    }

    public static void main(String[] args) {
        Random rand = new Random(10);
        int x = rand.nextInt(10);
        int y = rand.nextInt(10);
        System.out.println(x + " " + y);
    }
}
