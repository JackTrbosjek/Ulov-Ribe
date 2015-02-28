package diplomski.ulovribe;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import diplomski.ulovribe.baza.Baza;
import diplomski.ulovribe.baza.Lokacija;
import diplomski.ulovribe.baza.Mamac;
import diplomski.ulovribe.baza.Pribor;
import diplomski.ulovribe.baza.Riba;
import diplomski.ulovribe.baza.Unos;


public class MainActivity extends ActionBarActivity {

    Button btnUnos,btnPregled,btnUrediKategorije,btnObrisi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnUnos=(Button)findViewById(R.id.btnUnesiUlov);
        btnUnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,UnosUlovaActivity.class);
                startActivity(i);

            }
        });

        btnPregled=(Button)findViewById(R.id.btnPregledUlova);
        btnPregled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,PregledUlovaActivity.class);
                startActivity(i);
            }
        });

        btnUrediKategorije=(Button)findViewById(R.id.btnUrediKategorije);
        btnUrediKategorije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,UrediKategorijeActivity.class);
                startActivity(i);
            }
        });

        btnObrisi=(Button)findViewById(R.id.btnObrisiPodatke);
        btnObrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Baza baza= new Baza(MainActivity.this);
                baza.obrisiPodatke();
                Toast.makeText(MainActivity.this,"Uspješno obrisani podaci.",Toast.LENGTH_SHORT).show();
            }
        });

    }



}
