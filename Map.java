
public class Map {
    
    private Alien[] aliens;
    private Wall[][] walls;
    private Player player;
    private int x;
    private int y;

    
    public Map(int x, int y, int aliens) {
        this.x = x;
        this.y = y;
        MazeGenerator generator = new MazeGenerator(x, y);
        this.walls = generator.getMaze();
        if (x * y - countWalls() - 1 - aliens <= 1) {
            System.out.println("Falsche Programmparameter. Es passen nicht alle Spieler und Alien auf das Feld");
            System.exit(25);
        }
        this.aliens = new Alien[aliens];
        createPlayer();
        createAliens(aliens);
    }

  
    private int countWalls() {
        int wallCount = 0;
        for (Wall[] wall : walls) {
            for (int j = 0; j < walls[0].length; j++) {
                if (wall[j] != null) {
                    wallCount++;
                }
            }
        }
        return wallCount;
    }

    
    private void createPlayer() {
        int x = randomNumber(this.x);
        int y = randomNumber(this.y);

        while (walls[y][x] != null) { //Wenn an dem Platz eine Wand ist versuche es weiter
            x = randomNumber(this.x);
            y = randomNumber(this.y);
        }
        switch (randomNumber(3)) {
            case 0:
                player = new Player(x, y);
                break;
            case 1:
                player = new Sniper(x, y);
                break;
            case 2:
                player = new Gunner(x, y);
                break;
        }
    }


    private void createAliens(int aliens) {
        int xA;
        int yA;

        for (int i = 0; i < aliens; i++) {

            do {
                xA = randomNumber(this.x);
                yA = randomNumber(this.y);
            } while (getAlien(xA, yA) != null || (xA == player.getX() && yA == player.getY()) || walls[yA][xA] != null ); //Feld muss frei sein

            Alien alien = new Alien(xA, yA);
            this.aliens[i] = alien;
        }
    }

    
    private static int randomNumber(int n) {
        return (int) (Math.random() * n);
    }

    
    public Alien getAlien(int x, int y) {
        for (Alien alien: aliens) {
            if (alien == null) {
                return null;
            }
            if (alien.getX() == x && alien.getY() == y) {
                return alien;
            }
        }
        return null;
    }

 
    @Override
    public String toString() {

        int maxNumberLength = String.valueOf(y).length(); //Größte Zahl, die dargestellt werden soll
        StringBuilder ret = new StringBuilder();
        StringBuilder lineStart = new StringBuilder();
        for (int i = 0; i <= maxNumberLength; i++) {
            lineStart.append(" "); //Dementsprechend viele Leerzeichen müssen hinzugefügt werden
        }
        ret.append(lineStart);
        for (int i = 0; i < x; i += 10) {
            ret.append(String.format("%-10d", i / 10)); //Zehnersystem oberhalb der 1er
        }

        ret.append("\n").append(lineStart);
        for (int i = 0; i < x; i++) {
            if (i >= 10) {
                ret.append(i - 10 * (i / 10));
            } else {
                ret.append(i);
            }
        }
        ret.append("\n").append(lineStart);

        for (int i = 0; i < x; i++) { //Oberste Sternchenzeile
            ret.append("*");
        }

        for (int j = 0; j < y; j++) { //y viele Zeilen
            ret.append("\n");
            StringBuilder spaces = new StringBuilder();
            while ((j + spaces.toString()).length() < maxNumberLength) { //Anpassung an Zahlentiefe
                spaces.append(" ");
            }
            ret.append(j).append(spaces).append("*");
            for (int k = 0; k < x; k++) { //x oft
                if (k == player.getX() && j == player.getY()) {
                    ret.append(player);
                } else if (!getMapObject(j, k).equals(" ")) {
                    ret.append(getMapObject(j, k)); //Hole das Objekt, welches sich hier befindet, wenn es hier eins gibt
                } else {
                    ret.append(" "); //Sonst setze ein leeres Feld ein
                }
            }
            ret.append("*");
        }

        ret.append("\n").append(lineStart);

        for (int i = 0; i < x; i++) {
            ret.append("*");
        }

        return ret.toString();
    }

   
    private String getMapObject(int j, int k) {
        if (player.getX() == k && player.getY() == j) {
            return player.toString();
        }
        for (Alien alien: aliens) {
            if (alien.getY() == j && alien.getX() == k) {
                return alien.toString();
            }
        }
        if (walls[j][k] != null) {
            return walls[j][k].toString();
        }
        return " ";
    }

   
    public boolean aliensAreAlive() {
        for (Alien alien : aliens) {
            if (alien.isAlive()) {
                return true;
            }
        }
        return false;
    }

 
    public Alien[] getAliens() {
        return aliens;
    }

    public Player getPlayer() {
        return player;
    }

 
    public boolean isWalkable(int y, int x) {
        if ((y < this.y && y >= 0 && x < this.x && x >= 0)) {
            String mapObject = getMapObject(y, x);
            return (mapObject.equals(" ") || mapObject.equals("O"));
        } else {
            return false;
        }
    }
}
