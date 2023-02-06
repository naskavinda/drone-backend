package com.interview.drone.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.drone.backend.dto.DroneResponse;
import com.interview.drone.backend.dto.LoadDroneRequest;
import com.interview.drone.backend.dto.MedicationRequest;
import com.interview.drone.backend.dto.RegisterDroneRequest;
import com.interview.drone.backend.entity.DroneModel;
import com.interview.drone.backend.entity.DroneState;
import com.interview.drone.backend.service.DroneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DroneControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DroneService droneService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturnHttpStatusCreated_whenInputIsValid_forRegisterDrone() throws Exception {

        DroneResponse response = new DroneResponse();
        response.setSerialNumber("1234");
        response.setWeightLimit(500);
        response.setDroneState(DroneState.IDLE);
        response.setDroneModel(DroneModel.LIGHT_WEIGHT);
        response.setBatteryCapacity(100);

        when(droneService.registerDrone(any(RegisterDroneRequest.class))).thenReturn(response);
        MvcResult result = mockMvc
                .perform(post("/api/drone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"serialNumber\":\"1234\",\"droneModel\":\"LIGHT_WEIGHT\",\"weightLimitInGram\":500.00,\"batteryCapacity\":100}"))
                .andExpect(status().isCreated())
                .andReturn();

        DroneResponse droneResponse = objectMapper.readValue(result.getResponse().getContentAsString(), DroneResponse.class);
        assertThat(droneResponse).isEqualTo(response);
        assertThat(result.getResponse().getStatus()).isEqualTo(201);
    }

    @Test
    void shouldReturnHttpStatusBadRequest_whenInputIsInValid_forRegisterDrone() throws Exception {

        MvcResult result = mockMvc
                .perform(post("/api/drone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"serialNumber\":\"12341234123412341234123412341234123412341234123412341234123412341234123412341234123412341234123412341234\",\"droneModel\":\"LIGHT_WEIGHT\",\"weightLimitInGram\":-500.00,\"batteryCapacity\":122}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualTo("{\"weightLimitInGram\":\"Weight can not be less then 0\",\"serialNumber\":\"Serial Number max length is 100\",\"batteryCapacity\":\"Battery Capacity max value is 100\"}");
        assertThat(result.getResponse().getStatus()).isEqualTo(400);
    }

    @Test
    void loadMedication_whenInputIsValid_thenReturnHttpStatusOK() throws Exception {

        LoadDroneRequest response = LoadDroneRequest.builder()
                .addressId(1)
                .droneSerialNumber("111")
                .droneState(DroneState.IDLE.toString())
                .medications(
                        List.of(
                                MedicationRequest.builder()
                                        .medicationCode("MD_5")
                                        .medicationQty(5)
                                        .build()
                        )
                )
                .build();
        doNothing().when(droneService).loadMedicationToDrone(any(LoadDroneRequest.class));
        MvcResult result = mockMvc
                .perform(post("/api/drone/medications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"medications\":[{\"medicationCode\":\"MD_5\",\"medicationQty\":5}],\"addressId\":1,\"droneSerialNumber\":\"333\",\"droneState\":\"LOADING\"}"))
                .andExpect(status().isOk())
                .andReturn();

        Map<String, String> droneResponse = objectMapper.readValue(result.getResponse().getContentAsString(), HashMap.class);
        assertThat(droneResponse.get("message")).isEqualTo("Medication loaded successfully!");
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void loadMedication_whenInputIsInValid_thenReturnHttpStatusBadRequest() throws Exception {
        LoadDroneRequest loadDrone = new LoadDroneRequest();
        doThrow(new RuntimeException()).when(droneService).loadMedicationToDrone(loadDrone);
        MvcResult result = mockMvc
                .perform(post("/api/drone/medications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"medications\":[{\"medicationCode\":\"MD_1\",\"medicationQty\":-5}],\"addressId\":1,\"droneSerialNumber\":\"333\",\"droneState\":\"IDLE\"}"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String actualResponse = result.getResponse().getContentAsString();
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("{\"medications[0].medicationQty\":\"Medication quantity minimum value is 1\",\"droneState\":\"Drone State must be LOADING or LOADED.\"}", actualResponse);
    }

}
