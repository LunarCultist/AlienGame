import java.util.Scanner;

//Main class of the game


public final class AlienGame {
    
    private Map map;

    private Scanner scanner;

    private static AlienGame instance;

    private AlienGame(int x, int y, int aliens) {
        scanner = new Scanner(System.in);
        map = new Map(x, y, aliens);
        instance = this;
    }

    public static AlienGame getGame() {
        return instance;
    }

   
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("zu wenig Parameter");
            return;
        }

        int x;
        int y;
        int aliens;

        try {
            x = Integer.parseInt(args[0]);
            y = Integer.parseInt(args[1]);
            aliens = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Die Parameter sind nicht vom Typ Integer");
            return;
        }

        if (aliens < 1 || aliens >= x * y) {
            System.out.println("Die Anzahl an Aliens ist zu groß oder zu klein");
            return;
        }

        AlienGame game = new AlienGame(x, y, aliens);
        game.play();
    }


    private int getInt() {
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        return scanner.nextInt();
    }

    /**
     * Spiele das Spiel
     */
    private void play() {
        Player player = map.getPlayer();
        while (player.isAlive()) {
            System.out.println(map);
            System.out.println("Der Spieler hat noch " + map.getPlayer().getHitpoints() + " Hitpoints");
            int x;
            int y;
            if (!player.canMove(map, "w") //Überprüft, ob Spieler sich bewegen kann
                    && !player.canMove(map, "a")
                    && !player.canMove(map, "s")
                    && !player.canMove(map, "d")) { //Keine Ausgaben erwünscht, daher null
                //Ist der Spieler nicht eingekesselt kann es losgehen, sonst muss er mit der Bewegung aussetzen
                System.out.println("Der Spieler kann sich nicht bewegen.");
            } else {
                System.out.println("Wie soll sich der Spieler bewegen. Bitte eine Zeichenfolge"
                        + "von eins bis drei Zeichen eingeben. \n w(oben), a(links), s(unten), d(rechts)");
                player.move(waitForValidInput(map));
            }
            System.out.println(map);
            do {
                System.out.println("Jetzt bitte ein Alien anvisieren.");
                System.out.println("Wohin soll der Spieler angreifen? (X−Koordinate)");
                x = getInt();
                System.out.println("Wohin soll der Spieler angreifen? (Y−Koordinate)");
                y = getInt();
            } while (!roundPlayer(x, y));

            if (!map.aliensAreAlive()) {
                System.out.println("Der Spieler hat alle Aliens besiegt!");
                return;
            }

            roundAliens();
        }

        System.out.println("Der Spieler wurde besiegt!");
    }

   
    public String waitForValidInput(Map map) {
        Player player = map.getPlayer();
        String moveString = scanner.next();
        while (!player.canMove(map, moveString)) {
            System.out.println("Bitte ein gueltige Bewegungsfolge aus den Zeichen w,a,s,d eingeben. ");
            moveString = scanner.next(); //Neu abfragen
        }
        return moveString;
    }
  
    
    private boolean roundPlayer(int x, int y) {
        Alien alien = map.getAlien(x, y);
        //Der Spieler muss auf ein lebendes Alien zielen
        if (alien == null) {
            return false;
        }
        if (!alien.isAlive()) {
            return false;
        }

        if (map.getPlayer().attack(alien)) {
            System.out.println("Der Spieler hat das Alien getroffen");
        } else {
            System.out.println("Der Spieler hat das Alien verfehlt");
        }
        return true;
    }

   
    private void roundAliens() {
        for (Alien alien : map.getAliens()) {
            if (alien.isAlive()) {
                String alienMoveString = alien.generateRandomMoveString(map); //Lasse die Alien gehen
                alien.move(alienMoveString);
                System.out.println("Das Alien bei (" + alien.getX() + " ," + alien.getY() + ") greift den Spieler an");
                if (alien.attack(map.getPlayer())) { //Die Alien greifen an
                    System.out.println("Das Alien hat den Spieler getroffen");
                } else {
                    System.out.println("Das Alien hat den Spieler verfehlt");
                }
            }
        }
    }

   
    public Map getMap() {
        return map;
    }
}
