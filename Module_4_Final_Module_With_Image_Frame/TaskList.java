import java.awt.*;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.net.*;
import java.util.Vector;

public class TaskList extends javax.swing.JFrame
{
    public static Object task_list[][];
    JButton jButton1,jButton2,jButton3,jButton4;
    JLabel jLabel1;
    JScrollPane jScrollPane1;
    JTable jTable1;

    public static String taskMachineIP=null;
    public static String title[] = {"","Process Name","Process ID","Session Name","Memory Usage"};
    public DefaultTableModel dm = null;

    public TaskList()
    {
        initComponents();
    }

    public TaskList(Object temp[][],String taskMachineIP)
    {
        initComponents();

        this.taskMachineIP = taskMachineIP;

        task_list = temp;

        dm = new DefaultTableModel(temp,title)
        {
            public Class getColumnClass(int columnIndex)
            {
                  if(columnIndex == 0)
                  {
                       return Boolean.class;
                  }
                  else
                  {
                       return super.getColumnClass(columnIndex);
                  }
             }
         };

        Font f = new Font("Verdana",Font.BOLD,14);
        jTable1.setFont(f);
        this.setSize(800,700);

        jTable1.setModel(dm);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.sizeColumnsToFit(0);

        this.setTitle("Task List");
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
   }

  private void initComponents()
  {
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jScrollPane1MouseClicked(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object [][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}},
         new String [] {"Title 1", "Title 2", "Title 3", "Title 4"}
        ));

        jTable1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jTable1MouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Kill Selected");

        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Kill All Checked");

        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Refresh");

        jButton3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Back");

        jButton4.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
        .addGap(19, 19, 19)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
        .addComponent(jLabel1))
        .addContainerGap())
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addComponent(jButton3)
        .addGap(93, 93, 93)
        .addComponent(jButton4)
        .addGap(229, 229, 229))))
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap(230, Short.MAX_VALUE)
        .addComponent(jButton1)
        .addGap(77, 77, 77)
        .addComponent(jButton2)
        .addGap(181, 181, 181))
        );

        layout.setVerticalGroup
        (
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(jButton4)
            .addComponent(jButton3))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(30, 30, 30)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(jButton2)
            .addComponent(jButton1))
            .addGap(7, 7, 7)
            .addComponent(jLabel1)
            .addGap(43, 43, 43))
        );
        pack();
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)
    {
        Vector tasklst = null;

        try
       {
             Socket socket = new Socket(taskMachineIP,8080);
             InputStream sin = socket.getInputStream();
             OutputStream sout = socket.getOutputStream();
             DataInputStream in = new DataInputStream(sin);
             DataOutputStream out = new DataOutputStream(sout);
            String line = null;
            out.writeUTF("tasklist");
            out.flush();
            socket.close();
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

                System.out.println("Data in Table "+cmd);

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
        dm = new DefaultTableModel(task_list,title)
        {
            public Class getColumnClass(int columnIndex)
            {
                  if(columnIndex == 0)
                  {
                       return Boolean.class;
                  }
                  else
                  {
                       return super.getColumnClass(columnIndex);
                  }
             }};
          jTable1.setModel(dm);
       }
       catch(Exception e)
       {
           System.out.println("exception while tasklist "+e);
           e.printStackTrace();
       }
   }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt)
    {
		this.setVisible(false);
        NetStart nt =  new NetStart();
        nt.setVisible(true);
        this.dispose();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)
    {
        int selectedRow=0;
        StringBuffer cmd = new StringBuffer("");
        StringBuffer rowcnt = new StringBuffer("");
        cmd.append("taskkill /F ");
        for(int i=0;i<dm.getRowCount()-1;i++)
        {
            if(dm.getValueAt(i,0).toString().equals("true"))
            {
                try
                {
                    selectedRow = i;
                    rowcnt.append(selectedRow+",");
                    int taskPID = Integer.parseInt(task_list[selectedRow][2].toString());
                    cmd.append(" /PID "+taskPID);
                }catch(Exception e){System.out.println("for loop "+e);}
            }
        }
        try
        {
            Socket socket = new Socket(taskMachineIP,8080);
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);
            out.writeUTF(cmd.toString());
            out.flush();
            socket.close();
         }
         catch(Exception e)
         {
             System.out.println("task kill "+e);
          }
          try
          {
               ServerSocket ss = new ServerSocket(8081);
               Socket socket = ss.accept();
               ss.close();
               socket.close();
           }
           catch(Exception e)
           {
                System.out.println("8081 exception "+e);
            }
            try
            {
                 Thread.sleep(1000);
             }
             catch(Exception e)
             {
				 System.out.println("sleep "+e);
		      }
                StringTokenizer st = new StringTokenizer(rowcnt.toString(),",");
                int i=0;
                while(st.hasMoreTokens())
                {
                    String num = st.nextToken();
                    System.out.println(num);
                    if(!num.equals("")&&num!=null)
                    dm.removeRow(Integer.parseInt(num)-i);
                    i++;
                }
                jTable1.setModel(dm);
                jTable1.repaint();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)
    {
		int selectedRow =0;

        try
        {
            selectedRow = jTable1.getSelectedRow();
            int taskPID = Integer.parseInt(task_list[selectedRow][2].toString());
            String cmd = "taskkill /F /PID "+taskPID;
            System.out.println("hiiii " + cmd);
            Socket socket = new Socket(taskMachineIP,8080);
            System.out.println("after connection");
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);
            out.writeUTF(cmd);
            out.flush();
            socket.close();
        }
        catch(Exception e)
        {
            System.out.println("task kill "+e);
        }
        try
        {
            System.out.println("creating server");
            ServerSocket ss = new ServerSocket(8081);
            System.out.println("waiting for client");
            Socket socket = ss.accept();
            System.out.println("got client");
            dm.removeRow(selectedRow);
            jTable1.repaint();
            ss.close();
            socket.close();
          }
          catch(Exception e)
         {
             System.out.println("kill single process exception "+e);
          }
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt)
    {
		jTable1.setSelectionForeground(Color.BLACK);
        int selectedRow = jTable1.getSelectedRow();
        Font f = new Font("Verdana",Font.BOLD,17);
        String str = "<html>Process Name: "+task_list[selectedRow][1]+"<br>Process ID: "+task_list[selectedRow][2]+"<br>Session Name: "+task_list[selectedRow][3]+"<br>Memory Usage: "+task_list[selectedRow][4]+"</html>";
        jLabel1.setFont(f);
        jLabel1.setText(str);
    }

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {}
}
