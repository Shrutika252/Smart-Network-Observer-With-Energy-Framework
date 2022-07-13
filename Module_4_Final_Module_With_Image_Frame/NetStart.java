import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JOptionPane;

public class NetStart extends javax.swing.JDialog
{
    public static Object mac_list[][] = null;
    public static Object task_list[][] = null;
    public static String title[] = {"Machine Name","IP Address","Status"};
    public static Vector compname=null;
    public static Vector tasklst = null;
    public static Hashtable ipAddrTab = null;
    public static InetAddress address =null ;
    public static int flagrow=0;
    public static int flag=0;
    public static String taskmachineIP = "";

    public NetStart()
    {
        initComponents();
        screenrefresh();

        this.setSize(600,400);
        this.setSize(580,360);
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width- 550)/2,(d.height- 400)/2);
    }

    public void screenrefresh()
    {
        getMachines();
        getIPAddress();
        jTable1.setModel(new javax.swing.table.DefaultTableModel(mac_list,title));

        Font f = new Font("Verdana",Font.BOLD,14);
        jTable1.setFont(f);

        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.sizeColumnsToFit(0);
    }

    public void getIPAddress()
    {
     try
     {
         ipAddrTab = new Hashtable();

         for(int i=0;i<compname.size();i++)
         {
           try
          {
             address = InetAddress.getByName(compname.get(i).toString());
             String tempIP[] = address.toString().split("/");
             ipAddrTab.put(tempIP[0],tempIP[1]);
           }
           catch(UnknownHostException ue)
           {
               if(compname.get(i).toString().indexOf("---------")<=-1)
                ipAddrTab.put(compname.get(i).toString(),"NO IP FOUND");
           }
           catch(Exception ee)
           {
               if(compname.get(i).toString().indexOf("---------")<=-1)
               ipAddrTab.put(compname.get(i).toString(),"NO IP FOUND");
           }
        }

       for(int i=1,j=0;i<compname.size();i++,j++)
         if(ipAddrTab.get(compname.get(i))!=null||ipAddrTab.get(compname.get(i))!="")
         {
              mac_list[j][1] = ipAddrTab.get(compname.get(i));
              mac_list[j][2] = "ALIVE";
         }
        else
        {
              mac_list[j][1] = "NO IP FOUND";
              mac_list[j][2] = "DEAD";
         }
      }
      catch(Exception e)
      {
           System.out.println("getIPAddress "+e);
       }
    }

    public void getMachines()
    {
     try
     {
         String cmd = "net view";
         Process child = Runtime.getRuntime().exec(cmd);
         InputStream lsOut = child.getInputStream();
         InputStreamReader r = new InputStreamReader(lsOut);
         BufferedReader in = new BufferedReader(r);

         String line;
         int cnt = 0;
         compname = new Vector();

         while ((line = in.readLine()) != null)
         {
             StringTokenizer token = new StringTokenizer(line,"\\");

            while(token.hasMoreElements())
            {
                 String temp = token.nextToken().toString();
                 String tempar[] = temp.split(" ");
                 compname.addElement(tempar[0]);
             }
         }
         compname.remove(0);
         compname.remove(compname.size()-1);
         mac_list = new Object[compname.size()-1][3];

        for(int i=1,j=0;i<compname.size();i++,j++)
             mac_list[j][0] = compname.get(i);
     }
     catch(Exception e)
     {
        System.out.println("getMachines() "+e);
        e.printStackTrace();
     }
  }

  private void initComponents()
  {
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
		jButton6 = new javax.swing.JButton();

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        setTitle("List of machines in network");
        setBounds(new java.awt.Rectangle(400, 200, 400, 400));

        jButton1.setIcon(new javax.swing.ImageIcon("Images\\refresh.gif"));
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Restart");
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Shutdown");
        jButton3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setText("Image");
        jButton5.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Exit");
        jButton6.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton6ActionPerformed(evt);
            }
        });


        jScrollPane1.setToolTipText("");
        jScrollPane1.setFont(new java.awt.Font("Verdana", 1, 14));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object [][]
        {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
         },
         new String [] {"Title 1", "Title 2", "Title 3", "Title 4"
      }
        )
        {
            boolean[] canEdit = new boolean [] {false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });

        jTable1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                jTable1PropertyChange(evt);
            }
        });

        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jLabel1.setText("Scan");


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(15, 15, 15)
                        .addComponent(jButton3)
                        .addGap(15, 15, 15)
                        .addComponent(jButton5)
                        .addGap(19, 19, 19)
                        .addComponent(jButton6)))
                .addContainerGap(91, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(jButton3)
                        .addComponent(jButton6)
                        .addComponent(jButton5)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(167, 167, 167))
        );
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt)
    {
        this.dispose();
        this.setVisible(false);
        System.exit(0);
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt)
    {
      try
     {
        int i =jTable1.getSelectedRow();
        address = InetAddress.getByName(compname.get(i+1).toString());

        String tempIP = address.toString().split("/")[1];
        Socket socket = new Socket(tempIP,8080);

        InputStream sin = socket.getInputStream();
        OutputStream sout = socket.getOutputStream();
        DataInputStream in = new DataInputStream(sin);
        DataOutputStream out = new DataOutputStream(sout);
        String line = null;
        out.writeUTF("image");
        out.flush();
        socket.close();
      }
      catch(Exception e)
     {
         System.out.println("image request"+e);
     }

     try
    {
         System.out.println("creating server");
         ServerSocket ss = new ServerSocket(8081);
         System.out.println("waiting for client");
         Socket socket = ss.accept();
         System.out.println("got client");

         InputStream sin = socket.getInputStream();
         ObjectInputStream in = new ObjectInputStream(sin);
         ImageIcon i = (ImageIcon) in.readObject();
         RemoteImage ri = new RemoteImage();
         ri.updateScreen(i);
         in.close();
         sin.close();
         ss.close();
         socket.close();
       }
       catch(Exception e)
      {
          System.out.println("getting image exception  "+e);
          e.printStackTrace();
      }
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)
    {
      try
     {
         int i =jTable1.getSelectedRow();
         address = InetAddress.getByName(compname.get(i+1).toString());
         String tempIP = address.toString().split("/")[1];
         Socket socket = new Socket(tempIP,8080);

         InputStream sin = socket.getInputStream();
        OutputStream sout = socket.getOutputStream();
        DataInputStream in = new DataInputStream(sin);
        DataOutputStream out = new DataOutputStream(sout);
        String line = null;
        String shutdown = ("shutdown -s -t 20");
        out.writeUTF(shutdown);
        out.flush();
        socket.close();
    }
    catch(UnknownHostException e)
    {
         System.out.println("unknown host Error  "+e);
    }
    catch(Exception e)
    {
         System.out.println("jtable selection "+e);
    }
    try
    {
         System.out.println("creating server");
         ServerSocket ss = new ServerSocket(8081);
         System.out.println("waiting for client");
         Socket socket = ss.accept();
         System.out.println("got client");

         InputStream sin = socket.getInputStream();
         OutputStream sout = socket.getOutputStream();

         DataInputStream in = new DataInputStream(sin);
         DataOutputStream out = new DataOutputStream(sout);

         while(true)
         {
            try
           {
                 System.out.println("Byeeeeeee!!!!!");
                  break;
           }
           catch(Exception e)
           {
                System.out.println(e);
           }
        }
         out.flush();
         out.close();
         ss.close();
         socket.close();
    }
    catch(Exception ee)
    {
         System.out.println (ee);
     }
 }

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)
  {
   try
   {
        int i =jTable1.getSelectedRow();
        address = InetAddress.getByName(compname.get(i+1).toString());
        String tempIP = address.toString().split("/")[1];

        Socket socket = new Socket(tempIP,8080);

         InputStream sin = socket.getInputStream();
         OutputStream sout = socket.getOutputStream();
         DataInputStream in = new DataInputStream(sin);
         DataOutputStream out = new DataOutputStream(sout);
         String line = null;
         String restart = ("shutdown -r -t 20");
         out.writeUTF(restart);
         out.flush();
         socket.close();
      }
      catch(UnknownHostException e)
      {
           System.out.println("unknown host Error  "+e);
      }
      catch(Exception e)
      {
            System.out.println("jtable selection "+e);
       }
       try
       {
           System.out.println("creating server");
           ServerSocket ss = new ServerSocket(8081);
           System.out.println("waiting for client");
           Socket socket = ss.accept();
           System.out.println("got client");

           InputStream sin = socket.getInputStream();
          OutputStream sout = socket.getOutputStream();

           DataInputStream in = new DataInputStream(sin);
           DataOutputStream out = new DataOutputStream(sout);

         while(true)
         {
            try
           {
                 System.out.println("Byeeeeeee!!!!!");
                  break;
           }
           catch(Exception e)
           {
                System.out.println(e);
           }
        }
         out.flush();
         out.close();
         ss.close();
         socket.close();
    }
    catch(Exception ee)
    {
         System.out.println (ee);
     }
   }

    private void jTable1PropertyChange(java.beans.PropertyChangeEvent evt) {}

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt)
    {
		if(flag==0)
        {
            flagrow = jTable1.getSelectedRow();
            flag =1;
        }
        else if(flagrow==jTable1.getSelectedRow())
        {
            if (jTable1.getSelectedRow()!=-1)
            {
             try
             {
                 int i =jTable1.getSelectedRow();
                 address = InetAddress.getByName(compname.get(i+1).toString());
                 String tempIP = address.toString().split("/")[1];
                 taskmachineIP = tempIP;

                 Socket socket = new Socket(tempIP,8080);

                 InputStream sin = socket.getInputStream();
                 OutputStream sout = socket.getOutputStream();
                 DataInputStream in = new DataInputStream(sin);
                 DataOutputStream out = new DataOutputStream(sout);
                 String line = null;
                 out.writeUTF("tasklist");
                 out.flush();
                 socket.close();
                }
                catch(UnknownHostException e)
                {
                     System.out.println("unknown host Error  "+e);
                }
                catch(Exception e)
                {
                    System.out.println("jtable selection "+e);
                }
                try
               {
                   System.out.println("creating server");
                   ServerSocket ss = new ServerSocket(8081);
                   System.out.println("waiting for client");
                   Socket socket = ss.accept();
                   System.out.println("got client");

                   InputStream sin = socket.getInputStream();
                   OutputStream sout = socket.getOutputStream();

                   DataInputStream in = new DataInputStream(sin);
                   DataOutputStream out = new DataOutputStream(sout);

                   String cmd = "";
                   tasklst = new Vector();

                   while(true)
                   {
                    try
                    {
                       cmd = in.readUTF();
                       if(cmd.equals("bye"))
                               break;
                        tasklst.add(cmd);
                     }
                     catch(Exception e)
                    {
                         System.out.println(e);
                     }
                 }
                 out.flush();
                 out.close();
                 ss.close();
                 socket.close();
              }
              catch(Exception ee)
              {
                   System.out.println (ee);
              }
              try
              {
                   tasklst.remove(0);
                   tasklst.remove(1);

                   task_list = new Object[tasklst.size()][5];

                   for(int j=0;j<tasklst.size()-1;j++)
                   {
                       int i=0,k=1;
                       StringTokenizer tasktoken = new StringTokenizer(tasklst.get(j).toString()," ");

                       while(tasktoken.hasMoreTokens())
                       {
                          String temp = tasktoken.nextToken().toString();

                         if(i!=3&&i<=4)
                         if(i==4)
                         {
                            task_list[j][0] = new Boolean(false);
                            task_list[j][k++] = temp.trim()+" KB";
                         }
                         else
                         {
                             task_list[j][0] = new Boolean(false);
                             task_list[j][k++] = temp.trim();
                          }
                          i++;
                      }
                   }
                   try
                  {
                       this.setVisible(false);
                       TaskList taskList = new TaskList(task_list,taskmachineIP);
                   }
                   catch(Exception e)
                   {
                        e.printStackTrace();
                       JOptionPane.showMessageDialog(this,"Cant Launch TaskList......");
                   }
               }
               catch(Exception e)
               {
                    System.out.println("exception while tasklist "+e);
                    e.printStackTrace();
                }
            }
            flag=0;
            }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)
    {
		screenrefresh();
    }
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
}
