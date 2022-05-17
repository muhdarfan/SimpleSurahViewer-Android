package com.lepak.surahviewer.object;

import com.google.gson.annotations.SerializedName;

public class Surah {
    @SerializedName("number")
    public int number;

    @SerializedName("name")
    public String name;

    @SerializedName("englishName")
    public String engName;

}
