//===============================================================================================================================================
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class AboutFrame
{
  JFrame frame=new JFrame("About_Us");

  JPanel Title_panel,Name_panel;

  JProgressBar jprgbar;

  JLabel lblName, lbl0,lbl1,lbl2,lbl3,lbl4,lbl5,space1,space2;

  static int width=300,height=200;


  public AboutFrame()
  {

	jprgbar = new JProgressBar();                                     // Same as of
	jprgbar.setOrientation(JProgressBar.HORIZONTAL);                  // Main window
	jprgbar.setBackground(new Color(212,223,255));                    // progress Bar
	jprgbar.setFont(new java.awt.Font("Algerian",Font.BOLD,23 ));     // Design only
	jprgbar.setForeground(Color.black);                               // Diamention Change
	jprgbar.setMaximumSize(new Dimension(32767, 20));
	jprgbar.setMinimumSize(new Dimension(10, 20));
	jprgbar.setToolTipText("Smart Network");
	jprgbar.setIndeterminate(true);
	jprgbar.setBounds(new Rectangle(134, 84, 150, 23));
	jprgbar.setString("  SMART-NETWORK  ");
	jprgbar.setStringPainted(true);
	jprgbar.enable(true);

	Title_panel=new JPanel();

	Title_panel.add(jprgbar);
    frame.add(Title_panel);           // Add Title panel on main frame

    space1=new JLabel("");
    space2=new JLabel("");

  	lbl0= new JLabel("** Submitted By **");
 	lbl1= new JLabel("  1) abc     (32535)");           // Create Name
	lbl2= new JLabel("  2) abc   (1545)");          // template of student and
	lbl3= new JLabel("  3) abc  (12512)");        // guide
    lbl4= new JLabel("   ** Guided By **");
    lbl5= new JLabel("  Prof. Mr. XYZ");


    space1.setFont(new Font("Arial",1,10));
	lbl0.setFont(new Font("Eras Demi ITC",1,16));
	lbl1.setFont(new Font("Times New Roman",1,15));
	lbl2.setFont(new Font("Times New Roman",1,15));
	lbl3.setFont(new Font("Times New Roman",1,15));
	space2.setFont(new Font("Times New Roman",1,6));
	lbl4.setFont(new Font("Eras Demi ITC",1,16));
	lbl5.setFont(new Font("Times New Roman",1,15));

	Name_panel=new JPanel();         //crete name panel

	Name_panel.setLayout(new GridLayout(8,1));
    Name_panel.add(space1);
	Name_panel.add(lbl0);
	Name_panel.add(lbl1);             // add component on
	Name_panel.add(lbl2);             // name panel
	Name_panel.add(lbl3);
    Name_panel.add(space2);
    Name_panel.add(lbl4);
    Name_panel.add(lbl5);

	frame.add(Name_panel);         // Add Name panel on main frame

    Dimension d= Toolkit.getDefaultToolkit().getScreenSize();                // get screen diamention
    frame.setBounds((d.width- width)/2, (d.height- height)/2,230,240);       // and disply frmae

	frame.setResizable(false);
	frame.setLayout(new FlowLayout(FlowLayout.CENTER,70,0));
	frame.setVisible(true);
	frame.addWindowListener(new WindowAdapter()
	{
	 public void windowClosing(WindowEvent e)
	 {
	   xcopy.frame.setEnabled(true);
	   frame.setVisible(false);
	 }
	});
  }
}

//===============================================================================================================================================
