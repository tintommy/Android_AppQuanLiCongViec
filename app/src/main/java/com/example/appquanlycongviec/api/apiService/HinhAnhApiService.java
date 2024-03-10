package com.example.appquanlycongviec.api.apiService;

import com.example.appquanlycongviec.model.CongViecNgay;
import com.example.appquanlycongviec.model.HinhAnh;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HinhAnhApiService {
    @GET("HinhAnh/LayHinhAnh/{maCvNgay}")
    Call<List<HinhAnh>> layDanhSachHinhAnh(@Path("maCvNgay") int maCvNgay);
    @POST("HinhAnh/LuuHinhAnh")
    Call <Void> luuDanhSachAnh(@Body List<HinhAnh> hinhAnhList);
    @GET("/HinhAnh/XoaHinhAnh/{maHinhAnh}")
    Call <Void> XoaHinhAnh(@Path("maHinhAnh") int maHinhAnh);
}
