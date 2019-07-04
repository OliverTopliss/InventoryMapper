import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import InventoryMapper.ChooseFileWindow;

public class InventoryMapper extends JFrame implements ActionListener
{
  private JPanel fileChooserPanel = new JPanel();
  private JButton selectFileButton = new JButton("Select File");

  public InventoryMapper()
  {
    setTitle("Inventory Mapper");
    Container contents = getContentPane();
    contents.setLayout(new BorderLayout());


    fileChooserPanel.setLayout(new FlowLayout());

    fileChooserPanel.add(selectFileButton);

    contents.add(fileChooserPanel, BorderLayout.NORTH);


    pack();
  }// InventoryMapper Constructor

  public static void main (String[] args)
  {
    InventoryMapper inventoryMapper = new InventoryMapper();
    inventoryMapper.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent event)
  {
    if (event.getSource() == selectFileButton)
    {
      ChooseFileWindow selectFileWindow = new ChooseFileWindow();


    }
  }
}