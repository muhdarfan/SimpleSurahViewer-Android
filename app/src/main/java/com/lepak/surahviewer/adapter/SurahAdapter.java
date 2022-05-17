package com.lepak.surahviewer.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lepak.surahviewer.R;
import com.lepak.surahviewer.SurahActivity;
import com.lepak.surahviewer.object.Surah;

import java.util.ArrayList;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.ViewHolder> {
    private ArrayList<Surah> surahList;

    public SurahAdapter(ArrayList<Surah> surah) {
        this.surahList = surah;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_card_surah, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(this.surahList.get(position).name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(), SurahActivity.class).putExtra("surahNumber", surahList.get(holder.getAdapterPosition()).number));
            }
        });
    }

    @Override
    public int getItemCount() {
        return surahList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvName = itemView.findViewById(R.id.rv_card_surah_title);
        }
    }
}
