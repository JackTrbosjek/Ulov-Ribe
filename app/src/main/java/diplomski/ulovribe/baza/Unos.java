package diplomski.ulovribe.baza;

import java.util.Date;

public class Unos {
    public static final String TABLE_NAME="Unos";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_RIBA_ID="ribaId";
    public static final String COLUMN_PRIBOR_ID="priborId";
    public static final String COLUMN_MAMAC_ID="mamacId";
    public static final String COLUMN_LOKACIJA_ID="lokacijaId";
    public static final String COLUMN_KOLICINA="kolicina";
    public static final String COLUMN_DATUM="datum";
    public static final String SQL_CREATE="create table "
            + TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_RIBA_ID
            + " integer, "+ COLUMN_PRIBOR_ID+" integer, "+ COLUMN_MAMAC_ID+" integer, "+COLUMN_LOKACIJA_ID+" integer, "+COLUMN_KOLICINA+" REAL, "+COLUMN_DATUM+" INTEGER)";

    private long id;
    private Date datum;
    private double kolicina;
    private Riba riba;
    private Mamac mamac;
    private Pribor pribor;
    private Lokacija lokacija;

    public Unos(long id, Date datum, double kolicina, Riba riba, Mamac mamac, Pribor pribor, Lokacija lokacija) {
        this.id = id;
        this.datum = datum;
        this.kolicina = kolicina;
        this.riba = riba;
        this.mamac = mamac;
        this.pribor = pribor;
        this.lokacija = lokacija;
    }

    public Unos(Date datum, double kolicina, Riba riba, Mamac mamac, Pribor pribor, Lokacija lokacija) {
        this.datum = datum;
        this.kolicina = kolicina;
        this.riba = riba;
        this.mamac = mamac;
        this.pribor = pribor;
        this.lokacija = lokacija;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public double getKolicina() {
        return kolicina;
    }

    public void setKolicina(double kolicina) {
        this.kolicina = kolicina;
    }

    public Riba getRiba() {
        return riba;
    }

    public void setRiba(Riba riba) {
        this.riba = riba;
    }

    public Mamac getMamac() {
        return mamac;
    }

    public void setMamac(Mamac mamac) {
        this.mamac = mamac;
    }

    public Pribor getPribor() {
        return pribor;
    }

    public void setPribor(Pribor pribor) {
        this.pribor = pribor;
    }

    public Lokacija getLokacija() {
        return lokacija;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
    }

    @Override
    public String toString() {
        return riba.getIme()+" "+kolicina+" "+pribor.getIme()+" "+mamac.getIme()+" "+lokacija.getNaziv()+" "+datum.toString();
    }
}
