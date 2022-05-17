package com.lepak.surahviewer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lepak.surahviewer.R;
import com.lepak.surahviewer.object.Ayat;

import java.util.ArrayList;
import java.util.List;

public class AyatAdapter extends RecyclerView.Adapter<AyatAdapter.ViewHolder> {
    private ArrayList<Ayat> ayatList;

    public AyatAdapter(ArrayList<Ayat> ayats) {
        this.ayatList = ayats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_card_ayat, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvAyat.setText(this.ayatList.get(position).text);
        holder.tvNumber.setText(String.valueOf(this.ayatList.get(position).number));
    }

    @Override
    public int getItemCount() {
        return ayatList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAyat, tvNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvAyat = itemView.findViewById(R.id.rv_card_ayat_text);
            this.tvNumber = itemView.findViewById(R.id.rv_card_ayat_number);
        }
    }
}
