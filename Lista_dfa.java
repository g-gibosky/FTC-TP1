import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Celula_dfa simplesmente encadeada
 * @author Joao Paulo Domingos Silva
 * @version 1.1 02/2012
 */
class Celula_dfa {
	public String elemento; // Estado que que a transição vai.
	public char peso; // Qual o simbolo da transição.
	public Celula_dfa prox; // Aponta a Celula_dfa prox.
	public Celula_dfa ant; // Aponta a Celula_dfa prox.

	/**
	 * Construtor da classe.
	 * @param elemento Elemento e peso inserido na Celula_dfa.
	 */
	Celula_dfa(String elemento, char peso) {
		this.elemento = elemento;
		this.peso = peso;
		this.prox = null;
		this.ant = null;
	}

	/**
	 * Construtor da classe.
	 * @param elemento Elemento inserido na Celula_dfa_dfa.
	 * @param prox Aponta a Celula_dfa_dfa prox.
	 */
	Celula_dfa(String elemento, char peso, Celula_dfa prox) {
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
public class Lista_dfa {
	private Celula_dfa primeiro; // Primeira Celula_dfa: SEM elemento valido.
	private Celula_dfa ultimo; // Ultima Celula_dfa: COM elemento valido.
	private String tipo; // inicial, final ou normal

	/**
	 * Construtor da classe: Instancia uma Celula_dfa (primeira e ultima, com aresta de destino e peso).
	 */
	public Lista_dfa() {
		primeiro = new Celula_dfa("0", '0');
		ultimo = primeiro;
	}

	public Lista_dfa(String tipo) {
		primeiro = new Celula_dfa("0", '0');
		ultimo = primeiro;
		this.tipo = tipo;
	}
	/**
	 * Insere um elemento de maneira ordenada.
	 * @param destino Vertice de destino.
	 * @param peso Peso do vertice.
	 */
	// TODO: Make the search binary, look at first and last at same interation

	public void inserir(String destino, char peso){
		inserirFim(destino, peso);
	}

	/**
	 * Insere um elemento na ultima posicao da sequencia.
	 * @param elemento Elemento a inserir.
	 */
	public void inserirFim(String destino, char peso) {
		Celula_dfa tmp = new Celula_dfa(destino, peso);
		ultimo.prox = tmp;
		tmp.ant = ultimo;
		ultimo = ultimo.prox;
		tmp = null;
	}

	/**
	 * Mostra os elementos separados por espacos.
	 */
	public void mostrar() {
		if (this.tipo == "inicial" ||  this.tipo == "final")
			System.out.println("Tipo do estado: "+this.tipo);
		System.out.print("[ "); // Comeca a mostrar.
		for (Celula_dfa i = primeiro.prox; i != null; i = i.prox) {
			System.out.print("("+i.elemento +" - "+i.peso+") ");
		}
		System.out.println("] "); // Termina de mostrar.
	}

	// /**
	//  * Mostra os elementos separados por espacos.
	//  */
	// public void mostrar(String a) {
	// 	for (Celula_dfa i = primeiro.prox; i != null; i = i.prox) {
	// 		if (i.elemento < a) {
	// 			if(this.pesquisar(a) == true){
	// 				System.out.println(a+","+i.elemento +","+i.peso);		
	// 			}
	// 		}
	// 	}
	// }

	public boolean pesquisar(String destino) {
		boolean result = false;
		for (Celula_dfa i = primeiro.prox; i != null; i = i.prox) {
			result = (i.elemento == destino) ? true : false;
			i = ultimo;
		}
		return result;
	}
}
