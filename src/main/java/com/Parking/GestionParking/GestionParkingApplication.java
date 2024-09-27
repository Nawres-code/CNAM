package com.Parking.GestionParking;

//import com.Parking.GestionParking.entities.Role;
//import com.Parking.GestionParking.repository.RoleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableJpaAuditing
//@EnableAsync

@ComponentScan(basePackages = {"com.Parking.GestionParking", "com.Parking.GestionParking.email"})
public class GestionParkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionParkingApplication.class, args);
	}
//	@Bean
//	public CommandLineRunner runner(RoleRepository roleRepository) {
//		return args -> {
//			if (roleRepository.findByName("USER").isEmpty()) {
//				roleRepository.save(Role.builder().name("USER").build());
//			}
//		};
}