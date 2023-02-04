package com.interview.drone.backend.service.impl.validation;

import com.interview.drone.backend.dto.LoadDroneDTO;
import com.interview.drone.backend.entity.Address;
import com.interview.drone.backend.repository.AddressRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class AddressIdValidator extends LoadDroneValidator{

    private final AddressRepository addressRepository;

    public AddressIdValidator(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public LoadDroneChainResponse validate(LoadDroneDTO loadDroneDTO, LoadDroneChainResponse loadDroneChainResponse) {
        Address address = addressRepository.findById(loadDroneDTO.getAddressId()).orElseThrow(() -> new ValidationException("Can not find a Address"));
        loadDroneChainResponse.setAddress(address);
        return validateNext(loadDroneDTO, loadDroneChainResponse);
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
