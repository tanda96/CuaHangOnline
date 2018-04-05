package com.example.tnt.cuahangonline.model;

/**
 * Created by TNT on 4/1/2018.
 */

public class Loaisp {
    public int ID;
    public  String TenLoaiSanPham;
    public String HinhAnhLoaiSanPham;

    public Loaisp(int ID, String tenLoaiSanPham, String hinhAnhLoaiSanPham) {
        this.ID = ID;
        TenLoaiSanPham = tenLoaiSanPham;
        HinhAnhLoaiSanPham = hinhAnhLoaiSanPham;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenLoaiSanPham() {
        return TenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        TenLoaiSanPham = tenLoaiSanPham;
    }

    public String getHinhAnhLoaiSanPham() {
        return HinhAnhLoaiSanPham;
    }

    public void setHinhAnhLoaiSanPham(String hinhAnhLoaiSanPham) {
        HinhAnhLoaiSanPham = hinhAnhLoaiSanPham;
    }
}
