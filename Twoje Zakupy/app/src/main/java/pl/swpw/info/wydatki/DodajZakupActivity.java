package pl.swpw.info.wydatki;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DodajZakupActivity extends AppCompatActivity {

    private EditText etNazwa;



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_zakup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUIElements();

    }

    private void initUIElements() {
        etNazwa = (EditText) findViewById(R.id.etNazwa);

    }

    private boolean sprawdzFormularz(){

        if(TextUtils.isEmpty(etNazwa.getText().toString())){
            etNazwa.setError(getString(R.string.brakZakupu));
            return false;
        }


        return  true;
    }


    public void onZapiszButtonClick(View view) {
        if(sprawdzFormularz()) {
            BazaWydatki bazaWydatki = new BazaWydatki(DodajZakupActivity.this);
            bazaWydatki.wstawZakup(etNazwa.getText().toString());
            Toast.makeText(DodajZakupActivity.this, R.string.infoBaza, Toast.LENGTH_LONG).show();
            bazaWydatki.close();
            finish();
        }
    }
}

