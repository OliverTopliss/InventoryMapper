import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

//class that is used to model the GUI for choosing a map file to open
public class ChooseFileWindow extends JFrame implements ActionListener
{
  //default chosen file is nothing
  private String fileLocation = "";

  //choosefilewindow constrcutor
  public ChooseFileWindow()
  {
    setTitle("Choose File");
    Container contents = getContentPane();
    //deafult location to load the file chooser is in the current directory
    JFileChooser fileChooser = new JFileChooser("./");
    contents.add(fileChooser);

    //filter is applied for png and jpeg images so only these files are displayed by default
    FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("JPEG images and png images", "png", "JPEG", "jpg");
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
      System.out.println("cancel");
    }//elseIf

    setVisible(true);
    pack();
  }//ChooseFileWindow constructor

  //currently does nothing
  @Override
  public void actionPerformed(ActionEvent event)
  {


  }//actionPerformed

  //method used to get the location of the map file
  public String getFileLocation()
  {
    return fileLocation;
  }// getFileLocation

}//ChooseFileWindow class