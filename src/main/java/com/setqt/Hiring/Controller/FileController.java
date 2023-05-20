package com.setqt.Hiring.Controller;


import com.setqt.Hiring.Model.Company;
import com.setqt.Hiring.Model.Employer;
import com.setqt.Hiring.Model.ResponseObject;
import com.setqt.Hiring.Repository.CompanyRepository;
import com.setqt.Hiring.Repository.EmployerRepository;
import com.setqt.Hiring.Service.Firebase.FirebaseDocumentFileService;
import com.setqt.Hiring.Service.Firebase.FirebaseImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping(path = "api/v1/FileUpload")
public class FileController {

    @Autowired
    @Qualifier("imageService")
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

    @DeleteMapping("/deleteCV/{name}")
    public ResponseEntity<ResponseObject> deleteFile(@PathVariable String name) {
        try{
            firebaseDocumentFileService.delete(name);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete file document successfully", "")
            );
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", exception.getMessage(), "")
            );
        }
    }

    @DeleteMapping("/deleteImage/{name}")
    public ResponseEntity<ResponseObject> deleteImage(@PathVariable String name) {
        try{
            firebaseImageService.delete(name);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete file image successfully", "")
            );
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", exception.getMessage(), "")
            );
        }
    }


    @PutMapping("/updateImage/{name}")
    public ResponseEntity<ResponseObject> updateImage(@PathVariable String name, @RequestParam("file") MultipartFile file) {
        try {
            firebaseImageService = new FirebaseImageService();

            String imageUrl = firebaseImageService.update(name, file);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "update file image successfully", imageUrl)
            );
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", exception.getMessage(), "")
            );
        }
    }

    @PutMapping("/updateDocument/{name}")
    public ResponseEntity<ResponseObject> updateFile(@PathVariable String name, @RequestParam("file") MultipartFile file) {
        try {
            firebaseDocumentFileService = new FirebaseDocumentFileService();

            String imageUrl = firebaseDocumentFileService.update(name, file);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "update file document successfully", imageUrl)
            );
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", exception.getMessage(), "")
            );
        }
    }


}
