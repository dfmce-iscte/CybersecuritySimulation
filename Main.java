import Enums.Probabilities;
import Enums.Variables;
import Enums.VehicleStates;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static List<Vehicle> vehicles;
    private static List<Point> centralAttractors;
    private static Random rand;
    public static Gui gui;
    public static Lock lock;

    public static void main(String[] args) throws InterruptedException {
        lock = new ReentrantLock();
        //initializeEnvironment();
        // gui = new Gui(vehicles);
        initializeEnvironmentCentralAttractors();
        gui = new Gui(vehicles, centralAttractors);
        for (Vehicle v : vehicles) {
            v.start();
        }
    }


    public static List<Vehicle> getAllVehicles() {
        return vehicles;
    }

    public static List<Point> getCentralAttractors() {
        return centralAttractors;
    }

    private static void initializeEnvironment() {
        vehicles = new ArrayList<>();
        rand = new Random();

        createVehicles(false);
    }

    private static void initializeEnvironmentCentralAttractors() {
        vehicles = new ArrayList<>();
        rand = new Random();
        centralAttractors = new ArrayList<>();

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
            centralAttractors.add(new_position);
        }
    }

    private static void createVehicles(boolean vehiclesWithAttractors) {
        for (int i = 0; i < Variables.N_VEHICLES.getValue(); i++) {
            double random = rand.nextDouble();
            Point newPosition = generateRandomPosition();

            if (vehiclesWithAttractors) {
                Point randomAttractor = centralAttractors.get(rand.nextInt(centralAttractors.size()));
                if (random < Probabilities.START_INFECTED.getProb())
                    vehicles.add(new Vehicle(VehicleStates.INFECTED, newPosition, randomAttractor, vehicles, centralAttractors, lock));
                else
                    vehicles.add(new Vehicle(VehicleStates.NON_INFECTED, newPosition, randomAttractor, vehicles, centralAttractors, lock));
            } else {
                if (random < Probabilities.START_INFECTED.getProb())
                    vehicles.add(new Vehicle(VehicleStates.INFECTED, newPosition, vehicles, centralAttractors, lock));
                else
                    vehicles.add(new Vehicle(VehicleStates.NON_INFECTED, newPosition, vehicles, centralAttractors, lock));
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
