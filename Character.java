
public abstract class Character implements MovableObject {
    private int x;
    private int y;
    private int hitpoints;

  
    public Character(final int x, final int y, final int hitpoints) {
        this.x = x;
        this.y = y;
        this.hitpoints = hitpoints;
    }

    
    public int getX() {
        return x;
    }

    
    public int getY() {
        return y;
    }

   
    public boolean isAlive() {
        return hitpoints > 0;
    }

   
    public boolean attack(Character enemy) {
        int distance = Math.abs(x - enemy.getX()) + Math.abs(y - enemy.getY());

        if (Math.random() < 0.7 / distance) {
            enemy.takeDamage(1);
            return true;
        }
        return false;
    }

   
    public void takeDamage(int damage) {
        hitpoints -= damage;
        if (hitpoints < 0) {
            hitpoints = 0;
        }
    }

   
    public int getHitpoints() {
        return hitpoints;
    }

    
    public boolean canMove(Map map, String moveString) {
        boolean ret = true;
        int checkedX = 0;
        int checkedY = 0;
        for (int i = 0; i < moveString.length(); i++) {
            switch (moveString.charAt(i)) {
                case 'w':
                    if (!map.isWalkable(this.y - 1 + checkedY, this.x + checkedX)) {
                        ret = false;
                    }
                    checkedY--;
                    break;
                case 's':
                    if (!map.isWalkable(this.y + 1 + checkedY, this.x + checkedX)) {
                        ret = false;
                    }
                    checkedY++;
                    break;
                case 'a':
                    if (!map.isWalkable(this.y + checkedY, this.x - 1 + checkedX)) {
                        ret = false;
                    }
                    checkedX--;
                    break;
                case 'd':
                    if (!map.isWalkable(this.y + checkedY, this.x + 1 + checkedX)) {
                        ret = false;
                    }
                    checkedX++;
                    break;
                default:
                    ret = false; //Wenn ein anderes Zeichen vorkommt, kann der moveString nicht korrekt sein
            }
        }
        return ret;
    }

   
    public void move(String moveString) {
        for (int i = 0; i < moveString.length(); i++) {
            switch (moveString.charAt(i)) {
                case 'w':
                    this.y--;
                    break;
                case 's':
                    this.y++;
                    break;
                case 'a':
                    this.x--;
                    break;
                case 'd':
                    this.x++;
                    break;
                case ' ':
                    //Eingekesselt. Es folgt keine Bewegung
                    break;
            }
        }
    }
}
