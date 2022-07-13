import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class Client
{
   public static final int port = 8080;

   public static void main(String a[])
  {
    while(true)
    {
  	 try
     {
          System.out.println("creating server");
          ServerSocket ss = new ServerSocket(port);
          Socket socket = ss.accept();
          System.out.println("Connected");

          InputStream sin = socket.getInputStream();
    	  OutputStream sout = socket.getOutputStream();

          DataInputStream in = new DataInputStream(sin);
          DataOutputStream out = new DataOutputStream(sout);

          Socket client = new Socket("192.168.10.2",8081);

         InputStream sin1 = client.getInputStream();
         OutputStream sout1 = client.getOutputStream();

         DataInputStream in2 = new DataInputStream(sin1);
         DataOutputStream out2 = new DataOutputStream(sout1);

         String cmd = in.readUTF();
         ss.close();
         socket.close();

        if(cmd.equals("image"))
        {
             OutputStream oout = client.getOutputStream();
             ObjectOutputStream os = new ObjectOutputStream(oout);
           //  System.out.println("getting Image");
             try
            {
                 int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
                 int HEIGHT= Toolkit.getDefaultToolkit().getScreenSize().height;
                 Robot robot;
                 Rectangle  screenRect = new Rectangle(0,0,WIDTH,HEIGHT);
                 robot =new Robot();
                 BufferedImage i= robot.createScreenCapture(screenRect);
                 Image image = i.getScaledInstance(WIDTH,HEIGHT,Image.SCALE_SMOOTH);
                 ImageIcon ii = new ImageIcon(image);

//                 System.out.println("sending Image");
                 os.writeObject(ii);
                 os.flush();
                 os.close();
                 oout.close();

//                 System.out.println("Image sent");
             }
             catch(Exception e)
             {
				 System.out.println("image excp "+e);}
             }
              else
              {
               try
               {
//                   System.out.println (cmd);
                   Process child = Runtime.getRuntime().exec(cmd);
                   InputStream lsOut = child.getInputStream();
                   InputStreamReader r = new InputStreamReader(lsOut);
                   BufferedReader in1 = new BufferedReader(r);
                   String opt = in1.readLine();

                   while ((opt = in1.readLine()) != null)
                   {
//                      System.out.println(opt);
                      out2.writeUTF(opt);
                   }
                 out2.writeUTF("bye");
               }
               catch(Exception e)
              {
                   System.out.println(e);
              }
          }
          out2.flush();
          client.close();
    }
     catch(Exception ee)
     {
          System.out.println (ee);
     }
    }
  }
}
