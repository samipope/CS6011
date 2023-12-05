package assignment08;

import java.io.*;
import java.util.*;

public class PathFinder {

    /**
     * @param inputFile
     * @param outputFile
     */
    public static void solveMaze(String inputFile, String outputFile) {
        char[][] maze = readMaze(inputFile);
        Point start = findStart(maze);
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        Queue<Point> queue = new LinkedList<>();
        Map<Point, Point> predecessors = new HashMap<>();

        queue.add(start);
        visited[start.x][start.y] = true;

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (maze[current.x][current.y] == 'G') {
                markPath(maze, predecessors, current);
                writeMaze(outputFile, maze);
                return;
            }

            for (Point neighbor : getNeighbors(current, maze)) {
                if (!visited[neighbor.x][neighbor.y]) {
                    queue.add(neighbor);
                    visited[neighbor.x][neighbor.y] = true;
                    predecessors.put(neighbor, current);
                }
            }
        }

        // no path found, give back copy of original maze
        writeMaze(outputFile, maze);
    }

    /**
     * read maze from file
     * return an array of chars that represent the maze
     *
     * @param filename
     * @return
     */
    private static char[][] readMaze(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/Users/samanthapope/6011GitHub/Github/CS6012/Day20(11.31.2023)/A8Pacman/src/assignment08/mazes/" +filename));
            String[] dimensions = br.readLine().split(" ");
            int height = Integer.parseInt(dimensions[0]);
            int width = Integer.parseInt(dimensions[1]);
            char[][] maze = new char[height][width];

            for (int i = 0; i < height; i++) {
                maze[i] = br.readLine().toCharArray(); //read in each character
            }

            br.close();
            return maze;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * find starting point 's'
     * return point object that represents the start of the maze
     *
     * @param maze
     * @return
     */
    static Point findStart(char[][] maze) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 'S') { //mazes start with S
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    /**
     * get valid numbers on each side (up, down, left, right)
     *
     * @param point
     * @param maze
     * @return list of the point objects of valid neighbors
     */
    static List<Point> getNeighbors(Point point, char[][] maze) {
        List<Point> neighbors = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // each neighbor, right down left and up

        for (int[] dir : directions) { //for each neighbor
            int newX = point.x + dir[0];
            int newY = point.y + dir[1];
            if (newX >= 0 && newX < maze.length && newY >= 0 && newY < maze[0].length
                    && maze[newX][newY] != 'X') { //check both x and y are in bounds with how big the maze is and that it is not the end
                neighbors.add(new Point(newX, newY));
            }
        }

        return neighbors;
    }

    /**
     * backtracks from the end point using map of predeccessors
     * mark the path with a period in the maze
     * @param maze
     * @param predecessors
     * @param end
     */
        static void markPath(char[][] maze, Map<Point, Point> predecessors, Point end){
            Point step = end;
            while (predecessors.containsKey(step)) {
                Point prev = predecessors.get(step);
                if (maze[prev.x][prev.y] != 'S') {
                    maze[prev.x][prev.y] = '.';
                }
                step = prev;
            }
        }

    /**
     * writes maze to file
     * @param filename
     * @param maze
     */
    private static void writeMaze (String filename,char[][] maze){
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(filename));
            pw.println(maze.length + " " + maze[0].length);
            for (char[] row : maze) {
                pw.println(new String(row));
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        }

        static class Point {
            int x, y;

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }

        }

    }

