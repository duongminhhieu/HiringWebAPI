package com.setqt.Hiring.Controller;


import com.setqt.Hiring.Model.ResponseObject;
import com.setqt.Hiring.Service.FirebaseDocumentFileService;
import com.setqt.Hiring.Service.FirebaseImageService;
import com.setqt.Hiring.Service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "api/v1/FileUpload")
public class FileUploadController {

    @Autowired
    private FirebaseImageService firebaseImageService;
    @Autowired
    private FirebaseDocumentFileService firebaseDocumentFileService;

    @PostMapping("/fileImage")
    public ResponseEntity<ResponseObject> uploadImageFile(@RequestParam("file") MultipartFile file) {
        try {
            firebaseImageService = new FirebaseImageService();

            // save file to Firebase
            String fileName = firebaseImageService.save(file);

            String imageUrl = firebaseImageService.getFileUrl(fileName);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "upload file successfully", imageUrl)
            );
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", exception.getMessage(), "")
            );
        }
    }

    @PostMapping("/fileDocument")
    public ResponseEntity<ResponseObject> uploadDocumentFile(@RequestParam("file") MultipartFile file) {
        try {

            firebaseDocumentFileService = new FirebaseDocumentFileService();
            // save file to Firebase
            String fileName = firebaseDocumentFileService.save(file);

            String imageUrl = firebaseDocumentFileService.getFileUrl(fileName);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "upload file successfully", imageUrl)
            );
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", exception.getMessage(), "")
            );
        }
    }

}
