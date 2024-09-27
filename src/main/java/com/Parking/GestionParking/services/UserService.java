package com.Parking.GestionParking.services;

import com.Parking.GestionParking.entities.User;


public interface UserService {
    //Optional<User> findByEmail(String email);
    void createPasswordResetTokenForUser(User user, String token);
}