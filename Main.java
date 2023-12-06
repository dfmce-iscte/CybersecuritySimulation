import Enums.Probabilities;
import Enums.Variables;
import Enums.VehicleStates;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Main {

    private static List<Vehicle> vehicles;
    private static List<Point> centralAtractors;
    private static Random rand;

    public static void main(String[] args) throws InterruptedException {
        //initializeEnvironment();
        initializeEnvironmentCentralAttractors();
//        countVehiclesTypes();

        Gui gui = new Gui(vehicles,centralAtractors);
        //sleep(5000);
        for (int i = 0; i < 1000; i++) {
            for (Vehicle v : vehicles)
                v.move(vehicles);
            //dar update ao gui. Assim da próxima vez que se atualizar a gui só mudar os VehiclesStates.
            for (Vehicle v : vehicles)
                v.interact(vehicles);
            gui.updateGui(vehicles);
            System.out.println();
//            countVehiclesTypes();
           // sleep(5000);
        }
        System.out.println(vehicles);
    }

//    public static void countVehiclesTypes(){
//        int countInfected = 0;
//        int countNonInfected = 0;
//        int countRepaired = 0;
//        int countBrokenDown = 0;
//        for(Vehicle v:vehicles){
//            if(v.getVehicleState()== VehicleStates.INFECTED) countInfected++;
//            else if (v.getVehicleState()==VehicleStates.NON_INFECTED) countNonInfected++;
//            else if (v.getVehicleState()==VehicleStates.REPAIRED) countRepaired++;
//            else countBrokenDown++;
//        }
//        System.out.println("#INFECTED: " + countInfected);
//        System.out.println("#NON-INFECTED: " + countNonInfected);
//        System.out.println("#REPAIRED: " + countRepaired);
//        System.out.println("#BROKEN DOWN: " + countBrokenDown + "\n");
//    }

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

    private static void  createVehicles(boolean vehiclesWithAttractors) {
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
