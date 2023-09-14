package org.saigon4paws.Services;

import org.saigon4paws.DTO.Bank;

import java.util.List;

public interface BankService {
    List<Bank> getAllBanks();

    String getVietQRLink(String bankBin, String bankAccountNumber, String bankAccountName, Integer amount, String message);
}