public class Llibre {

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

    // TODO: validar cada camp als setters i llençar l'excepció corresponent
    // (ISBN no buit, preu > 0, any >= 1950, titol/autor/genere no buits...)
    public void setIsbn(String isbn) throws IsbnInvalidException { this.isbn = isbn; }
    public void setTitol(String titol) { this.titol = titol; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setGenere(String genere) { this.genere = genere; }
    public void setPreu(double preu) throws PreuInvalidException { this.preu = preu; }
    public void setAny(int any) throws AnyInvalidException { this.any = any; }
}
