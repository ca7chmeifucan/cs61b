package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Arrays;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    public static void addHexagon(TETile[][] tiles, int start_x, int start_y, int side_length, TETile t) {
        for (int level = 1; level <= side_length*2; level++) {
            int num = numberoftile(side_length, level);
            int row_x = findrowx(start_x, level, side_length);
            int row_y = findrowy(start_y, level);
            rowadd(tiles, row_x, row_y, num, t);
        }
        for (int a = 0; a < tiles.length; a++) {
            for (int b = 0; b < tiles[0].length; b++) {
                if (tiles[a][b] == null) {
                    tiles[a][b] = Tileset.NOTHING;
                }
            }
        }
    }

    public static int findrowy(int start_y, int level) {
        return start_y+level-1;
    }

    public static int findrowx(int start_x, int level, int side_length) {
        if (level <= side_length) {
            return start_x - (level-1);
        } else {
            return start_x - (2*side_length-level);
        }
    }

    public static int numberoftile(int side_length, int level) {
        if (level <= side_length) {
            return side_length + (level-1)*2;
        } else {
            return side_length + (2*side_length-level)*2;
        }
    }

    public static void rowadd(TETile[][] tiles, int start_x, int start_y, int num, TETile t) {
        for (int i = 0; i < num; i++) {
            tiles[start_x][start_y] = t;
            start_x += 1;
        }
    }

    public static TETile[][] addmultiplehexagon(int side_length, TETile t) {
        int width = 4 + 6*(side_length-1) + 5*(side_length); //first 4 is for horizontal padding
        int height = 2 + 5*side_length*2; //first 2 is for vertical padding
        TETile[][] tiles = new TETile[width][height];
        int[][] starting_points = findallstartingpoints(side_length);
        System.out.println(Arrays.stream(starting_points).toString());
        for (int i = 0; i < starting_points.length; i++) {
            int[] point = starting_points[i];
            Random seed = new Random(i);
            TETile t_color_variant = TETile.colorVariant(t, 200, 200, 200, seed);
            addHexagon(tiles, point[0], point[1], side_length, t_color_variant);
        }
        return tiles;
    }

    public static int[][] findallstartingpoints(int side_length) {
        int[][] points = new int[19][2];
        int width = 4 + 6*(side_length-1) + 5*(side_length);
        int height = 2 + 5*side_length*2;

        int middle_x = width/2 - (side_length)/2, middle_y = 1;
        for (int i = 0; i < 5; i++) {
            points[i] = new int[] {middle_x, middle_y};
            middle_y += side_length*2;
        }

        int middle_left_x = width/2 - (side_length)/2 - 2*side_length + 1, middle_left_y = 1 + side_length;
        for (int i = 0; i < 4; i++) {
            points[i+5] = new int[] {middle_left_x, middle_left_y};
            middle_left_y += side_length*2;
        }

        int left_x = width/2 - (side_length)/2 - 4*side_length + 2, left_y = 1 + 2*side_length;
        for (int i = 0; i < 3; i++) {
            points[i+9] = new int[] {left_x, left_y};
            left_y += side_length*2;
        }

        int middle_right_x = width/2 - (side_length)/2 + 2*side_length - 1, middle_right_y = 1 + side_length;
        for (int i = 0; i < 4; i++) {
            points[i+12] = new int[] {middle_right_x, middle_right_y};
            middle_right_y += side_length*2;
        }

        int right_x = width/2 - (side_length)/2 + 4*side_length - 2, right_y = 1 + 2*side_length;
        for (int i = 0; i < 3; i++) {
            points[i+16] = new int[] {right_x, right_y};
            right_y += side_length*2;
        }
        return points;
    }

    public static void main(String[] args) {
        TETile[][] hexagon_tile = addmultiplehexagon(3, Tileset.FLOWER);

        TERenderer ter = new TERenderer();
        ter.initialize(hexagon_tile.length, hexagon_tile[0].length);

        ter.renderFrame(hexagon_tile);
    }
}
