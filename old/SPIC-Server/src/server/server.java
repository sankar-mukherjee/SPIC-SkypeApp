/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.skype.Call;
import com.skype.ChatMessage;
import com.skype.ChatMessageListener;
import com.skype.Skype;
import com.skype.SkypeException;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import java.awt.*;


/**
 *
 * @author mukherjee
 */
public class server extends javax.swing.JDialog {

    public final String wrok_dir = "C:\\SPIC-Server\\";
    public ArrayList<String> data;
    ArrayList<String> allDominos;
    public String rcvMsg;
    public boolean outVoice = false;
    public int domminosize;
    public int domino_remain;
    public double sensetivity_th;
    public String sense;
    public int Right_word;

    public Thread RT;
    public SampleChatMessageListener L = new SampleChatMessageListener();

    static Logger log = Logger.getLogger(server.class.getName());
    java.util.Date date = new java.util.Date();

    
    public utility U = new utility();

    /**
     * Creates new form word
     */
    public server() {
        initComponents();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        target = new javax.swing.JLabel();
        distract = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        show_rightWord = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                onclose(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        target.setFont(new java.awt.Font("Arial", 0, 70)); // NOI18N
        target.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        target.setText("-");
        target.setToolTipText("");

        distract.setFont(new java.awt.Font("Arial", 0, 70)); // NOI18N
        distract.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        distract.setText("-");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(target, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(distract, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(target, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(distract, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        show_rightWord.setText("---");
        show_rightWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                show_rightWordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(show_rightWord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(show_rightWord, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onclose(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_onclose
        // TODO add your handling code here:
        outVoice = false;
        String Name = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        File source = new File(wrok_dir + "server\\" + "Server.log");
        File dest = new File(wrok_dir + "server\\" + Name);
        File source1 = new File(allDominos.get(0));
        String gg = allDominos.get(0).replace("data\\", "data\\done\\");
        File dest1 = new File(gg);

        try {
            U.copyFileUsingStream(source, dest);
            System.out.println(dest + "<---" + source);
            U.copyFileUsingStream(source1, dest1);
            System.out.println(dest1 + "<---" + source1);
            source1.delete();
            System.out.println("removed--->" + source1);
            RT.stop();
            System.out.println("Thread successfuly stopped");
            Skype.removeChatMessageListener(L);
            System.out.println("Listiner successfuly stopped");
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_onclose

    private void show_rightWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_show_rightWordActionPerformed
        // TODO add your handling code here:
        if (Right_word == 1) {
            target.setForeground(Color.red);
            log.info(System.currentTimeMillis() + "--Server Emergency--" + target.getText());
        } else {
            distract.setForeground(Color.red);
            log.info(System.currentTimeMillis() + "--Server Emergency--" + distract.getText());
        }
    }//GEN-LAST:event_show_rightWordActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws InterruptedException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                server w = new server();
                w.setVisible(true);
                w.setup();
            }
        });
    }

    public void setup() {
        DOMConfigurator.configure("C:\\SPIC-Server\\server\\Server_log4j.xml");
//        loadData();
//        recivemsg();
        RT = new Thread() {
            @Override
            public void run() {
                loadData();
                recivemsg();
            }
        };

//        if (flag == 0) {
        RT.start();
//        } else {
//            loadData();
//            recivemsg();
//        }
        System.out.println("--------");
    }

    public void updateTextField(final String value) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        if (value.equals("KKK")) {
                            setValuesTextField(data.get(0));
                            System.out.println(data.get(0));
                            if (data.size() > 1) {
                                data.remove(0);
                            } else if (data.size() == 1) {
                                data.remove(0);
                                data.add("--END--,--END--");
                            }
                        }
                    }
                }
        );
    }

    public void setValuesTextField(String sp) {
        String tmp[] = sp.split(",");
        if (tmp[1].equals("XXX")) {
            tmp[1] = "";
        }
        int y = 0;
        Right_word = 0;

        int x = (Math.random() < 0.5) ? 0 : 1;
        if (x == 0) {
            y = 1;
            Right_word = 1;
        }

        target.setText(tmp[x]);
        distract.setText(tmp[y]);
        domino_remain = domminosize - data.size();
        progressBar.setValue(domino_remain);
//        Toolkit.getDefaultToolkit().beep();                     // beep sound before text presentation
        log.info(System.currentTimeMillis() + "--Server Presented--" + data.get(0));
//        U.TriggerParallel(1);
    }

    public void removeTextField() {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        removeValuesTextField();
                    }
                }
        );
    }

    public void removeValuesTextField() {
        target.setText("-");
        distract.setText("-");
        target.setForeground(Color.BLACK);
        distract.setForeground(Color.BLACK);
    }

    public void setSensetivity(String value) {
        sense = value;
    }

    public void getMicVol() {
        float rms = 0;
        double sf = 0;
        float peak = 0;
        float choise = 0;
        try {
            AudioFormat audioFormat = U.getAudioFormat();
            DataLine.Info micInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

            if (!AudioSystem.isLineSupported(micInfo)) {
                System.err.println("Line not supoorted");
            }

            TargetDataLine mic = (TargetDataLine) AudioSystem.getLine(micInfo);
            mic.open();
            System.out.println("Server Mic open.");
            byte tmpBuff[] = new byte[mic.getBufferSize() / 5];
//            System.out.println("buffer"+mic.getBufferSize());
            mic.start();
            while (outVoice) {
                mic.read(tmpBuff, 0, tmpBuff.length);
                log.info(System.currentTimeMillis() + "--Server VAD--X");
                rms = U.calculateRMSLevel(tmpBuff);
                sf = U.spectralFlatness(U.shortFromByte(tmpBuff));

                peak = U.calculatePeak(tmpBuff);
                System.out.println("SF-->" + sf + " RMS-->" + rms + " Peak-->" + peak);
                sensetivity_th = Double.parseDouble(sense);

                choise = peak;      //choose which meathod to use

                if (choise > sensetivity_th) {
                    log.info(System.currentTimeMillis() + "--Server Spoken--X");
                    outVoice = false;
                    removeTextField();
                    sendmsg();
                    System.out.println("Server Spectral Flatness Level: " + sf);
                    System.out.println("Server RMS Level: " + rms);
                    System.out.println("Server Peak Level: " + peak);
                    System.out.println("current Threshold: " + sensetivity_th);
                }
            }
//            mic.drain();
            mic.close();
            System.out.println("Server Mic close.");
        } catch (LineUnavailableException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendmsg() {
        
        try {
            Call[] cc = Skype.getAllActiveCalls();
            while (cc[0].getStatus() == Call.Status.INPROGRESS) {
                String message = data.get(0);
                Skype.chat("aix.main").send(message + "," + domino_remain);
                log.info(System.currentTimeMillis() + "--Server sent--" + message);
//                U.TriggerParallel(2);
                if (data.size() > 1) {
                    data.remove(0);
                } else if (data.size() == 1) {
                    data.remove(0);
                    data.add("--END--,--END--");
                }

                break;
            }
        } catch (SkypeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void recivemsg() {
        try {
            Skype.addChatMessageListener(L);
        } catch (SkypeException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Server Ready!");
        log.info(allDominos.get(0));
        outVoice = true;
        updateTextField("KKK"); // For diplaying the first word in server window
        getMicVol();
    }

    public class SampleChatMessageListener implements ChatMessageListener {

        public void chatMessageReceived(final ChatMessage message) {
            try {
                log.info(System.currentTimeMillis() + "--Server recieved--XXX");
                updateTextField(message.getContent());
                outVoice = true;
                getMicVol();
            } catch (final SkypeException e) {
                e.printStackTrace();
            }
        }

        public void chatMessageSent(ChatMessage message) {

        }
    }

    public void loadData() {
        allDominos = U.getAllDominos(wrok_dir + "data\\");   //get all dominno in data dir
        data = U.build_dominno(allDominos.get(0));              //get the first one
        domminosize = data.size();
        progressBar.setMaximum(domminosize);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JLabel distract;
    private javax.swing.JPanel jPanel1;
    private static javax.swing.JProgressBar progressBar;
    private javax.swing.JButton show_rightWord;
    private static javax.swing.JLabel target;
    // End of variables declaration//GEN-END:variables
}
