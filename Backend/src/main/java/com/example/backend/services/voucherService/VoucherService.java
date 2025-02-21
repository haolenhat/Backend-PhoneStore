package com.example.backend.services.voucherService;

import com.example.backend.entities.voucher.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherService {
    List<Voucher> getAllVouchers();
    Voucher getVoucherById(int id);
    Voucher createVoucher(Voucher voucher);
    Voucher updateVoucher(int id, Voucher voucher);
    void deleteVoucher(int id);
    Optional<Voucher> validateVoucher(String maVoucher);
}
