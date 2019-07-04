import java.awt.*;
import java.awt.event.ActionListener;

public class ChooseFileWindow extends JFrame implements ActionListener
{
  public ChooseFileWindow()
  {
    setTitle("Choose File");
    Container contents = getContentPane();

    contents.add(new JFileChooser("./"));
  }
}