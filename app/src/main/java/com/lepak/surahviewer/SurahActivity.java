package com.lepak.surahviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepak.surahviewer.adapter.AyatAdapter;
import com.lepak.surahviewer.object.Ayat;
import com.lepak.surahviewer.object.Surah;
import com.lepak.surahviewer.service.APIClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class SurahActivity extends AppCompatActivity {
    RecyclerView rvAyat;
    AyatAdapter _adapter;
    ArrayList<Ayat> ayatList;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah);

        int surahNumber = getIntent().getIntExtra("surahNumber", -1);

        if (!getIntent().hasExtra("surahNumber") && surahNumber == -1)
            finish();

        ayatList = new ArrayList<>();

        rvAyat = findViewById(R.id.surah_rv_ayat);
        _adapter = new AyatAdapter(ayatList);

        rvAyat.setAdapter(_adapter);
        rvAyat.setLayoutManager(new LinearLayoutManager(this));

        Request request = new Request.Builder().url("http://api.alquran.cloud/v1/surah/" + surahNumber).build();

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
                    ayatList.clear();

                    Gson gson = new Gson();
                    String jsonData = response.body().string();
                    JSONObject obj = new JSONObject(jsonData);

                    JSONArray ayatArr = obj.getJSONObject("data").getJSONArray("ayahs");
                    List<Ayat> ayatTemp = gson.fromJson(ayatArr.toString(), new TypeToken<List<Ayat>>(){}.getType());
                    ayatList.addAll(ayatTemp);

                    runOnUiThread(() -> _adapter.notifyDataSetChanged());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}