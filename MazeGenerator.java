import java.util.PrimitiveIterator;
import java.util.Random;


public class MazeGenerator {

    
    private final int sizeX;
    private final int sizeY;
    private final Wall[][] walls;


    MazeGenerator(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        walls = new Wall[sizeY][sizeX];
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                walls[y][x] = new Wall();
            }
        }
        generateMap(0, 0);
    }

 
    private void generateMap(int x, int y) {
        int explorerX;
        int explorerY;
        walls[y][x] = null;
        //Einfache Methode für stets ungleiche Zufallszahlen
        PrimitiveIterator.OfInt random = new Random().ints(0, 4).distinct().iterator();
        for (int nr = 0; nr < 4; nr++) { //Gehe in alle Richtungen
            explorerX = x;
            explorerY = y;
            int direction = random.next(); //Gehe zufällig in eine nächste Richtung
            switch (direction) {
                case 0:
                    if (x + 2 < sizeX) { //Rechts
                        explorerX = x + 2;
                    }
                    if (walls[explorerY][explorerX] != null) { //Wenn Wand zwei Felder daneben zu finden ist...
                        walls[y][x + 1] = null; //...entferne die Zwischenwand
                        generateMap(explorerX, explorerY); //Rekursiver Aufruf mit neuen Explorerkoordinaten
                    }
                    break;
                case 1:
                    if (x - 2 >= 0) { //Links
                        explorerX = x - 2;
                    }
                    if (walls[explorerY][explorerX] != null) {
                        walls[y][x - 1] = null;
                        generateMap(explorerX, explorerY);
                    }
                    break;
                case 2:
                    if (y + 2 < sizeY) { //Unten
                        explorerY = y + 2;
                    }
                    if (walls[explorerY][explorerX] != null) {
                        walls[y + 1][x] = null;
                        generateMap(explorerX, explorerY);
                    }
                    break;
                case 3:
                    if (y - 2 >= 0) { //Oben
                        explorerY = explorerY - 2;
                    }
                    if (walls[explorerY][explorerX] != null) {
                        walls[y - 1][x] = null;
                        generateMap(explorerX, explorerY);
                    }
                    break;
            }
        }
    }

   
    public Wall[][] getMaze() {
        return walls;
    }
}
