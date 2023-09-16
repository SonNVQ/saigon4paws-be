package org.saigon4paws.Services.Impl;

import org.saigon4paws.DTO.ReliefGroupDTO;
import org.saigon4paws.Models.ReliefGroup;
import org.saigon4paws.Repositories.ReliefGroupRepository;
import org.saigon4paws.Services.FileService;
import org.saigon4paws.Services.ReliefGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ReliefGroupServiceImpl implements ReliefGroupService {
    @Autowired
    private ReliefGroupRepository reliefGroupRepository;

    @Autowired
    private FileService fileService;

    @Value("${upload.relief-group.dir}")
    private String reliefGroupPhotoDir;

    @Override
    public List<ReliefGroup> getAllReliefGroups() {
        return reliefGroupRepository.findAll();
    }

    @Override
    public ReliefGroup createReliefGroup(ReliefGroupDTO reliefGroupDTO) throws Exception {
        ReliefGroup existingReliefGroup = reliefGroupRepository.findByName(reliefGroupDTO.getName());
        if (existingReliefGroup != null) {
            throw new Exception("Relief group already exists!");
        }
        validateReliefGroup(reliefGroupDTO);
        ReliefGroup reliefGroup = ReliefGroup.builder()
                .name(reliefGroupDTO.getName())
                .workingArea(reliefGroupDTO.getWorkingArea())
                .description(reliefGroupDTO.getDescription())
                .email(reliefGroupDTO.getEmail())
                .phoneNumber(reliefGroupDTO.getPhoneNumber())
                .fanpageLink(reliefGroupDTO.getFanpageLink())
                .bankName(reliefGroupDTO.getBankName())
                .bankBin(reliefGroupDTO.getBankBin())
                .bankAccountNumber(reliefGroupDTO.getBankAccountNumber())
                .bankAccountName(reliefGroupDTO.getBankAccountName())
                .avatarUrl(reliefGroupDTO.getAvatarUrl())
                .build();
        return reliefGroupRepository.save(reliefGroup);
    }

    @Override
    public ReliefGroup getReliefGroupById(Integer id) {
        if (id == null)
            return null;
        return reliefGroupRepository.findById(id).orElse(null);
    }

    @Override
    public ReliefGroupDTO getReliefGroupDTOById(Integer id) {
        ReliefGroup reliefGroup = getReliefGroupById(id);
        if (reliefGroup == null)
            return null;
        return ReliefGroupDTO.builder()
                .id(reliefGroup.getId())
                .name(reliefGroup.getName())
                .workingArea(reliefGroup.getWorkingArea())
                .description(reliefGroup.getDescription())
                .email(reliefGroup.getEmail())
                .phoneNumber(reliefGroup.getPhoneNumber())
                .fanpageLink(reliefGroup.getFanpageLink())
                .bankName(reliefGroup.getBankName())
                .bankBin(reliefGroup.getBankBin())
                .bankAccountNumber(reliefGroup.getBankAccountNumber())
                .bankAccountName(reliefGroup.getBankAccountName())
                .avatarUrl(reliefGroup.getAvatarUrl())
                .build();
    }

    @Override
    public ReliefGroupDTO updateReliefGroupDTOById(Integer id, ReliefGroupDTO reliefGroupDTO) throws Exception {
        ReliefGroup reliefGroup = getReliefGroupById(id);
        if (reliefGroup == null)
            throw new Exception("Relief group not found!");
        validateUpdatingReliefGroup(getReliefGroupDTOById(id), reliefGroupDTO);
        reliefGroup.setName(reliefGroupDTO.getName());
        reliefGroup.setWorkingArea(reliefGroupDTO.getWorkingArea());
        reliefGroup.setDescription(reliefGroupDTO.getDescription());
        reliefGroup.setEmail(reliefGroupDTO.getEmail());
        reliefGroup.setPhoneNumber(reliefGroupDTO.getPhoneNumber());
        reliefGroup.setFanpageLink(reliefGroupDTO.getFanpageLink());
        reliefGroup.setBankName(reliefGroupDTO.getBankName());
        reliefGroup.setBankBin(reliefGroupDTO.getBankBin());
        reliefGroup.setBankAccountNumber(reliefGroupDTO.getBankAccountNumber());
        reliefGroup.setBankAccountName(reliefGroupDTO.getBankAccountName());
        if (reliefGroupDTO.getAvatarUrl() != null) {
            reliefGroup.setAvatarUrl(reliefGroupDTO.getAvatarUrl());
        }
        reliefGroupRepository.save(reliefGroup);
        return ReliefGroupDTO.builder()
                .id(reliefGroup.getId())
                .name(reliefGroup.getName())
                .workingArea(reliefGroup.getWorkingArea())
                .description(reliefGroup.getDescription())
                .email(reliefGroup.getEmail())
                .phoneNumber(reliefGroup.getPhoneNumber())
                .fanpageLink(reliefGroup.getFanpageLink())
                .bankName(reliefGroup.getBankName())
                .bankBin(reliefGroup.getBankBin())
                .bankAccountNumber(reliefGroup.getBankAccountNumber())
                .bankAccountName(reliefGroup.getBankAccountName())
                .avatarUrl(reliefGroup.getAvatarUrl())
                .build();
    }

    @Override
    public void deleteReliefGroupById(Integer id) throws Exception {
        ReliefGroup existingReliefGroup = getReliefGroupById(id);
        if (existingReliefGroup == null) {
            throw new Exception("Relief group not found!");
        }
        reliefGroupRepository.deleteById(id);
    }

    @Override
    public boolean isNameExisted(String name) {
        return reliefGroupRepository.existsByName(name);
    }

    @Override
    public boolean isEmailExisted(String email) {
        return reliefGroupRepository.existsByEmail(email);
    }

    @Override
    public boolean isPhoneNumberExisted(String phoneNumber) {
        return reliefGroupRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean isBankAccountNumberExisted(String bankAccountNumber) {
        return reliefGroupRepository.existsByBankAccountNumber(bankAccountNumber);
    }

    private void validateReliefGroup(ReliefGroupDTO reliefGroupDTO) throws Exception {
        if (isEmailExisted(reliefGroupDTO.getEmail())) {
            throw new Exception("Email already exists!");
        }
        if (isPhoneNumberExisted(reliefGroupDTO.getPhoneNumber())) {
            throw new Exception("Phone number already exists!");
        }
        if (isBankAccountNumberExisted(reliefGroupDTO.getBankAccountNumber())) {
            throw new Exception("Bank account number already exists!");
        }
    }

    private void validateUpdatingReliefGroup(ReliefGroupDTO oldReliefGroup, ReliefGroupDTO newReliefGroup) throws Exception {
        if (!oldReliefGroup.getName().equals(newReliefGroup.getName()) && isNameExisted(newReliefGroup.getName())) {
            throw new Exception("Relief group name already exists!");
        }
        if (!oldReliefGroup.getEmail().equals(newReliefGroup.getEmail()) && isEmailExisted(newReliefGroup.getEmail())) {
            throw new Exception("Email already exists!");
        }
        if (!oldReliefGroup.getPhoneNumber().equals(newReliefGroup.getPhoneNumber()) && isPhoneNumberExisted(newReliefGroup.getPhoneNumber())) {
            throw new Exception("Phone number already exists!");
        }
        if (!oldReliefGroup.getBankAccountNumber().equals(newReliefGroup.getBankAccountNumber()) && isBankAccountNumberExisted(newReliefGroup.getBankAccountNumber())) {
            throw new Exception("Bank account number already exists!");
        }
    }

    @Override
    public String uploadReliefGroupPhoto(MultipartFile avatar) throws Exception {
        return fileService.saveImageFromMultipartFile(reliefGroupPhotoDir, avatar);
    }
}
