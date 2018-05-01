package com.example.tnt.cuahangonline.model;

import java.util.ArrayList;

public class DonHang {
    private int idDonHang;
    private int status;
    private String tenKhachHang;
    private String emailKhachHang;
    private String soDt;
    private ArrayList<ThongTinDonHang> thongTinDonHangs;

    public DonHang(int idDonHang, int status, String tenKhachHang, String emailKhachHang, String soDt, ArrayList<ThongTinDonHang> thongTinDonHangs) {
        this.idDonHang = idDonHang;
        this.status = status;
        this.tenKhachHang = tenKhachHang;
        this.emailKhachHang = emailKhachHang;
        this.soDt = soDt;
        this.thongTinDonHangs = thongTinDonHangs;
    }

    public int getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(int idDonHang) {
        this.idDonHang = idDonHang;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getEmailKhachHang() {
        return emailKhachHang;
    }

    public void setEmailKhachHang(String emailKhachHang) {
        this.emailKhachHang = emailKhachHang;
    }

    public String getSoDt() {
        return soDt;
    }

    public void setSoDt(String soDt) {
        this.soDt = soDt;
    }

    public ArrayList<ThongTinDonHang> getThongTinDonHangs() {
        return thongTinDonHangs;
    }

    public void setThongTinDonHangs(ArrayList<ThongTinDonHang> thongTinDonHangs) {
        this.thongTinDonHangs = thongTinDonHangs;
    }
}
