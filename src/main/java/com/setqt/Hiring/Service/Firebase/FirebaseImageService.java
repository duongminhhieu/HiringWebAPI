package com.setqt.Hiring.Service.Firebase;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service("imageService")
public class FirebaseImageService implements IStorageService {

    // Get the Storage instance
    Storage storage;
    @EventListener
    public void init(ApplicationReadyEvent event) {

        try {
            storage = StorageClient.getInstance().bucket().getStorage();
        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }

    private boolean isImageFile(MultipartFile file) {
        //Let install FileNameUtils
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"png","jpg","jpeg", "bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String getFileUrl(String name) {
        Bucket bucket = StorageClient.getInstance().bucket();
        // Get the file URL
        BlobId blobId = BlobId.of(bucket.getName(), name);
        Blob blob = bucket.getStorage().get(blobId);
        return blob.signUrl(100000, TimeUnit.DAYS).toString();
    }

    @Override
    public String save(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file.");
        }
        //check file is image ?
        if(!isImageFile(file)) {
            throw new RuntimeException("You can only upload image file");
        }
        //file must be <= 5Mb
        float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
        if(fileSizeInMegabytes > 5.0f) {
            throw new RuntimeException("File must be <= 5Mb");
        }

        Bucket bucket = StorageClient.getInstance().bucket();
        String name = generateFileName(file.getOriginalFilename());

        bucket.create(name, file.getBytes(), file.getContentType());

        return name;
    }

    @Override
    public String save(BufferedImage bufferedImage, String originalFileName) throws IOException {

        byte[] bytes = getByteArrays(bufferedImage, getExtension(originalFileName));

        Bucket bucket = StorageClient.getInstance().bucket();

        String name = generateFileName(originalFileName);

        bucket.create(name, bytes);

        return name;
    }

    @Override
    public void delete(String name) throws IOException {

        Bucket bucket = StorageClient.getInstance().bucket();

        if (StringUtils.isEmpty(name)) {
            throw new IOException("invalid file name");
        }

        Blob blob = bucket.get(name);

        if (blob == null) {
            throw new IOException("file not found");
        }

        blob.delete();
    }

    @Override
    public String update(String name, MultipartFile file) throws IOException {
        delete(name);
        return getFileUrl(save(file));
    }


}