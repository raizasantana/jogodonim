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
import java.util.Random;
	
public class Servidor {
    ServerSocket socketServidor;
    int porta = 6789;
    Jogador jogador = null;
    static int numJogadores = 0;
    Jogador espectador;
    ServidorRequisicoes servidorRequisicoes;

    static String tela = "999"; // [0] - fileira 1 [1] - fileira 2 [2] - fileira 3

    int tipoJogador; // 1 = jog1, 2 = jog2, 3 = visitante

    static int turno = 1;
    static boolean jogoIniciado = false;
    static int vitoria = 0;
	
	public Servidor( int porta ) {
		this.porta = porta;
		
                jogador = new Jogador();
                espectador = null;
                numJogadores = 0;
                vitoria = 0;
                
                tela = "999";
                
                jogoIniciado = false;
                
                
		try {
                    socketServidor = new ServerSocket(porta);
                    System.out.println(" [ SERVIDOR CRIADO ]");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
        
        
	public void abrirConexao() throws IOException {
            
            // Cria palitos aleatoriamente
            Random r = new Random();
            tela = String.valueOf( r.nextInt(8) + 2 ) + String.valueOf( r.nextInt(8) + 2 ) + String.valueOf( r.nextInt(8) + 2 ); 
		
            while(true) {

                System.out.print("Servidor: Esperando alguem se conectar... [" + String.valueOf(numJogadores) +"] \n");

                if ( numJogadores < 2 ) {
                    
                    jogador = new Jogador();

                    jogador.socketConexao = socketServidor.accept();
                    
                    servidorRequisicoes = new ServidorRequisicoes(this, jogador);
                    
                    new Thread(servidorRequisicoes).start();
                    
                
                } else {
                    
                    espectador.socketConexao = socketServidor.accept();
                    
                    espectador = new Jogador();
                    espectador.nome = "Espectador";

                    System.out.println(" Conectou Espectador!");

                    tipoJogador = 3;

                    servidorRequisicoes = new ServidorRequisicoes(this, espectador);
                    
                    new Thread(servidorRequisicoes).start();
                }

            }
		
        }

    public void fecharConecao() throws IOException {
        numJogadores--;
        
        if ( numJogadores == 0 ) {
            socketServidor.close();
        }
    }
    
}
