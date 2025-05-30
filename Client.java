import java.util.ArrayList;
import java.util.Arrays;

public class Client {
    private static ArrayList<ArrayList<Integer>> maze = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 8, 0)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 1, 1, 1, 1, 0)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 3, 0, 0, 0, 0)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 4, 0, 0, 0, 0)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 1, 1, 1, 1, 1)),
        new ArrayList<>(Arrays.asList(0, 7, 0, 1, 0, 4, 0, 0)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0))
    ));

    private static int rows = maze.size();
    private static int cols = maze.get(0).size();
    private static ArrayList<String> path1 = new ArrayList<>();
    private static ArrayList<String> path2 = new ArrayList<>();
    private static ArrayList<String> path3 = new ArrayList<>();
    private static boolean[][] visited1 = new boolean[rows][cols];
    private static boolean[][] visited2 = new boolean[rows][cols];
    private static boolean[][] visited3 = new boolean[rows][cols];
    private static boolean foundSecondPath = false;
    private static boolean foundThirdPath = false;

    public static void main(String[] args) {
        // First path from [0][1] to [5][7]
        dfsLShaped(0, 1);

        // Try wall-to-top path (left and right walls)
        for (int r = 0; r < rows; r++) {
            if (maze.get(r).get(0) == 1 && !foundSecondPath) {
                dfsWallToTop(r, 0);
            }
            if (maze.get(r).get(cols - 1) == 1 && !foundSecondPath) {
                dfsWallToTop(r, cols - 1);
            }
        }

        // Try bottom-to-side path
        for (int c = 0; c < cols; c++) {
            if (maze.get(rows - 1).get(c) == 1 && !foundThirdPath) {
                dfsBottomToSide(rows - 1, c);
            }
        }

        printPath(path1, path2, path3);

        System.out.println("\nL-shaped Path coordinates:");
        System.out.println(path1);
        System.out.println("\nWall-to-Top Path coordinates:");
        System.out.println(path2);
        System.out.println("\nBottom-to-Side Path coordinates:");
        System.out.println(path3);
    }

    private static void dfsLShaped(int r, int c) {
        if (r < 0 || c < 0 || r >= rows || c >= cols || visited1[r][c] || maze.get(r).get(c) != 1) return;

        visited1[r][c] = true;
        path1.add("A[" + r + "][" + c + "]");

        // L-shaped DFS
        if (r == 0 && c < 3) {
            dfsLShaped(r, c + 1);
        } else if (c == 3 && r < 5) {
            dfsLShaped(r + 1, c);
        } else if (r == 5 && c >= 3 && c < 7) {
            dfsLShaped(r, c + 1);
        }
    }

    private static void dfsWallToTop(int r, int c) {
        if (r < 0 || c < 0 || r >= rows || c >= cols || visited2[r][c] || visited1[r][c] || maze.get(r).get(c) != 1 || foundSecondPath)
            return;

        visited2[r][c] = true;
        path2.add("A[" + r + "][" + c + "]");

        if (r == 0) {
            foundSecondPath = true;
            return;
        }

        dfsWallToTop(r + 1, c);
        dfsWallToTop(r - 1, c);
        dfsWallToTop(r, c + 1);
        dfsWallToTop(r, c - 1);

        if (!foundSecondPath) {
            path2.remove(path2.size() - 1);
        }
    }

    private static void dfsBottomToSide(int r, int c) {
        if (r < 0 || c < 0 || r >= rows || c >= cols || visited3[r][c] || maze.get(r).get(c) != 1 || foundThirdPath)
            return;

        visited3[r][c] = true;
        path3.add("A[" + r + "][" + c + "]");

        if (c == 0 || c == cols - 1) {
            foundThirdPath = true;
            return;
        }

        dfsBottomToSide(r + 1, c);
        dfsBottomToSide(r - 1, c);
        dfsBottomToSide(r, c + 1);
        dfsBottomToSide(r, c - 1);

        if (!foundThirdPath) {
            path3.remove(path3.size() - 1);
        }
    }

    private static void printPath(ArrayList<String> path1, ArrayList<String> path2, ArrayList<String> path3) {
        String[][] displayGrid = new String[rows][cols];

        // Fill empty
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                displayGrid[i][j] = "   ";

        // Mark path1 (L-shape)
        for (String coord : path1) {
            int r = Integer.parseInt(coord.substring(coord.indexOf('[') + 1, coord.indexOf(']')));
            int c = Integer.parseInt(coord.substring(coord.lastIndexOf('[') + 1, coord.lastIndexOf(']')));
            displayGrid[r][c] = " 1 ";
        }

        // Mark path2 (wall-to-top)
        for (String coord : path2) {
            int r = Integer.parseInt(coord.substring(coord.indexOf('[') + 1, coord.indexOf(']')));
            int c = Integer.parseInt(coord.substring(coord.lastIndexOf('[') + 1, coord.lastIndexOf(']')));
            displayGrid[r][c] = " 1 ";
        }

        // Mark path3 (bottom-to-side)
        for (String coord : path3) {
            int r = Integer.parseInt(coord.substring(coord.indexOf('[') + 1, coord.indexOf(']')));
            int c = Integer.parseInt(coord.substring(coord.lastIndexOf('[') + 1, coord.lastIndexOf(']')));
            displayGrid[r][c] = " 1 ";
        }

        // Print display
        for (int i = 0; i < rows; i++) {
            System.out.print("[");
            for (int j = 0; j < cols; j++) {
                System.out.print(displayGrid[i][j]);
                if (j < cols - 1) System.out.print(",");
            }
            System.out.println("]");
        }
    }
}
