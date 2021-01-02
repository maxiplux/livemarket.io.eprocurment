package io.eprocurment.b2b2021.util;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Endpoint(id = "liveness")
@Component
public class KubernetesLiveness {
    @ReadOperation
    public ResponseEntity<String> Liveness() {
        return new ResponseEntity<String>("{\"status\":\"UP\"}", HttpStatus.OK);

    }
}
