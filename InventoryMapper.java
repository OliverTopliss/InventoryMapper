import javax.sound.midi.SysexMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Iterator;
import java.util.Set;

//program that is used for mapping the location of devices when completing an inventory
public class InventoryMapper extends JFrame implements ActionListener, MouseListener
{

  private JPanel fileChooserPanel = new JPanel();
  private JPanel mapPanePanel = new JPanel();
  private JLayeredPane mapPane = new JLayeredPane();
  private JButton selectFileButton = new JButton("Select File");
  private ImageIcon mapImage = null;
  private JLabel mapImageLabel = new JLabel();
  private Container contents = getContentPane();
  private Iterator<MapPoint> iterator = null;

  //constructor method
  public InventoryMapper()
  {

    //gives the GUI a title, layout and adds components to it
    setTitle("Inventory Mapper");

    contents.setLayout(new BorderLayout());

    //contents.add(new JScrollPane(mapImageLabel));

    //adds a panel in the ceter to hold the map
    contents.add(mapPanePanel, BorderLayout.CENTER);

    //the panel that holds the map has no layout to allow components to be placed at coordinates
    mapPanePanel.setLayout(null);

    //the button to open the map image file is stored in a flow layout
    fileChooserPanel.setLayout(new FlowLayout());

    fileChooserPanel.add(selectFileButton);

    contents.add(fileChooserPanel, BorderLayout.NORTH);
    //the button has an action listener associated with it
    selectFileButton.addActionListener(this);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    pack();
  }// InventoryMapper Constructor


  public static void main (String[] args)
  {
    InventoryMapper inventoryMapper = new InventoryMapper();
    inventoryMapper.setVisible(true);
  }// main

  //performed when the button to open the file is clicked
  @Override
  public void actionPerformed(ActionEvent event)
  {
    if (event.getSource() == selectFileButton)
    {
      //creates a new window for choosing a file to load
      ChooseFileWindow selectFileWindow = new ChooseFileWindow();
      try
      {
        //the image of the map is stored as an image icon and created from the file selected
        mapImage = scaleImageIcon(new ImageIcon(selectFileWindow.getFileLocation()));

        System.out.println(selectFileWindow.getFileLocation());
        //the image icon is added to the label
        mapImageLabel.setIcon(mapImage);

        //the bounds of the layered pane are set which must be done for the layered pane to work correctly
        mapPane.setBounds(0, 0, mapImage.getIconWidth(), mapImage.getIconHeight());

        //the layered pane is added to the GUI in another panel
        mapPanePanel.add(mapPane);

        //the map image is added to the bottom most layer of the the layered panel
        mapPane.add(mapImageLabel, JLayeredPane.DEFAULT_LAYER);

        mapImageLabel.setBounds(0, 0, mapImage.getIconWidth(), mapImage.getIconHeight());

        //the image of the map has a mouse listener associated with it
        mapImageLabel.addMouseListener(this);

        pack();
      }//try
      //if an Illegal argument exception is made (ie adding another image as the map) then the old one is removed and the new one is added
      catch (IllegalArgumentException illegalArgumentException)
      {
        System.out.println("error caught: " + illegalArgumentException);
        mapPanePanel.remove(mapPane);
        mapPane.remove(JLayeredPane.DEFAULT_LAYER);

        mapImage = scaleImageIcon(new ImageIcon(selectFileWindow.getFileLocation()));
        //the image icon is added to the label
        mapImageLabel.setIcon(mapImage);


        mapPane.setBounds(0, 0, mapImage.getIconWidth(), mapImage.getIconHeight());
        mapPane.add(mapImageLabel, JLayeredPane.DEFAULT_LAYER);
        mapPanePanel.add(mapPane);
        pack();
      }//catch

    }// if
  }// actionPerformed

  @Override
  public void mouseClicked(MouseEvent event)
  {
    //when the mouse is clicked on the image of the map, a new image is created and added to a label
    ImageIcon dotImage = new ImageIcon("./dot.png");

    JLabel dot = new JLabel(dotImage);
    dot.setBounds(event.getX(), event.getY(), 10, 10);

    //the dot image is added to the GUI at the coordinates of the click
    mapPane.add(dot, JLayeredPane.DEFAULT_LAYER + 1, 0);
    InputDetailsWindow inputDetailsWindow = new InputDetailsWindow();
    inputDetailsWindow.setVisible(true);

    //gets the setOfMapPoints from the InputDetailsWindow
    Set<MapPoint> setOfMapPoints = InputDetailsWindow.getSetOfMapPoints();

    //creates an interator to go thorugh the set of MapPoints
    iterator = setOfMapPoints.iterator();

    //iterates through the set and checks if the point being placed is too close to another point
    //if it is too close then there is a clash and the already created MapPoint should be output
    while(iterator.hasNext())
    {
      MapPoint mapPointToCheck = iterator.next();
      System.out.println("Event: " + event.getX() + " " + event.getY());
      System.out.println(mapPointToCheck);
      boolean inXRange = mapPointToCheck.getXCoordinate() >= event.getX() - 10 && mapPointToCheck.getXCoordinate() <= event.getX() + 10;
      boolean inYRange = mapPointToCheck.getYCoordinate() >= event.getY() - 10 && mapPointToCheck.getYCoordinate() <= event.getY() + 10;
      if(inXRange && inYRange)
      {
        System.out.println(mapPointToCheck + " - Collision");
      }// if
    }// while

    //sets the coordinates
    inputDetailsWindow.setCoordinates(event.getX(), event.getY());
  }// mouseClicked

  //methods don't currently do anything
  @Override
  public void mouseEntered(MouseEvent event)
  {
  }// mouseEntered

  @Override
  public void mouseExited(MouseEvent event)
  {
  }// mouseExited

  @Override
  public void mousePressed(MouseEvent event)
  {
  }// mousePressed

  @Override
  public void mouseReleased(MouseEvent event)
  {
  }// mouseReleased


  //method that is called which will scale the selected image icon to a specific size.
  private ImageIcon scaleImageIcon(ImageIcon imageIconToScale)
  {
    Image imageForScaling = imageIconToScale.getImage();
    Image scaledImage = imageForScaling.getScaledInstance(800, 900, Image.SCALE_DEFAULT);
    System.out.println("scaled");
    return new ImageIcon(scaledImage);
  }//scaleImageIcon


  //used to get a reference to the button to select a file
  public JButton getSelectFileButton()
  {
    return selectFileButton;
  }// getSeledtedFileButton

}// InventoryMapper Class