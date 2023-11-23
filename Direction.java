import java.util.Random;

public enum Direction {
    UP(1, 0),
    DOWN(-1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    final int x_direction;
    final int y_direction;

    Direction(int deltaX, int deltaY) {
        this.x_direction = deltaX;
        this.y_direction = deltaY;
    }

//    public int getX_direction() {
//        return x_direction;
//    }
//
//    public int getY_direction() {
//        return y_direction;
//    }

    public static Direction random_Direction(){
        Random random= new Random();
        return Direction.values()[random.nextInt(Direction.values().length)];
    }
}
