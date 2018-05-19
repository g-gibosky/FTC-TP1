import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DFA {
	// private static ArrayList<Lista_dfa> dfa;
	// private static HashMap<String, Lista_dfa> dfa;
	// private HashMap<ArrayList<String>> visited;
	
	public void printTransitions(String input) {
		for (int i = 0; i < 10 ;i++) {
			for (int j = 0; i < 10 ;i++) {

			}
		}
	}

	// public HashIndiretoLista faToDFA(HashIndiretoLista fa){
		
	// }

	public static void converter (HashMap<String, Lista_dfa> dfa, ArrayList<String> e, ArrayList<Character> alfabeto, HashIndiretoLista fa, String [] ts){
		for (String key : dfa.keySet()) {
			if (e.contains(key)) {
				System.out.println("Chave: "+key);
				String [] s = key.split(",");
				for (int k = 0;k < s.length ;k++) {
					for (int j = 0; j < alfabeto.size();j++) {
						int counter = 0;
						for (Celula f = fa.tabela[Integer.parseInt(s[k])].getPrimeiro().prox; f != null; f = f.prox) {
							if ( f.peso == alfabeto.get(j) ) {
								if (ts[j] == "") {
									ts[j] += Integer.toString(f.elemento);
								}else{
									ts[j] += ","+Integer.toString(f.elemento);
								}
								counter++;
							}
						}
						// a = fa.tabela[ Integer.parseInt(s[k]) ].getTransitions(alfabeto);
						// a.mostrar();
						// System.out.println("Transição: "+t);
					}
				}
				Lista_dfa b = new Lista_dfa("no");
				for (int x = 0; x < ts.length ;x++) {
					System.out.println(alfabeto.get(x));
					System.out.println(ts[x]);
					if (!e.contains(ts[x])) {
						e.add(ts[x]);
					}
					b.inserir(ts[x], alfabeto.get(x));
				}
					dfa.put(key,  b);
			}
		}
	}


	public static int getFrom(String a){
		String result = "";
		int i = 6;
		while(a.charAt(i) != '<'){
			result += a.charAt(i);
			i++;
		}
		return Integer.parseInt(result);
	}

	public static int getTo(String a){
		String result = "";
		int i = 4;
		while(a.charAt(i) != '<'){
			result += a.charAt(i);
			i++;
		}
		return Integer.parseInt(result);	
	}

	//public String simulate(String input) {
	//
	//}

	public static void main(String[] args) {
		String line;
		int dest = 0;
		int ori = 0;
		int i = 0;
		int j = 0;
		int last = 0;
		char symbol;
		Arq.openRead("/home/gui/Downloads/teste.jff");
		// ignore first four lines of header
		line = Arq.readLine();
		line = Arq.readLine();
		line = Arq.readLine();
		line = Arq.readLine();
		ArrayList<Character> alfabeto = new ArrayList<Character>();
		while(true){
			line = Arq.readLine().trim();
			if (line.substring(0, 4).equals("<tra")) {
				break;
			}else{
				if ( line.substring(0, 4).equals("<sta") ) {
					last = i;
					i++;
				}
			}
		}
		Arq.close();
		Arq.openRead("/home/gui/Downloads/teste.jff");
		HashIndiretoLista fa = new HashIndiretoLista(i);
		while(true){
			line = Arq.readLine().trim();
			if ( line.equals("</automaton>")) {
				Arq.close();
				fa.mostrar();
				System.out.println("Numero de Estados: "+i);
				System.out.println("Numero de Transições: "+j);
				break;
			}else if (line.substring(0, 4).equals("<tra")) {
				ori = getFrom(Arq.readLine().trim());
				dest = getTo(Arq.readLine().trim());
				symbol = Arq.readLine().trim().charAt(6);
				if (alfabeto.contains(symbol) == false) {
					alfabeto.add(symbol);
				}
				fa.inserir_g(ori, dest, symbol);
				j++;
			}
		}
		System.out.println("Alfabeto : "+Arrays.toString(alfabeto.toArray()));
		HashMap<String, Lista_dfa> dfa = new HashMap<String, Lista_dfa>();
		Lista_dfa a = new Lista_dfa("inicial");
		a = fa.tabela[0].getTransitions(alfabeto);
		// a.mostrar();
		dfa.put( "0",  a);
		dfa.put( "0,1",  a);
		String [] ts = new String[2];
		for (i = 0;i < 2 ;i++ ) {
			ts[i] = "";
		}
		ArrayList<String> estados_visitados = new ArrayList<String>();
		/*Parte que gera tudo*/
		estados_visitados.add("0");
		converter(dfa, estados_visitados, alfabeto, fa, ts);
		for (String key : dfa.keySet()) {
			System.out.println("Chave: "+key);
			a = dfa.get(key);
			a.mostrar();
		}
		System.out.println("Estados Visitados : "+Arrays.toString(estados_visitados.toArray()));
		// System.out.println(dfa.get("0").mostrar());
		// b.mostrar();
	}
}