package com.example.tnt.cuahangonline.model;

public class Sanpham {
    public int ID;
    public String Tensanpham;
    public Integer Giasanpham;
    public  String Hinhanhsangpham;
    public  String Motasanpham;
    public  int IDsanpham;

    public Sanpham(int ID, String tensanpham, Integer giasanpham, String hinhanhsangpham, String motasanpham, int IDsanpham) {
        this.ID = ID;
        Tensanpham = tensanpham;
        Giasanpham = giasanpham;
        Hinhanhsangpham = hinhanhsangpham;
        Motasanpham = motasanpham;
        this.IDsanpham = IDsanpham;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTensanpham() {
        return Tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        Tensanpham = tensanpham;
    }

    public Integer getGiasanpham() {
        return Giasanpham;
    }

    public void setGiasanpham(Integer giasanpham) {
        Giasanpham = giasanpham;
    }

    public String getHinhanhsangpham() {
        return Hinhanhsangpham;
    }

    public void setHinhanhsangpham(String hinhanhsangpham) {
        Hinhanhsangpham = hinhanhsangpham;
    }

    public String getMotasanpham() {
        return Motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        Motasanpham = motasanpham;
    }

    public int getIDsanpham() {
        return IDsanpham;
    }

    public void setIDsanpham(int IDsanpham) {
        this.IDsanpham = IDsanpham;
    }
}
