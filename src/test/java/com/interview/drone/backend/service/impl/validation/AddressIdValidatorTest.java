package com.interview.drone.backend.service.impl.validation;

import com.interview.drone.backend.dto.LoadDroneRequest;
import com.interview.drone.backend.entity.Address;
import com.interview.drone.backend.repository.AddressRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AddressIdValidatorTest {

    @Autowired
    private AddressIdValidator addressIdValidator;

    @MockBean
    private AddressRepository addressRepository;

    @Test
    public void validate_whenAddressIsPresent_thenReturnResponseIncludingAddress() {

        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .addressId(1)
                .build();

        LoadDroneChainResponse loadDroneChainResponse = LoadDroneChainResponse.builder().build();

        Address address = Address.builder().build();

        when(addressRepository.findById(loadDroneRequest.getAddressId())).thenReturn(Optional.of(address));

        addressIdValidator.validate(loadDroneRequest, loadDroneChainResponse);

        verify(addressRepository).findById(loadDroneRequest.getAddressId());
        assertEquals(loadDroneChainResponse.getAddress(), address);
    }

    @Test
    public void validate_whenDroneIsNotPresent_thenReturnValidationException() {
        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .addressId(1)
                .build();

        LoadDroneChainResponse loadDroneChainResponse = LoadDroneChainResponse.builder().build();

        when(addressRepository.findById(loadDroneRequest.getAddressId())).thenReturn(Optional.empty());

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> addressIdValidator.validate(loadDroneRequest, loadDroneChainResponse));

        assertEquals("Can not find a Address", validationException.getMessage());

        verify(addressRepository).findById(loadDroneRequest.getAddressId());
    }
}