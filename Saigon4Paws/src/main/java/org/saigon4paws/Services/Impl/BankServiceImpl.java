package org.saigon4paws.Services.Impl;

import org.saigon4paws.DTO.Bank;
import org.saigon4paws.DTO.BankResponse;
import org.saigon4paws.Services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class BankServiceImpl implements BankService {
    private static final String BANK_API_URL = "https://api.vietqr.io/v2/banks";

    private static final String VIETQR_TEMPLATE_ID = "FTJqM8B";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Bank> getAllBanks() {
        ResponseEntity<BankResponse> response = restTemplate.getForEntity(BANK_API_URL, BankResponse.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            BankResponse bankResponse = response.getBody();
            if (bankResponse != null && bankResponse.getData() != null) {
                return bankResponse.getData();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public String getVietQRLink(String bankBin, String bankAccountNumber, String bankAccountName, Integer amount, String message) {
        StringBuilder qrLink = new StringBuilder("https://api.vietqr.io/image/");
        qrLink.append(bankBin).append("-");
        qrLink.append(bankAccountNumber).append("-");
        qrLink.append(VIETQR_TEMPLATE_ID).append(".jpg?");
        qrLink.append("accountName=").append(bankAccountName).append("&");
        qrLink.append("amount=").append(amount.toString()).append("&");
        qrLink.append("addInfo=").append(message);
        return qrLink.toString();
    }
}
