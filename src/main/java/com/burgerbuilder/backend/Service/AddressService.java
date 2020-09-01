package com.burgerbuilder.backend.Service;

import com.burgerbuilder.backend.DTO.Response.ApiResponse;
import com.burgerbuilder.backend.Exception.NotFoundException;
import com.burgerbuilder.backend.Model.Address;
import com.burgerbuilder.backend.Model.User;
import com.burgerbuilder.backend.Repository.AddressRepository;
import com.burgerbuilder.backend.Utils.Enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public ResponseEntity<?> addAddress(@Valid Address address, User user) {
        address.setUser(user);
        var response=new ApiResponse<>(Status.SUCCESS,addressRepository.save(address));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllAddresses(User user){
        var response=new ApiResponse<>(Status.SUCCESS,addressRepository.findAllByUserId(user.getId()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<?> updateAddress(UUID addressId, Address newAddress,User user) {
        var address=getAddressById(addressId,user.getId());
        address.setCity(newAddress.getCity());
        address.setStreet(newAddress.getStreet());
        address.setZipCode(newAddress.getZipCode());
        addressRepository.save(address);
        var response=new ApiResponse<>(Status.SUCCESS,null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private Address getAddressById(UUID addressId,UUID userId) {
        return addressRepository.findByIdAndUserId(addressId,userId)
                .orElseThrow( () -> new NotFoundException(
                        HttpStatus.NOT_FOUND.value(), "no address with this id"));
    }

    public ResponseEntity<?> deleteAddress(UUID addressId, User user) {
        if(addressRepository.existsByIdAndUserId(addressId,user.getId()) == 0)
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "no address with this id");
        addressRepository.deleteById(addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
