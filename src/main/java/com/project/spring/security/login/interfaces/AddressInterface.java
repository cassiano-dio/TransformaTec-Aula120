package com.project.spring.security.login.interfaces;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.spring.security.login.payload.response.AddressResponse;

//Comunicação com a API do ViaCEP
@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface AddressInterface {

    //Obter endereço por um cep informado
    @GetMapping("{cep}/json")
    AddressResponse getAddressByCep(@PathVariable("cep") String cep);

    //Obter endereços por descrição
    @GetMapping("{uf}/{cidade}/{logradouro}/json")
    List<AddressResponse> getAddressByDescription(
        @PathVariable("uf") String uf, 
        @PathVariable("cidade") String cidade, 
        @PathVariable("logradouro") String logradouro
    );
    
}

