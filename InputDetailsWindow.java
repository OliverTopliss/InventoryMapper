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
  //used to store the object which created this form
  private InventoryMapper inventoryMapper;
  private MapPoint mapPointBeingEdited = null;

  //constructor takes a parameter to be used to interface with the caller GUI
  public InputDetailsWindow(InventoryMapper inventoryMapper)
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
    this.inventoryMapper = inventoryMapper;
  }//InputDetailsWindow constructor

  public InputDetailsWindow(InventoryMapper inventoryMapper, MapPoint mapPoint)
  {
    setTitle("Input Details");
    contents.setLayout(new GridLayout(0,2));
    contents.add(new JLabel("Name: "));
    contents.add(nameInputField);
    nameInputField.setText(mapPoint.getName());
    contents.add(new JLabel("Location: "));
    contents.add(locationInputField);
    locationInputField.setText(mapPoint.getPointLocation());
    contents.add(new JLabel("Type of Device: "));
    contents.add(typeInputField);
    typeInputField.setText(mapPoint.getTypeOfDevice());
    contents.add(confirmDetailsButton);
    confirmDetailsButton.addActionListener(this);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    pack();
    mapPointBeingEdited = mapPoint;
    this.inventoryMapper = inventoryMapper;
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
      MapPoint mapPoint = new MapPoint(xCoordinate, yCoordinate, name, location, typeOfDevice, inventoryMapper.getSelectedColour());
      //adds the new point to the set of MapPoints belonging to the main GUI

      if(mapPointBeingEdited != null)
      {
        inventoryMapper.removeMapPointFromSetOfMapPoints(mapPointBeingEdited);
        inventoryMapper.addMapPointToSetOfMapPoints(mapPoint);
      }//if
      else
      {
        inventoryMapper.addMapPointToSetOfMapPoints(mapPoint);
        inventoryMapper.placeMapPoint(xCoordinate, yCoordinate);
      }//else
      dispose();
    }//if
  }//actionPerformed

  //mutator method for the x and y coordinates of the mouse click (location of a point to make)
  public void setCoordinates(int xValue, int yValue)
  {
    xCoordinate = xValue;
    yCoordinate = yValue;
  }//setCoordinates

  //accessor method for getting access to the set of MapPoints
  /*public static Set<MapPoint> getSetOfMapPoints()
  {
    return setOfMapPoints;
  }//getSetOfMapPoints*/

}//InputDetailsClass