import java.util.Random;


public class Alien extends Character {

   
    public Alien(final int x, final int y) {
        super(x, y, 1);
    }

   
    @Override
    public String toString() {
        if (isAlive()) {
            return "A"; //Lebendig
        }
        return "X"; //Tot
    }

   
    public String generateRandomMoveString(Map map) {
        Random random = new Random();
        String possibleSymbols = "wasd"; //Alle Symbole, die im MoveString enthalten sein koennen
        String moveString = "Empty"; //MoveString erstellen
        /**
         * Pruefen, ob Alien sich ueberhaupt bewegen kann, d.h., ob Richtung w|a|s|d frei ist.
         * Sonst kann sich das Alien nicht bewegen und es wird eine Meldung angezeigt
         */
        if (this.canMove(map, "w") || this.canMove(map, "a") || this.canMove(map, "s") || this.canMove(map, "d")) {
            while (!this.canMove(map, moveString)) {
                moveString = "";
                int length = random.nextInt(2) + 1; //Zufaellige Laenge und zufaellige Zeichen aus den moeglichen Symbolen
                for (int i = 0; i < length; i++) {
                    int symbol = random.nextInt(possibleSymbols.length());
                    moveString += (possibleSymbols.charAt(symbol));
                }
            }
        } else {
            moveString = " ";
            System.out.println("Das Alien kann sich nicht bewegen.");
        }
        return moveString; //Erstellter gueltiger MoveString wird zurueckgegeben
    }
}
