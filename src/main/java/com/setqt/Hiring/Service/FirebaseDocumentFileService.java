package com.setqt.Hiring.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseDocumentFileService implements IStorageService{


    private boolean isDocumentFile(MultipartFile file) {
        //Let install FileNameUtils
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"doc","docx","pdf"})
                .contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String getFileUrl(String name) {
        Bucket bucket = StorageClient.getInstance().bucket();
        // Get the file URL
        BlobId blobId = BlobId.of(bucket.getName(),"cvs_candidates/" + name);
        Blob blob = bucket.getStorage().get(blobId);
        return blob.signUrl(100000, TimeUnit.DAYS).toString();
    }

    @Override
    public String save(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file.");
        }
        //check file is image ?
        if(!isDocumentFile(file)) {
            throw new RuntimeException("You can only upload document file (pdf, doc, docx)");
        }
        //file must be <= 5Mb
        float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
        if(fileSizeInMegabytes > 5.0f) {
            throw new RuntimeException("File must be <= 5Mb");
        }

        Bucket bucket = StorageClient.getInstance().bucket();

        String name = generateFileName(file.getOriginalFilename());

        bucket.create( "cvs_candidates/" + name, file.getBytes(), file.getContentType());

        return name;
    }

    @Override
    public String save(BufferedImage bufferedImage, String originalFileName) throws IOException {
        byte[] bytes = getByteArrays(bufferedImage, getExtension(originalFileName));

        Bucket bucket = StorageClient.getInstance().bucket();

        String name = generateFileName(originalFileName);

        bucket.create("cvs_candidates/" + name, bytes);

        return name;
    }

    @Override
    public void delete(String name) throws IOException {

    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "firebase.document")
    public class Properties {
        private String bucketName;

    }
}
