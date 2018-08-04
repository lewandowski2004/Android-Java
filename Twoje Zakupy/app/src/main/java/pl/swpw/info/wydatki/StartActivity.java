package pl.swpw.info.wydatki;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btListaZakupow= (Button) findViewById(R.id.btListaZakupow);
        btListaZakupow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,WydatkiActivity.class));
            }
        });

        Button btDodajProdukty= (Button) findViewById(R.id.btDodajProdukty);
        btDodajProdukty.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,ZakupyActivity.class));
            }
        });
    }
}
