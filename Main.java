import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static List<Vehicle> vehicles;
    private static List<Point> centralAtractors;
    private static Random rand;

    public static void main(String[] args) {
        initializeEnvironment();
//        initializeEnvironmentCentralAttractors();
        System.out.println(vehicles);

        while (true) {
            for (Vehicle v : vehicles)
                v.move(vehicles);
            for (Vehicle v : vehicles)
                v.interact(vehicles);
        }
        
    }

    public static Point getNewAttractor(Point currentAttractor) {
        Point newAttractor = centralAtractors.get(rand.nextInt(centralAtractors.size()));
        while (newAttractor.equals(currentAttractor)) {
            newAttractor = centralAtractors.get(rand.nextInt(centralAtractors.size()));
        }
        return newAttractor;
    }

    private static void initializeEnvironment() {
        vehicles = new ArrayList<>();
        rand = new Random();

        createVehicles(false);
    }

    private static void initializeEnvironmentCentralAttractors() {
        vehicles = new ArrayList<>();
        rand = new Random();
        centralAtractors = new ArrayList<>();

        createCentralAttractors();
        createVehicles(true);
    }

    private static Point generateRandomPosition() {
        int x = rand.nextInt(Variables.N_COLS.getValue());
        int y = rand.nextInt(Variables.N_ROWS.getValue());

        Point new_position = new Point(x, y);

        // System.out.println("New position "+new_position);
        while (isPositionOccupied(new_position)) {
            System.err.println("  Already exists");
            x = rand.nextInt(Variables.N_COLS.getValue());
            y = rand.nextInt(Variables.N_ROWS.getValue());
            new_position = new Point(x, y);
        }
        return new_position;
    }

    private static void createCentralAttractors() {
        for (int i = 0; i < Variables.N_ATTRACTORS.getValue(); i++) {
            Point new_position = generateRandomPosition();
            centralAtractors.add(new_position);
        }
    }

    private static void createVehicles(boolean vehiclesWithAttractors) {
        for (int i = 0; i < Variables.N_VEHICLES.getValue(); i++) {
            double random = rand.nextDouble();
            Point newPosition = generateRandomPosition();

            if (vehiclesWithAttractors) {
                Point randomAttractor = centralAtractors.get(rand.nextInt(centralAtractors.size()));
                if (random < Probabilities.START_INFECTED.getProb())
                    vehicles.add(new Vehicle(VehicleStates.INFECTED, newPosition, randomAttractor));
                else
                    vehicles.add(new Vehicle(VehicleStates.NON_INFECTED, newPosition, randomAttractor));
            } else {
                if (random < Probabilities.START_INFECTED.getProb())
                    vehicles.add(new Vehicle(VehicleStates.INFECTED, newPosition));
                else
                    vehicles.add(new Vehicle(VehicleStates.NON_INFECTED, newPosition));
            }
        }
    }

    private static boolean isPositionOccupied(Point position) {
        for (Vehicle v : vehicles) {
            if (v.getPosition().equals(position)) return true;
        }
        return false;
    }

}
