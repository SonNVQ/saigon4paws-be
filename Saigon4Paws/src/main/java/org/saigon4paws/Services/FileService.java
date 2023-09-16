package org.saigon4paws.Services;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String saveFile(String savingPath, String fileName, byte[] fileContent) throws Exception;

    String saveImageFromMultipartFile(String savingFolder, MultipartFile image) throws Exception;
}
