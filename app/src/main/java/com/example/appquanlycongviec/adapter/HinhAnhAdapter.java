package com.example.appquanlycongviec.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appquanlycongviec.databinding.CongviecItemLayoutBinding;
import com.example.appquanlycongviec.databinding.HinhanhItemLayoutBinding;
import com.example.appquanlycongviec.model.CongViecNgay;
import com.example.appquanlycongviec.model.HinhAnh;

import java.util.List;

public class HinhAnhAdapter extends RecyclerView.Adapter<HinhAnhAdapter.HinhAnhViewHolder> {
    private List<HinhAnh> danhSachAnh;

    public HinhAnhAdapter(List <HinhAnh> danhSachAnh) {
        this.danhSachAnh=danhSachAnh;
    }


    public interface OnItemClickListener {
        void onDeleteButtonClick(int maHinhAnh,int position);
    }
    private OnItemClickListener itemClick;

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClick = listener;
    }

    @NonNull
    @Override
    public HinhAnhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HinhanhItemLayoutBinding binding = HinhanhItemLayoutBinding.inflate(inflater, parent, false);
        return new HinhAnhAdapter.HinhAnhViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull HinhAnhViewHolder holder, int position) {
        HinhAnh hinhAnh= danhSachAnh.get(position);
        holder.bind(hinhAnh);
        holder.binding.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onDeleteButtonClick(hinhAnh.getMaHinh(),position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return danhSachAnh.size();
    }


    public void xoaHinhAnhTrongDanhSach(int position){
        danhSachAnh.remove(position);
        notifyDataSetChanged();
    }


    static class HinhAnhViewHolder extends RecyclerView.ViewHolder {
        private final HinhanhItemLayoutBinding binding;


        public HinhAnhViewHolder( HinhanhItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(HinhAnh hinhAnh){
            Glide.with(itemView).load(hinhAnh.getLink()).into(binding.ivHinhAnh);
        }
    }
}
