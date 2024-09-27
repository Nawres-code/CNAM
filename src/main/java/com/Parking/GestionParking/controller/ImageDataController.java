package com.Parking.GestionParking.controller;


import com.Parking.GestionParking.entities.ImageDataDTO;
import com.Parking.GestionParking.entities.ImageData;

import com.Parking.GestionParking.services.DataImageService;
import com.Parking.GestionParking.services.IDataImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@SpringBootApplication
@RestController
@RequestMapping("/image")
public class ImageDataController {

    @Autowired
    private DataImageService service;
    @Autowired
    private IDataImageService imgService;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable String fileName) {
        try {
            byte[] fileData = service.downloadImage(fileName);

            if (fileData == null || fileData.length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            String contentType = getContentType(fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentDispositionFormData("attachment", fileName);

            InputStream inputStream = new ByteArrayInputStream(fileData);
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

            return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


    private String getContentType(String fileName) {
        Map<String, String> mimeTypes = new HashMap<>();
        mimeTypes.put("pdf", "application/pdf");
        mimeTypes.put("png", "image/png");
        mimeTypes.put("jpg", "image/jpeg");
        mimeTypes.put("jpeg", "image/jpeg");
        mimeTypes.put("txt", "text/plain");
        mimeTypes.put("html", "text/html");
        mimeTypes.put("csv", "text/csv");
        mimeTypes.put("doc", "application/msword");
        mimeTypes.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        mimeTypes.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        mimeTypes.put("zip", "application/zip");
        // Add other file types as needed

        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        return mimeTypes.getOrDefault(extension, "application/octet-stream");
    }

    @GetMapping("/getall")
    public List<ImageData> getAll() {
        return imgService.retrieveAllImages().stream()
                .filter(imageData -> !imageData.isArchived()) // Exclude archived files
                .collect(Collectors.toList());
    }


    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        imgService.removeFile(id);
    }

    @GetMapping("/getIMGById/{id}")
    public ImageDataDTO getImage(@PathVariable Long id) {
        return imgService.getImageData(id);
    }
    @PutMapping("/archive/{id}")
    public ResponseEntity<?> archiveImage(@PathVariable Long id) {
        try {
            ImageData archivedImage = imgService.archiveFile(id);
            return ResponseEntity.ok(archivedImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error archiving the file: " + e.getMessage());
        }
    }

    @GetMapping("/archive/getall")
    public List<ImageData> getAllArchived() {
        return imgService.retrieveAllImages().stream()
                .filter(ImageData::isArchived)
                .collect(Collectors.toList());
    }
}