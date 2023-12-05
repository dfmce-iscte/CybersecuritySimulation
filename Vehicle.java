import java.awt.*;
import java.util.List;

public class Vehicle {
    private Point attractorToVisit;
    private VehicleStates vehicleState;
    private Point position;
    private Direction directionTaken;

    public Vehicle(VehicleStates vehicleState, Point position) {
        this.vehicleState = vehicleState;
        this.position = position;
        this.attractorToVisit = null;
    }

    public Vehicle(VehicleStates vehicleState, Point position, Point attractorToVisit) {
        this.vehicleState = vehicleState;
        this.position = position;
        this.attractorToVisit = attractorToVisit;
    }

    public Point getPosition() {
        return position;
    }

    public void move(List<Vehicle> vehicles) {
        if (this.vehicleState != VehicleStates.BROKEN_DOWN) {
            Direction random_dir = getRandomDirection();
            Point newPosition = checkNewPosition(new Point(
                    position.x + random_dir.x_direction,
                    position.y + random_dir.y_direction));

            if (isNewPositionClear(vehicles, newPosition)) {
                this.position = newPosition;
                if (attractorToVisit!=null && attractorToVisit.equals(this.position)) {
                    attractorToVisit = Main.getNewAttractor(attractorToVisit);
                }
            }
        }
    }

    public void interact(List<Vehicle> vehicles) {
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
        return Math.abs(a.x - a.y) + Math.abs(a.y - b.y);
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

    public VehicleStates getVehicleState() {
        return vehicleState;
    }

    private void setVehicleState(VehicleStates vehicleState) {
        System.out.println("From " + this.vehicleState + " to " + vehicleState);
        this.vehicleState = vehicleState;
    }
    public Direction getDirectionTaken() {
        return directionTaken;
    }

    public void setDirectionTaken(Direction directionTaken) {
        this.directionTaken = directionTaken;
    }
    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleState=" + vehicleState +
                ", position=" + position +
                '}';
    }
}
