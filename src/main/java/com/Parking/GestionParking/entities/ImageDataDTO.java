package com.Parking.GestionParking.entities;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDataDTO {

    private Long id;
    private String name;
    private String type;
    private String imageData;
}
