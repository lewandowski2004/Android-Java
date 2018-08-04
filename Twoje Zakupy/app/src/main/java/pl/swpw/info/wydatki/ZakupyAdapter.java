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

public class ZakupyAdapter extends RecyclerView.Adapter<ZakupyViewHolder>{

    private ArrayList<Zakup> zakupy = new ArrayList<>();

    public ZakupyAdapter(ArrayList<Zakup> zakupy) {
        this.zakupy = zakupy;
    }

    @Override
    public ZakupyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zakup_pozycja, parent, false);
        ZakupyViewHolder zakupyViewHolder = new ZakupyViewHolder(view);
        return zakupyViewHolder;
    }

    @Override
    public void onBindViewHolder(ZakupyViewHolder holder, int position) {
        Zakup zakup = zakupy.get(position);
        holder.nazwa.setText(zakup.getNazwa());
        holder.itemView.setTag((long) zakup.getId());

    }

    @Override
    public int getItemCount() {
        return zakupy.size();
    }
}

class ZakupyViewHolder extends RecyclerView.ViewHolder {

    public TextView nazwa;

    public ZakupyViewHolder(View itemView) {
        super(itemView);
        nazwa =(TextView) itemView.findViewById(R.id.txtWydatek);

    }
}