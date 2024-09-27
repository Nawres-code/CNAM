package com.Parking.GestionParking.repository;

import com.Parking.GestionParking.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String username);

    @Query(value = "SELECT COUNT(*) FROM _user", nativeQuery = true)
    long getTotalUserCount();

    @Query(value = "SELECT COUNT(*) FROM _user WHERE role = 'SubscriptionClient'", nativeQuery = true)
    long getSubscribedUserCount();

    @Query(value = "SELECT COUNT(*) FROM _user WHERE role = 'SimpleClient'", nativeQuery = true)
    long getSimpleUserCount();

   /* @Query(value = "SELECT COUNT(*) FROM _user WHERE role = :role", nativeQuery = true)
    long getUserCountByRole(@Param("role") String role);*/


//    @Transactional
//    @Modifying
//    @Query("UPDATE User u SET u.roles = ?2 WHERE u.email = ?1")
//    void updateUserRole(String email, String newRole);





    @Query("SELECT COUNT(u) FROM User u WHERE u.createdDate >= :startDate")
    long countUsersAddedSince(@Param("startDate") LocalDateTime startDate);


}

