package diplomski.ulovribe;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import diplomski.ulovribe.R;
import diplomski.ulovribe.baza.Baza;
import diplomski.ulovribe.baza.Riba;
import diplomski.ulovribe.baza.Unos;

public class PregledUlovaActivity extends ActionBarActivity {
    Spinner spnrVrstaRibe;
    TextView tvStartDate,tvEndDate;
    ImageButton ibStartDate,ibEndDate;
    ListView listView;
    Baza baza;
    ListViewAdapter listAdapter;
    List<Unos> listaUnosa;
    Date startDate,endDate;

    SimpleDateFormat sdf= new SimpleDateFormat("dd.MM.yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregled_ulova);
        init();//povezivanje xml-a s java kodom

        //postavi adapter za listu
        listaUnosa=baza.dohvatiSveUnose();
        listAdapter=new ListViewAdapter(listaUnosa);
        listView.setAdapter(listAdapter);

        //postavi adapter za spinner
        spnrVrstaRibe.setAdapter(new SpinnerAdapter(baza.dohvatiRibe()));
        //odabir vrste ribe
        spnrVrstaRibe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filtriraj();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        postaviDatePickere();


    }

    public void filtriraj(){
        //kreiraj novu listu unosa
        List<Unos> novaLista= new ArrayList<Unos>();
        if(spnrVrstaRibe.getSelectedItemPosition()==0){ //ako su odabrane sve ribe
            novaLista=new ArrayList<Unos>(listaUnosa);//priduži novoj listi vrijednost svih unosa
        }else {
            Riba riba = (Riba) spnrVrstaRibe.getSelectedItem();//dohvati odabranu ribu
            //prođi kroz listu svih unosa
            for(Unos unos:listaUnosa){
                if(unos.getRiba().getId()==riba.getId()){ //ako je id ribe označene u spinneru jednak id-u ribe iz liste
                    novaLista.add(unos);//dodaj unos na novu listu
                }
            }

        }
        if(startDate!=null){//ako je start date postavljen
            Iterator<Unos> iterator= novaLista.iterator();
            while(iterator.hasNext()){
                Unos unos=iterator.next();//idući element iz liste
                if(unos.getDatum().before(startDate)){//ako je datum iz liste manji od početnog datuma
                    iterator.remove();//obriši zapis iz liste
                }
            }
            listAdapter.notifyDataSetChanged();
        }
        if(endDate!=null){
            Iterator<Unos> iterator= novaLista.iterator();
            while(iterator.hasNext()){
                Unos unos=iterator.next();//idući element iz liste
                if(unos.getDatum().after(endDate)){//ako je datum iz liste veci od end datuma
                    iterator.remove();//obriši zapis iz liste
                }
            }
            listAdapter.notifyDataSetChanged();
        }

        listAdapter.setListuUnosa(novaLista);//postavi novu listu u adapter
    }

    private void postaviDatePickere() {
        //prikaži DatePickere kad se klikne na kalendar
        final Calendar now= Calendar.getInstance();
        ibStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog datePicker= new DatePickerDialog(PregledUlovaActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar c= Calendar.getInstance();
                        c.clear();
                        c.set(year,monthOfYear,dayOfMonth);
                        tvStartDate.setText("Od: "+sdf.format(c.getTime()));
                        startDate=c.getTime();
                        filtriraj();
                    }
                },now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });
        ibEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog datePicker= new DatePickerDialog(PregledUlovaActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar c= Calendar.getInstance();
                        c.clear();
                        c.set(year,monthOfYear,dayOfMonth);
                        tvEndDate.setText("Do: "+sdf.format(c.getTime()));
                        endDate=c.getTime();
                        filtriraj();
                    }
                },now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });
    }


    private void init() {
        spnrVrstaRibe=(Spinner)findViewById(R.id.pregledSpnrVrstaRibe);
        tvStartDate=(TextView)findViewById(R.id.pregledTvStartDate);
        tvEndDate=(TextView)findViewById(R.id.pregledTvEndDate);
        ibStartDate=(ImageButton)findViewById(R.id.pregledIbStartDate);
        ibEndDate=(ImageButton)findViewById(R.id.pregledIbEndDate);
        listView=(ListView)findViewById(R.id.pregledListView);

        baza= new Baza(this);
    }

    //klasa adapter za spinner vrsta riba
    public class SpinnerAdapter extends BaseAdapter {
        //lista riba za koje se koristi adapter
        List<Riba> listaRiba;
        public SpinnerAdapter(List<Riba> listaRiba){
            this.listaRiba=listaRiba;
        }

        @Override
        public int getCount() {
            return listaRiba.size()+1;//broj elemenata spinnera + jedan za prvi element liste
        }

        @Override
        public Object getItem(int position) {
            return listaRiba.get(position-1);//objekt povezan s elementom liste
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //u slučaju da trenutni item nije kreiran
            if(convertView==null){
                //inflate item
                convertView= LayoutInflater.from(PregledUlovaActivity.this).inflate(android.R.layout.simple_list_item_1,parent,false);
            }
            //za prvi element spinnera (sve ribe)
            if(position==0){
                ((TextView)convertView).setText("Sve");
            }else {
                ((TextView) convertView).setText(listaRiba.get(position-1).getIme());//postavljanje teksta trenutnog itema
            }
            return convertView;
        }
    }

    public class ListViewAdapter extends BaseAdapter{
        List<Unos> listaUnosa;
        public ListViewAdapter(List<Unos> listaUnosa){
            this.listaUnosa=listaUnosa;
        }
        public void setListuUnosa(List<Unos> listaUnosa){
            this.listaUnosa=listaUnosa;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return listaUnosa.size();
        }

        @Override
        public Object getItem(int position) {
            return listaUnosa.get(position);
        }

        @Override
        public long getItemId(int position) {
            return listaUnosa.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=LayoutInflater.from(PregledUlovaActivity.this).inflate(R.layout.list_item_pregled,parent,false);//dohvati izgled za list item
            }
            //povezivanje izgleda list itema TextView
            TextView tvRiba= (TextView) convertView.findViewById(R.id.listViewRiba);
            TextView tvPribor=(TextView) convertView.findViewById(R.id.listViewPribor);
            TextView tvLokacija=(TextView)convertView.findViewById(R.id.listViewLokacija);

            Unos unos=listaUnosa.get(position); //unos za trenutni element

            //postavi vrijednosti unosa u TextView-ove
            tvRiba.setText(unos.getRiba().getIme()+" - "+unos.getKolicina()+"kg");
            tvPribor.setText(unos.getPribor().getIme()+" - "+unos.getMamac().getIme());
            tvLokacija.setText(unos.getLokacija().getNaziv()+" - "+sdf.format(unos.getDatum()));
            return convertView;
        }
    }
}
