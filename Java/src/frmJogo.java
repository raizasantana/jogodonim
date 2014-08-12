
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aluno
 */
public class frmJogo extends javax.swing.JFrame {
    Jogo jogo;
    Timer timer;
    
    int vencedor;
    
    /**
     * Creates new form frmJogo
     */
    public frmJogo( ) {
        initComponents();
    }
    
    public frmJogo( Jogo jogo ) {
        initComponents();
        
        this.jogo = jogo;
        
        vencedor = 0;
        
        iniciarJogo();
        
    }
    
    public void iniciarJogo() {
        
        reminder(3);
        
        lblMensagens.setText("Jogo iniciado");
        desabilitarBotoes();        
        
    }
    
    public void desabilitarBotoes() {
        btnRemover1.setEnabled(false);
        btnRemover2.setEnabled(false);
        btnRemover3.setEnabled(false);
    }
    
    public void habilitarBotoes() {
        
        txtFileira1.setText( lblQtdFileira1.getText() );
        txtFileira2.setText( lblQtdFileira2.getText() );
        txtFileira3.setText( lblQtdFileira3.getText() );
        
        btnRemover1.setEnabled(true);
        btnRemover2.setEnabled(true);
        btnRemover3.setEnabled(true);
        
        if ( lblQtdFileira1.getText().equals("0") )
            btnRemover1.setEnabled(false);
        if ( lblQtdFileira2.getText().equals("0") )
            btnRemover2.setEnabled(false);
        if ( lblQtdFileira3.getText().equals("0") )
            btnRemover3.setEnabled(false);
    }
    
    public void fechar() {
        this.dispose();
    }
    
    public void reminder(int seconds) {
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds*1000);
    }

    class RemindTask extends TimerTask {
        public void run() {
            
            //System.out.println("Time's up!");
            //timer.cancel(); //Terminate the timer thread
            
            System.out.println("JOGO: Testando se é meu turno");
            
            atualizarTela();
            
            try {
                if ( jogo.meuTurno() == true ) {
                    
                    atualizarTela();
                    
                    vencedor = jogoAcabou();
                    if ( vencedor != 0 ) {
                        timer.cancel();
                        // Eu venci
                        if ( vencedor == 1 ) {
                            JOptionPane.showMessageDialog(null, "Parabéns você ganhou!");
                            
                            // Passar o turno
                            jogo.jogada("10");
                            
                            
                        // Eu perdi
                        } else {
                            JOptionPane.showMessageDialog(null, "Que pena, você perdeu!");
                        }
                        
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(frmJogo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        jogo.fecharConexao();
                        
                        fechar();
                    }
                    
                    habilitarBotoes();
                    lblMensagens.setText("Faça sua jogada");
                    lblTurno.setText("Sua Vez!");
                    
                    timer.cancel();
                    
                } else {
                    
                    desabilitarBotoes();
                    lblMensagens.setText("Aguardando a jogada do outro jogador");
                    lblTurno.setText("Adversário!");
                    
                    reminder(3);
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(frmJogo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

        
    }
    
    public void atualizarTela()  {
        String atualizacao = "999";
        
        try {
            atualizacao = jogo.atualizarTela();
        } catch (IOException ex) {
            Logger.getLogger(frmJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        System.out.println("TELA ATUALIZADA: '"+ atualizacao +"'");
        
        lblQtdFileira1.setText( String.valueOf( atualizacao.charAt(0) ) );
        lblQtdFileira2.setText( String.valueOf( atualizacao.charAt(1) ) );
        lblQtdFileira3.setText( String.valueOf( atualizacao.charAt(2) ) );
        
    }
    
    public void cancelTimer() {
        timer.cancel();
    }
    
    public int jogoAcabou() throws IOException {
         return jogo.jogoAcabou();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblQtdFileira1 = new javax.swing.JLabel();
        lblQtdFileira2 = new javax.swing.JLabel();
        lblQtdFileira3 = new javax.swing.JLabel();
        btnRemover1 = new javax.swing.JButton();
        btnRemover2 = new javax.swing.JButton();
        btnRemover3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblTurno = new javax.swing.JLabel();
        lblMensagens = new javax.swing.JLabel();
        txtFileira1 = new javax.swing.JTextField();
        txtFileira2 = new javax.swing.JTextField();
        txtFileira3 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Jogo do Nim");

        jLabel1.setText("Fileira 1");

        jLabel2.setText("Fileira 2");

        jLabel3.setText("Fileira 3");

        lblQtdFileira1.setText("9");

        lblQtdFileira2.setText("9");

        lblQtdFileira3.setText("9");

        btnRemover1.setText("Remover palitos");
        btnRemover1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemover1ActionPerformed(evt);
            }
        });

        btnRemover2.setText("Remover palitos");
        btnRemover2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemover2ActionPerformed(evt);
            }
        });

        btnRemover3.setText("Remover palitos");
        btnRemover3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemover3ActionPerformed(evt);
            }
        });

        jLabel4.setText("Turno:");

        lblTurno.setText("Status_turno");

        lblMensagens.setText("Mensagens");

        txtFileira1.setText("0");
        txtFileira1.setToolTipText("");

        txtFileira2.setText("0");
        txtFileira2.setToolTipText("");

        txtFileira3.setText("0");
        txtFileira3.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblQtdFileira3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtFileira3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblQtdFileira2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtFileira2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblQtdFileira1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtFileira1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRemover3)
                            .addComponent(btnRemover2)
                            .addComponent(btnRemover1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTurno))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblMensagens)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblMensagens)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblQtdFileira1)
                    .addComponent(btnRemover1)
                    .addComponent(txtFileira1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblQtdFileira2)
                    .addComponent(btnRemover2)
                    .addComponent(txtFileira2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemover3)
                    .addComponent(jLabel3)
                    .addComponent(lblQtdFileira3)
                    .addComponent(txtFileira3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblTurno))
                .addGap(58, 58, 58))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRemover1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemover1ActionPerformed
        // Remover Fileira 1
        if ( Integer.parseInt( txtFileira1.getText() ) > Integer.parseInt( lblQtdFileira1.getText() ) ) {
            JOptionPane.showMessageDialog(this, "O valor retirado não pode exceder o valor da fileira");
            return;
        }
        if ( Integer.parseInt( txtFileira1.getText() ) < 1 ) {
            JOptionPane.showMessageDialog(this, "Deve-se remover pelo menos um elemento da fileira");
            return;
        }
        
        desabilitarBotoes();
        
        jogo.jogada("1" + txtFileira1.getText());
        
        reminder(3);
        
    }//GEN-LAST:event_btnRemover1ActionPerformed

    private void btnRemover2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemover2ActionPerformed
        // Remover Fileira 2
        if ( Integer.parseInt( txtFileira2.getText() ) > Integer.parseInt( lblQtdFileira2.getText() ) ) {
            JOptionPane.showMessageDialog(this, "O valor retirado não pode exceder o valor da fileira");
            return;
        }
        if ( Integer.parseInt( txtFileira2.getText() ) < 1 ) {
            JOptionPane.showMessageDialog(this, "Deve-se remover pelo menos um elemento da fileira");
            return;
        }
        
        desabilitarBotoes();
        
        jogo.jogada("2" + txtFileira2.getText());
        
        reminder(3);
        
    }//GEN-LAST:event_btnRemover2ActionPerformed

    private void btnRemover3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemover3ActionPerformed
        // Remover Fileira 3
        if ( Integer.parseInt( txtFileira3.getText() ) > Integer.parseInt( lblQtdFileira3.getText() ) ) {
            JOptionPane.showMessageDialog(this, "O valor retirado não pode exceder o valor da fileira");
            return;
        }
        if ( Integer.parseInt( txtFileira3.getText() ) < 1 ) {
            JOptionPane.showMessageDialog(this, "Deve-se remover pelo menos um elemento da fileira");
            return;
        }
        
        desabilitarBotoes();
        
        jogo.jogada("3" + txtFileira3.getText());
        
        reminder(3);
        
    }//GEN-LAST:event_btnRemover3ActionPerformed

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
            java.util.logging.Logger.getLogger(frmJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmJogo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRemover1;
    private javax.swing.JButton btnRemover2;
    private javax.swing.JButton btnRemover3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblMensagens;
    private javax.swing.JLabel lblQtdFileira1;
    private javax.swing.JLabel lblQtdFileira2;
    private javax.swing.JLabel lblQtdFileira3;
    private javax.swing.JLabel lblTurno;
    private javax.swing.JTextField txtFileira1;
    private javax.swing.JTextField txtFileira2;
    private javax.swing.JTextField txtFileira3;
    // End of variables declaration//GEN-END:variables
}
