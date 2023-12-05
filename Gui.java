import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class Gui extends JFrame {
    private final JPanel gridPanel = new JPanel(new GridLayout(Variables.N_ROWS.getValue(), Variables.N_COLS.getValue()));
    private final JButton[][] gridButtons = new JButton[Variables.N_COLS.getValue()][Variables.N_ROWS.getValue()];

    public Gui(List<Vehicle> vehicles) {
        setThings();
        updateGui(vehicles);
    }

    public void updateGui(List<Vehicle> vehicles) {
        setAllWhite();
        for (Vehicle v : vehicles) {
            Point p = v.getPosition();
            ImageIcon icon = new ImageIcon("C:\\Users\\ASUS\\Downloads\\faisca.jpg");
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_REPLICATE);
            icon = new ImageIcon(newimg);
            gridButtons[p.x][p.y].setIcon(icon);
            //gridButtons[p.x][p.y].setBackground(getColor(v));
        }
        repaint();
    }

    private Color getColor(Vehicle v){
        if(v.getVehicleState()==VehicleStates.BROKEN_DOWN)
            return Color.RED;
        else if(v.getVehicleState()==VehicleStates.INFECTED)
            return Color.GREEN;
        else if(v.getVehicleState()==VehicleStates.NON_INFECTED)
            return Color.BLUE;
        else if(v.getVehicleState()==VehicleStates.REPAIRED)
            return Color.YELLOW;
        else
            return Color.WHITE;
    }

    private void setAllWhite(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                gridButtons[i][j].setIcon(null);
                gridButtons[i][j].setBackground(Color.WHITE);
            }
        }
    }

    private void setCentralAttractors(List<Point> points) {
        for (Point p : points) {
            gridButtons[p.x][p.y].setBackground(Color.BLACK);
        }
    }

    private void setThings(){
        setTitle("Grid GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gridButtons[i][j] = new JButton();
                gridButtons[i][j].setBackground(Color.WHITE);
                gridButtons[i][j].setPreferredSize(new Dimension(50, 50)); // Set preferred size
                gridPanel.add(gridButtons[i][j]);
            }
        }

        add(gridPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        Vehicle v1 = new Vehicle(VehicleStates.BROKEN_DOWN, new Point(0,0));
        Vehicle v2 = new Vehicle(VehicleStates.INFECTED, new Point(0,1));
        Vehicle v3 = new Vehicle(VehicleStates.NON_INFECTED, new Point(0,2));
        Vehicle v4 = new Vehicle(VehicleStates.REPAIRED, new Point(0,3));
        List<Vehicle> vehicles = List.of(v1,v2,v3,v4);
        Gui a= new Gui(vehicles);
        a.updateGui(vehicles);

    }
}
