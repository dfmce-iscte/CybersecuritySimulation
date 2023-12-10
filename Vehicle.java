import Enums.Direction;
import Enums.Probabilities;
import Enums.Variables;
import Enums.VehicleStates;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;


public class Vehicle extends Thread {
    private Point attractorToVisit;
    private VehicleStates vehicleState;
    private Point position;
    private Direction directionTaken;


    private final Lock lock;
    private final List<Point> centralAttractors;

    private final List<Vehicle> vehicles;

    public Vehicle(VehicleStates vehicleState, Point position, List<Vehicle> vehicles, List<Point> centralAttractors, Lock lock) {
        this.vehicleState = vehicleState;
        this.position = position;
        this.attractorToVisit = null;
        this.vehicles = vehicles;
        this.centralAttractors = centralAttractors;
        this.lock = lock;
    }

    public Vehicle(VehicleStates vehicleState, Point position, Point attractorToVisit, List<Vehicle> vehicles, List<Point> centralAttractors, Lock lock) {
        this.vehicleState = vehicleState;
        this.position = position;
        this.attractorToVisit = attractorToVisit;
        this.vehicles = vehicles;
        this.centralAttractors = centralAttractors;
        this.lock = lock;
    }

    public Point getPosition() {
        return position;
    }


    @Override
    public void run() {
        while (true) {
            Point oldPosition = this.position;
            move(vehicles);
            interact(vehicles);
            Main.gui.updateCar(this, oldPosition);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void move(List<Vehicle> vehicles) {
        if (this.vehicleState != VehicleStates.BROKEN_DOWN) {
            Direction random_dir = getRandomDirection();
            Point newPosition = checkNewPosition(new Point(
                    position.x + random_dir.getX_direction(),
                    position.y + random_dir.getY_direction()));

            if (newPosition.equals(attractorToVisit)) {
                System.out.println("Arrived to Atractor: " + attractorToVisit);
                attractorToVisit = getNewAttractor(attractorToVisit);
                return;
            }
            lock.lock();
            if (isNewPositionClear(vehicles, newPosition)) {
                this.position = newPosition;
                this.directionTaken = random_dir;
            }
            lock.unlock();
        }
    }

    private void interact(List<Vehicle> vehicles) {
        for (Vehicle other : vehicles) {
            if (isVehicleNeighboor(other) && other.getVehicleState() == VehicleStates.INFECTED &&
                    (this.vehicleState == VehicleStates.NON_INFECTED || this.vehicleState == VehicleStates.REPAIRED)) {
                double random = Math.random();
                if (random < Probabilities.TO_INFECTED.getProb()) {
                    infect();
                }
            }
        }
        updateStatusIfVehicleInfected();
    }

    public Point getNewAttractor(Point currentAttractor) {
        Point newAttractor = centralAttractors.get(new Random().nextInt(centralAttractors.size()));
        while (newAttractor.equals(currentAttractor)) {
            newAttractor = centralAttractors.get(new Random().nextInt(centralAttractors.size()));
        }
        return newAttractor;
    }

    public VehicleStates getVehicleState() {
        return vehicleState;
    }

    public Direction getDirectionTaken() {
        return directionTaken;
    }

    private Direction getRandomDirection() {
        if (attractorToVisit == null)
            return Direction.randomDirection();
        else
            return Direction.randomDirectionWithPreferencialDirection(bestDirectionToTake());
    }

    private Direction bestDirectionToTake() {
        if (attractorToVisit.x > position.x)
            return Direction.RIGHT;
        else if (attractorToVisit.x < position.x)
            return Direction.LEFT;
        else if (attractorToVisit.y > position.y)
            return Direction.DOWN;
        else
            return Direction.UP;
    }

    private boolean isNewPositionClear(List<Vehicle> vehicles, Point newPosition) {
        if (attractorToVisit != null)
            for (Point attractor : centralAttractors) {
                if (newPosition.equals(attractor)) return false;
            }
        for (Vehicle v : vehicles) {
            if (v.getPosition().equals(newPosition)) return false;
        }
        return true;
    }

    private Point checkNewPosition(Point newPosition) {
        if (newPosition.x == -1)
            newPosition.x = Variables.N_COLS.getValue() - 1;
        else if (newPosition.x == Variables.N_COLS.getValue())
            newPosition.x = 0;

        if (newPosition.y == -1)
            newPosition.y = Variables.N_ROWS.getValue() - 1;
        else if (newPosition.y == Variables.N_ROWS.getValue())
            newPosition.y = 0;

        return newPosition;
    }

    private boolean isVehicleNeighboor(Vehicle vehicle) {
        return calculateManhattanDistance(this.position, vehicle.getPosition()) == 1;
    }

    private void updateStatusIfVehicleInfected() {
        if (vehicleState == VehicleStates.INFECTED) {
            double random = Math.random();
            if (random < Probabilities.INFECTED__TO_REPAIRED.getProb()) {
                repair();
            } else if (random < Probabilities.INFECTED__TO_BROKEN.getProb() + Probabilities.INFECTED__TO_REPAIRED.getProb()) {
                breakDown();
            }
        }
    }

    private int calculateManhattanDistance(Point a, Point b) {
        return Math.abs(b.x - a.x) + Math.abs(b.y - a.y);
    }

    private void infect() {
        setVehicleState(VehicleStates.INFECTED);
    }

    private void repair() {
        setVehicleState(VehicleStates.REPAIRED);
    }

    private void breakDown() {
        setVehicleState(VehicleStates.BROKEN_DOWN);
    }

    private void setVehicleState(VehicleStates vehicleState) {
        Gui.addStateChange(this.vehicleState, vehicleState);
        System.out.println("From " + this.vehicleState + " to " + vehicleState + " in " + position);
        this.vehicleState = vehicleState;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleState=" + vehicleState +
                ", position=" + position +
                '}';
    }
}
