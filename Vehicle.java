import java.awt.*;

public class Vehicle {


    private VehicleStates vehicleState;
    private Point position;

    public Vehicle(VehicleStates vehicleState, Point position) {
        this.vehicleState = vehicleState;
        this.position = position;
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
