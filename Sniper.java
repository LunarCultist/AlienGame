
public class Sniper extends Player {
    
    public Sniper(final int x, final int y) {
        super(x, y);
    }

    @Override
    public boolean attack(final Character enemy) {
        int distance = Math.abs(getX() - enemy.getX()) + Math.abs(getY() - enemy.getY());

        if (Math.random() < 3.0 / distance) {
            enemy.takeDamage(1);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "S";
    }
}
