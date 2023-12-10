import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;

public class MazeConfigTest {

    @Test
    public void testTxtToMaze() throws IOException {
        File file = new File("/Users/Forsythe/Desktop/dev_start_jetbrains/IdeaProjects/cproj-vendredi-groupe1/src/main/resources/config/testMap1.txt");
        MazeConfig config = MazeConfig.txtToMaze(file);

        assertNotNull(config);
        // Peut-être considérer d'autres assertions basées sur le contenu attendu du fichier testMap.txt ?? difficile pour le moment
    }

    @Test
    public void testGetCell() {
        MazeConfig config = MazeConfig.mockExample();

        assertNotNull(config);
        Cell cell = config.getCell(new IntCoordinates(0, 0));
        assertNotNull(cell);
        assertTrue(cell.northWall() && !cell.westWall() && !cell.eastWall() && !cell.southWall());
    }

    @Test
    public void testMakeGenericExample() throws IOException {
        MazeConfig config = MazeConfig.makeGenericExample(1);

        assertNotNull(config);
    }

    @Test
    public void testIsGameComplete() {
        boolean result = MazeConfig.isGameComplete();
        assertTrue(result);
    }

    @Test
    public void testResetItems() throws IOException {
        MazeConfig config = MazeConfig.makeGenericExample(1);
        // Supposons que le labyrinthe contient des items actifs initialement
        config.resetItems();

        // Vérifiez que tous les items dans toutes les cellules sont inactifs
        for (int y = 0; y < config.getHeight(); y++) {
            for (int x = 0; x < config.getWidth(); x++) {
                Cell cell = config.getCell(new IntCoordinates(x, y));
                if (cell.initialItem() != null) {
                    assertFalse(cell.initialItem().isActive());
                }
            }
        }
    }
}