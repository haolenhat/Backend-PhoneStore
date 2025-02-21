package com.example.backend.controller.voucherController;

import com.example.backend.entities.voucher.Voucher;
import com.example.backend.services.voucherService.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping
    public ResponseEntity<List<Voucher>> getAllVouchers() {
        return ResponseEntity.ok(voucherService.getAllVouchers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voucher> getVoucherById(@PathVariable int id) {
        return ResponseEntity.ok(voucherService.getVoucherById(id));
    }

    @PostMapping
    public ResponseEntity<Voucher> createVoucher(@RequestBody Voucher voucher) {
        return ResponseEntity.ok(voucherService.createVoucher(voucher));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voucher> updateVoucher(@PathVariable int id, @RequestBody Voucher voucher) {
        return ResponseEntity.ok(voucherService.updateVoucher(id, voucher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable int id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/validate/{maVoucher}")
    public ResponseEntity<?> validateVoucher(@PathVariable String maVoucher) {
        try {
            Optional<Voucher> voucher = voucherService.validateVoucher(maVoucher);
            if (voucher.isPresent()) {
                return ResponseEntity.ok(voucher.get());
            } else {
                return ResponseEntity.status(404).body("Mã giảm giá không hợp lệ.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
