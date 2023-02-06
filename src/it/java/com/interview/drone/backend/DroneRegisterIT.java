package com.interview.drone.backend;

import com.interview.drone.backend.dto.DroneResponse;
import com.interview.drone.backend.dto.RegisterDroneRequest;
import com.interview.drone.backend.entity.Drone;
import com.interview.drone.backend.entity.DroneModel;
import com.interview.drone.backend.repository.DroneRepository;
import com.interview.drone.backend.service.DroneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DroneRegisterIT {

    @Container
    private static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:8.0.22");

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.sql.init.mode", () -> "never");
        registry.add("spring.jpa.defer-datasource-initialization", () -> false);
    }

    @Autowired
    private DroneService droneService;

    @Autowired
    private DroneRepository droneRepository;

    @Test
    public void registerDrone_whenDroneIsNotPresentInTheDB_thenReturnDroneResponse() {
        RegisterDroneRequest request = RegisterDroneRequest.builder()
                .serialNumber("123")
                .droneModel(DroneModel.LIGHT_WEIGHT)
                .weightLimitInGram(500)
                .batteryCapacity(100)
                .build();

        DroneResponse response = droneService.registerDrone(request);

        assertNotNull(response);
        assertEquals("123", response.getSerialNumber());

        Drone drone = droneRepository.findById("123").get();
        assertNotNull(drone);
        assertEquals("123", drone.getSerialNumber());
        assertEquals(100, drone.getBatteryCapacity());
        assertEquals(DroneModel.LIGHT_WEIGHT, drone.getDroneModel());
        assertEquals(500, drone.getWeightLimitInGram());
    }
}
