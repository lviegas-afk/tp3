import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Huffman {
    Map<Character, String> mapCodigos;
    Map<Character, Integer> mapFrecuencia;
    Character[] caracteres;
    PriorityQueue<NodoHuffman> cola;
    private NodoHuffman raiz;
    private String candenaOriginal;
    private List<Character> charList;

    public Huffman(){
        this.inicializar();
    }

    private void inicializar() {
        this.mapCodigos = new HashMap<Character, String>();
        this.mapFrecuencia = new HashMap<Character, Integer>();
        this.raiz = null;
    }

    // funcion iterativa para generar los codios de cada caracter  utlizando un arbol binario
    public void generarCodigo(NodoHuffman nodo, String s) {
        // Si el nodo es una hoja lo agrego al mapa de codigos
        if (nodo.izquierda == null && nodo.derecha == null && nodo.c != null){
            this.mapCodigos.put(nodo.c, s);
            return;
        }
        // En caso de no ser un nodo hoja, se se le agrega un 0 o un 1 segun corresponde izquierda o derecha y se vuelva a aplicar la comparadion
        generarCodigo(nodo.izquierda, s + "0");
        generarCodigo(nodo.derecha, s + "1");
    }

    public void codificarCadena(String cadena){
        this.candenaOriginal = cadena;

        // utilizamos una cola de prioridad para obtener los nodos con menor frecuencia y de esa manera generar el arbol de huffman
        this.cola = new PriorityQueue<NodoHuffman>(this.candenaOriginal.length(), new Comparador());

        this.inicializar();

        this.generarMapaDeFrecuencias();

        this.popularCola();

        this.generarCodigo(this.raiz, "");

        this.imprimirResultado();


    }

    private void imprimirResultado() {
        String espaciador = "";
        if(this.candenaOriginal.length() > 15) {
            for (int i = 0; i < this.candenaOriginal.length() - 15; i++) {
                espaciador = espaciador + " ";
            }
        } else {
            espaciador = " ";
        }

        String cadenaResultante = "";
        // Se recorre la cadena de entrada, por cada caracter se busca en el diccionario de codigos el codigo que le pertenece
        for(Character caracter : this.charList){
            cadenaResultante = cadenaResultante + this.mapCodigos.get(caracter);
        }
        System.out.println("Cadena Original" + espaciador + "--------------- Cadena resultante");
        System.out.println(this.candenaOriginal + " --------------- " + cadenaResultante);
    }

    private void popularCola() {
        // primero se recorre el mapa de frecuencias que contiene todos los caracteres de la frase y
        // la cantidad de ocurrencias de cada uno, y se obtiene los primeros nodos para generar el arbol de huffman
        for(Map.Entry<Character, Integer> entrada : this.mapFrecuencia.entrySet()){
            NodoHuffman nodo = new NodoHuffman();
            nodo.c = entrada.getKey();
            nodo.frecuencia = entrada.getValue();
            nodo.izquierda = null;
            nodo.derecha = null;
            this.cola.add(nodo);
        }
        // Generamos los nodos con las sumas para completar el arbol
        while (cola.size() > 1) {
            // se obtienen los nodos con menores frecuencias dentro de la lista
            NodoHuffman x = cola.peek();
            cola.poll();
            NodoHuffman y = cola.peek();
            cola.poll();
            // Generamos un nuevo nodo del arbol para almacenar el resultado
            NodoHuffman f = new NodoHuffman();
            // Se suman las frecuencias de los nodos menos frecuentes y se agregan al nuevo nuevo
            f.frecuencia = x.frecuencia + y.frecuencia;
            f.c = null;
            f.izquierda = x;
            f.derecha = y;
            this.raiz = f;
            cola.add(f);
        }
    }

    private void generarMapaDeFrecuencias() {
        charList = this.candenaOriginal.chars().mapToObj(e->(char)e).collect(Collectors.toList());
        // se recorre el arreglo ingresado por parametro
        for(Character character : this.charList){
            // si el caracter no esta dentro del mapa se lo inicializa en 1, caso contrario se le suma uno al valor obtenido
            if(!mapFrecuencia.containsKey(character)){
                mapFrecuencia.put(character,1);
            } else {
                mapFrecuencia.put(character, mapFrecuencia.get(character) + 1);
            }
        }
    }
}
