package com.example.appquanlycongviec.api.apiService;

import com.example.appquanlycongviec.model.CongViecNgay;
import com.example.appquanlycongviec.viewModel.CongViecNgayViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CongViecNgayApiService {
    @GET("CongViecNgay/NguoiDung/{maNd}/{ngay}")
    Call<List<CongViecNgay>> layDanhSachCongViecNgay(@Path("maNd") int maNd, @Path("ngay") String ngay);

    @GET("CongViecNgay/{maCvNgay}")
    Call<CongViecNgay> layCongViecNgay(@Path("maCvNgay") int maCvNgay);

    @GET("CongViecNgay/CapNhatTrangThai/{maCvNgay}/{maNd}/{ngay}")
    Call<List<CongViecNgay>> capNhatTrangThaiCongViecNgay(@Path("maCvNgay") int maCvNgay, @Path("maNd") int maNd, @Path("ngay") String ngay);

    @GET("CongViecNgay/XoaCongViecNgay/{maCvNgay}/{maNd}/{ngay}")
    Call<List<CongViecNgay>> xoaCongViecNgay(@Path("maCvNgay") int maCvNgay, @Path("maNd") int maNd, @Path("ngay") String ngay);

    @POST("CongViecNgay/LuuCongViecNgay")
    Call<CongViecNgay> luuCongViecNgay(@Body CongViecNgay congViecNgay);
}
