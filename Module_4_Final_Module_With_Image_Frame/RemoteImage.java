import java.awt.*;
import javax.swing.*;

public class RemoteImage extends javax.swing.JFrame
{
    private javax.swing.JLabel jLabel1;

    public RemoteImage()
    {
       setTitle("Image Captured...");
       setBackground(Color.WHITE);
       getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
       initComponents();
      Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
      setSize(d.width,d.height);
  //     setSize(1000,800);
       setVisible(true);
    }

    public void updateScreen(ImageIcon ii)
    {
        jLabel1.setIcon(ii);
        repaint();
    }
     private void initComponents()
     {
        jLabel1 = new javax.swing.JLabel();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup
        (
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
            .addGap(22, 22, 22)
            .addComponent(jLabel1)
            .addContainerGap(378, Short.MAX_VALUE))
        );

        layout.setVerticalGroup
        (
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel1)
            .addContainerGap(289, Short.MAX_VALUE))
        );
        pack();
    }
}
