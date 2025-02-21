package com.example.backend.repository.voucherRepository;

import com.example.backend.entities.voucher.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher,Integer> {
    Optional<Voucher> findByMaVoucher(String maVoucher);
}
