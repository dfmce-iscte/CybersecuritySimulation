import Enums.Direction;
import Enums.Variables;
import Enums.VehicleStates;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class Gui extends JFrame {

    private final JPanel window = new JPanel(new GridLayout(1, 2));
    private static final JLabel label = new JLabel();
    private final JPanel gridPanel = new JPanel(new GridLayout(Variables.N_ROWS.getValue(), Variables.N_COLS.getValue()));
    private final JButton[][] gridButtons = new JButton[Variables.N_ROWS.getValue()][Variables.N_COLS.getValue()];
    private static int nonInfectedToInfected = 0;
    private static int infectedToRepaired = 0;
    private static int repairedToInfected = 0;
    private static int infectedToBreakDown = 0;
    private static int countInfected = 0;
    private static int countNonInfected = 0;
    private static int countRepaired = 0;
    private static int countBrokenDown = 0;


    private List<Point> centralAttractors;


    public Gui(List<Vehicle> vehicles) {
        setThings();
        this.centralAttractors = null;
        updateGui(vehicles);
    }

    public Gui(List<Vehicle> vehicles, List<Point> centralAttractors) {
        setThings();
        this.centralAttractors = centralAttractors;
        updateGui(vehicles);
    }

    public void updateCar(Vehicle vehicle, Point oldPosition) {
        ImageIcon icon = new ImageIcon(getImage(vehicle));
        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_REPLICATE);
        icon = new ImageIcon(newImg);
        gridButtons[oldPosition.y][oldPosition.x].setIcon(null);
        gridButtons[vehicle.getPosition().y][vehicle.getPosition().x].setIcon(icon);
        gridButtons[vehicle.getPosition().y][vehicle.getPosition().x].repaint();
        gridButtons[oldPosition.y][oldPosition.x].repaint();
        setLabelInfo();
    }

    public void updateGui(List<Vehicle> vehicles) {
        setAllWhite();
        for (Vehicle v : vehicles) {
            Point p = v.getPosition();
            ImageIcon icon = new ImageIcon(getImage(v));
            Image image = icon.getImage();
            Image newImg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_REPLICATE);
            icon = new ImageIcon(newImg);
            gridButtons[p.y][p.x].setIcon(icon);
            //gridButtons[p.x][p.y].setBackground(getColor(v));
        }
        countVehiclesTypes(vehicles);
        setCentralAttractors(centralAttractors);
        repaint();
    }

    public String getImage(Vehicle v) {
        if (v.getVehicleState() == VehicleStates.BROKEN_DOWN) {
            if (Direction.UP == v.getDirectionTaken()) {
                return "Images/BrokenUP.png";
            } else if (Direction.DOWN == v.getDirectionTaken()) {
                return "Images/BrokenDOWN.png";
            } else if (Direction.LEFT == v.getDirectionTaken()) {
                return "Images/BrokenLEFT.png";
            } else {
                return "Images/BrokenRIGHT.png";
            }
        } else if (v.getVehicleState() == VehicleStates.INFECTED) {
            if (Direction.UP == v.getDirectionTaken()) {
                return "Images/InfectedUP.png";
            } else if (Direction.DOWN == v.getDirectionTaken()) {
                return "Images/InfectedDOWN.png";
            } else if (Direction.LEFT == v.getDirectionTaken()) {
                return "Images/InfectedLEFT.png";
            } else {
                return "Images/InfectedRIGHT.png";
            }
        } else if (v.getVehicleState() == VehicleStates.NON_INFECTED) {
            if (Direction.UP == v.getDirectionTaken()) {
                return "Images/NonInfectedUP.png";
            } else if (Direction.DOWN == v.getDirectionTaken()) {
                return "Images/NonInfectedDOWN.png";
            } else if (Direction.LEFT == v.getDirectionTaken()) {
                return "Images/NonInfectedLEFT.png";
            } else {
                return "Images/NonInfectedRIGHT.png";
            }
        } else {
            if (Direction.UP == v.getDirectionTaken()) {
                return "Images/RepairedUP.png";
            } else if (Direction.DOWN == v.getDirectionTaken()) {
                return "Images/RepairedDOWN.png";
            } else if (Direction.LEFT == v.getDirectionTaken()) {
                return "Images/RepairedLEFT.png";
            } else {
                return "Images/RepairedRIGHT.png";
            }
        }
    }

    public static void addStateChange(VehicleStates from, VehicleStates to) {
        if (from.equals(VehicleStates.NON_INFECTED)) {
            nonInfectedToInfected++;
            countNonInfected--;
            countInfected++;
        } else if (from.equals(VehicleStates.INFECTED) && to.equals(VehicleStates.REPAIRED)) {
            infectedToRepaired++;
            countInfected--;
            countRepaired++;
        } else if (from.equals(VehicleStates.REPAIRED) && to.equals(VehicleStates.INFECTED)) {
            repairedToInfected++;
            countRepaired--;
            countInfected++;
        } else if (from.equals(VehicleStates.INFECTED) && to.equals(VehicleStates.BROKEN_DOWN)) {
            infectedToBreakDown++;
            countInfected--;
            countBrokenDown++;
        }
    }

    private static void setLabelInfo() {
        String countInfo = "<br>#Infected: " + countInfected +
                "<br>#Non Infected: " + countNonInfected + "<br>#Repaired: " + countRepaired +
                "<br>#Broken Down: " + countBrokenDown;
        String statesChangesInfo = "<br>#From Non Infected to Infected: " + nonInfectedToInfected +
                "<br>#From Infected to Repaired: " + infectedToRepaired +
                "<br>#From Infected to Broken Down: " + infectedToBreakDown +
                "<br>#From Repaired to Infected: " + repairedToInfected;
        label.setText("<html>" +
                "<br>" + countInfo + "<br>" + statesChangesInfo);
        label.repaint();
    }

    private static void countVehiclesTypes(List<Vehicle> vehicles) {
        for (Vehicle v : vehicles) {
            if (v.getVehicleState() == VehicleStates.INFECTED) countInfected++;
            else if (v.getVehicleState() == VehicleStates.NON_INFECTED) countNonInfected++;
            else if (v.getVehicleState() == VehicleStates.REPAIRED) countRepaired++;
            else countBrokenDown++;
        }
        setLabelInfo();
    }

    private void setAllWhite() {
        for (int i = 0; i < Variables.N_ROWS.getValue(); i++) {
            for (int j = 0; j < Variables.N_COLS.getValue(); j++) {
                gridButtons[i][j].setIcon(null);
                //gridButtons[i][j].setBackground(Color.WHITE);
            }
        }
    }

    public void setCentralAttractors(List<Point> points) {
        if (points == null) return;
        for (Point p : points) {
            gridButtons[p.y][p.x].setBackground(Color.BLACK);
        }
        repaint();
    }

    private void setThings() {
        setTitle("Grid GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        for (int i = 0; i < Variables.N_ROWS.getValue(); i++) {
            for (int j = 0; j < Variables.N_COLS.getValue(); j++) {
                gridButtons[i][j] = new JButton();
                gridButtons[i][j].setBackground(Color.WHITE);
                gridButtons[i][j].setPreferredSize(new Dimension(50, 50)); // Set preferred size
                gridPanel.add(gridButtons[i][j]);
            }
        }
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        window.add(label);
        window.add(gridPanel);
        add(window, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
