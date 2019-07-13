import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.TreeSet;

//class used to model the GUI for inputting details about each point
public class InputDetailsWindow extends JFrame implements ActionListener
{
  private Container contents = getContentPane();
  private JTextField nameInputField = new JTextField();
  private JTextField locationInputField = new JTextField();
  private JTextField typeInputField = new JTextField();
  private JButton confirmDetailsButton = new JButton("Confirm Details");
  private String name = "";
  private String location = "";
  private String typeOfDevice = "";
  private int xCoordinate = 0;
  private int yCoordinate = 0;
  private static Set<MapPoint> setOfMapPoints = new TreeSet<MapPoint>();

  public InputDetailsWindow()
  {
    setTitle("Input Details");
    contents.setLayout(new GridLayout(0,2));
    contents.add(new JLabel("Name: "));
    contents.add(nameInputField);
    contents.add(new JLabel("Location: "));
    contents.add(locationInputField);
    contents.add(new JLabel("Type of Device: "));
    contents.add(typeInputField);
    contents.add(confirmDetailsButton);
    confirmDetailsButton.addActionListener(this);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    pack();
  }//InputDetailsWindow constructor

  @Override
  public void actionPerformed(ActionEvent event)
  {
    //executed when the button to confirm submission is pressed
    if(event.getSource() == confirmDetailsButton)
    {
      name = nameInputField.getText();
      location = locationInputField.getText();
      typeOfDevice = typeInputField.getText();
      //creates a new map point when the data is submitted
      //x and y coordinates are assigned to the xCoordinate and yCorrdinate varaibles when the point is first placed (clicked)
      MapPoint mapPoint = new MapPoint(xCoordinate, yCoordinate, name, location, typeOfDevice);

      setOfMapPoints.add(mapPoint);
      dispose();
    }// if
  }//actionPerformed

  //mutator method for the x and y coordinates of the mouse click (location of a point to make)
  public void setCoordinates(int xValue, int yValue)
  {
    xCoordinate = xValue;
    yCoordinate = yValue;
  }//setCoordinates

  //accessor method for getting access to the set of MapPoints
  public static Set<MapPoint> getSetOfMapPoints()
  {
    return setOfMapPoints;
  }//getSetOfMapPoints

}//InputDetailsClass