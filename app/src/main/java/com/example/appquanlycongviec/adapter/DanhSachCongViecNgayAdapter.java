package com.example.appquanlycongviec.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appquanlycongviec.api.apiService.CongViecNgayApiService;
import com.example.appquanlycongviec.databinding.CongviecItemLayoutBinding;
import com.example.appquanlycongviec.model.CongViecNgay;

public class DanhSachCongViecNgayAdapter extends RecyclerView.Adapter<DanhSachCongViecNgayAdapter.DanhSachCongViecNgayViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(CongViecNgay congViecNgay);

        void onCheckBoxClick(int maCongViecNgay);

        void onImageButtonClick(CongViecNgay congViecNgay);
    }

    private OnItemClickListener itemClick;

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClick = listener;
    }

    private final DiffUtil.ItemCallback<CongViecNgay> callback = new DiffUtil.ItemCallback<CongViecNgay>() {
        @Override
        public boolean areItemsTheSame(@NonNull CongViecNgay oldItem, @NonNull CongViecNgay newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull CongViecNgay oldItem, @NonNull CongViecNgay newItem) {
            return oldItem.getMaCvNgay().equals(newItem.getMaCvNgay());
        }
    };

    public final AsyncListDiffer<CongViecNgay> differ = new AsyncListDiffer<>(this, callback);

    public AsyncListDiffer<CongViecNgay> getDiffer() {
        return differ;
    }

    @NonNull
    @Override
    public DanhSachCongViecNgayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CongviecItemLayoutBinding binding = CongviecItemLayoutBinding.inflate(inflater, parent, false);
        return new DanhSachCongViecNgayViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachCongViecNgayViewHolder holder, int position) {
        CongViecNgay congViecNgay = differ.getCurrentList().get(position);
        holder.bind(congViecNgay);
        holder.binding.cbCongViec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onCheckBoxClick(congViecNgay.getMaCvNgay());
            }
        });


        holder.binding.btnAnhCongViec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onImageButtonClick(congViecNgay);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(congViecNgay);
            }
        });
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    static class DanhSachCongViecNgayViewHolder extends RecyclerView.ViewHolder {
        private final CongviecItemLayoutBinding binding;

        public DanhSachCongViecNgayViewHolder(CongviecItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CongViecNgay congViecNgay) {
            binding.cbCongViec.setChecked(congViecNgay.getTrangThai());
            binding.tvTieuDeCongViec.setText(congViecNgay.getCongViec().getTieuDe());
            switch (congViecNgay.getCongViec().getTinhChat()) {
                case 0: {
                    binding.tvTinhChatCongViec.setText("Bình Thường");
                    binding.tvTinhChatCongViec.setTextColor(Color.parseColor("#80BCBD"));
                    break;
                }
                case 1: {
                    binding.tvTinhChatCongViec.setText("Quan trọng");
                    binding.tvTinhChatCongViec.setTextColor(Color.parseColor("#2400FF"));
                    break;
                }
                case 2: {
                    binding.tvTinhChatCongViec.setText("Rất quan trọng");
                    binding.tvTinhChatCongViec.setTextColor(Color.RED);
                    break;
                }

            }
        }
    }
}
