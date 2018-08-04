package pl.swpw.info.wydatki;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView okres;
    private TextView suma;
    private RecyclerView listaWydatkow;

    final Calendar kal = Calendar.getInstance();
    private int rok, miesiac, dzien;
    private String dataPoczatkowa;

    private BazaWydatki bazaWydatki;
    private ArrayList<Wydatek> tablicaWydatki;
    private WydatkiAdapter wydatkiAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show(); */
//                Intent i = new Intent(MainActivity.this, DodajWpisActivity.class);
//                startActivity(i);
//            }
//        });

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();



        initUIElements();
        danePoczatkowe();
    }

    private void initUIElements(){
        okres = (TextView) findViewById(R.id.tvOkres);
        suma =(TextView) findViewById(R.id.tvSuma);
        listaWydatkow=(RecyclerView) findViewById(R.id.rvLista);
        listaWydatkow.setLayoutManager(new LinearLayoutManager(this));
        listaWydatkow.setHasFixedSize(true);
    }

    private void danePoczatkowe(){
        rok=kal.get(Calendar.YEAR);
        miesiac=kal.get(Calendar.MONTH);
        dzien=kal.get(Calendar.DAY_OF_MONTH);
        dataPoczatkowa=Wspolne.zmienDate(rok, miesiac, 1);
    }

    private void pokazListe(String dataPocz){
        bazaWydatki = new BazaWydatki(this);
        tablicaWydatki = new ArrayList<Wydatek>();
        tablicaWydatki = bazaWydatki.selectListaWydatki(dataPocz);
        wydatkiAdapter = new WydatkiAdapter(tablicaWydatki);
        listaWydatkow.setAdapter(wydatkiAdapter);
        pokazSume(dataPocz);
        bazaWydatki.close();
    }

    private void pokazSume(String dataPocz){
        float ile=bazaWydatki.sumaWydatkow(dataPocz);
        suma.setText(String.format("%.2f", ile));
    }

    @Override
    protected void onStart() {
        super.onStart();
        pokazListe(dataPoczatkowa);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long idRekordu = (long) viewHolder.itemView.getTag();
                bazaWydatki.skasujWydatek(idRekordu);
                pokazSume(dataPoczatkowa);

                int pozycjaListy = (int) viewHolder.getAdapterPosition();
                tablicaWydatki.remove(pozycjaListy);
                wydatkiAdapter.notifyItemRemoved(pozycjaListy);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(listaWydatkow);
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
