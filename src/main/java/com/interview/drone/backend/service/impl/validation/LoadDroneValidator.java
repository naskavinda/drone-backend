package com.interview.drone.backend.service.impl.validation;

import com.interview.drone.backend.dto.LoadDroneRequest;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class LoadDroneValidator implements Ordered {

    private LoadDroneValidator next;

    public LoadDroneValidator setNext(LoadDroneValidator handler) {
        this.next = handler;
        return handler;
    }

    public LoadDroneValidator link(List<LoadDroneValidator> validators) {
        LoadDroneValidator head = this;
        for (LoadDroneValidator nextInChain : validators) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return this;
    }

    public abstract LoadDroneChainResponse validate(LoadDroneRequest loadDroneRequest, LoadDroneChainResponse loadDroneChainResponse);

    protected LoadDroneChainResponse validateNext(LoadDroneRequest loadDroneRequest, LoadDroneChainResponse loadDroneChainResponse) {
        if (next == null) {
            return loadDroneChainResponse;
        }
        return next.validate(loadDroneRequest, loadDroneChainResponse);
    }
}
