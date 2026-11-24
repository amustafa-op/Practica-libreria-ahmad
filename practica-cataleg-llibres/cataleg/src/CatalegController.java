import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Lògica del catàleg: manté la llista de llibres en memòria
 * i la sincronitza amb un fitxer CSV (isbn;titol;autor;genere;preu;any).
 */
public class CatalegController {

    private List<Llibre> llibres = new ArrayList<>();
    private final String rutaFitxer;

    public CatalegController(String rutaFitxer) {
        this.rutaFitxer = rutaFitxer;
        carregar();
    }

    /** Valida les dades, comprova que l'ISBN no estigui repetit i NOMÉS després guarda al fitxer. */
    public Llibre afegirLlibre(String isbn, String titol, String autor, String genere, double preu, int any) throws Exception {
        // Si alguna dada no és vàlida, el constructor llença l'excepció i no es toca res.
        Llibre nou = new Llibre(isbn, titol, autor, genere, preu, any);

        if (buscarPerIsbn(nou.getIsbn()) != null) {
            throw new IsbnDuplicatException("Ja existeix un llibre amb l'ISBN " + nou.getIsbn() + ".");
        }

        llibres.add(nou);
        try {
            guardar();
        } catch (IOException e) {
            llibres.remove(nou); // desfem el canvi perquè memòria i fitxer no quedin desincronitzats
            throw e;
        }
        return nou;
    }

    public List<Llibre> obtenirTots() {
        return new ArrayList<>(llibres);
    }

    /** Retorna el llibre amb aquest ISBN o null si no existeix. */
    public Llibre buscarPerIsbn(String isbn) {
        if (isbn == null) return null;
        String buscat = isbn.trim();
        for (Llibre l : llibres) {
            if (l.getIsbn().equalsIgnoreCase(buscat)) {
                return l;
            }
        }
        return null;
    }

    /** Retorna els llibres el títol dels quals conté el text (sense distingir majúscules). */
    public List<Llibre> buscarPerTitol(String titol) {
        List<Llibre> resultat = new ArrayList<>();
        if (titol == null) return resultat;
        String buscat = titol.trim().toLowerCase();
        for (Llibre l : llibres) {
            if (l.getTitol().toLowerCase().contains(buscat)) {
                resultat.add(l);
            }
        }
        return resultat;
    }

    /** Modifica un llibre existent (l'ISBN s'usa per identificar-lo i no canvia). */
    public void editarLlibre(String isbn, String titol, String autor, String genere, double preu, int any) throws Exception {
        Llibre antic = buscarPerIsbn(isbn);
        if (antic == null) {
            throw new IllegalArgumentException("No existeix cap llibre amb l'ISBN " + isbn + ".");
        }

        // Validem amb un llibre nou abans de modificar res: si falla, l'original queda intacte.
        Llibre nou = new Llibre(antic.getIsbn(), titol, autor, genere, preu, any);

        int pos = llibres.indexOf(antic);
        llibres.set(pos, nou);
        try {
            guardar();
        } catch (IOException e) {
            llibres.set(pos, antic); // rollback
            throw e;
        }
    }

    /** Llegeix el CSV. Si el fitxer no existeix, comença amb un catàleg buit. */
    private void carregar() {
        Path ruta = Paths.get(rutaFitxer);
        if (!Files.exists(ruta)) {
            System.out.println("Avís: el fitxer '" + rutaFitxer + "' no existeix. Es crearà en guardar el primer llibre.");
            return;
        }

        try {
            List<String> linies = Files.readAllLines(ruta, StandardCharsets.UTF_8);
            int numLinia = 0;
            for (String linia : linies) {
                numLinia++;
                if (linia.trim().isEmpty()) continue;
                try {
                    Llibre l = Llibre.fromCsv(linia);
                    if (buscarPerIsbn(l.getIsbn()) != null) {
                        System.out.println("Avís: línia " + numLinia + " ignorada (ISBN repetit).");
                    } else {
                        llibres.add(l);
                    }
                } catch (Exception e) {
                    System.out.println("Avís: línia " + numLinia + " ignorada (" + e.getMessage() + ").");
                }
            }
        } catch (IOException e) {
            System.out.println("Error llegint el fitxer '" + rutaFitxer + "': " + e.getMessage());
        }
    }

    /** Reescriu el CSV complet amb tots els llibres (crea la carpeta si cal). */
    private void guardar() throws IOException {
        Path ruta = Paths.get(rutaFitxer);
        if (ruta.getParent() != null) {
            Files.createDirectories(ruta.getParent());
        }
        List<String> linies = new ArrayList<>();
        for (Llibre l : llibres) {
            linies.add(l.toCsv());
        }
        Files.write(ruta, linies, StandardCharsets.UTF_8);
    }
}
