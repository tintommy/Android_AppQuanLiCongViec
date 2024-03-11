package com.example.appquanlycongviec.model;

import java.io.Serializable;


public class HinhAnh implements Serializable {
    private int maHinh;
    private String link;
    private CongViecNgay cvNgay;

    public HinhAnh(int maHinh, String link, CongViecNgay cvNgay) {
        this.maHinh = maHinh;
        this.link = link;
        this.cvNgay = cvNgay;
    }

    public int getMaHinh() {
        return maHinh;
    }

    public void setMaHinh(int maHinh) {
        this.maHinh = maHinh;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public CongViecNgay getCvNgay() {
        return cvNgay;
    }

    public void setCvNgay(CongViecNgay cvNgay) {
        this.cvNgay = cvNgay;
    }
}
