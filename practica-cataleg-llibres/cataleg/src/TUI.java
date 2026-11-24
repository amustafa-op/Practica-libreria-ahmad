import java.util.Scanner;

public class TUI {

    private Scanner sc = new Scanner(System.in);

    public String mostrarMenu() {
        System.out.println("======================================================");
        System.out.println("  Benvingut/da al Gestor del Catàleg de Llibres");
        System.out.println("======================================================");
        System.out.println("  1. Registrar un nou llibre (Create)");
        System.out.println("  2. Cercar / Mostrar llibres (Read)");
        System.out.println("  3. Actualitzar un llibre existent (Update)");
        System.out.println("  4. Eliminar un llibre (Delete)");
        System.out.println("  5. Sortir del programa");
        System.out.println();
        System.out.print("  Selecciona una opció (1-5): ");
        return sc.nextLine();
    }

    public String llegirLinia() {
        return sc.nextLine();
    }

    public void mostrarMissatge(String missatge) {
        System.out.println(missatge);
    }

    // TODO: demanar ISBN, titol, autor, genere, preu i any (controlant que preu i any siguin numeros)

    // TODO: mostrar els llibres en format taulat

    // TODO: submenu de cerca (per ISBN o per titol)
}
