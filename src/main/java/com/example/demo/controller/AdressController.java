package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AdressResponce;
import com.example.demo.service.AdressService;

import lombok.RequiredArgsConstructor;

@RestController
//①RestControllerである理由は？なぜ@Controllerではないのか。
@RequestMapping("/api")
//①RequestMappingの理由は？
//②なぜ("/api")がついているのか
@RequiredArgsConstructor
public class AdressController {
	private final AdressService addressService;
	
	@GetMapping("/address")
	public AdressResponce searchAddress(@RequestParam String zipCode) {
        return addressService.getAddressByZip(zipCode);
    }
}
