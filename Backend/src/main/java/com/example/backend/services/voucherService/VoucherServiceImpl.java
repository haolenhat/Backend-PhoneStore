package com.example.backend.services.voucherService;

import com.example.backend.entities.voucher.Voucher;
import com.example.backend.repository.voucherRepository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher getVoucherById(int id) {
        return voucherRepository.findById(id).orElseThrow(() -> new RuntimeException("Voucher không tồn tại."));
    }

    @Override
    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher updateVoucher(int id, Voucher voucher) {
        Voucher existingVoucher = voucherRepository.findById(id).orElseThrow(() -> new RuntimeException("Voucher không tồn tại."));
        existingVoucher.setMaVoucher(voucher.getMaVoucher());
        existingVoucher.setMoTa(voucher.getMoTa());
        existingVoucher.setGiaTri(voucher.getGiaTri());
        existingVoucher.setLoaiVoucher(voucher.getLoaiVoucher());
        existingVoucher.setNgayBatDau(voucher.getNgayBatDau());
        existingVoucher.setNgayKetThuc(voucher.getNgayKetThuc());
        existingVoucher.setDieuKienSuDung(voucher.getDieuKienSuDung());
        existingVoucher.setSoLuong(voucher.getSoLuong());
        return voucherRepository.save(existingVoucher);
    }

    @Override
    public void deleteVoucher(int id) {
        voucherRepository.deleteById(id);
    }

    public Optional<Voucher> validateVoucher(String maVoucher) {
        Optional<Voucher> voucher = voucherRepository.findByMaVoucher(maVoucher);

        if (voucher.isPresent()) {
            Voucher v = voucher.get();
            Date now = new Date();
            if (now.before(v.getNgayBatDau())) {
                throw new RuntimeException("Mã giảm giá chưa đến hạn sử dụng.");
            }
            if (now.after(v.getNgayKetThuc())) {
                throw new RuntimeException("Mã giảm giá đã hết hạn.");
            }
            if (v.getSoLuong() <= 0) {
                throw new RuntimeException("Mã giảm giá đã hết số lượng.");
            }
            return Optional.of(v);
        }
        return Optional.empty();
    }
}
