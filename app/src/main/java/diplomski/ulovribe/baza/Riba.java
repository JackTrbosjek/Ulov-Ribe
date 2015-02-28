package diplomski.ulovribe.baza;


import java.io.Serializable;

public class Riba implements Serializable {
    public static final String TABLE_NAME="Riba";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_NAZIV="naziv";
    public static final String SQL_CREATE="create table "
            + TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAZIV
            + " text not null)";
    private long id;
    private String ime;

    public Riba(long id, String ime) {
        this.id = id;
        this.ime = ime;
    }

    public Riba(String ime) {
        this.ime = ime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    @Override
    public String toString() {
        return ime;
    }
}
