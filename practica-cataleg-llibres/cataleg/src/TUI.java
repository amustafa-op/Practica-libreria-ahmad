import java.util.List;
import java.util.Scanner;

/** Interfície d'usuari per consola: menús, entrada validada i taules. */
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
        return sc.nextLine().trim();
    }

    public String llegirLinia() {
        return sc.nextLine();
    }

    public void mostrarMissatge(String missatge) {
        System.out.println(missatge);
    }

    /** Demana un text; si 'actual' no és null, Enter el conserva. Repeteix fins que sigui vàlid. */
    public String demanarText(String etiqueta, String actual) {
        while (true) {
            System.out.print(etiqueta + (actual != null ? " [" + actual + "]" : "") + ": ");
            String text = sc.nextLine().trim();
            if (text.isEmpty() && actual != null) return actual;
            if (text.isEmpty()) {
                System.out.println("  Aquest camp no pot estar buit.");
            } else if (text.contains(Llibre.SEPARADOR)) {
                System.out.println("  No es pot utilitzar el caràcter '" + Llibre.SEPARADOR + "'.");
            } else {
                return text;
            }
        }
    }

    /** Demana un preu decimal (accepta coma o punt). Si 'actual' no és null, Enter el conserva. */
    public double demanarPreu(Double actual) {
        while (true) {
            System.out.print("Preu (€)" + (actual != null ? " [" + actual + "]" : "") + ": ");
            String text = sc.nextLine().trim().replace(',', '.');
            if (text.isEmpty() && actual != null) return actual;
            try {
                double preu = Double.parseDouble(text);
                if (preu > 0 && !Double.isInfinite(preu)) return preu;
                System.out.println("  El preu ha de ser major que 0.");
            } catch (NumberFormatException e) {
                System.out.println("  Introdueix un número vàlid (ex. 12.50).");
            }
        }
    }

    /** Demana un any enter >= 1950. Si 'actual' no és null, Enter el conserva. */
    public int demanarAny(Integer actual) {
        while (true) {
            System.out.print("Any de publicació" + (actual != null ? " [" + actual + "]" : "") + ": ");
            String text = sc.nextLine().trim();
            if (text.isEmpty() && actual != null) return actual;
            try {
                int any = Integer.parseInt(text);
                if (any >= Llibre.ANY_MINIM) return any;
                System.out.println("  L'any no pot ser anterior a " + Llibre.ANY_MINIM + ".");
            } catch (NumberFormatException e) {
                System.out.println("  Introdueix un any enter (ex. 1999).");
            }
        }
    }

    /** Demana totes les dades d'un llibre nou: {isbn, titol, autor, genere, preu, any} (preu i any com a text). */
    public String[] demanarDadesLlibre() {
        String isbn = demanarText("ISBN", null);
        String titol = demanarText("Títol", null);
        String autor = demanarText("Autor", null);
        String genere = demanarText("Gènere", null);
        double preu = demanarPreu(null);
        int any = demanarAny(null);
        return new String[]{isbn, titol, autor, genere, String.valueOf(preu), String.valueOf(any)};
    }

    /** Mostra els llibres en format taulat. */
    public void mostrarLlibres(List<Llibre> llibres) {
        if (llibres.isEmpty()) {
            System.out.println("No s'ha trobat cap llibre.");
            return;
        }
        String format = "| %-15s | %-28s | %-22s | %-15s | %9s | %5s |%n";
        String linia = "+" + "-".repeat(17) + "+" + "-".repeat(30) + "+" + "-".repeat(24)
                + "+" + "-".repeat(17) + "+" + "-".repeat(11) + "+" + "-".repeat(7) + "+";
        System.out.println(linia);
        System.out.printf(format, "ISBN", "Títol", "Autor", "Gènere", "Preu (€)", "Any");
        System.out.println(linia);
        for (Llibre l : llibres) {
            System.out.printf(format, retallar(l.getIsbn(), 15), retallar(l.getTitol(), 28),
                    retallar(l.getAutor(), 22), retallar(l.getGenere(), 15),
                    String.format("%.2f", l.getPreu()), l.getAny());
        }
        System.out.println(linia);
        System.out.println(llibres.size() + " llibre(s).");
    }

    /** Talla un text si supera l'amplada de la columna. */
    private String retallar(String text, int max) {
        return text.length() <= max ? text : text.substring(0, max - 3) + "...";
    }

    /** Submenú de cerca. Retorna "1" (tots), "2" (ISBN), "3" (títol) o "4" (tornar). */
    public String mostrarSubmenuCerca() {
        System.out.println();
        System.out.println("  --- Consulta de llibres ---");
        System.out.println("  1. Mostrar tots els llibres");
        System.out.println("  2. Cercar per ISBN");
        System.out.println("  3. Cercar per títol");
        System.out.println("  4. Tornar al menú principal");
        System.out.print("  Selecciona una opció (1-4): ");
        return sc.nextLine().trim();
    }
}
