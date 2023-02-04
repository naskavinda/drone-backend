package com.interview.drone.backend.service.impl.validation;

import com.interview.drone.backend.dto.LoadDroneRequest;
import com.interview.drone.backend.entity.Address;
import com.interview.drone.backend.repository.AddressRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import static com.interview.drone.backend.service.impl.validation.LoadDroneValidatorChainOrder.ADDRESS_ID_VALIDATOR_ORDER;

@Component
public class AddressIdValidator extends LoadDroneValidator{

    private final AddressRepository addressRepository;

    public AddressIdValidator(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public LoadDroneChainResponse validate(LoadDroneRequest loadDroneRequest, LoadDroneChainResponse loadDroneChainResponse) {
        Address address = addressRepository.findById(loadDroneRequest.getAddressId()).orElseThrow(() -> new ValidationException("Can not find a Address"));
        loadDroneChainResponse.setAddress(address);
        return validateNext(loadDroneRequest, loadDroneChainResponse);
    }

    @Override
    public int getOrder() {
        return ADDRESS_ID_VALIDATOR_ORDER;
    }
}
