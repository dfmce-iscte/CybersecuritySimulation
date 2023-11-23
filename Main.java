import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static final int n_rows = 10;
    public static final int n_cols = 10;
    private static List<Vehicle> vehicles;
    private static Random rand;

    private static boolean is_position_occupied(Point position) {
        for (Vehicle v: vehicles) {
            if (v.getPosition().equals(position)) return true;
        }
        return false;
    }

    private static Vehicle is_position_occupied_return_vehicle(Point position) {
        for (Vehicle v: vehicles) {
            if (v.getPosition().equals(position)) return v;
        }
        return null;
    }

    private static Point generate_random_position() {
        int x = rand.nextInt(10);
        int y = rand.nextInt(10);
        Point new_position = new Point(x,y);

       // System.out.println("New position "+new_position);
        while (is_position_occupied(new_position)) {
            System.err.println("  Already exists");
            x = rand.nextInt(10);
            y = rand.nextInt(10);
            new_position = new Point(x,y);
        }
        return new_position;
    }

    private static void initialize_environment() {
        vehicles = new ArrayList<>();
        rand = new Random();
        
        for (int i = 0; i < 20; i++) {
            double random = rand.nextDouble();
            Point new_position = generate_random_position();

            // System.out.println("ADICIONAR "+new_position);
            if (random < Probabilities.START_INFECTED.getProb())
                vehicles.add(new Vehicle(VehicleStates.INFECTED,new_position));
            else
                vehicles.add(new Vehicle(VehicleStates.NON_INFECTED, new_position));
        }
    }

    public static void move_cars(){
        for (Vehicle v:vehicles){
            if(v.getVehicleState()==VehicleStates.BROKEN_DOWN){
                break;
            }
            Direction random_dir = Direction.random_Direction();
            Point new_position = new Point(
                    v.getPosition().x + random_dir.x_direction,
                    v.getPosition().y + random_dir.y_direction);

            Vehicle vehicle_on_new_position = is_position_occupied_return_vehicle(new_position);

            if (vehicle_on_new_position != null) {
                /*
                falar com o stor sobre o que acontece qd o veículo não está no estado NO_INFECTED e tenta interagir.
                O mesmo sobre o que acontece qd o veículo está no estado NO_INFECTED e tenta interagir com um carro que
                não está no estado INFECTED
                 */
                v.interact(vehicle_on_new_position);
            } else {
                switch (new_position.x) {
                    case -1:
                        new_position.x = n_cols - 1;
                        break;
                    case n_cols:
                        new_position.x = 0;
                        break;
                }
                switch (new_position.y) {
                    case -1:
                        new_position.y = n_rows - 1;
                        break;
                    case n_rows:
                        new_position.y = 0;
                        break;
                }
                v.move(new_position);
            }
        }
       // System.out.println(vehicles);
    }

    public static void main(String[] args) {
        initialize_environment();
        System.out.println(vehicles);
        for (int x=0; x<1000;x++){
            move_cars();
        }
        System.out.println(vehicles);


    }
}
