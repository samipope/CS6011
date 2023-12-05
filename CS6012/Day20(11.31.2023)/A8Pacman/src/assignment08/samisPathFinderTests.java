package assignment08;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class samisPathFinderTests {

    @Test
    public void testingSmallMaze() throws IOException {
        var samiFilePath = "/Users/samanthapope/6011GitHub/Github/CS6012/Day20(11.31.2023)/A8Pacman/tinyMazeOutput.txt";
        var benFilePath = "/Users/samanthapope/6011GitHub/Github/CS6012/Day20(11.31.2023)/A8Pacman/src/assignment08/mazes/tinyMazeSol.txt";
        byte[] samiSmallTest = Files.readAllBytes(Path.of(samiFilePath));
        byte[] givenSmallTest = Files.readAllBytes(Path.of(benFilePath));
        assertArrayEquals(samiSmallTest, givenSmallTest);
    }

    @Test
    public void testingBigMaze() throws IOException {
        var samiFilePath = "/Users/samanthapope/6011GitHub/Github/CS6012/Day20(11.31.2023)/A8Pacman/bigMazeTest.txt";
        var benFilePath = "/Users/samanthapope/6011GitHub/Github/CS6012/Day20(11.31.2023)/A8Pacman/src/assignment08/mazes/bigMazeSol.txt";
        byte[] samiTest = Files.readAllBytes(Path.of(samiFilePath));
        byte[] givenTest = Files.readAllBytes(Path.of(benFilePath));
        assertArrayEquals(samiTest, givenTest);
    }

    @Test
    public void testingClassicMaze() throws IOException {
        var samiFilePath = "/Users/samanthapope/6011GitHub/Github/CS6012/Day20(11.31.2023)/A8Pacman/classicTest.txt";
        var benFilePath = "/Users/samanthapope/6011GitHub/Github/CS6012/Day20(11.31.2023)/A8Pacman/src/assignment08/mazes/classicSol.txt";
        byte[] samiTest = Files.readAllBytes(Path.of(samiFilePath));
        byte[] givenTest = Files.readAllBytes(Path.of(benFilePath));
        assertArrayEquals(samiTest, givenTest);
    }
    @Test
    public void testingMediumMaze() throws IOException {
        var samiFilePath = "/Users/samanthapope/6011GitHub/Github/CS6012/Day20(11.31.2023)/A8Pacman/mediumMazeTest.txt";
        var benFilePath = "/Users/samanthapope/6011GitHub/Github/CS6012/Day20(11.31.2023)/A8Pacman/src/assignment08/mazes/mediumMazeSol.txt";
        byte[] samiTest = Files.readAllBytes(Path.of(samiFilePath));
        byte[] givenTest = Files.readAllBytes(Path.of(benFilePath));
        assertArrayEquals(samiTest, givenTest);
    }

    @Test
    public void testingRandomMaze() throws IOException {
        var samiFilePath = "/Users/samanthapope/6011GitHub/Github/CS6012/Day20(11.31.2023)/A8Pacman/randomMazeTest.txt";
        var benFilePath = "/Users/samanthapope/6011GitHub/Github/CS6012/Day20(11.31.2023)/A8Pacman/src/assignment08/mazes/randomMazeSol.txt";
        byte[] samiTest = Files.readAllBytes(Path.of(samiFilePath));
        byte[] givenTest = Files.readAllBytes(Path.of(benFilePath));
        assertArrayEquals(samiTest, givenTest);
    }

    @Test
    public void testingUnsolvableMaze() throws IOException {
        var samiFilePath = "/Users/samanthapope/6011GitHub/Github/CS6012/Day20(11.31.2023)/A8Pacman/unsolvableTest.txt";
        var benFilePath = "/Users/samanthapope/6011GitHub/Github/CS6012/Day20(11.31.2023)/A8Pacman/src/assignment08/mazes/unsolvableSol.txt";
        byte[] samiTest = Files.readAllBytes(Path.of(samiFilePath));
        byte[] givenTest = Files.readAllBytes(Path.of(benFilePath));
        assertArrayEquals(samiTest, givenTest);
    }
    @Test
    public void testingTurnMaze() throws IOException {
        var samiFilePath = "/Users/samanthapope/6011GitHub/Github/CS6012/Day20(11.31.2023)/A8Pacman/turnTest.txt";
        var benFilePath = "/Users/samanthapope/6011GitHub/Github/CS6012/Day20(11.31.2023)/A8Pacman/src/assignment08/mazes/turnSol.txt";
        byte[] samiTest = Files.readAllBytes(Path.of(samiFilePath));
        byte[] givenTest = Files.readAllBytes(Path.of(benFilePath));
        assertArrayEquals(samiTest, givenTest);
    }


    @Test
    public void testFindStartNormal() {
        char[][] maze = {
                {'X', 'X', 'X', 'X', 'X'},
                {'X', 'S', ' ', ' ', 'X'},
                {'X', ' ', 'X', ' ', 'X'},
                {'X', ' ', ' ', 'G', 'X'},
                {'X', 'X', 'X', 'X', 'X'}
        };

        PathFinder.Point expected = new PathFinder.Point(1, 1);
        PathFinder.Point actual = PathFinder.findStart(maze);
        System.out.println(actual);
        System.out.println(expected);
        assertPointEquals(actual,expected);
    }

    @Test
    public void testFindStartNoStart() {
        char[][] maze = {
                {'X', 'X', 'X', 'X', 'X'},
                {'X', ' ', ' ', ' ', 'X'},
                {'X', ' ', 'X', ' ', 'X'},
                {'X', ' ', ' ', 'G', 'X'},
                {'X', 'X', 'X', 'X', 'X'}
        };
        //null when no start point
        assertNull(PathFinder.findStart(maze));
    }

    @Test
    public void testFindStartMultipleStarts() {
        char[][] maze = {
                {'X', 'S', 'X', 'X', 'X'},
                {'X', 'S', ' ', ' ', 'X'},
                {'X', ' ', 'X', ' ', 'X'},
                {'X', ' ', ' ', 'G', 'X'},
                {'X', 'X', 'X', 'X', 'X'}
        };

        PathFinder.Point expected = new PathFinder.Point(0, 1);
        PathFinder.Point actual = PathFinder.findStart(maze);
        assertPointEquals(expected, actual);
    }

    //wrote this helper function to see if points are equal to make testing easier
    private static void assertPointEquals(PathFinder.Point expected, PathFinder.Point actual) {
        if (expected == null) {
            assertNull(actual);
        } else {
            assertNotNull(actual);
            assertEquals(expected.x, actual.x);
            assertEquals(expected.y, actual.y);
        }

    }
    @Test
    public void testGetNeighborsOpenSpaces() {
        char[][] maze = {
                {'X', 'X', 'X', 'X', 'X'},
                {'X', ' ', 'S', ' ', 'X'},
                {'X', ' ', ' ', ' ', 'X'},
                {'X', 'G', ' ', ' ', 'X'},
                {'X', 'X', 'X', 'X', 'X'}
        };
        PathFinder.Point point = new PathFinder.Point(2, 2);
        List<PathFinder.Point> neighbors = PathFinder.getNeighbors(point, maze);
        assertEquals(4, neighbors.size());
    }

    @Test
    public void testGetNeighborsInCorner() {
        char[][] maze = {
                {'X', 'X', 'X'},
                {'X', 'S', 'X'},
                {'X', 'X', 'X'}
        };
        PathFinder.Point point = new PathFinder.Point(1, 1);
        List<PathFinder.Point> neighbors = PathFinder.getNeighbors(point, maze);
        assertEquals( 0, neighbors.size());
    }

    @Test
    public void testMarkSimplePath() {
        char[][] maze = {
                {'X', 'X', 'X', 'X', 'X'},
                {'X', 'S', ' ', ' ', 'X'},
                {'X', ' ', ' ', 'G', 'X'},
                {'X', 'X', 'X', 'X', 'X'}
        };
        Map<PathFinder.Point, PathFinder.Point> predecessors = new HashMap<>();
        predecessors.put(new PathFinder.Point(2, 3), new PathFinder.Point(1, 3));
        predecessors.put(new PathFinder.Point(1, 3), new PathFinder.Point(1, 1));

        PathFinder.markPath(maze, predecessors, new PathFinder.Point(2, 3));

        boolean b = checkPathMarked(maze, new char[][]{
                {'X', 'X', 'X', 'X', 'X'},
                {'X', 'S', '.', '.', 'X'},
                {'X', ' ', ' ', 'G', 'X'},
                {'X', 'X', 'X', 'X', 'X'}
        });
    }
    private boolean checkPathMarked(char[][] maze, char[][] expectedMaze) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] != expectedMaze[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


}
