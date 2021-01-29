package ryuuri.mapgen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CelluralMapHandlerTest {
    private CelluralMapHandler cell;

    @BeforeEach
    @Tag("UnitTest")
    public void setUp() {
        cell = new CelluralMapHandler(20, 20, 40, 0, 555);
    }

    @Test
    @Tag("UnitTest")
    void correctStringOutput() {
        String output = cell.mapToString();
        String expected =
                "#...#.....##...#....\n" +
                "....#....##...##....\n" +
                "##......#....#......\n" +
                "#....##...##..#..#..\n" +
                "#.##...#.#....##.#..\n" +
                "#.###.#.###.#..#.#.#\n" +
                "..#.#..###.#........\n" +
                "...#.##.##..####..##\n" +
                "...###...#.......#.#\n" +
                "###.......##...#.##.\n" +
                "##...#...###.##...#.\n" +
                "#..##...#....#.#..#.\n" +
                ".##..#..######..###.\n" +
                "..##.....###....#...\n" +
                "..###.##...#...#...#\n" +
                "#.#.##..##..##...##.\n" +
                "#.#...#..#........##\n" +
                "#....##........#..##\n" +
                "..##.#.#.#.#..##.##.\n" +
                "#.#....##..#..#....#\n";

        assertEquals(output, expected);
    }

    @Test
    @Tag("UnitTest")
    public void correctAmountOfAliveNeighbours() {
        assertEquals(cell.countAliveNeighbours(0,0), 6);
        assertEquals(cell.countAliveNeighbours(10,10), 5);
    }

    @Test
    @Tag("UnitTest")
    public void correctStringOutputOnThreeSimulationSteps() {
        cell.doSimulationStep(3);
        String output = cell.mapToString();
        String expected =
                "######..#########..#\n" +
                "##.......##...##....\n" +
                "##...........#......\n" +
                "###.......#...#.....\n" +
                "####...####...###...\n" +
                "###########....#...#\n" +
                "############.......#\n" +
                "#..########.#.....##\n" +
                "#..###...##......###\n" +
                "###.......##.....###\n" +
                "####.....######..###\n" +
                "#####...############\n" +
                "####....######..####\n" +
                "####....#####...####\n" +
                "############.....###\n" +
                "###########......###\n" +
                "###.###..#........##\n" +
                "###.###........#.###\n" +
                "########......######\n" +
                "####################\n";

        assertEquals(output, expected);
    }

    @Test
    @Tag("UnitTest")
    public void correctStringOutputOn10000SimulationSteps() {
        cell.doSimulationStep(10000);
        String output = cell.mapToString();
        String expected =
                "######..#########..#\n" +
                "####.....#######....\n" +
                "###..........###....\n" +
                "###.......#...##....\n" +
                "####...####...###...\n" +
                "###########....#...#\n" +
                "############.......#\n" +
                "############......##\n" +
                "######..####.....###\n" +
                "#####....####...####\n" +
                "#####....###########\n" +
                "######..############\n" +
                "####################\n" +
                "#############...####\n" +
                "############.....###\n" +
                "###########......###\n" +
                "##########......####\n" +
                "##########.....#####\n" +
                "###########...######\n" +
                "####################\n";

        assertEquals(output, expected);
    }
}