
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gkaustchr
 */

// 192.168.0.102
public class Jogo extends Thread{
    Cliente cliente;
    String ip_servidor;
    
    public Jogo( String ip) {
        this.ip_servidor = ip;
    }
    
    
    public void entrarNoJogo() throws IOException {
        cliente.entrarNoJogo();
    }
    
    public boolean criarCliente(  )  {
        try {
           
            cliente = new Cliente(ip_servidor, 6789);
            
            return true;
            
        }  catch (UnknownHostException ex) {
            
            JOptionPane.showMessageDialog(null, "Servidor não encontrado!");
            
            return false;
            
        } catch (IOException ex) {
            
            JOptionPane.showMessageDialog(null, "Servidor não encontrado!");
            
            return false;
        } 
        
    }
    
    public void run() {
      
        if ( ! criarCliente() ) {
            return;
        }
        System.out.println("Jogo: Cliente Criado");
       
        
        
        try {
            System.out.println("Jogo: Cliente entrando no jogo");
            entrarNoJogo();
            
        } catch (IOException ex) {
            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (true) {
            try {
                System.out.println("Jogo: Cliente jogar");
                cliente.jogar();
                // Durante o jogo
            } catch (IOException ex) {
                Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
                break;
            } catch (InterruptedException ex) {
                Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    
    public String atualizarTela() throws IOException {
        return cliente.atualizarTela();
    }
    
    public boolean meuTurno() throws IOException {
        return cliente.meuTurno();
    }

    public void jogada(String jogada) {
        cliente.jogada(jogada);
    }

    public int jogoAcabou() throws IOException {
        return cliente.jogoAcabou();
    }

    public void fecharConexao() throws IOException {
        cliente.fecharConexao();
    }
    
}
