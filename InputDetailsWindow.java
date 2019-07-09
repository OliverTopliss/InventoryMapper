import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputDetailsWindow extends JFrame implements ActionListener
{
  private Container contents = getContentPane();
  private JTextField nameInputField = new JTextField();
  private JTextField detailsInputField = new JTextField();
  private JButton confirmDetailsButton = new JButton("Confirm Details");
  public InputDetailsWindow()
  {
    setTitle("Input Details");
    contents.setLayout(new GridLayout(0,2));
    contents.add(new JLabel("Name: "));
    contents.add(nameInputField);
    contents.add(new JLabel("Details"));
    contents.add(detailsInputField);
    contents.add(confirmDetailsButton);
    pack();
  }

  @Override
  public void actionPerformed(ActionEvent event)
  {

  }
}