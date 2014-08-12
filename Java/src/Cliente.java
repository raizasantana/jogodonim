/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gkaustchr
 */
import java.io.*;
import java.net.*;
	
public class Cliente {
	Socket conexao;
	BufferedReader entrada;
	DataOutputStream saida;
        
        boolean conectado = false;
        static boolean jogoIniciado = false;
        boolean meuTurno = false;
        //int vitoria = 0;
        String id = "0";
        
        String jogada = null;
        String tela = null;
    
	
	
	
	public Cliente(String host, int porta) throws UnknownHostException, IOException {
            conexao = new Socket(host, porta);
            
            entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream())); //new BufferedReader(new InputStreamReader(System.in));
            saida = new DataOutputStream(conexao.getOutputStream());
                
            System.out.println("Cliente conectado!");
	}
	
	public void entrarNoJogo() throws IOException {
            String nome = "YOLO";

            while (true) {
                
                saida.writeBytes(nome + '\n');

                String in = entrada.readLine();

                if ( !in.equals("0") ) {
                    conectado = true;
                    id = in;
                    break;
                } else {
                    System.out.println("Cliente: entrarNoJogo: Negado!");
                }
            }

            System.out.println("Cliente: Conectado ao server!");

	}
	
	public void jogar() throws IOException, InterruptedException {
            
            while (true) {
                
                System.out.println("Cliente: testar se jogo começou");
                
                saida.writeBytes( Status.createStatus("JOGO_INICIADO").getValor() + '\n' );

                String in = entrada.readLine();
                
                System.out.println("Resposta SERVER: " + in);

                if (in.equals(Status.createStatus("SIM").getValor())) {
                    conectado = true;
                    break;
                }
            }
            
            jogoIniciado = true;

            while (true) {

                // Testar se é minha vez
                while (true) {

                    saida.writeBytes( Status.createStatus("MEU_TURNO").getValor() + '\n' );

                    String in = entrada.readLine();

                    if ( in.equals("NAO")) {

                        Thread.sleep(3000);
                        continue;

                    } else {

                        // Atualiza tela
                        saida.writeBytes( Status.createStatus("TELA").getValor() + '\n' );

                        in = entrada.readLine();

                        tela = in;

                        meuTurno = true;
                        break;
                    }
                }

                // Jogada
                while (true) {
                    
                    Thread.sleep(1000);

                    if ( jogada == null ) {
                        continue;
                    }
                    
                    System.out.println("Cliente: Jogada feita: " +jogada+"\n" );

                    saida.writeBytes(">"+jogada+"\n");
                    jogada = null;
                    meuTurno = false;

                    String in = entrada.readLine();

                    if ( in.equals("SIM") ) {
                        meuTurno = false;
                        break;
                    }
                }
            }
	
	}
	
	public void sairJogo () throws IOException {
		conexao.close();
	}
        
        public void jogada( String jogada ) {
            System.out.println("JOGADA: '" + jogada +"'");
            this.jogada = jogada;
        }
        
        public boolean meuTurno() throws IOException {
            saida.writeBytes( Status.createStatus("MEU_TURNO").getValor() + '\n' );

            String in = entrada.readLine();
            
            return in.equals("SIM");
            
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
           
            if  ( in.equals( String.valueOf(id ) ) )
                return 1;
            else 
                return 2;

        }

    public void fecharConexao() throws IOException {
        conexao.close();
    }
}

