package com.example.appquanlycongviec.viewModel;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appquanlycongviec.api.ApiInstance;
import com.example.appquanlycongviec.api.apiService.CongViecNgayApiService;
import com.example.appquanlycongviec.model.CongViecNgay;
import com.example.appquanlycongviec.util.Resource;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CongViecNgayViewModel extends ViewModel {
    public MutableLiveData<Resource<List<CongViecNgay>>> danhSachCongViecNgay = new MutableLiveData<>(new Resource.Unspecified<>());
    CongViecNgayApiService service = ApiInstance.getRetrofitInstance().create(CongViecNgayApiService.class);
    public MutableLiveData<String> soViecCanLam = new MutableLiveData<>("");
    public MutableLiveData<String> phanTramHoanThanh = new MutableLiveData<>("");


    public void taiDanhSachCongViecNgay(int maNd, String ngay) {
        danhSachCongViecNgay.postValue(new Resource.Loading<>());

        Call<List<CongViecNgay>> call = service.layDanhSachCongViecNgay(maNd, ngay);
        call.enqueue(new Callback<List<CongViecNgay>>() {
            @Override
            public void onResponse(Call<List<CongViecNgay>> call, Response<List<CongViecNgay>> response) {
                if (response.isSuccessful()) {
                    List<CongViecNgay> dsCv = response.body();

                    danhSachCongViecNgay.postValue(new Resource.Success<>(dsCv));
                    capNhatSoViecVaPhanTram(dsCv,200);
                } else {
                    if (response.code() == 404) {
                        danhSachCongViecNgay.postValue(new Resource.Error<>("404"));
                        soViecCanLam.postValue("");
                        phanTramHoanThanh.postValue("");
                    }

                }
            }

            @Override
            public void onFailure(Call<List<CongViecNgay>> call, Throwable t) {
                danhSachCongViecNgay.postValue(new Resource.Error<>("error"));
            }
        });

    }

    public void capNhatTrangThaiCongViecNgay(int maCvNgay, int maNd, String ngay) {
        Call<List<CongViecNgay>> call = service.capNhatTrangThaiCongViecNgay(maCvNgay, maNd, ngay);
        call.enqueue(new Callback<List<CongViecNgay>>() {
            @Override
            public void onResponse(Call<List<CongViecNgay>> call, Response<List<CongViecNgay>> response) {
                List<CongViecNgay> cvnList = response.body();
                capNhatSoViecVaPhanTram(cvnList, 200);
            }


            @Override
            public void onFailure(Call<List<CongViecNgay>> call, Throwable t) {

            }
        });
    }

    public void xoaCongViecNgay(int maCvNgay, int maNd, String ngay) {
        Call<List<CongViecNgay>> call = service.xoaCongViecNgay(maCvNgay, maNd, ngay);
        call.enqueue(new Callback<List<CongViecNgay>>() {
            @Override
            public void onResponse(Call<List<CongViecNgay>> call, Response<List<CongViecNgay>> response) {
                if (response.isSuccessful()) {
                    List<CongViecNgay> cvnList = response.body();

                    capNhatSoViecVaPhanTram(cvnList, 200);
                }
                if (response.code() == 404) {
                    List<CongViecNgay> cvnList = response.body();
                    danhSachCongViecNgay.postValue(new Resource.Error<>("404"));
                    capNhatSoViecVaPhanTram(cvnList, 404);
                }

            }


            @Override
            public void onFailure(Call<List<CongViecNgay>> call, Throwable t) {

            }
        });
    }

    public void luuCongViecNgay(CongViecNgay congViecNgay) {
        service.luuCongViecNgay(congViecNgay).enqueue(new Callback<CongViecNgay>() {
            @Override
            public void onResponse(Call<CongViecNgay> call, Response<CongViecNgay> response) {

            }

            @Override
            public void onFailure(Call<CongViecNgay> call, Throwable t) {

            }
        });

    }

    public void capNhatSoViecVaPhanTram(List<CongViecNgay> cvnList, int code) {
        int hoanThanh = 0, chuaHoanThanh = 0;
        if (code==200) {
            if (cvnList.size() > 0) {
                for (int i = 0; i < cvnList.size(); i++) {
                    if (cvnList.get(i).getTrangThai() == true) {
                        hoanThanh += 1;
                    } else chuaHoanThanh += 1;
                }
                if (hoanThanh == cvnList.size()) {
                    phanTramHoanThanh.postValue("100%");
                } else if (chuaHoanThanh == cvnList.size()) {
                    phanTramHoanThanh.postValue("0%");

                }
                double phanTram = (double) hoanThanh / cvnList.size();
                phanTramHoanThanh.postValue(new DecimalFormat("#.##").format(phanTram * 100) + "%");
            }
            soViecCanLam.postValue("Bạn có " + (cvnList.size() - hoanThanh) + " việc cần làm");
        }
        if (code == 404) {
            phanTramHoanThanh.postValue("");
            soViecCanLam.postValue("");
        }
    }
}