package org.saigon4paws.Services;

import org.saigon4paws.DTO.ReliefGroupDTO;
import org.saigon4paws.Models.ReliefGroup;

import java.util.List;

public interface ReliefGroupService {
    List<ReliefGroup> getAllReliefGroups();

    ReliefGroup createReliefGroup(ReliefGroupDTO reliefGroupDTO) throws Exception;

    ReliefGroupDTO getReliefGroupById(Integer id);

    ReliefGroupDTO updateReliefGroupById(Integer id, ReliefGroupDTO reliefGroupDTO) throws Exception;

    void deleteReliefGroupById(Integer id) throws Exception;

    boolean isNameExisted(String name);

    boolean isEmailExisted(String email);

    boolean isPhoneNumberExisted(String phoneNumber);

    boolean isBankAccountNumberExisted(String bankAccountNumber);
}
