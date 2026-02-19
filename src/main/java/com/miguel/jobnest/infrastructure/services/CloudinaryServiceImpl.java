package com.miguel.jobnest.infrastructure.services;

import com.cloudinary.Cloudinary;
import com.miguel.jobnest.application.abstractions.services.UploadService;
import com.miguel.jobnest.infrastructure.exceptions.FileDestructionFailedException;
import com.miguel.jobnest.infrastructure.exceptions.FileUploadFailedException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements UploadService {
    private final Cloudinary cloudinary;

    private static final Logger log = LoggerFactory.getLogger(CloudinaryServiceImpl.class);

    @Override
    public String uploadFile(final byte[] bytesFile, final String folderName, final String resourceType) {
        try {
            final Map<String, Object> options = new HashMap<>();
            options.put("folder", folderName);
            options.put("resource_type", resourceType);

            return (String) this.cloudinary.uploader().upload(bytesFile, options).get("secure_url");
        } catch (final Exception ex) {
            log.error("Failed to upload file with bytes size of {} in folder {} with resource type being {}", bytesFile.length, folderName, resourceType, ex);
            throw FileUploadFailedException.with("Failed to upload file");
        }
    }

    @Override
    public void destroyFile(final String publicId, final String resourceType) {
        try {
            final Map<String, Object> options = new HashMap<>();
            options.put("resource_type", resourceType);

            this.cloudinary.uploader().destroy(publicId, options);
        } catch (final Exception ex) {
            log.error("Failed to destroy file with publicId {} with resource type being {}", publicId, resourceType);
            throw FileDestructionFailedException.with("Failed to destroy file");
        }
    }

    @Override
    public String extractPublicId(String secureUrl, String folderName) {
        final String noHost = secureUrl.substring(secureUrl.indexOf(folderName + "/"));
        return noHost.substring(0, noHost.lastIndexOf('.'));
    }
}
