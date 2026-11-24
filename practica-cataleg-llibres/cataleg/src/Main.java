import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        CatalegController ctrl = new CatalegController("data/llibres.csv");
        TUI tui = new TUI();
        String opcio;

        do {
            opcio = tui.mostrarMenu();

            try {
                switch (opcio) {
                    case "1":
                        afegirLlibre(tui, ctrl);
                        break;
                    case "2":
                        consultarLlibres(tui, ctrl);
                        break;
                    case "3":
                        modificarLlibre(tui, ctrl);
                        break;
                    case "4":
                        tui.mostrarMissatge("Opció no implementada.");
                        break;
                    case "5":
                        // Els canvis ja es guarden al fitxer en cada operació.
                        tui.mostrarMissatge("Fins aviat!");
                        break;
                    default:
                        tui.mostrarMissatge("Opció invàlida.");
                }
            } catch (Exception e) {
                tui.mostrarMissatge("Error: " + e.getMessage());
            }
            System.out.println();
        } while (!opcio.equals("5"));
    }

    /** Opció 1: demana les dades i dóna d'alta el llibre. */
    private static void afegirLlibre(TUI tui, CatalegController ctrl) throws Exception {
        tui.mostrarMissatge("--- Alta de llibre ---");
        String[] d = tui.demanarDadesLlibre();
        Llibre l = ctrl.afegirLlibre(d[0], d[1], d[2], d[3], Double.parseDouble(d[4]), Integer.parseInt(d[5]));
        tui.mostrarMissatge("Llibre '" + l.getTitol() + "' afegit correctament.");
    }

    /** Opció 2: llistat complet o cerca per ISBN / títol. */
    private static void consultarLlibres(TUI tui, CatalegController ctrl) {
        String opcio = tui.mostrarSubmenuCerca();
        switch (opcio) {
            case "1":
                tui.mostrarLlibres(ctrl.obtenirTots());
                break;
            case "2":
                tui.mostrarMissatge("ISBN a cercar: ");
                Llibre trobat = ctrl.buscarPerIsbn(tui.llegirLinia());
                List<Llibre> resultat = new ArrayList<>();
                if (trobat != null) resultat.add(trobat);
                tui.mostrarLlibres(resultat);
                break;
            case "3":
                tui.mostrarMissatge("Títol (o part del títol) a cercar: ");
                tui.mostrarLlibres(ctrl.buscarPerTitol(tui.llegirLinia()));
                break;
            case "4":
                break;
            default:
                tui.mostrarMissatge("Opció invàlida.");
        }
    }

    /** Opció 3: modifica un llibre identificat per ISBN (Enter conserva el valor actual). */
    private static void modificarLlibre(TUI tui, CatalegController ctrl) throws Exception {
        tui.mostrarMissatge("--- Modificació de llibre ---");
        tui.mostrarMissatge("ISBN del llibre a modificar: ");
        Llibre l = ctrl.buscarPerIsbn(tui.llegirLinia());
        if (l == null) {
            tui.mostrarMissatge("No existeix cap llibre amb aquest ISBN.");
            return;
        }
        tui.mostrarMissatge("Prem Enter per mantenir el valor actual.");
        String titol = tui.demanarText("Títol", l.getTitol());
        String autor = tui.demanarText("Autor", l.getAutor());
        String genere = tui.demanarText("Gènere", l.getGenere());
        double preu = tui.demanarPreu(l.getPreu());
        int any = tui.demanarAny(l.getAny());

        ctrl.editarLlibre(l.getIsbn(), titol, autor, genere, preu, any);
        tui.mostrarMissatge("Llibre modificat correctament.");
    }
}
