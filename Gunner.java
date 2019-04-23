
public class Gunner extends Player {
  
    public Gunner(final int x, final int y) {
        super(x, y);
    }

    
    @Override
    public boolean attack(final Character enemy) {
        int x = enemy.getX();
        int y = enemy.getY();

        boolean hit = false;

        Map map = AlienGame.getGame().getMap();

        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                Alien alien = map.getAlien(i, j);
                if (alien != null) {
                    hit |= super.attack(alien);
                }
            }
        }

        return hit;
    }

    
    @Override
    public String toString() {
        return "G";
    }
}
