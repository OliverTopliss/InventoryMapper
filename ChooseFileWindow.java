import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ChooseFileWindow extends JFrame implements ActionListener
{
  public ChooseFileWindow()
  {
    setTitle("Choose File");
    Container contents = getContentPane();
    JFileChooser fileChooser = new JFileChooser("./");
    contents.add(fileChooser);

    FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("JPEG images and png images", "png", "JPEG");
    fileChooser.setFileFilter(fileFilter);
    fileChooser.setAcceptAllFileFilterUsed(false);
    //gets the button that was pressed on the file chooser: open or cancel
    int buttonPressed = fileChooser.showOpenDialog(this);

    //if the button is the open button then...
    if(buttonPressed == fileChooser.APPROVE_OPTION)
      System.out.println("yes");
    //otherwise if the cancel button is pressed then do...
    else if(buttonPressed == fileChooser.CANCEL_OPTION)
      System.out.println("cancel");
    setVisible(true);
    pack();
  }

  @Override
  public void actionPerformed(ActionEvent event)
  {


  }

  /*@Override
  public void approveSelection()
  {

  }*/
}