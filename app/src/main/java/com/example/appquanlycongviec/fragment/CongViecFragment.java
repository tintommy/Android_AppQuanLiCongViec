package com.example.appquanlycongviec.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.appquanlycongviec.MainActivity;
import com.example.appquanlycongviec.R;
import com.example.appquanlycongviec.adapter.DanhSachCongViecNgayAdapter;
import com.example.appquanlycongviec.api.ApiInstance;
import com.example.appquanlycongviec.api.apiService.CongViecNgayApiService;
import com.example.appquanlycongviec.databinding.FragmentCongViecBinding;
import com.example.appquanlycongviec.model.CongViecNgay;
import com.example.appquanlycongviec.util.Resource;
import com.example.appquanlycongviec.viewModel.CongViecNgayViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.appquanlycongviec.util.ShowHideMenu;

public class CongViecFragment extends Fragment {
    int maNd = 2;
    private CongViecNgayViewModel congViecNgayViewModel;
    private DanhSachCongViecNgayAdapter danhSachCongViecNgayAdapter;

    private FragmentCongViecBinding binding;

    private Calendar calendar = Calendar.getInstance();
    private int nam = calendar.get(Calendar.YEAR);
    private int thang = calendar.get(Calendar.MONTH); // Tháng bắt đầu từ 0
    private int ngay = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCongViecBinding.inflate(getLayoutInflater());
        ShowHideMenu showHideMenu = new ShowHideMenu();
        showHideMenu.showBottomNavigation(this);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        khaiBaoSpinner();
        congViecNgayViewModel = new CongViecNgayViewModel();
        khaiBaoAdapter();
        binding.rvCongViec.setAdapter(danhSachCongViecNgayAdapter);
        binding.rvCongViec.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        taiDanhSachCongViecNgay(maNd, dinhDangNgayAPI(ngay, thang, nam));

        binding.tvNgay.setText(dinhDangNgay(ngay, thang, nam));
        binding.btnLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLichDialog();
            }
        });

        congViecNgayViewModel.soViecCanLam.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvSoViecCanLam.setText(s);
            }
        });

        congViecNgayViewModel.phanTramHoanThanh.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvPhanTram.setText(s);
            }
        });
    }

    private void khaiBaoSpinner() {
        String[] luaChon = {"Mặc định", "Trạng thái tăng", "Trạng thái giảm", "Tính chất tăng", "Tính chất giảm"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, luaChon);
        binding.spSapXep.setAdapter(adapter);

        binding.spSapXep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (position == 0) {
                   congViecNgayViewModel.sapXepCvNgay(0);
                } else if (position == 1) {

                    congViecNgayViewModel.sapXepCvNgay(1);
                } else if (position == 2) {

                    congViecNgayViewModel.sapXepCvNgay(2);
                } else if (position == 3) {

                    congViecNgayViewModel.sapXepCvNgay(3);
                } else if (position == 4) {

                    congViecNgayViewModel.sapXepCvNgay(4);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void taiDanhSachCongViecNgay(int maNd, String ngay) {
        congViecNgayViewModel.taiDanhSachCongViecNgay(maNd, ngay);
        congViecNgayViewModel.danhSachCongViecNgay.observe(getViewLifecycleOwner(), new Observer<Resource<List<CongViecNgay>>>() {
            @Override
            public void onChanged(Resource<List<CongViecNgay>> list) {
                if (list instanceof Resource.Loading) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.tvTrong.setVisibility(View.GONE);
                    binding.spSapXep.setVisibility(View.GONE);
                }
                if (list instanceof Resource.Success) {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.tvTrong.setVisibility(View.GONE);
                    binding.spSapXep.setVisibility(View.VISIBLE);
                    danhSachCongViecNgayAdapter.differ.submitList(list.getData());

                }
                if (list instanceof Resource.Error) {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.spSapXep.setVisibility(View.GONE);
                    if (list.getMessage().equals("error")) {
                        Toast.makeText(requireContext(), "Kiểm Tra Kết Nối Mạng", Toast.LENGTH_LONG).show();
                    }
                    if (list.getMessage().equals("404")) {
                        danhSachCongViecNgayAdapter.differ.submitList((List<CongViecNgay>) new Resource.Unspecified<>().getData());
                        binding.progressBar.setVisibility(View.GONE);
                        binding.tvTrong.setVisibility(View.VISIBLE);

                    }

                }
            }
        });
    }

    private void khaiBaoAdapter() {
        danhSachCongViecNgayAdapter = new DanhSachCongViecNgayAdapter();
        danhSachCongViecNgayAdapter.setOnItemClickListener(new DanhSachCongViecNgayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CongViecNgay congViecNgay) {

            }

            @Override
            public void onCheckBoxClick(int maCongViecNgay) {
                congViecNgayViewModel.capNhatTrangThaiCongViecNgay(maCongViecNgay, maNd, dinhDangNgayAPI(ngay, thang, nam));


            }


            @Override
            public void onImageButtonClick(CongViecNgay congViecNgay) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("cvNgay", congViecNgay);
                Navigation.findNavController(requireView()).navigate(R.id.action_congViecFragment_to_hinhAnhCongViecFragment, bundle);
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setMessage("Bạn chắc chắn xoá ?");
                builder.setTitle("Xác nhận !");
                builder.setCancelable(false);
                builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = viewHolder.getAdapterPosition();
                        CongViecNgay congViecNgay = danhSachCongViecNgayAdapter.getDiffer().getCurrentList().get(position);
                        congViecNgayViewModel.xoaCongViecNgay(congViecNgay.getMaCvNgay(), maNd, dinhDangNgayAPI(ngay, thang, nam));

                        Snackbar.make(getView(), "Đã xoá công việc", Snackbar.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        danhSachCongViecNgayAdapter.notifyDataSetChanged();

                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvCongViec);
    }

    private void openLichDialog() {

        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                binding.tvNgay.setText(dinhDangNgay(day, month, year));
                taiDanhSachCongViecNgay(maNd, dinhDangNgayAPI(day, month, year));
                ngay = day;
                thang = month;
                nam = year;
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


    private String dinhDangNgayAPI(int ngay, int thang, int nam) {

        String temp = "";
        temp += nam;
        temp += "-";

        if (thang + 1 < 10)
            temp += "0" + String.valueOf(thang + 1);
        else temp += String.valueOf(thang + 1);
        temp += "-";
        if (ngay < 10)
            temp += "0" + String.valueOf(ngay);
        else temp += String.valueOf(ngay);

        return temp;
    }
}