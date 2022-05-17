package com.lepak.surahviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepak.surahviewer.adapter.SurahAdapter;
import com.lepak.surahviewer.object.Surah;
import com.lepak.surahviewer.service.APIClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvSurah;
    Button btnFetch;

    ArrayList<Surah> surahs;
    SurahAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvSurah = findViewById(R.id.main_rv_surah);
        btnFetch = findViewById(R.id.main_btn_fetch);

        surahs = new ArrayList<>();
        _adapter = new SurahAdapter(surahs);
        rvSurah.setAdapter(_adapter);
        rvSurah.setLayoutManager(new LinearLayoutManager(this));


        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url = "http://api.alquran.cloud/v1/surah";

                    Request request = new Request.Builder().url(url).build();

                    APIClient.getClient().newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if (!response.isSuccessful())
                                throw new IOException("Unexpected code " + response);

                            try {
                                surahs.clear();

                                Gson gson = new Gson();
                                String jsonData = response.body().string();
                                JSONObject obj = new JSONObject(jsonData);

                                JSONArray surahArray = obj.getJSONArray("data");

                                List<Surah> tempo = gson.fromJson(surahArray.toString(), new TypeToken<List<Surah>>(){}.getType());

                                surahs.addAll(tempo);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        _adapter.notifyDataSetChanged();
                                    }
                                });
//                                for(Surah s : tempo) {
//                                    surahs.add(s);
//                                }

//                                for (int i = 0; i < surahArray.length(); i++) {
//                                    JSONObject surah = surahArray.getJSONObject(i);
//                                    Surah temp = gson.fromJson(surahArray.getJSONObject(i).toString(), Surah.class);
//
//                                    Log.v("asd", temp.engName);
//                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.v("ERR", "ASD: " + e);
                }
            }
        });
    }
}