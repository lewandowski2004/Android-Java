package pl.swpw.info.wydatki;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class ZakupyActivity extends AppCompatActivity
        {



    private RecyclerView listaZakupow;
    private BazaWydatki bazaWydatki;
    private ArrayList<Zakup> tablicaZakupy;
    private ZakupyAdapter zakupyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakupy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button btnDodajPozycje= (Button) findViewById(R.id.btnDodajPozycje);
        btnDodajPozycje.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ZakupyActivity.this,DodajZakupActivity.class));
            }
        });
        Button btnPodsumowanie= (Button) findViewById(R.id.btnPodsumowanie);
        btnPodsumowanie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ZakupyActivity.this,DodajWpisActivity.class));
            }
        });

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();



        initUIElements();

    }

    private void initUIElements(){

        listaZakupow=(RecyclerView) findViewById(R.id.rvLista);
        listaZakupow.setLayoutManager(new LinearLayoutManager(this));
        listaZakupow.setHasFixedSize(true);
    }



    private void pokazListe(){
        bazaWydatki = new BazaWydatki(this);
        tablicaZakupy = new ArrayList<Zakup>();
        tablicaZakupy = bazaWydatki.selectListaZakupy();
        zakupyAdapter = new ZakupyAdapter(tablicaZakupy);
        listaZakupow.setAdapter(zakupyAdapter);
        bazaWydatki.close();
    }



    @Override
    protected void onStart() {
        super.onStart();
        pokazListe();

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long idRekordu = (long) viewHolder.itemView.getTag();
                bazaWydatki.skasujZakup(idRekordu);


                int pozycjaListy = (int) viewHolder.getAdapterPosition();
                tablicaZakupy.remove(pozycjaListy);
                zakupyAdapter.notifyItemRemoved(pozycjaListy);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(listaZakupow);
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }




}
