package diplomski.ulovribe.baza;

public class Lokacija {
    public static final String TABLE_NAME="Lokacija";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_NAZIV="naziv";
    public static final String SQL_CREATE="create table "
            + TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAZIV
            + " text not null)";

    private long id;
    private String naziv;

    public Lokacija(long id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return naziv;
    }
}
