package pl.swpw.info.wydatki;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class DodajWpisActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText etData;
    private EditText etNazwa;
    private EditText etKwota;
    int rok, miesiac, dzien;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_wpis);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUIElements();
        final Calendar kal = Calendar.getInstance();
        rok = kal.get(Calendar.YEAR);
        miesiac = kal.get(Calendar.MONTH);
        dzien=kal.get(Calendar.DAY_OF_MONTH);
        etData.setText(Wspolne.zmienDate(rok, miesiac, dzien));
    }

    private void initUIElements() {
        etData = (EditText) findViewById(R.id.etData);
        etNazwa = (EditText) findViewById(R.id.etNazwa);
        etKwota=(EditText)findViewById(R.id.etKwota);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        etData.setText(Wspolne.zmienDate(year, month, dayOfMonth));
        rok=year;
        miesiac=month;
        dzien=dayOfMonth;
    }

    public void onDateClick(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(DodajWpisActivity.this, DodajWpisActivity.this, rok, miesiac, dzien);
        datePickerDialog.show();
    }

    private boolean sprawdzFormularz(){
        if(TextUtils.isEmpty(etData.getText().toString())){
            etData.setError(getString(R.string.brakDaty));
            return false;
        }
        if(TextUtils.isEmpty(etNazwa.getText().toString())){
            etNazwa.setError(getString(R.string.brakZakupu));
            return false;
        }
        if(TextUtils.isEmpty(etKwota.getText().toString())){
            etKwota.setError(getString(R.string.brakKwoty));
            return false;
        }

        return  true;
    }

    public void onZapiszButtonClick(View view) {
        if(sprawdzFormularz()) {
            BazaWydatki bazaWydatki = new BazaWydatki(DodajWpisActivity.this);
            bazaWydatki.wstawWydatek(etData.getText().toString(), etNazwa.getText().toString(), Float.valueOf(etKwota.getText().toString()));
            Toast.makeText(DodajWpisActivity.this, R.string.infoBaza, Toast.LENGTH_LONG).show();
            bazaWydatki.close();
            finish();
        }
    }
}
