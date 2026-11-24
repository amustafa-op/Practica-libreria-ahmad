import java.util.ArrayList;
import java.util.List;

public class CatalegController {

    private List<Llibre> llibres = new ArrayList<>();
    private final String rutaFitxer;

    public CatalegController(String rutaFitxer) {
        this.rutaFitxer = rutaFitxer;
        // TODO: carregar() el fitxer en començar (i controlar que no existeixi)
    }

    // TODO: comprovar que l'ISBN no estigui repetit, validar i NOMES despres guardar al fitxer
    public Llibre afegirLlibre(String isbn, String titol, String autor, String genere, double preu, int any) throws Exception {
        throw new UnsupportedOperationException("TODO");
    }

    public List<Llibre> obtenirTots() {
        return new ArrayList<>(llibres);
    }

    // TODO
    public Llibre buscarPerIsbn(String isbn) {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO
    public List<Llibre> buscarPerTitol(String titol) {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO
    public void editarLlibre(String isbn, String titol, String autor, String genere, double preu, int any) throws Exception {
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: llegir el CSV (isbn;titol;autor;genere;preu;any) i omplir la llista
    private void carregar() {
    }

    // TODO: reescriure el CSV amb tots els llibres
    private void guardar() {
    }
}
