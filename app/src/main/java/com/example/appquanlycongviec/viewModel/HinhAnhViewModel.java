package com.example.appquanlycongviec.viewModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appquanlycongviec.api.ApiInstance;
import com.example.appquanlycongviec.api.apiService.CongViecNgayApiService;
import com.example.appquanlycongviec.api.apiService.HinhAnhApiService;
import com.example.appquanlycongviec.fragment.HinhAnhCongViecFragment;
import com.example.appquanlycongviec.model.CongViecNgay;
import com.example.appquanlycongviec.model.HinhAnh;
import com.example.appquanlycongviec.util.Resource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HinhAnhViewModel extends ViewModel {
    public MutableLiveData<Resource<List<HinhAnh>>> hinhAnhList = new MutableLiveData<>(new Resource.Unspecified<>());
    private HinhAnhApiService hinhAnhApiService = ApiInstance.getRetrofitInstance().create(HinhAnhApiService.class);


    public void taiDanhSachHinhAnh(int maCvNgay) {
        hinhAnhList.postValue(new Resource.Loading<>());
        Call<List<HinhAnh>> call = hinhAnhApiService.layDanhSachHinhAnh(maCvNgay);
        call.enqueue(new Callback<List<HinhAnh>>() {
            @Override
            public void onResponse(Call<List<HinhAnh>> call, Response<List<HinhAnh>> response) {
                if (response.isSuccessful()) {

                    List<HinhAnh> hinhAnhs = response.body();
                    hinhAnhList.postValue(new Resource.Success<>(hinhAnhs));


                } else {
                    if (response.code() == 404) {
                        hinhAnhList.postValue(new Resource.Error<>("404"));
                    }
                }

            }

            @Override
            public void onFailure(Call<List<HinhAnh>> call, Throwable t) {
                hinhAnhList.postValue(new Resource.Error<>("error"));
            }
        });

    }


    public void luuDanhSachAnh(List<Uri> linkHinhAnh, Context context, CongViecNgay cvNgay) {

        Log.e("Ngay",cvNgay.getMaCvNgay().toString());
        List<String> anhURL = new ArrayList<>();
        int soAnh = linkHinhAnh.size();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        try {
            for (Uri imageUri : linkHinhAnh) {
                String imageName = "image_" + UUID.randomUUID().toString(); // Tên duy nhất cho mỗi ảnh
                StorageReference imageRef = storageRef.child("images/" + imageName);


                InputStream stream = context.getContentResolver().openInputStream(imageUri);
                UploadTask uploadTask = imageRef.putStream(stream);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String imageUrl = uri.toString();
                                anhURL.add(imageUrl);
                                Log.e("SIZE", String.valueOf(anhURL.size()));
                                if (anhURL.size() == soAnh) {
                                    List<HinhAnh> hinhAnhs = new ArrayList<>();
                                    for (int i = 0; i < anhURL.size(); i++) {

                                        hinhAnhs.add(new HinhAnh(i,anhURL.get(i), cvNgay));
                                        Log.e("Ngay1", hinhAnhs.get(i).getCvNgay().getMaCvNgay().toString());
                                    }

                                    hinhAnhApiService.luuDanhSachAnh(hinhAnhs).enqueue(new Callback() {
                                        @Override
                                        public void onResponse(Call call, Response response) {
                                            taiDanhSachHinhAnh(cvNgay.getMaCvNgay());

                                            if (response.isSuccessful())
                                                Log.e("API", "thành công");
                                            else
                                                Log.e("API", "lỗi");
                                        }

                                        @Override
                                        public void onFailure(Call call, Throwable t) {
                                            Log.e("API", "lỗi");
                                        }
                                    });

                                }
                            }
                        });
                    }
                });

            }
        } catch (IOException e) {
            e.printStackTrace();
        }





    }

    public void xoaAnh(int maHinhAnh) {
        hinhAnhApiService.XoaHinhAnh(maHinhAnh).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
