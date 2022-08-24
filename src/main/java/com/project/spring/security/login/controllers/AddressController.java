package com.project.spring.security.login.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.project.spring.security.login.interfaces.AddressInterface;
import com.project.spring.security.login.models.Address;
import com.project.spring.security.login.payload.response.AddressResponse;
import com.project.spring.security.login.repository.AddressRepository;

@RestController
public class AddressController {

    @Autowired
    private AddressInterface addressInterface;

    @Autowired
    private AddressRepository addressRepository;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
    @GetMapping("/api/{cep}")
    public ResponseEntity<AddressResponse> getByCep(@PathVariable String cep) {

        AddressResponse response = addressInterface.getAddressByCep(cep);

        Address address = new Address(
                response.getCep(),
                response.getLogradouro(),
                response.getComplemento(),
                response.getBairro(),
                response.getLocalidade(),
                response.getUf(),
                response.getIbge(), 
                response.getGia(),
                response.getDdd(), 
                response.getSiafi()
        );

        addressRepository.save(address);

        return address != null ? ResponseEntity.ok().body(response)
                            : ResponseEntity.notFound().build();

    }


    @PreAuthorize("hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
    @GetMapping("/api/{uf}/{cidade}/{logradouro}")
    public ResponseEntity<List<AddressResponse>> getByDescription(
        @PathVariable String uf, @PathVariable String cidade, @PathVariable String logradouro
    ){
        List<AddressResponse> addresses = addressInterface.getAddressByDescription(uf, cidade, logradouro);

        return addresses != null ? ResponseEntity.ok().body( addresses ) : ResponseEntity.notFound().build();
    }


    
}

