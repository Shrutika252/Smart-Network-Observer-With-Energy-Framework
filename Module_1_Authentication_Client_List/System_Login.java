import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
//==================================================================================================================================================================
class System_Login extends JFrame implements ActionListener
{
  JLabel lblPass,lbl;

  JButton changePass, login;

  JProgressBar jprgbar;

  JPanel panel,panel1;

  static int width=300,height=200;
  static public JPasswordField txtPass,txtConPass;
static String path="System_Login.dat";
  private GridBagLayout gbl;

  private GridBagConstraints gbc;


  public System_Login()
  {
   try
   {

     File file = new File(path);           // create file object

	 if(!(file.exists()))
	 {
	   DataOutputStream dout = new DataOutputStream(new FileOutputStream(path));
	   dout.writeUTF("Smart");
	   dout.close();
	 }

	 lblPass= new JLabel("Enter Password",JLabel.LEFT);  // create lable with alignment
	 lblPass.setForeground(Color.black);       //Text Color

	 txtPass= new JPasswordField(10);
	 txtPass.setEchoChar('$');

	 login = new JButton("Login");                      // Button
	 changePass = new JButton("Change Password");       // Creation

     jprgbar = new JProgressBar();
 	 jprgbar.setOrientation(JProgressBar.HORIZONTAL);   // spin style
	 jprgbar.setBackground(new Color(212,223,255));

	 jprgbar.setFont(new java.awt.Font("Algerian",Font.BOLD,28 ));
	 jprgbar.setForeground(Color.black);                // Spindle color

	 jprgbar.setToolTipText("Smart_Network");
	 jprgbar.setIndeterminate(true);  //Show the spinning
	 jprgbar.setString("  SMART-NETWORK ");
	 jprgbar.setStringPainted(true);  // Show String with FONT & Color ,

	 panel=new JPanel();

	 gbl= new GridBagLayout();
	 gbc= new GridBagConstraints();

	 panel.setLayout(gbl);

	 gbc.weighty= 2;      // divide vertical space into 2 parts

	 gbl.setConstraints(lblPass, gbc);
	 panel.add(lblPass);

	 gbl.setConstraints(txtPass, gbc);
	 panel.add(txtPass);

	 gbc.gridx= 0;         // locate first part
	 gbl.setConstraints(login, gbc);
	 panel.add(login);

	 gbc.gridx= 1;        // locate second part
	 gbl.setConstraints(changePass, gbc);
	 panel.add(changePass);

	 jprgbar.enable(true);

	 panel1= new JPanel();
	 panel1.add(jprgbar);

	 panel1.setBackground(new Color(212,223,255));
	 panel.setBackground(new Color(212,223,255));

	 getContentPane().add(panel);
	 getContentPane().add(panel1,"North");
	 setSize(width, height);
	 setResizable(false);

	 Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
	 setLocation((d.width- width)/2, (d.height- height)/2);
	 setTitle("System_Login");

	 txtPass.addActionListener(this);
	 changePass.addActionListener(this);
	 login.addActionListener(this);

	}
	catch(Exception e){}
  }

//===================================================================================================================================================================

  public void actionPerformed(ActionEvent e)
  {
 	 Object obj = e.getSource();

	 String pass = txtPass.getText();

	 if(obj == login)
	 {
	  try
	  {
		if(checkPassword())
		{
	 	  setVisible(false);

          System.out.println("!!! Welcome  !!! ");

            Net_Scan tl=new Net_Scan();
            tl.setVisible(true);

		}
		else
		{
		  JOptionPane.showMessageDialog(null,"Password does not matches with original password","Wrong User",JOptionPane.ERROR_MESSAGE);
		  txtPass.setText("");
		}
	  }
	   catch(Exception e1){}
	 }
	 else
	   changePassword();
   }

//===================================================================================================================================================================

   public boolean checkPassword()
   {
	  String original = null;
	  try
	  {
	 	DataInputStream dout = new DataInputStream(new FileInputStream(path));
	 	original = dout.readUTF();
	  }
	  catch(Exception e){}
	  if(txtPass.getText().equals(original))
	 	return true;
	  else
	    return false;
   }

//===================================================================================================================================================================

   public void changePassword()
   {
	try
	{
	  DataInputStream din = new DataInputStream(new FileInputStream("System_Login.dat"));        // Open Secure file
	  String pass = din.readUTF();           //Read Password
	  din.close();

	  final JFrame frame = new JFrame("Change Password");

	  JPanel panel = new JPanel();

	  JLabel lblOldPass = new JLabel("Old Password ");          // create label
	  JLabel lblNewPass = new JLabel("New Password ");          // for password
	  JLabel lblConPass = new JLabel("Confirm Password ");      // input and verification

	  final JPasswordField txtOldPass = new JPasswordField(15);  // create TextArea
	  final JPasswordField txtNewPass = new JPasswordField(15);  // for password entry
	  final JPasswordField txtConPass = new JPasswordField(15);  // and conform password

	  JButton btnOk = new JButton("Change");
	  JButton btnCancel = new JButton("Cancel");

	  panel.setBackground(new Color(212,223,255));

	  gbl= new GridBagLayout();
	  gbc= new GridBagConstraints();

	  panel.setLayout(gbl);

	  gbc.weighty= 4;     // divide vertical space into 4 parts

	  gbc.gridx= 0;       // locate first part
	  gbl.setConstraints(lblOldPass, gbc);         // old password
	  panel.add(lblOldPass);                       // label

	  gbl.setConstraints(lblNewPass, gbc);  // New password
	  panel.add(lblNewPass);                // label

	  gbl.setConstraints(lblConPass, gbc);  // conform password
	  panel.add(lblConPass);                // label

	  gbl.setConstraints(btnOk, gbc);  // Change button
	  panel.add(btnOk);

	  gbc.gridx= 1;       //locate second part
	  gbl.setConstraints(txtOldPass, gbc);   // old password textArea
	  panel.add(txtOldPass);

	  gbl.setConstraints(txtNewPass, gbc);   // New password textArea
	  panel.add(txtNewPass);

      gbl.setConstraints(txtConPass, gbc);   // conform password textArea
	  panel.add(txtConPass);

	  gbl.setConstraints(btnCancel, gbc);  // cancel button
	  panel.add(btnCancel);

	  getContentPane().add(panel);

      frame.add(panel);

	  frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	  frame.setResizable(false);
	  frame.setLocation(400,300);
	  frame.setSize(310,200);
	  frame.setVisible(true);
	  this.setVisible(false);  // hide first window

	  btnOk.addActionListener(new ActionListener()
	  {
	   public void actionPerformed(ActionEvent event)
	   {
	    try
	    {
	 	  DataInputStream din = new DataInputStream(new FileInputStream("System_Login.dat"));
		  String orginalPass = din.readUTF();
		  din.close();

		  if(txtOldPass.getText().equals(orginalPass))
		  {
		    if(txtNewPass.getText().equals(txtConPass.getText()))
		    {
		     try
		     {
		 	   DataOutputStream dout = new DataOutputStream(new FileOutputStream("System_Login.dat"));
			   dout.writeUTF(txtNewPass.getText());
			   dout.close();

			   JOptionPane.showMessageDialog(null,"Password Changed!!!");
			   frame.setVisible(false);             // hide password window
			   new System_Login().setVisible(true); // disply first window
			 }
			 catch(Exception e){}
		    }
		    else
			{
			  JOptionPane.showMessageDialog(null,"New password does not match with confirmation password");
			}
		  }
		  else
			  JOptionPane.showMessageDialog(null,"Password does not match with the original password");
		 }
		   catch(Exception e){}
		  }
		  });

		  btnCancel.addActionListener(new ActionListener()
		  {
			 public void actionPerformed(ActionEvent event)
			 {
				frame.setVisible(false);              //hide password window
				new System_Login().setVisible(true);  // show first window
			 }
		   });
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

//===================================================================================================================================================================

	public static void main(String args[])
	{
		new System_Login().setVisible(true);
	}
}

//===================================================================================================================================================================
