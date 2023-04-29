package com.setqt.Hiring.Controller;


import com.setqt.Hiring.Model.ResponseObject;
import com.setqt.Hiring.Service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IStorageService storageService;


    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // save file to Firebase
        	System.out.println("ok"+file);
            String fileName = storageService.save(file);
            String imageUrl = storageService.getFileUrl(fileName);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "upload file successfully", imageUrl)
            );
            //06a290064eb94a02a58bfeef36002483.png => how to open this file in Web Browser ?
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", exception.getMessage(), "")
            );
        }
    }

}
