package com.Parking.GestionParking.services;

import com.Parking.GestionParking.Utils.Base64Utils;
import com.Parking.GestionParking.Utils.ImageUtils;
import com.Parking.GestionParking.entities.ImageData;
import com.Parking.GestionParking.entities.ImageDataDTO;
import com.Parking.GestionParking.repository.IDataImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DataImageService implements IDataImageService{

    @Autowired
    IDataImageRepository imageRepo ;
    @Override
    public void removeFile(Long id) {
        imageRepo.deleteById(id);
    }

    @Autowired
    private IDataImageRepository repository;

    public String uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

    @Override
    public List<ImageData> retrieveAllImages() {
        return (List<ImageData>) repository.findAll();
    }


    @Override
    public ImageDataDTO getImageData(Long id) {
        ImageData imageData = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        // Encode the image data to Base64
        String base64Image = Base64Utils.encodeToBase64(imageData.getImageData());

        // Build and return the DTO with Base64 encoded image data
        return ImageDataDTO.builder()
                .id(imageData.getId())
                .name(imageData.getName())
                .type(imageData.getType())
                .imageData(base64Image)
                .build();
    }

    @Override
    public ImageData archiveFile(Long id) {
        Optional<ImageData> imageDataOptional = imageRepo.findById(id);
        if (imageDataOptional.isPresent()) {
            ImageData imageData = imageDataOptional.get();

            // Mark the file as archived
            imageData.setArchived(true);

            // Save the updated entity
            return imageRepo.save(imageData);
        } else {
            throw new RuntimeException("Image not found");
        }
    }

}
