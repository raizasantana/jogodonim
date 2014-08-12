/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gkaustchr
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

// 127.0.0.1
public class NovoJogo extends Thread {
    Servidor servidor;
    
    public void criarServidor(  ) {
        servidor = new Servidor(6789);
    }
    
    public void iniciar() throws IOException {
        servidor.abrirConexao();
    }
    
    public void run() {  
        criarServidor();
        
        try {
            iniciar();
        } catch (IOException ex) {
            Logger.getLogger(NovoJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
    

	
    
        /*
	public static void main(String argv[]) throws Exception {
		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomeSocket = new ServerSocket(6789);
		
		while(true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);
		}
	}
     * */
     
    



