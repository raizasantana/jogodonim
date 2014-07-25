all: 	
		clear
		gcc -pthread -o server servidor.c
		gcc -o clienteativo cliente-ativo.c
		./server

