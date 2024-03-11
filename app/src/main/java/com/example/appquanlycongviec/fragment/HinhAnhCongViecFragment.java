package com.example.appquanlycongviec.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appquanlycongviec.MainActivity;
import com.example.appquanlycongviec.R;
import com.example.appquanlycongviec.adapter.HinhAnhAdapter;
import com.example.appquanlycongviec.databinding.FragmentHinhAnhCongViecBinding;
import com.example.appquanlycongviec.model.CongViecNgay;
import com.example.appquanlycongviec.model.HinhAnh;
import com.example.appquanlycongviec.util.Resource;
import com.example.appquanlycongviec.util.ShowHideMenu;
import com.example.appquanlycongviec.viewModel.HinhAnhViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HinhAnhCongViecFragment extends Fragment {
    private FragmentHinhAnhCongViecBinding binding;
    private HinhAnhAdapter hinhAnhAdapter;
    private HinhAnhViewModel hinhAnhViewModel;
    public static CongViecNgay cvNgay;
    private List<Uri> anhDuocChon ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ShowHideMenu showHideMenu = new ShowHideMenu();
        showHideMenu.hideBottomNavigation(this);
        binding = FragmentHinhAnhCongViecBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hinhAnhViewModel = new HinhAnhViewModel();
        binding.rvHinhAnh.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        binding.btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigateUp();
            }
        });


        taiDanhSachHinhAnh();
        chonAnh();

    }

    private void taiDanhSachHinhAnh() {
        Bundle bundle = getArguments();
        cvNgay = (CongViecNgay) bundle.getSerializable("cvNgay");
        hinhAnhViewModel.taiDanhSachHinhAnh(cvNgay.getMaCvNgay());
        hinhAnhViewModel.hinhAnhList.observe(getViewLifecycleOwner(), new Observer<Resource<List<HinhAnh>>>() {
            @Override
            public void onChanged(Resource<List<HinhAnh>> list) {
                if (list instanceof Resource.Loading) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.tvTrong.setVisibility(View.GONE);
                }
                if (list instanceof Resource.Success) {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.tvTrong.setVisibility(View.GONE);
                    khaiBaoAdapter(list.getData());
                    binding.rvHinhAnh.setAdapter(hinhAnhAdapter);


                }
                if (list instanceof Resource.Error) {
                    binding.progressBar.setVisibility(View.GONE);
                    if (list.getMessage().equals("error")) {
                        Toast.makeText(requireContext(), "Kiểm Tra Kết Nối Mạng", Toast.LENGTH_LONG).show();
                    }
                    if (list.getMessage().equals("404")) {
                        binding.progressBar.setVisibility(View.GONE);
                        binding.tvTrong.setVisibility(View.VISIBLE);

                    }

                }
            }
        });
    }

    private void khaiBaoAdapter(List<HinhAnh> hinhAnhs) {
        hinhAnhAdapter = new HinhAnhAdapter(hinhAnhs);
        hinhAnhAdapter.setOnItemClickListener(new HinhAnhAdapter.OnItemClickListener() {
            @Override
            public void onDeleteButtonClick(HinhAnh hinhAnh,int position) {

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setMessage("Xoá ảnh này ?");
                builder.setTitle("Xác nhận !");
                builder.setCancelable(false);
                builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hinhAnhViewModel.xoaAnh(hinhAnh.getMaHinh(),hinhAnh.getLink());
                        hinhAnhAdapter.xoaHinhAnhTrongDanhSach(position);
                        if(hinhAnhs.isEmpty())
                            binding.tvTrong.setVisibility(View.VISIBLE);
                    }
                });

                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    private void chonAnh() {

        ActivityResultLauncher<Intent> selectImageActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                anhDuocChon = new ArrayList<>();
                Intent intent = result.getData();

                // Multiple images selected
                if (intent != null && intent.getClipData() != null) {
                    int count = intent.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = intent.getClipData().getItemAt(i).getUri();
                        if (imageUri != null) {
                            anhDuocChon.add(imageUri);
                        }
                    }
                } else if (intent != null) {
                    Uri imageUri = intent.getData();
                    if (imageUri != null) {
                        anhDuocChon.add(imageUri);
                    }
                }
                // updateImages();
                hinhAnhViewModel.luuDanhSachAnh(anhDuocChon, requireActivity(), cvNgay);
                //hinhAnhAdapter= new HinhAnhAdapter(anhDuocChon);
                // binding.rvHinhAnh.setAdapter(hinhAnhAdapter);
                //  binding.rvHinhAnh.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
            }
        });

        binding.btnThemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                selectImageActivityResult.launch(intent);
            }
        });


    }
}
