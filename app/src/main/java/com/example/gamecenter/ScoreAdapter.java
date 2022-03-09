package com.example.gamecenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private ArrayList<Puntuacion> lista;

    public ScoreAdapter(ArrayList<Puntuacion> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_scores, null, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        holder.posicion.setText(String.valueOf(position + 1));
        holder.nombre.setText(lista.get(position).getUserName());
        holder.score.setText(lista.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {

        TextView posicion, nombre, score;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            posicion = (TextView) itemView.findViewById(R.id.listaPosicion);
            nombre = (TextView) itemView.findViewById(R.id.listaNombre);
            score = (TextView) itemView.findViewById(R.id.listaScore);
        }
    }
}
