package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

import java.util.Arrays;

public class worldGenerator implements Serializable {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    private static long SEED;
    private static Random RANDOM;   //deleted the original final keyword

    public static int[][] roomsCoord = new int[3000][2];
    public static int floorNum = 0;
    public static int[][] wallsCoord = new int[1000][2];
    public static int wallNum = 0;
    //public TETile world;
    public static int[] playerPos = new int[2];
    public static int[] doorPos = new int[2];
    static boolean win = false;

    public worldGenerator (long s) {
        SEED = s;
        RANDOM = new Random(SEED);
    }
    //Add a room of size a*b at position (x, y) in the world. (x, y) is the bottom left corner of the room.
    //A room must be connected to at least one other room and up to 3 other rooms
    public static class room {
        public int a;
        public int b;
        public int x;
        public int y;
        public room (int Width, int Height, int X, int Y) {
            a = Width;
            b = Height;
            x = X;
            y = Y;
        }

        public static boolean overlapFloor(int[] Coord) {
            for (int o = 0; o < floorNum; o++) {
                if (Arrays.equals(Coord, roomsCoord[o])) {
                    return true;
                }
            }
            return false;
        }
        public static boolean overlapWall(int[] Coord) {
            for (int o = 0; o < wallNum; o++) {
                if (Arrays.equals(Coord, wallsCoord[o])) {
                    return true;
                }
            }
            return false;
        }

        public static boolean overlapRoom(room r) {
            int[] Coord = new int[2];
            for (int i = 0; i < r.a; i++) {
                for (int j = 0; j < r.b; j++) {
                    Coord[0] = r.x + i;
                    Coord[1] = r.y + j;
                    if (overlapFloor(Coord)) {
                        return true;
                    }
                }
            }
            return false;
        }
        public static boolean roomOverflow(room r) {
            return !(r.x + r.a < WIDTH - 1 && r.x > 0 && r.y + r.b < HEIGHT - 1 && r.y > 0);
        }
    }

    public static boolean addRoom(room r, TETile[][] world) {
        if (room.overlapRoom(r) || room.roomOverflow(r)) {
            return false;
        }
        for (int i = 0; i < r.a; i++) {
            int[] coord = new int[2];
            for (int j = 0; j < r.b; j++) {
                coord[0] = r.x + i;
                coord[1] = r.y + j;
                world[r.x + i][r.y + j] = Tileset.FLOOR;
                roomsCoord[floorNum][0] = coord[0];
                roomsCoord[floorNum][1] = coord[1];
                floorNum = floorNum + 1;
                }
        }
        addWall(r, world);
        return true;
    }

    public static void addWall(room r,TETile[][] world) {
        int[] coord = new int[2]; //store the current coordinates of the wall
        for (int i = 0; i < r.a + 2; i++) {
            coord[0] = r.x - 1 + i;
            coord[1] = r.y - 1;
            if (!room.overlapFloor(coord)) {
                world[r.x - 1 + i][r.y - 1] = Tileset.WALL;
                wallsCoord[wallNum][0] = coord[0];
                wallsCoord[wallNum][1] = coord[1];
                wallNum++;
            }
            coord[1] = r.y + r.b;
            if (!room.overlapFloor(coord)) {
                world[r.x - 1 + i][r.y + r.b] = Tileset.WALL;
                wallsCoord[wallNum][0] = coord[0];
                wallsCoord[wallNum][1] = coord[1];
                wallNum++;
            }
        }
        for (int j = 0; j < r.b + 1; j++) {
            coord[0] = r.x - 1;
            coord[1] = r.y +j;
            if (!room.overlapFloor(coord)) {
                world[r.x - 1][r.y + j] = Tileset.WALL;
                wallsCoord[wallNum][0] = coord[0];
                wallsCoord[wallNum][1] = coord[1];
                wallNum++;
            }
            coord[0] = r.x + r.a;
            if (!room.overlapFloor(coord)) {
                world[r.x + r.a][r.y + j] = Tileset.WALL;
                wallsCoord[wallNum][0] = coord[0];
                wallsCoord[wallNum][1] = coord[1];
                wallNum++;
            }
        }
    }

    //add a random-sized room connecting to the existing room r, return the generated room
    public static room addMoreRoom(room r, TETile[][] world) {
//        Random ran = new Random();
//        Random Ran = new Random();
        int x0 = 0, y0 = 0;
        int s = RANDOM.nextInt(4);
        switch (s) {
            case 0: x0 = r.x + RANDOM.nextInt(r.a); y0 = r.y - 1;  //min + SEED.nextInt(max - min + 1) //original: r.y -1
            case 1: x0 = r.x + RANDOM.nextInt(r.a); y0 = r.y + r.b; //original: r.y + r.b
            case 2: x0 = r.x - 1; y0 = r.y + RANDOM.nextInt(r.b);
            case 3: x0 = r.x + r.a; y0 = r.y + RANDOM.nextInt(r.b);
            default: x0 = r.x + r.a; y0 = r.y + RANDOM.nextInt(r.b);
        }
        int A = 1 + RANDOM.nextInt(6);
        int B = 1 + RANDOM.nextInt(4);
        room rm = new room(A, B, x0, y0);
        boolean added = addRoom(rm, world);
        if (added) {
            return rm;
        } else {
            return r;
        }
    }

    public static room addHallway (room r, TETile[][] world) {
        int x0 = 0, y0 = 0;
        int s = RANDOM.nextInt(4);
        switch (s) {
            case 0: x0 = r.x + RANDOM.nextInt(r.a); y0 = r.y - 1; break;//original: r.y -1
            case 1: x0 = r.x + RANDOM.nextInt(r.a); y0 = r.y + r.b; break; //original: r.y + r.b
            case 2: x0 = r.x - 1; y0 = r.y + RANDOM.nextInt(r.b); break;
            case 3: x0 = r.x + r.a; y0 = r.y + RANDOM.nextInt(r.b); break;
            default: x0 = r.x + RANDOM.nextInt(r.a); y0 = r.y + r.b;
        }
        int A = 5 + RANDOM.nextInt(10);
        int ran1 = RANDOM.nextInt(2);
        room hallway;
        if (ran1 == 0) {
            hallway = new room(A, 1, x0, y0);
            //System.out.println(x0 + " " + y0);  //for test
        } else {
            hallway = new room(1, A, x0, y0);
            //System.out.println(x0 + " " + y0);  //for test
        }
        //System.out.println(ran1); //for test
        boolean added = addRoom(hallway, world);
        if (added) {
            return hallway;
        } else {
            return r;
        }

    }
    //add N more rooms and N more hallways started from the existing room
    public static room addRooms1(room r, int N, TETile[][] world) {
        room rx = r;
        for (int i = 0; i < N; i++) {
            rx = addHallway(rx, world);
            rx = addHallway(rx, world);
            rx = addMoreRoom(rx, world);
        }
        return rx;
    }
    //add some more rooms or hallways started from the existing room
    public static room addRooms2(room r, int N, TETile[][] world) {
        room rx = r;
        room rx2 = r;
//        Random rand;
        int ranX;
        for (int i = 0; i < N; i++) {
//            rand = new Random();
            ranX= RANDOM.nextInt(2);
            switch (ranX) {
                case 0: addHallway(rx, world); addHallway(rx, world); rx = addHallway(rx, world); break;
                default: addHallway(rx, world); rx2 = addMoreRoom(rx, world); rx = addHallway(rx, world);
            }
        }
        return rx;
    }


    public static TETile[][] worldGeneration() {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize raw world with nothing
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // fills in a with rooms
        room r1 = new room(3,5,10,5);
        addRoom(r1, world);
        room r2 = addRooms1(r1, 50, world);
        addPlayer(r2, world);
        room r3 = addRooms2(r1, 50, world);
        room r4 = addRooms2(r1, 50, world);
        room r5 = addRooms2(r4, 50, world);
        room r6 = addRooms2(r2, 50, world);
        room r7 = addRooms1(r3, 50, world);
        addLockedDoor(world);
//        room r8 = addRooms2(r3, 7, world);
//        room r9 = addRooms2(r4, 7, world);
//        room r10 = addRooms1(r4, 7, world);

        // draws the world to the screen
        ter.renderFrame(world);
        return world;
    }

    public static int[] addPlayer(room r, TETile[][] world) {
        int x0 = r.x + RANDOM.nextInt(r.a);
        int y0 = r.y + RANDOM.nextInt(r.b);
        world[x0][y0] = Tileset.PLAYER;
        playerPos[0] = x0;
        playerPos[1] = y0;
        return playerPos;          //return the position of the player
    }
    public static int[] addLockedDoor(TETile[][] world) {
        int x0, y0;
        int[] door = new int[2];
        int[] up = new int[2];
        int[] down = new int[2];
        for (int i = wallNum - 1; i > -1; i--) {
            x0 = wallsCoord[i][0];
            y0 = wallsCoord[i][1];
            door[0] = x0;
            door[1] = y0;
            up[0] = x0;
            up[1] = y0 + 1;
            down[0] = x0;
            down[1] = y0 - 1;
            if (!room.overlapFloor(door) && room.overlapFloor(up) && !room.overlapFloor(down) && !room.overlapWall(down)) {
                world[x0][y0] = Tileset.LOCKED_DOOR;
                doorPos[0] = x0;
                doorPos[1] = y0;
                return doorPos;
            }
        }
        return doorPos;
    }

    public static int[] movePlayer(char c, TETile[][] world) {
        int[] newPos = new int[2];
        int[] pos = playerPos;
        switch (c) {
            case 'W': c = 'w';
            case 'w': newPos[0] = pos[0]; newPos[1] = pos[1] + 1; break;
            case 'S': c = 's';
            case 's': newPos[0] = pos[0]; newPos[1] = pos[1] - 1; break;
            case 'A': c = 'a';
            case 'a': newPos[0] = pos[0] - 1; newPos[1] = pos[1]; break;
            case 'D': c = 'd';
            case 'd': newPos[0] = pos[0] + 1; newPos[1] = pos[1]; break;
            default: newPos[0] = pos[0]; newPos[1] = pos[1];
        }
        if (win(newPos)) {
            world[pos[0]][pos[1]] = Tileset.FLOOR;
            world[newPos[0]][newPos[1]] = Tileset.UNLOCKED_DOOR;
        } else if(room.overlapFloor(newPos)) {
            world[pos[0]][pos[1]] = Tileset.FLOOR;
            world[newPos[0]][newPos[1]] = Tileset.PLAYER;
            playerPos[0] = newPos[0];
            playerPos[1] = newPos[1];
        }
        return newPos;
    }

    public static boolean win(int[] coord) {
        win = Arrays.equals(coord, doorPos);
        return win;
    }

    public static void main(String[] args) {
        worldGenerator wg = new worldGenerator(1234);
        TETile[][] world1 = wg.worldGeneration();
    }

}
