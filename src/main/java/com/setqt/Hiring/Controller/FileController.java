package com.setqt.Hiring.Controller;


import com.setqt.Hiring.Model.ResponseObject;
import com.setqt.Hiring.Service.FirebaseDocumentFileService;
import com.setqt.Hiring.Service.FirebaseImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "api/v1/FileUpload")
public class FileController {

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

}
