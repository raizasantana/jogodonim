
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aluno
 */
public class ServidorRequisicoes extends Thread {
	Jogador jogador;
        Servidor servidor;
        int indice;
        
        static int cont = 0; 

    public ServidorRequisicoes(Servidor servidor, Jogador jogador) {
        this.servidor = servidor;
        this.jogador = jogador;
        
        cont++;
        
        indice = cont;
    }
    
    public void run() {

        try {
            jogador.saidaJogador = new BufferedReader(new InputStreamReader(jogador.socketConexao.getInputStream()));
            jogador.entradaJogador = new DataOutputStream(jogador.socketConexao.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServidorRequisicoes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("[ RUN SERVIDOR ]");
        
        try {

            // Espera nome do jogador

            String nome = jogador.saidaJogador.readLine();
            
            jogador.nome = nome;

            String msgSaida = String.valueOf( indice );

            jogador.entradaJogador.writeBytes(msgSaida + '\n');

            System.out.println("Servidor: Conectou Jogador [NOME: " +jogador.nome+ "]");
            
            if ( indice == 2 )
                servidor.jogoIniciado = true;
        
            while ( true ) {
                //System.out.println("Servidor: Aguardando Requisição");

                String msg = jogador.saidaJogador.readLine();

                if ( msg.equals(Status.createStatus("JOGO_INICIADO").getValor()) ) {
                    
                    //System.out.println("Thread["+ indice +"]: Requisição de jogo iniciado");

                    if ( servidor.jogoIniciado == true ) {
                        msgSaida = Status.createStatus("SIM").getValor();
                    } else {
                        msgSaida = Status.createStatus("NAO").getValor();
                    }

                    //System.out.println("Thread["+ indice +"]: Requisição respondida!");
                    jogador.entradaJogador.writeBytes(msgSaida + '\n');
                    
                } else if ( msg.equals(Status.createStatus("MEU_TURNO").getValor()) ) {
                    
                    //System.out.println("Thread["+ indice +"]: Requisição meu turno");

                    if ( servidor.turno == indice ) {
                        msgSaida = Status.createStatus("SIM").getValor();
                    } else {
                        msgSaida = Status.createStatus("NAO").getValor();
                    }

                    //System.out.println("Thread["+ indice +"]: Requisição respondida!");
                    jogador.entradaJogador.writeBytes(msgSaida + '\n');
                    
                
                } else if ( msg.equals(Status.createStatus("TURNO").getValor()) ) {
                    
                    //System.out.println("Thread["+ indice +"]: Requisição meu turno");

                    msgSaida = String.valueOf( servidor.turno );

                    //System.out.println("Thread["+ indice +"]: Requisição respondida!");
                    jogador.entradaJogador.writeBytes(msgSaida + '\n');
                    
                
                } else if ( msg.equals(Status.createStatus("TELA").getValor()) ) {
                    
                    //System.out.println("Thread["+ indice +"]: Requisição Tela");

                    msgSaida = servidor.tela;
                    
                    //System.out.println("Thread["+ indice +"]: Requisição respondida!");
                    jogador.entradaJogador.writeBytes(msgSaida + '\n');
                    
                
                    // Veio uma jogada ( ">FN" ) F - fileira | N - retirados
                } else if ( msg.charAt(0) == '>') {
                    
                    //System.out.println("Thread["+ indice +"]: Requisição de jogada");
                    
                    // Altera a tela
                    String temp = servidor.tela;
                    servidor.tela = "";
                    for (int i = 0; i < temp.length(); i++) {
                        if ( Integer.parseInt( String.valueOf( msg.charAt(1)) )-1 == i ) {
                            servidor.tela += String.valueOf( Integer.parseInt( String.valueOf(temp.charAt(i)) ) - Integer.parseInt( String.valueOf( msg.charAt(2) ) )  );
                        } else {
                            servidor.tela += temp.charAt(i);
                        }
                    }
                    
                    // se teve mudança na jogada
                    if ( ! temp.equals(servidor.tela) ) {
                        // Testa se o Jogo Acabou
                        if ( servidor.tela.equals("000") ) {
                            if (indice == 1)
                                servidor.vitoria = 2;
                            else
                                servidor.vitoria = 1;
                        }
                    }

                    System.out.println(">>>>>>>   TELA NOVA: '"+ servidor.tela +"'");
                    
                    if ( servidor.turno == 1 )
                        servidor.turno = 2;
                    else
                        servidor.turno = 1;
                    
                    msgSaida = Status.createStatus("SIM").getValor();
                    
                    //System.out.println("Thread["+ indice +"]: Requisição respondida!");
                    jogador.entradaJogador.writeBytes(msgSaida + '\n');
                    
                
                } else if ( msg.equals(Status.createStatus("JOGO_ACABOU").getValor()) ) {
                    
                    //System.out.println("Thread["+ indice +"]: Requisição Tela");

                    msgSaida = String.valueOf( servidor.vitoria );
                    
                    //System.out.println("Thread["+ indice +"]: Requisição respondida!");
                    jogador.entradaJogador.writeBytes(msgSaida + '\n');
                    
                
                    // Veio uma jogada ( ">FN" ) F - fileira | N - retirados
                } if ( msg.equals(Status.createStatus("FIM").getValor()) ) {
                    
                    msgSaida = Status.createStatus("SIM").getValor();
                    
                    jogador.entradaJogador.writeBytes(msgSaida + '\n');
                    
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ServidorRequisicoes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    jogador.socketConexao.close();
                    
                    servidor.fecharConecao();
                    
                }else {
                    System.out.println("Thread["+ indice +"]: Requisição X! <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                }

            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(ServidorRequisicoes.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
 
}
