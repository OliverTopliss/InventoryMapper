import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class ChooseInventoryMapFile extends JFrame
{
  //default chosen file is nothing
  private String fileLocation = "";
  private boolean cancelledFileChoice = false;

  public ChooseInventoryMapFile()
  {
    setTitle("Choose InventoryMap File to Save to");
    Container contents = getContentPane();
    //deafult location to load the file chooser is in the current directory
    JFileChooser fileChooser = new JFileChooser("./");
    contents.add(fileChooser);

    //filter is applied for png and jpeg images so only these files are displayed by default
    FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("InventoryMap Files", "imp");
    fileChooser.setFileFilter(fileFilter);

    //filter to search for all file types is disabled
    fileChooser.setAcceptAllFileFilterUsed(false);

    //gets the button that was pressed on the file chooser: open or cancel
    int buttonPressed = fileChooser.showOpenDialog(this);

    //if the button is the open button then...
    if(buttonPressed == fileChooser.APPROVE_OPTION)
    {
      System.out.println("yes");
      File selectedFile = fileChooser.getSelectedFile();

      fileLocation = selectedFile.getPath();
    }//if
    //otherwise if the cancel button is pressed then do...
    else if(buttonPressed == fileChooser.CANCEL_OPTION)
    {
      //sets a flag if the cancel button is pressed
      System.out.println("cancel");
      cancelledFileChoice = true;
    }//elseIf
    setVisible(true);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    pack();
  }

  //method used to get the location of the map file
  public String getFileLocation()
  {
    return fileLocation;
  }// getFileLocation
  public boolean getCancelledFileChoice()
  {
    return cancelledFileChoice;
  }
}