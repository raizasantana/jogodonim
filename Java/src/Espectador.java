
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gkaustchr
 */
public class Espectador {
    Socket conexao;
    BufferedReader entrada;
    DataOutputStream saida;

    boolean conectado = false;
    boolean jogoAcabou = false;
    int vencedor = 0;
    String tela = null;




    public Espectador(String host, int porta) throws UnknownHostException, IOException {
        conexao = new Socket(host, porta);

        entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream())); //new BufferedReader(new InputStreamReader(System.in));
        saida = new DataOutputStream(conexao.getOutputStream());

        System.out.println("Espectador conectado!");
    }

    public void entrarNoJogo() throws IOException {
        String nome = "ESPECTADOR";

        while (true) {

            saida.writeBytes(nome + '\n');

            String in = entrada.readLine();

            if ( !in.equals("0") ) {
                conectado = true;
                break;
            } else {
                System.out.println("Espectador: entrarNoJogo: Negado!");
            }
        }

        System.out.println("Espectador: Conectado ao server!");

    }

    public void assistir() throws IOException, InterruptedException {
        String in;
        
        while ( jogoAcabou() == 0 ) {

            atualizarTela();
            
        }

        jogoAcabou = true;
        
        vencedor = jogoAcabou();

    }

    public void sairJogo () throws IOException {
            conexao.close();
    }

    public String atualizarTela() throws IOException {
        saida.writeBytes( Status.createStatus("TELA").getValor() + '\n' );

        String in = entrada.readLine();

        tela = in;

        return tela;

    }

    public int jogoAcabou() throws IOException {
        saida.writeBytes( Status.createStatus("JOGO_ACABOU").getValor() + '\n' );

        String in = entrada.readLine();

        if ( in.equals("0") ) 
            return 0;

        else 
            return Integer.parseInt(in);

    }
    
    public int turno() throws IOException {
        saida.writeBytes( Status.createStatus("TURNO").getValor() + '\n' );

        String in = entrada.readLine();

        return Integer.parseInt(in);

    }

    public void fecharConexao() throws IOException {
        conexao.close();
    }
}
