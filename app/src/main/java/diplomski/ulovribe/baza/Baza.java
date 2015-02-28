package diplomski.ulovribe.baza;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Baza extends SQLiteOpenHelper {

    //naziv baze podataka
    private static final String DATABASE_NAME = "baza";
    //verzija baze, promjenom vrijednosti briše se cijela baza i ponovno kreira nova s početnim vrijednostima
    private static final int DATABASE_VERSION = 1;

    //konstruktor klase, proslijeđen context potreban za kreiranje objekta
    public Baza(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Riba.SQL_CREATE);
        db.execSQL(Lokacija.SQL_CREATE);
        db.execSQL(Mamac.SQL_CREATE);
        db.execSQL(Pribor.SQL_CREATE);
        db.execSQL(Unos.SQL_CREATE);
        popuniPocetneVrijednosti(db);
    }

    private void popuniPocetneVrijednosti(SQLiteDatabase db) {
        String[] ribe= {"Štuka","Šaran","Som","Smuđ","Amur"};
        for(String riba:ribe){
            ContentValues cv= new ContentValues();
            cv.put(Riba.COLUMN_NAZIV,riba);
            db.insert(Riba.TABLE_NAME,null,cv);
        }

        String[] pribori= {"dvodjelni štap","trodjelni štap","teleskopski štap","direktaš","bolognese"};
        for(String pribor:pribori){
            ContentValues cv= new ContentValues();
            cv.put(Pribor.COLUMN_NAZIV,pribor);
            db.insert(Pribor.TABLE_NAME,null,cv);
        }

        String[] mamci= {"kukuruz","bojla","crv","kruh","glista"};
        for(String mamac:mamci){
            ContentValues cv= new ContentValues();
            cv.put(Mamac.COLUMN_NAZIV,mamac);
            db.insert(Mamac.TABLE_NAME,null,cv);
        }

        String[] lokacije= {"Sava","Drava","Dunav","Vuka","Bosut"};
        for(String lokacija:lokacije){
            ContentValues cv= new ContentValues();
            cv.put(Lokacija.COLUMN_NAZIV,lokacija);
            db.insert(Lokacija.TABLE_NAME,null,cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Lokacija.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Mamac.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Pribor.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Riba.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Unos.TABLE_NAME);
        onCreate(db);
    }

    //region Riba
    public long dodajRibu(String ime){
        SQLiteDatabase db=getWritableDatabase();

        ContentValues cv= new ContentValues();
        cv.put(Riba.COLUMN_NAZIV,ime);
        return db.insert(Riba.TABLE_NAME,null,cv);
    }

    public void urediRibu(Riba riba){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(Riba.COLUMN_NAZIV,riba.getIme());
        db.update(Riba.TABLE_NAME,cv,Riba.COLUMN_ID+"="+riba.getId(),null);
        db.close();
    }

    public List<Riba> dohvatiRibe(){
        SQLiteDatabase db=getWritableDatabase();

        String[] columns = { Riba.COLUMN_ID, Riba.COLUMN_NAZIV };

        Cursor c = db.query(Riba.TABLE_NAME, columns, null, null, null, null, null);

        List<Riba> listRiba = new ArrayList<Riba>();

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            Riba riba= new Riba(c.getLong(0),c.getString(1));
            listRiba.add(riba);
        }
        c.close();
        db.close();
        return listRiba;
    }
    //endregion

    public long dodajLokaciju(String naziv){
        SQLiteDatabase db=getWritableDatabase();

        ContentValues cv= new ContentValues();
        cv.put(Lokacija.COLUMN_NAZIV,naziv);
        return db.insert(Lokacija.TABLE_NAME,null,cv);
    }

    public void urediLokaciju(Lokacija lokacija){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(Lokacija.COLUMN_NAZIV,lokacija.getNaziv());
        db.update(Lokacija.TABLE_NAME,cv,Lokacija.COLUMN_ID+"="+lokacija.getId(),null);
        db.close();
    }

    public List<Lokacija> dohvatiLokacije(){
        SQLiteDatabase db=getReadableDatabase();

        String[] columns = { Lokacija.COLUMN_ID, Lokacija.COLUMN_NAZIV };

        Cursor c = db.query(Lokacija.TABLE_NAME, columns, null, null, null, null, null);

        List<Lokacija> listLokacija = new ArrayList<Lokacija>();

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            Lokacija lokacija=new Lokacija(c.getLong(0),c.getString(1));
            listLokacija.add(lokacija);
        }
        c.close();
        db.close();
        return listLokacija;
    }

    public long dodajPribor(String naziv){
        SQLiteDatabase db=getWritableDatabase();

        ContentValues cv= new ContentValues();
        cv.put(Pribor.COLUMN_NAZIV,naziv);
        return db.insert(Pribor.TABLE_NAME,null,cv);
    }

    public void urediPribor(Pribor pribor){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(Pribor.COLUMN_NAZIV,pribor.getIme());
        db.update(Pribor.TABLE_NAME,cv,Pribor.COLUMN_ID+"="+pribor.getId(),null);
        db.close();
    }

    public List<Pribor> dohvatiPribore(){
        SQLiteDatabase db=getReadableDatabase();

        String[] columns = { Pribor.COLUMN_ID, Pribor.COLUMN_NAZIV };

        Cursor c = db.query(Pribor.TABLE_NAME, columns, null, null, null, null, null);

        List<Pribor> listPribora = new ArrayList<Pribor>();

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            Pribor pribor=new Pribor(c.getLong(0),c.getString(1));
            listPribora.add(pribor);
        }
        c.close();
        db.close();
        return listPribora;
    }

    public long dodajMamac(String naziv){
        SQLiteDatabase db=getWritableDatabase();

        ContentValues cv= new ContentValues();
        cv.put(Mamac.COLUMN_NAZIV,naziv);
        return db.insert(Mamac.TABLE_NAME,null,cv);
    }

    public void urediMamac(Mamac mamac){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(Mamac.COLUMN_NAZIV,mamac.getIme());
        db.update(Mamac.TABLE_NAME,cv,Mamac.COLUMN_ID+"="+mamac.getId(),null);
        db.close();
    }

    public List<Mamac> dohvatiMamce(){
        SQLiteDatabase db=getReadableDatabase();

        String[] columns = { Mamac.COLUMN_ID, Mamac.COLUMN_NAZIV };

        Cursor c = db.query(Mamac.TABLE_NAME, columns, null, null, null, null, null);

        List<Mamac> listMamac = new ArrayList<Mamac>();

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            Mamac mamac=new Mamac(c.getLong(0),c.getString(1));
            listMamac.add(mamac);
        }
        c.close();
        db.close();
        return listMamac;
    }

    public long dodajUnos(Unos unos){
        SQLiteDatabase db=getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(Unos.COLUMN_DATUM,unos.getDatum().getTime());
        cv.put(Unos.COLUMN_KOLICINA,unos.getKolicina());
        cv.put(Unos.COLUMN_LOKACIJA_ID,unos.getLokacija().getId());
        cv.put(Unos.COLUMN_MAMAC_ID,unos.getMamac().getId());
        cv.put(Unos.COLUMN_PRIBOR_ID,unos.getPribor().getId());
        cv.put(Unos.COLUMN_RIBA_ID,unos.getRiba().getId());
        return db.insert(Unos.TABLE_NAME,null,cv);
    }

    public List<Unos> dohvatiSveUnose(){
        SQLiteDatabase db= getReadableDatabase();

        //query za dohvaćanje unosa, povezuje više tablica kako bi se odmah dobili svi potrebni podaci
        String query= "SELECT "+ Unos.TABLE_NAME+"."+Unos.COLUMN_ID+","+Unos.TABLE_NAME+"."+Unos.COLUMN_DATUM+","+Unos.TABLE_NAME+"."+Unos.COLUMN_KOLICINA+","+
                Lokacija.TABLE_NAME+"."+Lokacija.COLUMN_ID+","+Lokacija.TABLE_NAME+"."+Lokacija.COLUMN_NAZIV+","+
                Mamac.TABLE_NAME+"."+Mamac.COLUMN_ID+","+Mamac.TABLE_NAME+"."+Mamac.COLUMN_NAZIV+","+
                Pribor.TABLE_NAME+"."+Pribor.COLUMN_ID+","+Pribor.TABLE_NAME+"."+Pribor.COLUMN_NAZIV+","+
                Riba.TABLE_NAME+"."+Riba.COLUMN_ID+","+Riba.TABLE_NAME+"."+Riba.COLUMN_NAZIV+
                " FROM "+Unos.TABLE_NAME+
                " INNER JOIN "+Lokacija.TABLE_NAME+" ON "+Unos.TABLE_NAME+"."+Unos.COLUMN_LOKACIJA_ID+"="+Lokacija.TABLE_NAME+"."+Lokacija.COLUMN_ID+
                " INNER JOIN "+Mamac.TABLE_NAME+" ON "+Unos.TABLE_NAME+"."+Unos.COLUMN_MAMAC_ID+"="+Mamac.TABLE_NAME+"."+Mamac.COLUMN_ID+
                " INNER JOIN "+Pribor.TABLE_NAME+" ON "+Unos.TABLE_NAME+"."+Unos.COLUMN_PRIBOR_ID+"="+Pribor.TABLE_NAME+"."+Pribor.COLUMN_ID+
                " INNER JOIN "+Riba.TABLE_NAME+" ON "+Unos.TABLE_NAME+"."+Unos.COLUMN_RIBA_ID+"="+Riba.TABLE_NAME+"."+Riba.COLUMN_ID;

        Cursor c=db.rawQuery(query,null);

        List<Unos> listaUnosa= new ArrayList<Unos>();

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            long id=c.getLong(0);

            long lDatum=c.getLong(1);
            Date datum=new Date(lDatum);

            double kolicina=c.getDouble(2);

            Lokacija lokacija= new Lokacija(c.getLong(3),c.getString(4));

            Mamac mamac= new Mamac(c.getLong(5),c.getString(6));

            Pribor pribor= new Pribor(c.getLong(7),c.getString(8));

            Riba riba= new Riba(c.getLong(9),c.getString(10));

            Unos unos = new Unos(id,datum,kolicina,riba,mamac,pribor,lokacija);
            listaUnosa.add(unos);
        }

        return listaUnosa;
    }

    public void obrisiPodatke(){
        SQLiteDatabase db= getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + Lokacija.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Mamac.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Pribor.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Riba.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Unos.TABLE_NAME);


        db.execSQL(Riba.SQL_CREATE);
        db.execSQL(Lokacija.SQL_CREATE);
        db.execSQL(Mamac.SQL_CREATE);
        db.execSQL(Pribor.SQL_CREATE);
        db.execSQL(Unos.SQL_CREATE);
        popuniPocetneVrijednosti(db);
        db.close();
    }
}
