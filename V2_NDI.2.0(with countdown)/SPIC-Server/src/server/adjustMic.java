/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.SwingUtilities;

/**
 *
 * @author mukherjee
 */
public class adjustMic extends javax.swing.JDialog {

    public boolean outVoice = false;
    public Thread RT;
    public utility U = new utility();
    public ArrayList<Float> adj_value = new ArrayList<Float>();

    /**
     * Creates new form adjMicro
     */
    public adjustMic() {
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

        text = new javax.swing.JLabel();
        save = new javax.swing.JButton();
        start = new javax.swing.JButton();
        value = new javax.swing.JTextField();
        cur_rms_value = new javax.swing.JLabel();
        adjusted_value = new javax.swing.JLabel();
        set_value = new javax.swing.JButton();
        stop = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        text.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        text.setText("Gaba");
        text.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        save.setText("Save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        start.setText("Start");
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });

        value.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        value.setText("0.12");

        cur_rms_value.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cur_rms_value.setText("-");
        cur_rms_value.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        adjusted_value.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adjusted_value.setText("-");
        adjusted_value.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        set_value.setText("Set this value");
        set_value.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                set_valueActionPerformed(evt);
            }
        });

        stop.setText("stop");
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(value, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(127, 127, 127)
                        .addComponent(cur_rms_value, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(stop))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(adjusted_value, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(set_value)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(text, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(value, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cur_rms_value, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(text, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stop))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adjusted_value, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(set_value))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_saveActionPerformed

    private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed
        // TODO add your handling code here:
        outVoice = true;
        setup();
    }//GEN-LAST:event_startActionPerformed

    private void set_valueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_set_valueActionPerformed
        // TODO add your handling code here:
        value.setText(adjusted_value.getText());
        adj_value.clear();
    }//GEN-LAST:event_set_valueActionPerformed

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed
        // TODO add your handling code here:
        outVoice = false;
        try {
            Thread.sleep(2000);
            RT.stop();
        } catch (InterruptedException ex) {
            Logger.getLogger(adjMicro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_stopActionPerformed

    public void setup() {
        RT = new Thread() {
            @Override
            public void run() {
                getMicVol();
            }
        };
        RT.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(adjustMic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(adjustMic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(adjustMic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(adjustMic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                adjustMic dialog = new adjustMic();
                dialog.setVisible(true);

            }
        });
    }

    public void getMicVol() {

        float cur_rms, prev_rms, voice = 0;

        ArrayList<String> TEXT = new ArrayList<String>();
        TEXT.add("Babi");
        TEXT.add("Conde");
        TEXT.add("Lira");
        TEXT.add("Pipa");
        TEXT.add("Real");
        TEXT.add("Simpa");
        Random myRandomizer = new Random();
        try {
            AudioFormat audioFormat = U.getAudioFormat();
            DataLine.Info micInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

            if (!AudioSystem.isLineSupported(micInfo)) {
                System.err.println("Line not supoorted");
            }

            TargetDataLine mic = (TargetDataLine) AudioSystem.getLine(micInfo);
            mic.open();
            System.out.println("Mic open.");

            byte tmpBuff[] = new byte[mic.getBufferSize() / 5];
            mic.start();
            prev_rms = 20f;
            while (outVoice) {
                mic.read(tmpBuff, 0, tmpBuff.length);
                cur_rms = U.calculateRMSLevel(tmpBuff);
                
                cur_rms = U.calculatePeak(tmpBuff);
                cur_rms_value.setText(""+cur_rms);
                if (cur_rms > Float.parseFloat(value.getText())) {
                    
                    text.setText(TEXT.get(myRandomizer.nextInt(TEXT.size())));
                    adj_value.add(cur_rms);
                    outVoice = false;
                }
            }
            mic.close();
            System.out.println("Mic close");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        // Adjusted value after each text change
        adjusted_value.setText(""+U.calculateAverageFloat(adj_value));

    }

    public String getSFValue() {
        return value.getText();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adjusted_value;
    private javax.swing.JLabel cur_rms_value;
    private javax.swing.JButton save;
    private javax.swing.JButton set_value;
    private javax.swing.JButton start;
    private javax.swing.JButton stop;
    private javax.swing.JLabel text;
    private javax.swing.JTextField value;
    // End of variables declaration//GEN-END:variables
}
