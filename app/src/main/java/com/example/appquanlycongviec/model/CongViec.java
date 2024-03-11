package com.example.appquanlycongviec.model;

import java.io.Serializable;

public class CongViec implements Serializable {
    private Integer tinhChat;

    private String ngayBatDau;

    private Integer maCV;

    private Integer soLan;

    private String dungSauNgay;

    private String chuKi;

    private NguoiDung nguoiDung;

    private String noiDung;

    private String tieuDe;

    public Integer getTinhChat() {
        return this.tinhChat;
    }

    public void setTinhChat(Integer tinhChat) {
        this.tinhChat = tinhChat;
    }

    public String getNgayBatDau() {
        return this.ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Integer getMaCV() {
        return this.maCV;
    }

    public void setMaCV(Integer maCV) {
        this.maCV = maCV;
    }

    public Integer getSoLan() {
        return this.soLan;
    }

    public void setSoLan(Integer soLan) {
        this.soLan = soLan;
    }

    public Object getDungSauNgay() {
        return this.dungSauNgay;
    }

    public void setDungSauNgay(String dungSauNgay) {
        this.dungSauNgay = dungSauNgay;
    }

    public String getChuKi() {
        return this.chuKi;
    }

    public void setChuKi(String chuKi) {
        this.chuKi = chuKi;
    }

    public NguoiDung getNguoiDung() {
        return this.nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public String getNoiDung() {
        return this.noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getTieuDe() {
        return this.tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }


}