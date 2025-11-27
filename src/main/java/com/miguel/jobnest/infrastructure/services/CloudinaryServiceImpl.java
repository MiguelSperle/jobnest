package com.miguel.jobnest.infrastructure.services;

import com.cloudinary.Cloudinary;
import com.miguel.jobnest.application.abstractions.services.UploadService;
import com.miguel.jobnest.infrastructure.services.exceptions.UploadFailedException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements UploadService {
    private final Cloudinary cloudinary;

    private static final Logger log = LoggerFactory.getLogger(CloudinaryServiceImpl.class);

    @Override
    public String uploadFile(byte[] bytesFile, String folderName) {
        try {
            final HashMap<String, Object> options = new HashMap<>();
            options.put("folder", folderName);
            options.put("resource_type", "auto");

            return (String) this.cloudinary.uploader().upload(bytesFile, options).get("secure_url");
        } catch (Exception ex) {
            log.error("Failed to upload file with size [{}] in folder [{}]", bytesFile.length, folderName, ex);
            throw UploadFailedException.with("Failed to upload file");
        }
    }
}
