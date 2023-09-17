package org.saigon4paws.Services.Impl;

import org.saigon4paws.Services.FileService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${upload.root-dir}")
    private String savingRootFolderPath;

    @Override
    public String saveFile(String savingPath, String fileName, byte[] fileContent) throws Exception {
        //Saving folder path
//        String savingFolderPath = System.getProperty("user.home") + File.separator
//                + savingRootFolderPath + File.separator
//                + savingPath;
        String savingFolderPath = savingRootFolderPath + File.separator
                + savingPath;

        File savingFolder = new File(savingFolderPath);

        if (!savingFolder.exists()) //Check if folder exists
            savingFolder.mkdirs();  //Create folder if not exists

        //Saving file path
        String savingFilePath = savingFolderPath + File.separator + fileName;

        //Save file
        Files.write(new File(savingFilePath).toPath(), fileContent);

        //Upload file url
        String savingFileUrl = "/" + savingPath + "/" + fileName;

        return savingFileUrl;
    }

    @Override
    public String saveImageFromMultipartFile(String savingFolder, MultipartFile image) throws Exception {
        //Check if image is null or empty
        if (image == null || image.getSize() == 0)
            return null;

        //Check if image file name is valid
        String originalAvatarFilename = image.getOriginalFilename();
        if (originalAvatarFilename == null || originalAvatarFilename.isEmpty())
            throw new Exception("Image file is invalid!");

        //Check if image extension is valid
        String imageExtension = originalAvatarFilename.substring(originalAvatarFilename.lastIndexOf(".") + 1);
        if (!Constants.ALLOWED_IMAGE_EXTENSIONS.contains(imageExtension.toLowerCase())) {
            throw new Exception("Image extension is not accepted!");
        }

        //Save image file
        String savedPhotoUrl;
        try {
            String fileName = UUID.randomUUID() + "." + imageExtension;
            savedPhotoUrl = saveFile(savingFolder, fileName, image.getBytes());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        //Return saved image url
        return savedPhotoUrl;
    }
}
