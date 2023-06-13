package rikkoRicardo.tcp.chat.Interfaces;

import rikkoRicardo.tcp.chat.ClientComponents.MClientLogInterface;
import rikkoRicardo.tcp.chat.ClientComponents.MClientLogicInterface;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import rikkoRicardo.tcp.chat.ClientComponents.JsonLoader;

public class MClientInterface extends javax.swing.JFrame {

    static {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            FlatLightLaf.setup();
        } catch (UnsupportedLookAndFeelException ex) {
        }
    }

    /**
     *
     */
    private DefaultListModel messageModel;

    /**
     *
     */
    private DefaultListModel UserModel;

    /**
     *
     */
    private JsonLoader json;

    /**
     *
     */
    private MClientLogicInterface client;

    /**
     *
     * @throws IOException
     */
    public MClientInterface() throws IOException {
        initComponents();
        this.json = new JsonLoader();

        //set models
        mensagens.setModel((messageModel = new DefaultListModel()));
        lista.setModel((UserModel = new DefaultListModel()));

        try {

            //Ask user for IP Address
            String ip = JOptionPane.showInputDialog("Server Address:");
            Socket socket = new Socket(ip.isBlank() ? "localhost" : ip, 6000);

            //show UI, disable client interaction
            this.setVisible(true);
            this.setEnabled(false);

            //Setup LP element
            UserModel.addElement("Global");

            //Ask for email
            String username;
            do {
                username = JOptionPane.showInputDialog("Email Adress:");
            } while (username.isBlank());

            client = new MClientLogicInterface(socket, username);
            client.listen(messageModel, UserModel);

            //Set title
            this.setTitle("ChatClient[host:" + ip + "] - " + client.getUsername() + ")");
            this.setEnabled(true);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Couldnt connect to the server!");
            System.exit(0);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        mensagens = new javax.swing.JList<>();
        enviar = new javax.swing.JButton();
        TFMessage = new javax.swing.JTextField();
        loadChat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));
        setMinimumSize(new java.awt.Dimension(707, 400));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Connected Users ");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Messages");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jScrollPane1.setBackground(new java.awt.Color(51, 51, 51));

        lista.setBackground(new java.awt.Color(204, 204, 204));
        lista.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lista.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Global" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lista.setToolTipText("UWU");
        lista.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(lista);

        jScrollPane2.setBackground(new java.awt.Color(153, 255, 51));

        mensagens.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jScrollPane2.setViewportView(mensagens);

        enviar.setBackground(new java.awt.Color(204, 204, 204));
        enviar.setText(" ▶");
        enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarActionPerformed(evt);
            }
        });

        TFMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                enviarActionPerform(evt);
            }
        });

        loadChat.setBackground(new java.awt.Color(153, 153, 153));
        loadChat.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        loadChat.setText("Open Logs");
        loadChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadChatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                    .addComponent(loadChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(TFMessage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enviar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enviar)
                    .addComponent(TFMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loadChat))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Sends a message to the selected client
     */
    void sendMessage() {
        String value = lista.getSelectedValue();
        String messageFormat = "message:" + value + "/" + TFMessage.getText();
        client.sendTCPMessageToClient(messageFormat);
        TFMessage.setText("");
    }

    private void enviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarActionPerformed
        sendMessage();
    }//GEN-LAST:event_enviarActionPerformed

    private void enviarActionPerform(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_enviarActionPerform
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            sendMessage();
        }
    }//GEN-LAST:event_enviarActionPerform

    private void loadChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadChatActionPerformed
        try {

            DefaultListModel modelR = new DefaultListModel();

            String value;
            if ((value = lista.getSelectedValue()) == null) {
                return;
            }

            if (value.equals("Global")) {
                JsonLoader.LoadFile();
                ArrayList<String> listaR = JsonLoader.getMessagesChat(value, value);
                for (String s : listaR) {
                    modelR.addElement(s);
                }
            } else {
                JsonLoader.LoadFile();
                ArrayList<String> listaR = JsonLoader.getMessagesChat(value, client.getUsername());
                for (String s : listaR) {
                    modelR.addElement(s);
                }
            }

            MClientLogInterface history = new MClientLogInterface(modelR, value, client.getUsername());
            history.setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_loadChatActionPerformed

    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new MClientInterface().setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(MClientInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TFMessage;
    private javax.swing.JButton enviar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> lista;
    private javax.swing.JButton loadChat;
    private javax.swing.JList<String> mensagens;
    // End of variables declaration//GEN-END:variables
}
