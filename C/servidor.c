#include <stdio.h>
#include <string.h>    //strlen
#include <stdlib.h>    //strlen
#include <sys/socket.h>
#include <arpa/inet.h> //inet_addr
#include <unistd.h>    //write
#include <pthread.h> //for threading , link with lpthread

#define MAX 2
#define N 10

/*
* 	VARIÁVEIS
*/

int sck_usuarios[MAX], sck_watcher;
char *lista_usuarios[MAX], *watcher;
static int indice = -1, partida = 0, watchers = 0, rodada = 0; 
static int palitos[3];

/*
*	FUNÇÕES
*/
int criar_sock()
{
	int sock = socket(AF_INET , SOCK_STREAM , 0);
	if (sock == -1)
	{
		perror("Não foi possível criar o socket.");
		exit (EXIT_FAILURE);	
	}

	return sock;	
}

void  config_server(struct sockaddr_in *server)
{	
	server->sin_family = AF_INET;
	server->sin_addr.s_addr = INADDR_ANY;
	server->sin_port = htons( 8888 );
}

void binder(int sock, struct sockaddr_in server)
{
	if( bind(sock,(struct sockaddr *)&server , sizeof(server)) < 0)
	{
		perror("Erro de BIND.");
		exit (EXIT_FAILURE);
	}
}

void enviar_mensagem(char *msg, int sock)
{
	if( send(sock , msg , strlen(msg) , 0) < 0)
	{
		perror("Mensagen não enviada.");
		exit (EXIT_FAILURE);
	}
}

char *get_msg(int sock, int flag)
{
	char *server_reply = malloc(2000);
	if( recv(sock , server_reply , 2000 , flag) < 0)
	{	
		printf("Flag mensagem com erro: %d. ",flag);
		perror("Mensagen não recebida.");
		exit (EXIT_FAILURE);
	}

	return server_reply;
}

void add_player(char *nickname, int sock)
{	
	char *msg = malloc(10);
	
	indice++;
	lista_usuarios[indice] = nickname;
	sck_usuarios[indice] = sock;
	printf("%d usuário conectado ao servidor.\n\n",indice+1);
	//sprintf(msg, "Usuário %s is online.",nickname);
	//enviar_mensagem(msg,sock);
}

void add_watcher(char *nickname, int sock)
{	
	char *msg = malloc(10);
	watchers++;
	watcher = malloc(10);
	watcher = nickname;
	sck_watcher =  sock;
	sprintf(msg, "Usuário %s is online.\n",nickname);
	enviar_mensagem(msg,sock);
}

void lista_usuarios_online()
{
	int i;
	for(i = 0; i <= indice; i++)
		printf("Nickname: %s | Socket: %d\n",lista_usuarios[i], sck_usuarios[i]);
}

void enviar_anunciado()
{	
	int i = 0;
	char *mensagem = malloc(1000), *temp = malloc(1000);
	
	rodada++;
	sprintf(temp,"\n%d Rodada.\n",rodada);
	strcat(mensagem,temp);
	for(i = 0; i < 3; i++)
	{
		palitos[i] = rand()% 8 + 2;	
		sprintf(temp,"Fileira %d: %d\n",i,palitos[i]);
		strcat(mensagem,temp);
	}
	printf("%s",mensagem);	
	enviar_mensagem(mensagem,sck_usuarios[0]);
}

void *connection_handler(void *socket_desc)
{
	int sock = *(int*)socket_desc, qtd_rodadas = 0, i;
	int read_size;
	char *message = malloc(2000), *nickname = malloc(10);

	nickname = get_msg(sock, 0);
	

	//Player or watcher?
	message = "Deseja Jogar ou Assistir? [J = JOGAR / A = ASSISTIR] ";
	enviar_mensagem(message, sock);
	message = get_msg(sock, 0);
	
	if(strcmp(message,"J") == 0)
		add_player(nickname,sock);
	else
		add_watcher(nickname,sock);

	if(indice == 1)
		enviar_anunciado();
	/*if(indice == 0)
	{
		printf("Hey bitch!");
		//lista_usuarios_online();
		montar_palitos();
		
		/*int i;	
		for(i = 0; i <= indice; i++)
			enviar_mensagem()*/

}
	

	/*if(indice == 1)
	{	

		partida = 1;
		int i, p1, p2, n = 1;
		char *mensagem = malloc(100), *mensagem2 = malloc(100), *msg_resultado = malloc(1000);
		Winner winner; 

		for(i = 1; i <= indice; i++)
			enviar_mensagem("\n\n\t*** JOKENPO!. ***\n\n\tPEDRA 0\n\tPAPEL 1\n\tTESOURA 2\n\tLAGARTO 3\n\tSPOCK 4\n\nQual jogada?",sck_usuarios[i]);
				


		mensagem = get_msg(sck_usuarios[0], 0);
		p1 = atoi(mensagem);

		mensagem2 = get_msg(sck_usuarios[1], 0);
		p2 = atoi(mensagem2);

		winner = vencedor_rodada(p1, p2);
		
		//Placar
		for(i = 1; i <= indice; i++)
			enviar_mensagem(winner.msg,sck_usuarios[i]);
			
		}
		
		return 0;*/



/*
*	PROGRAMA PRINCIPAL
*/

int main(int argc , char *argv[])
{
	int sock, client_sock , c , read_size, *new_sock;
	struct sockaddr_in server , client;
	char client_message[10] = "\0";
	
	

	sock = criar_sock();
	config_server(&server);
	binder(sock, server);
	listen(sock , 10);

	puts("\n\nSERVIDOR ONLINE\n");

	c = sizeof(struct sockaddr_in);


	while( (client_sock = accept(sock, (struct sockaddr *)&client, (socklen_t*)&c)) )
	{
		puts("Aceitou uma conexão.");
		 
		pthread_t sniffer_thread;
		new_sock = malloc(1);
		*new_sock = client_sock;
		 
		if( pthread_create( &sniffer_thread , NULL ,  connection_handler , (void*) new_sock) < 0)
		{
		    perror("could not create thread");
		    return 1;
		}
		 
		//Now join the thread , so that we dont terminate before the thread
		pthread_join( sniffer_thread , NULL);
		puts("Handler assigned\n");
	}

	return 0;
}

