

public class Llibre {



    public static final String SEPARADOR = ";";


    public static final int ANY_MINIM = 1950;

    private String isbn;
    private String titol;
    private String autor;
    private String genere;
    private double preu;
    private int any;

    public Llibre(String isbn, String titol, String autor, String genere, double preu, int any) throws Exception {
        setIsbn(isbn);
        setTitol(titol);
        setAutor(autor);
        setGenere(genere);
        setPreu(preu);
        setAny(any);
    }

    public String getIsbn() { return this.isbn; }
    public String getTitol() { return this.titol; }
    public String getAutor() { return this.autor; }
    public String getGenere() { return this.genere; }
    public double getPreu() { return this.preu; }
    public int getAny() { return this.any; }

    public void setIsbn(String isbn) throws IsbnInvalidException {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IsbnInvalidException("L'ISBN no pot estar buit.");
        }
        this.isbn = isbn.trim();
    }

    public void setTitol(String titol) {
        this.titol = validarText(titol, "El títol");
    }

    public void setAutor(String autor) {
        this.autor = validarText(autor, "L'autor");
    }

    public void setGenere(String genere) {
        this.genere = validarText(genere, "El gènere");
    }

    public void setPreu(double preu) throws PreuInvalidException {
        if (Double.isNaN(preu) || Double.isInfinite(preu) || preu < 0) {
            throw new PreuInvalidException("El preu ha de ser un numero major que 0.");
        }
        this.preu = preu;
    }

    public void setAny(int any) throws AnyInvalidException {
        if (any <= ANY_MINIM) {
            throw new AnyInvalidException("L'any de publicació no pot ser anteriro a " + ANY_MINIM + ".");
        }
        this.any = any;
    }



    private static String validarText(String valor, String nomCamp) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nomCamp + " no pot estar buit.");
        }
        if (valor.contains(SEPARADOR)) {
            throw new IllegalArgumentException(nomCamp + " no pot contenir el caràcter '" + SEPARADOR + "'.");
        }
        return valor.trim();
    }



    public String toCsv() {
        return String.join(SEPARADOR, isbn, titol, autor, genere, String.valueOf(preu), String.valueOf(any));
    }



    public static Llibre fromCsv(String linia) throws Exception {
        String[] c = linia.split(SEPARADOR, -1);
        if (c.length != 6) {
            throw new IllegalArgumentException("Nombre de camps incorrecte (" + c.length + " en lloc de 6).");
        }
        return new Llibre(c[0], c[1], c[2], c[3], Double.parseDouble(c[4].trim()), Integer.parseInt(c[5].trim()));
    }
}
