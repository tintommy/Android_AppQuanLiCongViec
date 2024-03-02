package com.example.appquanlycongviec.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.appquanlycongviec.R;
import com.example.appquanlycongviec.databinding.FragmentCongViecBinding;

import java.util.Calendar;


public class CongViecFragment extends Fragment {

    private FragmentCongViecBinding binding;
    Calendar calendar = Calendar.getInstance();
    int nam = calendar.get(Calendar.YEAR);
    int thang = calendar.get(Calendar.MONTH); // Tháng bắt đầu từ 0
    int ngay = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCongViecBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvNgay.setText(dinhDangNgay(ngay, thang, nam));
        binding.btnLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLichDialog();
            }
        });

    }

    private void openLichDialog() {

        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                binding.tvNgay.setText(dinhDangNgay(day, month, year));
            }
        }, nam, thang, ngay);
        dialog.show();
    }

    private String dinhDangNgay(int ngay, int thang, int nam) {

        String temp = "";
        if (ngay < 10)
            temp += "0" + String.valueOf(ngay);
        else temp += String.valueOf(ngay);
        temp += "/";
        if (thang + 1 < 10)
            temp += "0" + String.valueOf(thang + 1);
        else temp += String.valueOf(thang + 1);
        temp += "/";
        temp += nam;
        return temp;
    }
}