import java.awt.*;
import java.util.List;

public class Vehicle {



    private List<Point> place_to_visit;

    private List<Point> places_visited;
    private VehicleStates vehicleState;
    private Point position;

    public Vehicle(VehicleStates vehicleState, Point position, List<Point> place_to_visit) {
        this.vehicleState = vehicleState;
        this.position = position;
        this.place_to_visit= place_to_visit;
    }

    public Point getPosition() {
        return position;
    }

//    public void setPosition(Point position) {
//        this.position = position;
//    }


    public void move(Point position) {
//        setPosition(point);
        this.position = position;
        new_iteration();
    }

    public List<Point> getPlace_to_visit() {
        return place_to_visit;
    }

    public void setPlace_to_visit(List<Point> place_to_visit) {
        this.place_to_visit = place_to_visit;
    }

    public void interact(Vehicle other) {
        // Define interaction logic based on infection probabilities.
        if (other.getVehicleState() == VehicleStates.INFECTED && this.vehicleState == VehicleStates.NON_INFECTED) {
            double random = Math.random();
            if (random < Probabilities.NON_INFECTED__TO_INFECTED.getProb()) {
                infected();
            }
        }
    }

    public void new_iteration() {
        if (vehicleState == VehicleStates.INFECTED) {
            double random = Math.random();
            if (random < Probabilities.INFECTED__TO_REPAIRED.getProb()) {
                repair();
            } else if (random < Probabilities.INFECTED__TO_BROKEN.getProb() + Probabilities.INFECTED__TO_REPAIRED.getProb()) {
                breakDown();
            }
        }
    }

    public void infected() {
        setVehicleState(VehicleStates.INFECTED);
    }

    public void repair() {
        setVehicleState(VehicleStates.REPAIRED);
    }

    public void breakDown() {
        setVehicleState(VehicleStates.BROKEN_DOWN);
    }

    public VehicleStates getVehicleState() {
        return vehicleState;
    }

    public void setVehicleState(VehicleStates vehicleState) {
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
