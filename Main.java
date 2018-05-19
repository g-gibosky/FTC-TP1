import java.io.*;
import java.*;
import java.util.Formatter;
import java.util.Scanner;
import java.io.File;
import java.nio.charset.*;

	// Formato do arquivo
	// 0		#grafo(0) ou dígrafo(1)
	// 5 		#número total de vértices			
	// 0,1,1		#arestas: origem, destino, peso
	// 0,3,6		#número arbitrário de arestas
	// 0,4,2		#arestas terminam em FIM 
	// 3,4,3		
	// ...
	// FIM
	// 3		#nº do vértice para dizer o grau
	// 2,4		#aresta para verificar a existência

class Grafo{
	int completo, tipo, tamanho;

	public Grafo(){
		this.completo = 0;
		this.tipo = -1;
		this.tamanho = 0;
	}

	public Grafo(int tipo){
		this.tipo = tipo;
		this.completo = 0;
		this.tamanho = 0;
	}

	public static void build_graph(){
		String line;
		int ori, dest, weight, tipo, t_arestas, vertice;
		tipo = Integer.parseInt(MyIO.readLine());
		t_arestas = Integer.parseInt(MyIO.readLine());
		Grafo graph_info = new Grafo(tipo);
		HashIndiretoLista graph = new HashIndiretoLista(t_arestas);
		while((line = MyIO.readLine() ) != ""){
			// line = MyIO.readLine();
     	if (line.equals("FIM")){
    	  vertice = Integer.parseInt(MyIO.readLine());
    	  String [] aresta = MyIO.readLine().split(",");
    	  System.out.print(graph.grau_vertice(vertice)+"\n");
    	  System.out.print(graph.is_aresta(Integer.parseInt(aresta[0]), Integer.parseInt(aresta[1]))+"\n");
    	  // Arq.close();
    	  break;
  	  }else{
  	  	String [] path = line.split(",");
  	  	ori = Integer.parseInt(path[0]);
  	  	dest = Integer.parseInt(path[1]);
  	  	weight = Integer.parseInt(path[2]);
      	graph.inserir_g(ori, dest, weight);
      	if (tipo == 0) {
      		graph.inserir_g(dest, ori, weight);
      	}
      	graph_info.tamanho += 1;
  	  }
  	}
	  System.out.println(graph_info.tamanho);
	  graph.completo(t_arestas);
	  // graph.mostrar(t_arestas);
	  HashIndiretoLista completo = new HashIndiretoLista(t_arestas);
	  graph.complemento(completo);
	}
}

/**
 * Celula simplesmente encadeada
 * @author Joao Paulo Domingos Silva
 * @version 1.1 02/2012
 */
class Celula {
	public int elemento; // Elemento inserido na celula.
	public int peso; // Peso da aresta.
	public Celula prox; // Aponta a celula prox.
	public Celula ant; // Aponta a celula anterior.

	/**
	 * Construtor da classe.
	 * @param elemento Elemento e peso inserido na celula.
	 */
	Celula(int elemento, int peso) {
		this.elemento = elemento;
		this.peso = peso;
		this.prox = null;
		this.ant = null;
	}

	/**
	 * Construtor da classe.
	 * @param elemento Elemento inserido na celula.
	 * @param prox Aponta a celula prox.
	 */
	Celula(int elemento, int peso, Celula prox) {
		this.elemento = elemento;
		this.peso = peso;
		this.prox = prox;
		this.ant = null;
	}
}

/**
 * Lista dinamica simplesmente encadeada
 * @author Joao Paulo Domingos Silva - Modificado por Guilherme Gibosky
 * @version 1.1 02/2012
 */
	class Lista {
	private Celula primeiro; // Primeira celula: SEM elemento valido.
	private Celula ultimo; // Ultima celula: COM elemento valido.

	/**
	 * Construtor da classe: Instancia uma celula (primeira e ultima, com aresta de destino e peso).
	 */
	public Lista() {
		primeiro = new Celula(-1, -1);
		ultimo = primeiro;
	}

	/**
	 * Insere um elemento de maneira ordenada.
	 * @param destino Vertice de destino.
	 * @param peso Peso do vertice.
	 */
	// TODO: Make the search binary, look at first and last at same interation

	public void inserir(int destino, int peso){
				inserirFim(destino, peso);
	}

	/**
	 * Insere um elemento na primeira posicao da sequencia.
	 * @param elemento Elemento a inserir.
	 */
	public void inserirInicio(int destino, int peso) {
		Celula tmp = new Celula(destino, peso);
		tmp.prox = primeiro.prox;
		tmp.ant = primeiro;
		primeiro.prox = tmp;
		primeiro.ant = tmp;
		if (primeiro == ultimo) {
			ultimo = tmp;
		}
			tmp = null;
	}

	/**
	 * Insere um elemento na ultima posicao da sequencia.
	 * @param elemento Elemento a inserir.
	 */
	public void inserirFim(int destino, int peso) {
		Celula tmp = new Celula(destino, peso);
		ultimo.prox = tmp;
		tmp.ant = ultimo;
		ultimo = ultimo.prox;
		tmp = null;
	}

	/**
	 * Mostra os elementos separados por espacos.
	 */
	public void mostrar() {
		System.out.print("[ "); // Comeca a mostrar.
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
			System.out.print("("+i.elemento +" - "+i.peso+") ");
		}
		System.out.println("] "); // Termina de mostrar.
	}

	/**
	 * Mostra os elementos separados por espacos.
	 */
	public void mostrar(int a) {
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
			if (i.elemento > a) {
				if(this.pesquisar(a) == false){
					System.out.println(a+","+i.elemento +","+i.peso);		
				}
			}
		}
	}

	public boolean pesquisar(int destino) {
	 	boolean result = false;
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
			 result = (i.elemento == destino) ? true : false;
			 i = ultimo;
		}
		return result;
	}

	public int grau(){
		int count = 0;
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
			 count++;
		}
		return count;
	}

	public void cria_completo(int size, int pos) {
		for (int count = 0; count < size; count++) {
			if (count != pos) {
				inserir(count,1);	
			}
		}
	}

	/**
	 * Procura um elemento e retorna se ele existe.
	 * @param x Elemento a pesquisar.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	public int get_pos(int x) {
		int pos = 0;
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
			if(i.elemento == x){
			  break;
			}else{
				pos++;	
			}
		}
		return pos;
	}

	public void complementar(Lista original, int size){
		int pos = 0;
		int pos_ori = 0;
		int count;
		for (Celula i = original.primeiro.prox; i != null; i = i.prox) {
			for (Celula j = primeiro.prox; j != null; j = j.prox) {
				if (j.elemento == i.elemento) {
					if (j == primeiro.prox) {
						j.prox.ant = j.ant;
						primeiro.prox = j.prox;
						j = null;
						break;
					}else if (j == ultimo) {
						ultimo = j.ant;
	 					j = ultimo.prox = null;
	 					break;
					}else{
						j.ant.prox = j.prox;
						j.prox.ant = j.ant;
						j.prox = j.ant = null;
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Remove elemento de um indice especifico.
	 * Considera que primeiro elemento esta no indice 0.
	 * @param posicao Meio da remocao.
	 */
	public void remove_aresta(int elemento){
		Celula i;
		if (primeiro == ultimo){
			System.out.println("Erro ao remover (vazia)!");
		}else{
			// Caminhar ate chegar na posicao da remoção
			for(i = primeiro.prox; i.prox != null; i = i.prox){
				if (i.elemento == elemento) {
					i.ant.prox = i.prox;
					i.prox.ant = i.ant;
					i = null;
					break;
				}	
			}
		}
	}
	
	// /**
	//  * Remove um elemento da primeira posicao da sequencia.
	//  * @return Elemento removido.
	//  * @throws Exception Se a sequencia nao contiver elementos.
	//  */
	public void removerInicio() {
		if (primeiro == ultimo) {
			System.out.println("Erro ao remover (vazia)!");
		}else{
			primeiro = primeiro.prox;
		}
	}

	public void removerFim(){
		Celula i = null;
		if (primeiro == ultimo) {
			System.out.println("Erro ao remover (vazia)!");
		}else {
		// Caminhar ate a penultima celula:
		for(i = primeiro; i.prox != ultimo; i = i.prox);
			ultimo = i;
	 		i = ultimo.prox = null;
		}
	}
}

class HashIndiretoLista {
	Lista tabela[];
	int tamanho;
	final int NULO = -1;

	public HashIndiretoLista (){
		this(7);
	}

	public HashIndiretoLista (int tamanho){
		this.tamanho = tamanho;
		tabela = new Lista[tamanho];
		for(int i = 0; i < tamanho; i++){
			 tabela[i] = new Lista();
		}
	}

	public void mostrar(int size){
		for (int i = 0; i < size; i++) {
			tabela[i].mostrar(i);
		}
	}

	public void inserir_g (int origem, int destino, int peso){
			tabela[origem].inserir(destino, peso);
	}

	public int grau_vertice(int vertice){
		return tabela[vertice].grau();
	}

	public String is_aresta(int ori, int dest){
		if (tabela[ori].pesquisar(dest) == true) {
			return "SIM";
		}else{
			return "NAO";
		}
	}

	public void completo(int size){
		boolean c = false;
		for (int i = 0; i < size; i++) {
			if (grau_vertice(i) == (size - 1)) {
				c = true;
			}else{
				break;
			}
		}
		if (c == true) {
			System.out.println("SIM");	
		}else{
			System.out.println("NAO");
		}
	}

	public void complemento(HashIndiretoLista graph){
		int i = 0;
		for (i = 0; i < graph.tamanho; i++) {
			graph.tabela[i].cria_completo(graph.tamanho, i);
		}

		// for (i = 0; i < graph.tamanho; i++) {
		// 	graph.tabela[i].mostrar();
		// }

		for (i = 0; i < graph.tamanho; i++) {			
			graph.tabela[i].complementar(tabela[i], graph.tamanho);
		}

		for (i = 0; i < graph.tamanho; i++) {
			graph.tabela[i].mostrar(i);
		}
	}
}

class Graph_Matrix {
	protected int numVertices;    // Vertices
	protected int type;    // 0 - grafo, 1 - digrafo
  protected int[][] adjMatrix;    
  protected int size;
	
	/**
   * Construtor
   * @param int m - Numero de vertices
   * @param int t - Tipo do Grafo
   * @author Guilherme Gibosky
   * @version 1.0
   */

	public Graph_Matrix(){
	  numVertices = 0;
	  this.adjMatrix = new int[100][100];
	  this.type = 0;
  }

  public Graph_Matrix(int m, int t){
	  numVertices = m;
	  this.adjMatrix = new int[m][m];
	  this.type = t;
  }

  /**
   * Preenche uma aresta entre 2 vertices com o peso passado
   * @param int m - Inteiro que representa o "eixo" x da matriz
   * @param int n - Inteiro que representa o "eixo" y da matriz
   * @param int p - Peso da aresta
   * @author Guilherme Gibosky
   * @version 1.0
   */
	public void fill_matrix(int m, int n, int p) {
		adjMatrix[m][n] = p;
		if (type == 0) {
			adjMatrix[n][m] = p;	
		}else{
			adjMatrix[n][m] = -p;
		}
    size++;
	}

	/**
   * Verifica o grau de um vertice.
   * @param int m - Inteiro que representa o grau
   * @author Guilherme Gibosky
   * @version 1.0
   */
	public int verifica_grau(int m) {
		int grau = 0;
		if (type == 0) {
			for (int j = 0; j < adjMatrix[m].length; j++) {
				if (adjMatrix[m][j] != 0)
						grau++;
			}
		}else{
			for (int i = 0; i < adjMatrix.length; i++) {
				for (int j = 0; j < adjMatrix[i].length; j++) {
					if (adjMatrix[m][j] != 0 || adjMatrix[i][m] != 0) {	
						grau++;	
					}
				}
			}	
		}
		return grau;
	}

	/**
   * Constroi o grafo complementar
   * @param Grafo g- Grafo de referencia
   * @author Guilherme Gibosky
   * @version 1.0
   */
	public void build_complementar(Graph_Matrix g) {

		for (int i = 0; i < g.adjMatrix.length; i++) {
    	for (int j = 0; j < g.adjMatrix[i].length; j++) {
    		if (g.adjMatrix[i][j] == 0 && (i != j)) {
    			adjMatrix[i][j] = 1;
    			if (type == 0){
						adjMatrix[j][i] = 1;	
    			}else{
    				adjMatrix[j][i] = -1;	
    			}
    		}
			}
		}
	}
		
	/**
   * Testa se o Grafo é completo
   * @param Metodo de Grafo, logo o grafo que chama é parametro
   * @author Guilherme Gibosky
   * @version 1.0
   */
	public boolean completo(){
		boolean resp = true;
		if (type == 0) {
			for (int i = 0; i < adjMatrix.length; i++) {
				for (int j = 0; j < adjMatrix[i].length; j++) {
					if (adjMatrix[i][j] == 0 && i <= j)
						resp = false;
				}
			}
		}else{
			for (int i = 0; i < adjMatrix.length; i++) {
				for (int j = 0; j < adjMatrix[i].length; j++) {
					if (adjMatrix[i][j] == 0)
						resp = false;
				}
			}
		}
		return resp;
	}

	/**
   * Imprime o grafo
   * @param Metodo de Grafo, logo o grafo que chama é parametro
   * @author Guilherme Gibosky
   * @version 1.0
   */
	public void print_matrix(){
		if (type == 0) {
			for (int i = 0; i < adjMatrix.length; i++) {
				for (int j = 0; j < adjMatrix[i].length; j++) {
					if (adjMatrix[i][j] != 0 && i < j)
						System.out.println(i+","+j+","+adjMatrix[i][j] + " ");	
				}
			}
		}else{
			for (int i = 0; i < adjMatrix.length; i++) {
				for (int j = 0; j < adjMatrix[i].length; j++) {
					if (adjMatrix[i][j] != 0)
						System.out.println(i+","+j+","+adjMatrix[i][j] + " ");		
				}
			}
		}
	}

	/**
   * Conta arestas do grafo
   * @param Metodo de Grafo, logo o grafo que chama é parametro
   * @author Guilherme Gibosky
   * @version 1.0
   */
	public int conta_arestas(){
		int contador = 0;
		if (type == 0) {
			for (int i = 0; i < adjMatrix.length; i++) {
				for (int j = 0; j < adjMatrix[i].length; j++) {
					if (adjMatrix[i][j] != 0 && i < j)
						contador++;
				}
			}
		}else{
			for (int i = 0; i < adjMatrix.length; i++) {
				for (int j = 0; j < adjMatrix[i].length; j++) {
					if (adjMatrix[i][j] != 0)
						contador++;	
				}
			}
		}
		return contador;
	}

	public static void build_graph_matrix(){
		String line;
		int ori, dest, weight, tipo, t_vertices;
		// Arq.openRead("pub.in");
		// Arq.openRead("/tmp/pub.in");
		tipo = Integer.parseInt(MyIO.readLine());
		t_vertices = Integer.parseInt(MyIO.readLine());
		Graph_Matrix g = new Graph_Matrix(t_vertices, tipo);
		while( (line = MyIO.readLine() ) != ""){
			// line = MyIO.readLine();
			if (line.equals("FIM") == false) {
				String [] path = line.split(",");
				g.fill_matrix(Integer.parseInt(path[0]), Integer.parseInt(path[1]),Integer.parseInt(path[2]));
			}else{
				line = MyIO.readLine();
				String [] path = line.split(",");
				int verifica_grau = g.verifica_grau(Integer.parseInt(path[0]));
				line = MyIO.readLine();
				path = line.split(",");
				if (g.adjMatrix[Integer.parseInt(path[0])][Integer.parseInt(path[1])] != 0) {
					System.out.println(verifica_grau);
					System.out.println("SIM");
				}else{
					System.out.println(verifica_grau);
					System.out.println("NAO");
				}
				// System.out.println("Matriz Original");
				// g.print_matrix();
			 	System.out.println(g.conta_arestas());
			 	if (g.completo() == true){
			 		System.out.println("SIM");
			 	}else{
					System.out.println("NAO");
			 	}
				Graph_Matrix g2 = new Graph_Matrix(t_vertices, tipo);
				g2.build_complementar(g);
				// System.out.println("Matriz Complementar");
				g2.print_matrix();
				// Arq.close();
				break;
			}
		}
	}
}

public class Main {
	 public static void main(String [] args){
		 Grafo a = new Grafo();
		 a.build_graph();
		 // Graph_Matrix b = new Graph_Matrix();
		 // b.build_graph_matrix();
		 // Startup code for server here
	 }
}