package pl.swpw.info.wydatki;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rzak on 22.04.2018.
 */

public class WydatkiAdapter extends RecyclerView.Adapter<WydatkiViewHolder>{

    private ArrayList<Wydatek> wydatki = new ArrayList<>();

    public WydatkiAdapter(ArrayList<Wydatek> wydatki) {
        this.wydatki = wydatki;
    }

    @Override
    public WydatkiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wydatek_pozycja, parent, false);
        WydatkiViewHolder wydatkiViewHolder = new WydatkiViewHolder(view);
        return wydatkiViewHolder;
    }

    @Override
    public void onBindViewHolder(WydatkiViewHolder holder, int position) {
        Wydatek wydatek = wydatki.get(position);
        holder.data.setText(wydatek.getData());
        holder.cena.setText(String.format("%.2f", wydatek.getCena()));
        holder.wydatek.setText(wydatek.getNazwa());
        holder.itemView.setTag((long) wydatek.getId());

    }

    @Override
    public int getItemCount() {
        return wydatki.size();
    }
}

class WydatkiViewHolder extends RecyclerView.ViewHolder {

    public TextView data, wydatek, cena;

    public WydatkiViewHolder(View itemView) {
        super(itemView);
        data = (TextView) itemView.findViewById(R.id.txtData);
        wydatek =(TextView) itemView.findViewById(R.id.txtWydatek);
        cena = (TextView) itemView.findViewById(R.id.txtCena);
    }
}