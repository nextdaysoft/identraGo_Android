package com.project.identranaccess.database;

import com.project.identranaccess.model.QRClass;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "https://exendara.com/identran_online/admin/httpconfig/request/api/";

    @GET("getcode.php")
    Call<ArrayList<QRClass>> getAllData();
}
