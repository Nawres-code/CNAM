package com.Parking.GestionParking.services;

import com.Parking.GestionParking.entities.ImageData;
import com.Parking.GestionParking.entities.ImageDataDTO;

import java.util.List;

public interface IDataImageService {

    List<ImageData> retrieveAllImages();

    void removeFile(Long id);

    public ImageDataDTO getImageData(Long id);

    ImageData archiveFile(Long id);
}
