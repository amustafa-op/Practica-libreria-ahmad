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
                        tui.mostrarMissatge("Fins aviat!");
                        break;
                    default:
                        tui.mostrarMissatge("Opció invàlida.");
                }
            } catch (Exception e) {
                // TODO: mostrar un missatge d'error que serveixi a l'usuari
            }
        } while (!opcio.equals("5"));
    }

    private static void afegirLlibre(TUI tui, CatalegController ctrl) {
        // TODO
    }

    private static void consultarLlibres(TUI tui, CatalegController ctrl) {
        // TODO
    }

    private static void modificarLlibre(TUI tui, CatalegController ctrl) {
        // TODO
    }
}
