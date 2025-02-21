package com.example.backend.controller.orderController;

import com.example.backend.entities.Order.ThongTinKhachHang;
import com.example.backend.services.orderService.ThongTinKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order/info")
public class thongTinKhachHangController {

    @Autowired
    private ThongTinKhachHangService thongTinKhachHangService;

    @PostMapping
    public ResponseEntity<ThongTinKhachHang> addThongTinKh(@RequestBody ThongTinKhachHang thongTinKhachHang) {
        ThongTinKhachHang addedThongTinKhachHang = thongTinKhachHangService.addThongTinKhachHang(thongTinKhachHang);
        return ResponseEntity.ok(addedThongTinKhachHang); // Trả về đối tượng ThongTinKhachHang mới được thêm
    }


    @PutMapping("/{idThongTin}")
    public ResponseEntity<ThongTinKhachHang> updateThongTinKh(@PathVariable int idThongTin, @RequestBody ThongTinKhachHang thongTinKhachHang) {
        ThongTinKhachHang updatedThongTinKhachHang = thongTinKhachHangService.updateThongTinKhachHang(idThongTin, thongTinKhachHang);
        if (updatedThongTinKhachHang != null) {
            System.out.println("Updated ThongTinKhachHang: " + updatedThongTinKhachHang); // Log dữ liệu được cập nhật
            return ResponseEntity.ok(updatedThongTinKhachHang);
        } else {
            return ResponseEntity.notFound().build();
        }
    }







    @GetMapping("/{idKh}")
    public ResponseEntity<List<ThongTinKhachHang>> getThongTinKh(@PathVariable int idKh) {
        List<ThongTinKhachHang> thongTinKhachHangList = thongTinKhachHangService.getThongTinKhachHangByIdKh(idKh);
        if (!thongTinKhachHangList.isEmpty()) {
            return ResponseEntity.ok(thongTinKhachHangList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{idThongTin}")
    public ResponseEntity<Void> deleteThongTinKh(@PathVariable int idThongTin)
    {
        thongTinKhachHangService.deleteThongTinKhachHang(idThongTin);
        return ResponseEntity.noContent().build();
    }

}
