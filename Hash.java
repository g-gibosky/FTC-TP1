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

	public void mostrar(){
		for (int i = 0; i < this.tamanho; i++) {
			tabela[i].mostrar();
		}
	}

	public void inserir_g (int origem, int destino, char peso){
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

	 // public int h(int elemento){
	 //    return elemento % tamanho;
	 // }

	/*
		void remover(int elemento){
			int resp = NULO;
			if (pesquisar(elemento) == false){
			 throw new Exception("Erro ao remover!");
			} else {
			 int pos = h(elemento);
			 resp = tabela[pos].remover(elemento);
			}
			return resp;
	 }
	*/
}
