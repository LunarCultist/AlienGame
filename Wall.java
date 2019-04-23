import java.util.Random;

public class Wall {

    
    private static final int WINDOWRATE = 10;

   
    private boolean window;

 
    public Wall() {
        Random rand = new Random();
        int seed = rand.nextInt(100);
        this.window = seed > (100 - WINDOWRATE);
    }

    
    @Override
    public String toString() {
        return this.window ? "O" : "#";
    }
}
