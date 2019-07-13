import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.rmi.MarshalledObject;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

//program that is used for mapping the location of devices when completing an inventory
public class InventoryMapper extends JFrame implements ActionListener, MouseListener
{

  private JPanel fileChooserPanel = new JPanel();
  private JPanel mapPanePanel = new JPanel();
  private JPanel displayDataPanel = new JPanel();

  private JLabel nameLabel = new JLabel("Name: ");
  private JLabel locationLabel = new JLabel("location: ");
  private JLabel typeLabel = new JLabel("Type of Device: ");
  private JLabel mapImageLabel = new JLabel();

  private JLayeredPane mapPane = new JLayeredPane();

  private JButton selectFileButton = new JButton("Select File");
  private JButton saveButton = new JButton("Save");
  private JButton loadFileButton = new JButton("Load");

  private ImageIcon mapImage = null;
  private String mapFileLocation = "";

  private Container contents = getContentPane();
  private Iterator<MapPoint> iterator = null;
  private String currentInventoryMapFileBeingEdited = "";

  //used for reading and writing to the file
  private PrintWriter lineToFileWriter = null;
  private FileWriter characterToFileWriter = null;
  private BufferedReader lineFromFileReader = null;
  private FileReader characterFromFileReader = null;

  private Set<MapPoint> setOfMapPoints = new TreeSet<MapPoint>();

  private boolean firstSave = true;

  //constructor method
  public InventoryMapper()
  {
    //gives the GUI a title, layout and adds components to it
    setTitle("Inventory Mapper");

    contents.setLayout(new BorderLayout());

    //adds a panel in the ceter to hold the map
    contents.add(mapPanePanel, BorderLayout.CENTER);
    //adds the panel for showing the data on
    contents.add(displayDataPanel, BorderLayout.EAST);

    //the panel that holds the map has no layout to allow components to be placed at coordinates
    mapPanePanel.setLayout(null);
    displayDataPanel.setLayout(new GridLayout(0,1));
    displayDataPanel.add(nameLabel);
    displayDataPanel.add(locationLabel);
    displayDataPanel.add(typeLabel);

    //the button to open the map image file is stored in a flow layout
    fileChooserPanel.setLayout(new FlowLayout());

    fileChooserPanel.add(selectFileButton);
    fileChooserPanel.add(saveButton);
    fileChooserPanel.add(loadFileButton);

    contents.add(fileChooserPanel, BorderLayout.NORTH);
    //the button has an action listener associated with it
    selectFileButton.addActionListener(this);
    saveButton.addActionListener(this);
    loadFileButton.addActionListener(this);
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
      ChooseFileWindow selectFileWindow = new ChooseFileWindow(new FileNameExtensionFilter("JPEG images and png images", "png", "JPEG", "jpg"));
      try
      {
        //only do this if the cancel button is not pressed on the fileChooserForm
        if(!selectFileWindow.getCancelledFileChoice())
        {
          //the image of the map is stored as an image icon and created from the file selected
          mapFileLocation = selectFileWindow.getFileLocation();
          mapImage = scaleImageIcon(new ImageIcon(mapFileLocation));

          System.out.println(mapFileLocation);
          //the image icon is added to the label
          mapImageLabel.setIcon(mapImage);

          //the bounds of the layered pane are set which must be done for the layered pane to work correctly
          mapPane.setBounds(0, 0, mapImage.getIconWidth(), mapImage.getIconHeight());

          //the layered pane is added to the GUI in another panel
          mapPanePanel.add(mapPane);

          //the map image is added to the bottom most layer of the the layered panel
          mapPane.add(mapImageLabel, JLayeredPane.DEFAULT_LAYER);

          mapImageLabel.setBounds(0, 0, mapImage.getIconWidth(), mapImage.getIconHeight());
          setPreferredSize(new Dimension(mapImage.getIconWidth(), mapImage.getIconHeight()));

          //the image of the map has a mouse listener associated with it
          mapImageLabel.addMouseListener(this);

          pack();
        }
      }//try
      //if an Illegal argument exception is made (ie adding another image as the map) then the old one is removed and the new one is added
      catch (IllegalArgumentException illegalArgumentException)
      {
        System.err.println("error caught: " + illegalArgumentException + " | " + illegalArgumentException.getCause());

        mapImage = scaleImageIcon(new ImageIcon(selectFileWindow.getFileLocation()));
        //the image icon is added to the label
        mapImageLabel.setIcon(mapImage);


        mapPane.setBounds(0, 0, mapImage.getIconWidth(), mapImage.getIconHeight());
        mapPane.add(mapImageLabel, JLayeredPane.DEFAULT_LAYER);
        mapPanePanel.add(mapPane);
        pack();
      }//catch

    }// if

    //if the save button is pressed
    else if(event.getSource() == saveButton)
    {
      //if this is the first dave, then let the user choose the file to save to
      if(firstSave)
      {
        ChooseInventoryMapFile newFileChooser = new ChooseInventoryMapFile();
        currentInventoryMapFileBeingEdited = newFileChooser.getFileLocation();
        try
        {
          characterToFileWriter = new FileWriter((new File(currentInventoryMapFileBeingEdited)));
          lineToFileWriter = new PrintWriter(characterToFileWriter);
          //store the location of the image that was used for the map so that it can be loaded later for another .imp file
          lineToFileWriter.write(mapFileLocation);
        }//try
        catch(Exception exception)
        {
          System.out.println("Error: " + exception + " " +  exception.getCause());
        }//catch
        finally
        {
          try
          {
            lineToFileWriter.close()
          }//try
          catch(Exception exception)
          {
            System.out.println("Error: " + exception + " " +  exception.getCause());
          }//catch
        }//finally

      }//if

      //try writing data to the file
      try
      {
        //use the file that the user chose previously
        //will ensure the file is overwritten every time - inefficient but there will be no duplicates
        characterToFileWriter = new FileWriter((new File(currentInventoryMapFileBeingEdited)));
        lineToFileWriter = new PrintWriter(characterToFileWriter);

        //gets the line seperator for the current running system.
        String lineSeperator = System.getProperty("line.separator");
        setOfMapPoints = InputDetailsWindow.getSetOfMapPoints();
        iterator = setOfMapPoints.iterator();
        lineToFileWriter.write(mapFileLocation + lineSeperator);
        //loops through all of the plotted MapPoints so far and writes them to the file
        while(iterator.hasNext())
        {
          MapPoint currentMapPoint = iterator.next();
          lineToFileWriter.write(currentMapPoint.getXCoordinate() + lineSeperator);
          lineToFileWriter.write(currentMapPoint.getYCoordinate() + lineSeperator);
          lineToFileWriter.write(currentMapPoint.getName() + lineSeperator);
          lineToFileWriter.write(currentMapPoint.getLocation() + lineSeperator);
          lineToFileWriter.write(currentMapPoint.getTypeOfDevice() + lineSeperator);
        }//while
        //it is not longer the first save
        firstSave = false;

      }//try
      catch(Exception exception)
      {
        System.out.println("Error: " + exception + " " +  exception.getCause());
      }//catch
      //always tries to close the Writers
      finally
      {
        try
        {
          characterToFileWriter.close();
          lineToFileWriter.close();
        }//try
        catch(Exception exception)
        {
          System.out.println("Error: " + exception.getCause());
        }//catch
      }//finally
    }//else if

    //if the load button is pressed
    else if(event.getSource() == loadFileButton)
    {
      try
      {
        //clears the mapPane
        mapPane.removeAll();
        //Creates a new JFrame for choosing a .imp file to load
        ChooseFileWindow loadFile = new ChooseFileWindow(new FileNameExtensionFilter("Inventory Mapper Files", "imp"));
        loadFile.setVisible(true);
        //gets the file to load from the JFrame
        String fileToReadLocation = loadFile.getFileLocation();
        lineFromFileReader = new BufferedReader(new FileReader(fileToReadLocation));
        //read the map file location fromt he file that is loaded
        mapFileLocation = lineFromFileReader.readLine();
        setOfMapPoints = new TreeSet<MapPoint>();
        String xCoordinateReadAsString = "";

        //while there are still records in the file to read
        //reads the file and also executes th condition simultaneously

        while((xCoordinateReadAsString = lineFromFileReader.readLine()) != null)
        {
          //gets the data from the file
          int xCoordinateRead = Integer.parseInt(xCoordinateReadAsString);
          int yCoordinateRead = Integer.parseInt(lineFromFileReader.readLine());
          String name = lineFromFileReader.readLine();
          String location = lineFromFileReader.readLine();
          String type = lineFromFileReader.readLine();
          //adds the new map point to the tree set

          setOfMapPoints.add(new MapPoint(xCoordinateRead, yCoordinateRead, name, location, type));
          placeMapPoint(xCoordinateRead, yCoordinateRead);
        }//while

        //loads the new image from the address that was stored in the file
        mapImage = scaleImageIcon(new ImageIcon(mapFileLocation));
        //the image icon is added to the label
        mapImageLabel.setIcon(mapImage);
        mapPane.setBounds(0, 0, mapImage.getIconWidth(), mapImage.getIconHeight());
        mapPane.add(mapImageLabel, JLayeredPane.DEFAULT_LAYER);
        mapPanePanel.add(mapPane);
        pack();
      }//try
      catch(Exception exception)
      {
        System.out.println("Error: " + exception + " " +  exception.getCause());
      }//catch
      finally
      {
        try
        {
          lineFromFileReader.close();
        }//try
        catch(Exception exception)
        {
          System.out.println("Error: " + exception + " " +  exception.getCause());
        }//catch
      }//finally
    }//else if
  }//actionPerformed

  @Override
  public void mouseClicked(MouseEvent event)
  {

    InputDetailsWindow inputDetailsWindow = new InputDetailsWindow();
    //gets the setOfMapPoints from the InputDetailsWindow
    setOfMapPoints = InputDetailsWindow.getSetOfMapPoints();

    //checks if the point being placed is the first point to place
    //if it is then it is marked on the map
    if(setOfMapPoints.isEmpty())
    {

      inputDetailsWindow.setVisible(true);
      placeMapPoint(event.getX(), event.getY());
    }
    //if it is not the first, then it looks for another close point
    else
    {
      //creates an interator to go thorugh the set of MapPoints
      iterator = setOfMapPoints.iterator();

      //iterates through the set and checks if the point being placed is too close to another point
      //if it is too close then there is a clash and the already created MapPoint should be output
      while (iterator.hasNext())
      {
        MapPoint mapPointToCheck = iterator.next();
        System.out.println("Event: " + event.getX() + " " + event.getY());
        System.out.println(mapPointToCheck);
        boolean inXRange = mapPointToCheck.getXCoordinate() >= event.getX() - 10 && mapPointToCheck.getXCoordinate() <= event.getX() + 10;
        boolean inYRange = mapPointToCheck.getYCoordinate() >= event.getY() - 10 && mapPointToCheck.getYCoordinate() <= event.getY() + 10;

        //if a close point is found then its details are output and the loop is exited
        if (inXRange && inYRange)
        {
          nameLabel.setText("Name: " + mapPointToCheck.getName());
          locationLabel.setText("Location: " + mapPointToCheck.getLocation());
          typeLabel.setText("Type of Device: " + mapPointToCheck.getTypeOfDevice());
          pack();
          break;
        }// if
        //otherwise if the end of the set of close points is reached then the point is placed anyway because a close point hasn't been found
        else if (!iterator.hasNext())
        {

          inputDetailsWindow.setVisible(true);

          placeMapPoint(event.getX(), event.getY());

        }//else if
      }// while
    }//else
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

  //helper method which places a point on the map at the specific coordinates
  private void placeMapPoint(int xCoordinate, int yCoordinate)
  {
    //when the mouse is clicked on the image of the map, a new image is created and added to a label
    ImageIcon dotImage = new ImageIcon("./dot.png");

    JLabel dot = new JLabel(dotImage);
    dot.setBounds(xCoordinate, yCoordinate, 10, 10);

    //the dot image is added to the GUI at the coordinates of the click
    mapPane.add(dot, JLayeredPane.DEFAULT_LAYER + 1, 0);
  }//placeMapPoint


  //used to get a reference to the button to select a file
  public JButton getSelectFileButton()
  {
    return selectFileButton;
  }// getSeledtedFileButton

}// InventoryMapper Class