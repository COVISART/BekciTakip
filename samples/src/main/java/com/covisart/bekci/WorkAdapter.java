package com.covisart.bekci;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.MyViewHolder> {

    private List<Work> worksList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, hectar, litre, nozzle, aralik;

        public MyViewHolder(View view) {
            super(view);
            //id = (TextView) view.findViewById(R.id.id);
            hectar = (TextView) view.findViewById(R.id.hektar);
            litre = (TextView) view.findViewById(R.id.litre);
            nozzle = (TextView) view.findViewById(R.id.nozzle);
            aralik = (TextView) view.findViewById(R.id.aralik);
        }
    }


    public WorkAdapter(List<Work> worksList) {
        this.worksList = worksList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Work work = worksList.get(position);
        //holder.id.setText(work.getID());
        holder.hectar.setText(work.getHektar());
        holder.litre.setText(work.getLitre());
        holder.nozzle.setText(work.getNozzle());
        holder.aralik.setText(work.getAralik());
    }

    @Override
    public int getItemCount() {
        return worksList.size();
    }

    public void clear() {
        worksList.clear();
    }

    public void removeItem(int position) {
        worksList.remove(position);
    }

}
