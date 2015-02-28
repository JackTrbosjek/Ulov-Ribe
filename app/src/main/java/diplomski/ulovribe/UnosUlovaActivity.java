package diplomski.ulovribe;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import diplomski.ulovribe.baza.Baza;
import diplomski.ulovribe.baza.Lokacija;
import diplomski.ulovribe.baza.Mamac;
import diplomski.ulovribe.baza.Pribor;
import diplomski.ulovribe.baza.Riba;
import diplomski.ulovribe.baza.Unos;

public class UnosUlovaActivity extends ActionBarActivity {

    Spinner spnrRiba, spnrPribor, spnrMamac, spnrLokacija;
    Button btnUnesi;
    ImageButton btnDatePicker;
    TextView tvDate;
    EditText etKolicina;

    Baza baza;
    SimpleDateFormat sdf= new SimpleDateFormat("dd.MM.yyyy");//oblik datuma (npr. 21.05.2015)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unos_ulova);
        init(); //povezivanje xml-a s java kodom

        //postavljanje adaptera za spinnere
        spnrMamac.setAdapter(new SpinnerAdapter(baza.dohvatiMamce()));
        spnrLokacija.setAdapter(new SpinnerAdapter(baza.dohvatiLokacije()));
        spnrRiba.setAdapter(new SpinnerAdapter(baza.dohvatiRibe()));
        spnrPribor.setAdapter(new SpinnerAdapter(baza.dohvatiPribore()));

        // postavljanje click listenera kada se odabere kalendar
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kreiranje dialoga za odabir datuma
                Calendar c= Calendar.getInstance();
                Dialog datePicker= new DatePickerDialog(UnosUlovaActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //event kad se odabere datum
                        Calendar c= Calendar.getInstance();
                        c.set(year,monthOfYear,dayOfMonth);
                        Date date= new Date(c.getTimeInMillis());
                        tvDate.setText(sdf.format(date));
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });

        //postavi zadani datum na današnji datum
        tvDate.setText(sdf.format(new Date()));

        //klik na button Unesi
        btnUnesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spremiOdabraneVrijednosti();
            }
        });

    }

    private void spremiOdabraneVrijednosti() {
        //provjeri dali je postavljena vrijednost za količinu
        String sKolicina=etKolicina.getText().toString();
        if(sKolicina.equals("")){
            Toast.makeText(this,"Unesite Količinu...",Toast.LENGTH_SHORT).show();
            return;
        }
        //dohvati vrijednost za količinu i pretvori je u double
        double kolicina=Double.parseDouble(sKolicina);

        //odabrana vrsta ribe
        Riba riba= (Riba) spnrRiba.getSelectedItem();

        //odabrana vrsta pribora
        Pribor pribor= (Pribor) spnrPribor.getSelectedItem();

        //odabrani mamac
        Mamac mamac= (Mamac) spnrMamac.getSelectedItem();

        //odabrana lokacija
        Lokacija lokacija= (Lokacija) spnrLokacija.getSelectedItem();

        //odabrani datum
        Date datum= new Date();
        try {
            datum=sdf.parse(tvDate.getText().toString());
        } catch (ParseException e) {
            Log.e(this.getLocalClassName(),e.getMessage());
        }

        Unos noviUnos= new Unos(datum,kolicina,riba,mamac,pribor,lokacija);
        baza.dodajUnos(noviUnos);
        Toast.makeText(this,"Uspješno dodan zapis.",Toast.LENGTH_SHORT).show();
        finish();
    }


    private void init() {
        spnrRiba = (Spinner) findViewById(R.id.unosSpinnerRiba);
        etKolicina = (EditText) findViewById(R.id.unosEtKolicina);
        spnrPribor = (Spinner) findViewById(R.id.unosSpinnerPribor);
        spnrMamac = (Spinner) findViewById(R.id.unosSpinnerMamac);
        spnrLokacija = (Spinner) findViewById(R.id.unosSpinnerLokacija);
        tvDate = (TextView) findViewById(R.id.unosTvDate);
        btnDatePicker = (ImageButton) findViewById(R.id.unosIbDatePicker);
        btnUnesi = (Button) findViewById(R.id.unosBtnUnesi);

        baza = new Baza(this);
    }

    //klasa adapter za spinnere
    public class SpinnerAdapter extends BaseAdapter{
        //lista objekata za koje se koristi adapter
        List<?> list;
        public SpinnerAdapter(List<?> list){
            this.list=list;
        }

        @Override
        public int getCount() {
            return list.size();//broj elemenata spinnera
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);//objekt povezan s elementom liste
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
                convertView= LayoutInflater.from(UnosUlovaActivity.this).inflate(android.R.layout.simple_list_item_1,parent,false);
            }

            ((TextView)convertView).setText(list.get(position).toString());//postavljanje teksta trenutnog itema
            return convertView;
        }
    }

}
