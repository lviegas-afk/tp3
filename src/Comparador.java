import java.util.Comparator;

class Comparador implements Comparator<NodoHuffman> {
    public int compare(NodoHuffman x, NodoHuffman y) {
        return x.frecuencia - y.frecuencia;
    }
}