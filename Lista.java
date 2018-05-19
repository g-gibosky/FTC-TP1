import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Celula simplesmente encadeada
 * @author Joao Paulo Domingos Silva
 * @version 1.1 02/2012
 */
class Celula {
	public int elemento; // Estado que que a transição vai.
	public char peso; // Qual o simbolo da transição.
	public Celula prox; // Aponta a celula prox.
	public Celula ant; // Aponta a celula prox.

	/**
	 * Construtor da classe.
	 * @param elemento Elemento e peso inserido na celula.
	 */
	Celula(int elemento, char peso) {
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
	Celula(int elemento, char peso, Celula prox) {
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
public class Lista {
	private Celula primeiro; // Primeira celula: SEM elemento valido.
	private Celula ultimo; // Ultima celula: COM elemento valido.
	private String tipo; // inicial, final ou normal

	/**
	 * Construtor da classe: Instancia uma celula (primeira e ultima, com aresta de destino e peso).
	 */
	public Lista() {
		primeiro = new Celula(-1, '0');
		ultimo = primeiro;
	}

	public Celula getPrimeiro(){
		return this.primeiro;
	}
	/**
	 * Insere um elemento de maneira ordenada.
	 * @param destino Vertice de destino.
	 * @param peso Peso do vertice.
	 */
	// TODO: Make the search binary, look at first and last at same interation

	public void inserir(int destino, char peso){
			// inserirInicio(destino, peso);
		// if (primeiro == ultimo) {
		// }else{
		// 	if (ultimo.elemento < destino) {
				inserirFim(destino, peso);
		// 	}else{
				// Celula tmp = new Celula(destino, peso);
				// for (Celula i = primeiro.prox; i != ultimo; i = i.prox) {
				// 	if (i.elemento < destino) {
				// 		tmp.prox = i.prox;
				// 		i.prox = tmp;
				// 		tmp = null;
				// 		break;
				// 	}
				// }
		// 	}
		// }
	}

	/**
	 * Insere um elemento na primeira posicao da sequencia.
	 * @param elemento Elemento a inserir.
	 */
	public void inserirInicio(int destino, char peso) {
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
	public void inserirFim(int destino, char peso) {
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
	public Lista_dfa getTransitions(ArrayList<Character> alfabeto) {
		Lista_dfa b = new Lista_dfa("inicial");
		String t = "";
		int j = 0;
		for (j = 0; j < alfabeto.size();j++) {
			if (t != "") {
				b.inserir(t,alfabeto.get(j-1));
				// b.mostrar();
				t = "";
			}
			int counter = 0;
			for (Celula i = primeiro.prox; i != null; i = i.prox) {
				if ( i.peso == alfabeto.get(j) ) {
					if (t == "") {
						t += Integer.toString(i.elemento);
					}else{
						t += ","+Integer.toString(i.elemento);
					}
					counter++;
				}
			}	
		}
		b.inserir(t,alfabeto.get(j-1));
		// b.mostrar();
		t = "";
		return b;
	}

	/**
	 * Mostra os elementos separados por espacos.
	 */
	public void mostrar(int a) {
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
			if (i.elemento < a) {
				if(this.pesquisar(a) == true){
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
				inserir(count,'1');	
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
					// pos = get_pos(i.elemento);
					// pos_ori = original.get_pos(j.elemento);
					// if (pos == 0) {
					// 	removerInicio();
					// }else if (pos == size-1) {
					// 	removerFim();
					// }else{
					// remove_aresta(j.elemento);
					// }
					// System.out.println("Posicao no Completo: "+pos);
					// System.out.println("Posicao no Original: "+pos_ori);
					// System.out.println("Elemento no Completo: "+i.elemento);
					// System.out.println("Elemento no Original: "+j.elemento);
					// System.out.println();
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
