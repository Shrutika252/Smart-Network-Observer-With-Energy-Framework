import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;
import javax.media.MediaLocator;


public class Recorder
{
	public static int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();

	public static int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	public static int captureInterval = 200;

	public static String store = "tmp";

	public static boolean record = false;

	public static void startRecord()
	{
		Thread recordThread = new Thread()
		{
			public void run()
			{
				Robot rt;
				int cnt = 0;
				try
				{
					rt = new Robot();
					while (cnt == 0 || record)
					{
						BufferedImage img = rt.createScreenCapture(new Rectangle(screenWidth,screenHeight));
						ImageIO.write(img, "jpeg", new File("./"+store+"/"+ System.currentTimeMillis() + ".jpeg"));

						if (cnt == 0)
						{
							record = true;
							cnt = 1;
						}
			//			Thread.sleep(captureInterval);
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		};
		recordThread.start();
	}

	public static void main(String[] args) throws Exception
	{
		System.out.println("######### Starting Easy Capture Recorder #######");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		System.out.println("Your Screen [Width,Height]:" + "["+ screen.getWidth() + "," + screen.getHeight() + "]");
		Scanner sc = new Scanner(System.in);
		for(int i=0;i<5;i++)
		{
			System.out.print(".");
			//Thread.sleep(1000);
		}
		File f = new File(store);
		if(!f.exists())
		{
			f.mkdir();
		}
		startRecord();
		System.out.println("\nEasy Capture is recording now!!!!!!!");

		System.out.println("Press e to exit:");
		String exit = sc.next();
		while (exit == null || "".equals(exit) || !"e".equalsIgnoreCase(exit))
		{
			System.out.println("\nPress e to exit:");
			exit = sc.next();
		}
		record = false;
		System.out.println("Easy Capture has stopped.");
	}
}

