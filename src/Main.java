import java.util.Scanner;

public class Main {

    static Scanner scanInput = new Scanner(System.in);

    public static void main(String[] args) {

        String cadenaEntrada = "";
        System.out.println("\n\033[1m" + "Ingrese la cadena de caracteres que desa codificar, precione enter para terminar:" + "\033[0m\n");
        System.out.print("\033[1m" + "> " + "\033[0m");
        cadenaEntrada = scanInput.nextLine();

        Huffman aH = new Huffman();
        aH.codificarCadena(cadenaEntrada.trim());

        System.out.println("Bye!");
    }
}