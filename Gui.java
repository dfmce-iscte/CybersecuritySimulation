import Enums.Direction;
import Enums.Variables;
import Enums.VehicleStates;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static java.lang.Thread.sleep;

public class Gui extends JFrame {

    private final JPanel window = new JPanel(new GridLayout(1, 2));
    private final JLabel label = new JLabel();
    private final JPanel gridPanel = new JPanel(new GridLayout(Variables.N_ROWS.getValue(), Variables.N_COLS.getValue()));
    private final JButton[][] gridButtons = new JButton[Variables.N_COLS.getValue()][Variables.N_ROWS.getValue()];
    private static String statesChanges = "";

    private List<Point> centralAttractors;

    private boolean stop = true;

    public Gui(List<Vehicle> vehicles) {
        setThings();
        updateGui(vehicles);
    }

    public Gui(List<Vehicle> vehicles, List<Point> centralAttractors) {
        setThings();
        this.centralAttractors = centralAttractors;
        updateGui(vehicles);
    }

    public void updateGui(List<Vehicle> vehicles) {
        setAllWhite();
        for (Vehicle v : vehicles) {
            Point p = v.getPosition();
            ImageIcon icon = new ImageIcon(getImage(v));
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_REPLICATE);
            icon = new ImageIcon(newimg);
            gridButtons[p.x][p.y].setIcon(icon);
            //gridButtons[p.x][p.y].setBackground(getColor(v));
        }
        countVehiclesTypes(vehicles);
        setCentralAttractors(centralAttractors);
        repaint();
        while (stop) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stop = true;
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
                return "Images/NonInfectedUP.jpeg";
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
        statesChanges += "From: " + from + " To: " + to + "<br>";
    }

    private void setLabelInfo(int nInfected, int nNonInfected, int nRepaired, int nBrokenDown) {
        String[] previousInfo = label.getText().split("<br>#Infected");
        String newInfo = "<br>#Infected: " + nInfected +
                "<br>#Non Infected: " + nNonInfected + "<br>#Repaired: " + nRepaired +
                "<br>#Broken Down: " + nBrokenDown + "<br></html>";
        if (previousInfo.length == 1)
            label.setText("<html>" + newInfo);
        else
            label.setText("<html><br>From:<br>#Infected" +
                    previousInfo[previousInfo.length - 1].replace("</html>", "") +
                    "<br>" + statesChanges +
                    "<br>To:" + newInfo);
        statesChanges = "";
    }

    private void countVehiclesTypes(List<Vehicle> vehicles) {
        int countInfected = 0;
        int countNonInfected = 0;
        int countRepaired = 0;
        int countBrokenDown = 0;
        for (Vehicle v : vehicles) {
            if (v.getVehicleState() == VehicleStates.INFECTED) countInfected++;
            else if (v.getVehicleState() == VehicleStates.NON_INFECTED) countNonInfected++;
            else if (v.getVehicleState() == VehicleStates.REPAIRED) countRepaired++;
            else countBrokenDown++;
        }
        setLabelInfo(countInfected, countNonInfected, countRepaired, countBrokenDown);
    }

//    private Color getColor(Vehicle v) {
//        if (v.getVehicleState() == VehicleStates.BROKEN_DOWN)
//            return Color.RED;
//        else if (v.getVehicleState() == VehicleStates.INFECTED)
//            return Color.GREEN;
//        else if (v.getVehicleState() == VehicleStates.NON_INFECTED)
//            return Color.BLUE;
//        else if (v.getVehicleState() == VehicleStates.REPAIRED)
//            return Color.YELLOW;
//        else
//            return Color.WHITE;
//    }

    private void setAllWhite() {
        for (int i = 0; i < Variables.N_ROWS.getValue(); i++) {
            for (int j = 0; j < Variables.N_COLS.getValue(); j++) {
                gridButtons[i][j].setIcon(null);
                gridButtons[i][j].setBackground(Color.WHITE);
            }
        }
    }

    public void setCentralAttractors(List<Point> points) {
        for (Point p : points) {
            gridButtons[p.x][p.y].setBackground(Color.BLACK);
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
//        add(gridPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //System.out.println("Clicked");
                stop = false;
            }
        });
    }

//    public static void main(String[] args) {
//        Vehicle v1 = new Vehicle(VehicleStates.BROKEN_DOWN, new Point(0, 0));
//        Vehicle v2 = new Vehicle(VehicleStates.INFECTED, new Point(0, 1));
//        Vehicle v3 = new Vehicle(VehicleStates.NON_INFECTED, new Point(0, 2));
//        Vehicle v4 = new Vehicle(VehicleStates.REPAIRED, new Point(0, 3));
//        List<Vehicle> vehicles = List.of(v1, v2, v3, v4);
//        Gui a = new Gui(vehicles);
//        a.updateGui(vehicles);
//    }
}
