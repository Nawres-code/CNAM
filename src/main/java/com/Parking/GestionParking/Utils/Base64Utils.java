package com.Parking.GestionParking.Utils;

import java.util.Base64;

public class Base64Utils {

    public static String encodeToBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decodeFromBase64(String base64Data) {
        return Base64.getDecoder().decode(base64Data);
    }
}
