import java.util.ArrayList;
import java.util.Arrays;

public class Client {
    private static ArrayList<ArrayList<Integer>> maze = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(9, 1, 1, 1, 0, 0, 0, 0)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 1, 0)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 1, 2, 0, 0, 0)),
        new ArrayList<>(Arrays.asList(1, 0, 0, 1, 1, 1, 1, 1))
    ));

    public static void main(String[] args) {
        ArrayList<String> path = findPath();
        printPath(path);
    }

    private static ArrayList<String> findPath() {
        ArrayList<String> path = new ArrayList<>();
        boolean[][] visited = new boolean[maze.size()][maze.get(0).size()];

        for (int r = 0; r < maze.size(); r++) {
            for (int c = 0; c < maze.get(0).size(); c++) {
                if (maze.get(r).get(c) == 9) {
                    if (dfs(r, c, path, visited)) {
                        return path;
                    }
                }
            }
        }

        System.out.println("No path found.");
        return path;
    }

    private static boolean dfs(int r, int c, ArrayList<String> path, boolean[][] visited) {
        if (r < 0 || c < 0 || r >= maze.size() || c >= maze.get(0).size() || visited[r][c]) {
            return false;
        }

        int cell = maze.get(r).get(c);
        if (cell != 1 && cell != 9 && cell != 2) {
            return false;
        }

        path.add("A[" + r + "][" + c + "]");
        visited[r][c] = true;

        if (cell == 2) return true; // goal reached

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] d : directions) {
            if (dfs(r + d[0], c + d[1], path, visited)) return true;
        }

        path.remove(path.size() - 1); // backtrack
        return false;
    }

    private static void printPath(ArrayList<String> path) {
        for (String coord : path) {
            System.out.println(coord);
        }
    }
}
