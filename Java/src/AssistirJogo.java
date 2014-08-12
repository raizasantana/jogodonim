
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gkaustchr
 */
public class AssistirJogo extends Thread {
    Espectador espectador;
    String ip_servidor;
    
    public AssistirJogo( String ip) {
        this.ip_servidor = ip;
    }
    
    
    public void entrarNoJogo() throws IOException {
        espectador.entrarNoJogo();
    }
    
    public boolean criarEspectador(  )  {
        try {
           
            espectador = new Espectador(ip_servidor, 6789);
            
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
      
        if ( ! criarEspectador() ) {
            return;
        }
        System.out.println("Jogo: Espectador Criado");

        try {
            System.out.println("AssistirJogo: Espectador entrando no jogo");
            entrarNoJogo();
            
        } catch (IOException ex) {
            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (true) {
            try {
                System.out.println("AssistirJogo: Espectador Assistir");
                espectador.assistir();
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
        return espectador.atualizarTela();
    }

    public int jogoAcabou() throws IOException {
        return espectador.jogoAcabou();
    }
    
    public int turno() throws IOException {
        return espectador.turno();
    }

    public void fecharConexao() throws IOException {
        espectador.fecharConexao();
    }
    
}
