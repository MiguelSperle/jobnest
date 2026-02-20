package com.miguel.jobnest.application.abstractions.services;

public interface UploadService {
    String uploadFile(byte[] bytesFile, String folderName, String resourceType);
    void destroyFile(String publicId, String resourceType);
}
