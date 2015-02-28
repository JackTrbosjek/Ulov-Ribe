package diplomski.ulovribe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import diplomski.ulovribe.R;
import diplomski.ulovribe.baza.Baza;
import diplomski.ulovribe.baza.Lokacija;
import diplomski.ulovribe.baza.Mamac;
import diplomski.ulovribe.baza.Pribor;
import diplomski.ulovribe.baza.Riba;

public class UrediKategorijeActivity extends ActionBarActivity {
    Spinner spnrKategorija;
    Button btnDodaj;
    ListView listView;
    ListViewAdapter listAdapter;
    Baza baza;
    List<Riba> listaRiba;
    List<Pribor> listaPribora;
    List<Mamac> listaMamaca;
    List<Lokacija> listaLokacija;
    int kategorijaType=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uredi_kategorije);
        init();

        //postavljanje spinnera
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.kategorije_spinner_items, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrKategorija.setAdapter(adapter);

        //postavljanje liste
        listAdapter= new ListViewAdapter(listaRiba);
        listView.setAdapter(listAdapter);

        //odabir nove kategorije
        spnrKategorija.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kategorijaType=position;//postavljanje kategorija type na odabranu kategoriju
                // odredi koji je označeni element i promjeni listu za tu kategoriju
                switch (kategorijaType){
                    case 0:
                        listAdapter.setList(listaRiba);break;
                    case 1:
                        listAdapter.setList(listaPribora);break;
                    case 2:
                        listAdapter.setList(listaMamaca);break;
                    case 3:
                        listAdapter.setList(listaLokacija);break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //klik na button dodaj
        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prikaziDialog(null);
            }
        });
    }
    public void prikaziDialog(final Object item){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Kategorija");
        final EditText input = new EditText(this);
        if(item==null){//dodavanje novog itema kategorije
            alert.setMessage("Dodavanje Kategorije");
        }else{//uređivanje postojećeg itema kategorije
            alert.setMessage("Uređivanje Kategorije");
            input.setText(item.toString());
        }

        alert.setView(input);

        alert.setPositiveButton("Uredu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                if(item==null){
                    dodajKategoriju(value);
                }else{
                    urediKategoriju(item,value);
                }
            }
        });

        alert.setNegativeButton("Odustani", null);

        alert.show();

    }

    private void dodajKategoriju(String naziv) {
        switch (kategorijaType){
            case 0:
                long id=baza.dodajRibu(naziv);
                listaRiba.add(new Riba(id,naziv));
                listAdapter.setList(listaRiba);
                break;
            case 1:
                id=baza.dodajPribor(naziv);
                listaPribora.add(new Pribor(id,naziv));
                listAdapter.setList(listaPribora);
                break;
            case 2:
                id=baza.dodajMamac(naziv);
                listaMamaca.add(new Mamac(id,naziv));
                listAdapter.setList(listaMamaca);
                break;
            case 3:
                id=baza.dodajLokaciju(naziv);
                listaLokacija.add(new Lokacija(id,naziv));
                listAdapter.setList(listaMamaca);
                break;
        }
    }

    public void urediKategoriju(Object item,String noviNaziv){
        switch (kategorijaType){
            case 0:
                Riba riba=(Riba)item;
                riba.setIme(noviNaziv);
                baza.urediRibu(riba);
                break;
            case 1:
                Pribor pribor=(Pribor)item;
                pribor.setIme(noviNaziv);
                baza.urediPribor(pribor);
                break;
            case 2:
                Mamac mamac=(Mamac)item;
                mamac.setIme(noviNaziv);
                baza.urediMamac(mamac);
                break;
            case 3:
                Lokacija lokacija=(Lokacija)item;
                lokacija.setNaziv(noviNaziv);
                baza.urediLokaciju(lokacija);
                break;
        }
        listAdapter.notifyDataSetChanged();
    }

    private void init() {
        spnrKategorija=(Spinner)findViewById(R.id.kategorijeSpinner);
        btnDodaj=(Button)findViewById(R.id.kategorijeBtnDodaj);
        listView=(ListView)findViewById(R.id.kategorijeListView);
        //dohvati vrijednosti iz baze
        baza=new Baza(this);
        listaRiba=baza.dohvatiRibe();
        listaPribora=baza.dohvatiPribore();
        listaMamaca=baza.dohvatiMamce();
        listaLokacija=baza.dohvatiLokacije();
    }

    public class ListViewAdapter extends BaseAdapter{

        List<?>  list;
        public ListViewAdapter(List<?> list){
            this.list=list;
        }
        public void setList(List<?> list){
            this.list=list;
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView= LayoutInflater.from(UrediKategorijeActivity.this).inflate(R.layout.list_item_kategorije,parent,false);
            }
            TextView tvNaziv= (TextView) convertView.findViewById(R.id.kategorijaItemTvNaziv);
            tvNaziv.setText(list.get(position).toString());

            ImageButton ibEdit=(ImageButton)convertView.findViewById(R.id.kategorijeItemIbEdit);
            ibEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prikaziDialog(getItem(position));
                }
            });
            return convertView;
        }
    }
}
