import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JOptionPane;

public class Net_Scan extends javax.swing.JDialog
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
    private JButton jButton1,jButton2,jButton3,jButton5,jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;

    public Net_Scan()
    {
        initComponents();
        screenrefresh();
        this.setSize(600,400);
        this.setLocation(200,200);
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

        }catch(Exception e)
        {
            System.out.println("getIPAddress "+e);
        }
    }

    private void initComponents()
    {
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setTitle("List of machines in network");

        setBounds(new java.awt.Rectangle(400, 200, 400, 400));

        jButton1.setIcon(new javax.swing.ImageIcon("./image/refresh.gif"));

        jButton2.setText("Restart");

        jButton3.setText("Shutdown");

        jScrollPane1.setToolTipText("");

        jScrollPane1.setFont(new java.awt.Font("Verdana", 1, 14));

        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14));

        jLabel1.setText("Scan");

        jButton5.setText("Image");

        jButton6.setText("Exit");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());

        getContentPane().setLayout(layout);

        layout.setHorizontalGroup
        (
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
        layout.setVerticalGroup
        (
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
}
