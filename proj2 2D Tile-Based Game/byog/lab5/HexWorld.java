package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 28;
    private static final int HEIGHT = 30;

    private static final long SEED = 3;
    private static final Random RANDOM = new Random(SEED);

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.FLOWER;
            case 1: return Tileset.GRASS;
            case 2: return Tileset.MOUNTAIN;
            case 4: return Tileset.TREE;
            case 5: return Tileset.SAND;
            default: return Tileset.MOUNTAIN;
        }
    }

    //add a hexgaon of 2N rows starting at position (X, Y)
    public static void addHexagon(int n, int X, int Y, TETile[][] T, TETile tile) {

        for (int y = 0; y < n ; y += 1) {
            for (int x = 0; x < 3*n - 2; x += 1) {
                if (x  > n - 2 - y && x < 2 * n - 1 + y ) {
                    T[x + X][y + Y] = tile;
                }
            }
        }

        for (int y = n; y < 2*n ; y += 1) {
            int k = 2*n - 1 - y;
            for (int x = 0; x < 3*n - 2; x += 1) {
                if (x > n - 2 - k && x < 2 * n - 1 + k) {
                    T[x + X][y + Y] = tile;
                }
            }
        }


    }
    //Drawing A Tesselation of N Hexagons of size n
    public static void Tess(TETile[][] T, int N, int n) {
        int t = 2*N - 1 ;
        int s = 2*N;
        int x = 0, y = 0;
        for (int i = 0; i < N ; i++, x = x + t) {
            y = N * (N - 1 - i);
            for (int j = 0; j < N + i; j++, y = y + s) {
                    addHexagon(n, x, y, T, randomTile());
            }
        }

        for (int i = N - 2; i > -1 ; i--, x = x + t) {
            y = N * (N - 1 - i);
            for (int j = 0; j < N + i; j++, y = y + s) {
                addHexagon(n, x, y, T, randomTile());
            }
        }

    }



    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] hexWorld = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                hexWorld[x][y] = Tileset.NOTHING;
            }
        }
        int a = 11, b = 0;
        Tess(hexWorld, 3, 3);
        ter.renderFrame(hexWorld);
    }
}
