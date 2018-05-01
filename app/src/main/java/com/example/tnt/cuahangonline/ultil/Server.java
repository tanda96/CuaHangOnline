package com.example.tnt.cuahangonline.ultil;

public class Server {
    public static String localhost = "newsmachine.website/JSON";//địa chỉ ip của máy
    public static String DuongdanLoaisp = "https://" + localhost + "/getloaisanpham.php";
    public static  String Duongdansanphammoinhat = "https://" + localhost + "/getsanphammoinhat.php";
    public static  String Duongdandienthoai = "https://" + localhost + "/getsanpham.php?page=";
    public static  String Duongdandonhang = "https://" + localhost + "/thongtinkhachhang.php";
    public static  String Duongdanchitietdonhang = "https://" + localhost + "/chitietdonhang.php";
    public static  String DuongdanThongTinDonHang = "https://" + localhost + "/getdonhang.php";


    public static final String EMAIL ="phong@conceptual.studio";
    public static final String PASSWORD ="Mz1j8a1adgjm";
    public static final String subject ="CONFIRM ORDER";
    public static final String message_1 ="Thanks your order with \n" +
            "I wanted to thank you for your interest in (insert the name of the product or the service offered).\n" +
            "\n" +
            "I enjoyed the conversation we had the other day and hope I was able to answer all your questions that you had in your mind.\n" +
            "\n" +
            "Please feel free to contact me if you have any other queries.\n" +
            "\n" +
            "I am also attaching a little gift for you, a (insert the name of the gift), it has my phone number along with the website address. So this will help you to find me and you can easily contact me again in the future.\n" +
            "\n" +
            "I look forward to hearing from you soon.";

}
