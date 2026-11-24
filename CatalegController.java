import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



public class CatalegController {

    private List<Llibre> llibres = new ArrayList<>();
    private final String rutaFitxer;

    public CatalegController(String rutaFitxer) {
        this.rutaFitxer = rutaFitxer;
        carregar();
    }



    public Llibre afegirLlibre(String isbn, String titol, String autor, String genere, double preu, int any) throws Exception {


        Llibre nou = new Llibre(isbn, titol, autor, genere, preu, any);

        if (buscarPerIsbn(nou.getIsbn()) != null) {
            throw new IsbnDuplicatException("Ja existeix un libre amb l'ISBN " + nou.getIsbn() + ".");
        }

        llibres.add(nou);
        guardar();
        return nou;
    }

    public List<Llibre> obtenirTots() {
        return new ArrayList<>(llibres);
    }



    public Llibre buscarPerIsbn(String isbn) {
        if (isbn == null) return null;
        String buscat = isbn.trim();
        for (Llibre l : llibres) {
            if (l.getIsbn() == buscat) {
                return l;
            }
        }
        return null;
    }



    public List<Llibre> buscarPerTitol(String titol) {
        List<Llibre> resultat = new ArrayList<>();
        if (titol == null) return resultat;
        String buscat = titol.trim().toLowerCase();
        for (Llibre l : llibres) {
            if (l.getTitol().contains(buscat)) {
                resultat.add(l);
            }
        }
        return resultat;
    }



    public void editarLlibre(String isbn, String titol, String autor, String genere, double preu, int any) throws Exception {
        Llibre antic = buscarPerIsbn(isbn);
        if (antic == null) {
            throw new IllegalArgumentException("No existeix cap libre amb l'ISBN " + isbn + ".");
        }



        Llibre nou = new Llibre(antic.getIsbn(), titol, autor, genere, preu, any);

        llibres.set(llibres.indexOf(antic), nou);
    }



    private void carregar() {
        Path ruta = Paths.get(rutaFitxer);
        if (!Files.exists(ruta)) {
            System.out.println("Avis: el fitxer '" + rutaFitxer + "' no existeix. Es crearà en guardar el primer llibre.");
            return;
        }

        try {
            List<String> linies = Files.readAllLines(ruta, StandardCharsets.UTF_8);
            int numLinia = 0;
            for (String linia : linies) {
                numLinia++;
                if (linia.trim().isEmpty()) continue;
                try {
                    llibres.add(Llibre.fromCsv(linia));
                } catch (Exception e) {
                    System.out.println("Avís: línia " + numLinia + " ignorada (" + e.getMessage() + ").");
                }
            }
        } catch (IOException e) {
            System.out.println("Error legint el fitxer '" + rutaFitxer + "': " + e.getMessage());
        }
    }



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
