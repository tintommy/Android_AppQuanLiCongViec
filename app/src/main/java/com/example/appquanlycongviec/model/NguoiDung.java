package com.example.appquanlycongviec.model;

import java.io.Serializable;

public class NguoiDung implements Serializable {
    private String matKhau;

    private String ho;

    private String ngaySinh;

    private Boolean gioiTinh;

    private String ten;

    private String maPin;

    private Integer maNguoiDung;

    private String email;

    public String getMatKhau() {
        return this.matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHo() {
        return this.ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getNgaySinh() {
        return this.ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Boolean getGioiTinh() {
        return this.gioiTinh;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getTen() {
        return this.ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMaPin() {
        return this.maPin;
    }

    public void setMaPin(String maPin) {
        this.maPin = maPin;
    }

    public Integer getMaNguoiDung() {
        return this.maNguoiDung;
    }

    public void setMaNguoiDung(Integer maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}