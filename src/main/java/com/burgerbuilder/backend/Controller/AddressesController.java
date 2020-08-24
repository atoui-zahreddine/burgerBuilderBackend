package com.burgerbuilder.backend.Controller;

import com.burgerbuilder.backend.Model.Address;
import com.burgerbuilder.backend.Model.User;
import com.burgerbuilder.backend.Service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
public class AddressesController {

    private final AddressService addressService;

    @Autowired
    public AddressesController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("")
    public ResponseEntity<?> addAddress(@AuthenticationPrincipal User user, @RequestBody @Valid Address address){
        return addressService.addAddress(address,user);
    }
    @GetMapping("")
    public ResponseEntity<?> getAllAddresses(@AuthenticationPrincipal User user){
        return addressService.getAllAddresses(user);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<?> updateAddress(@PathVariable String addressId,
                                           @RequestBody @Valid Address address,
                                           @AuthenticationPrincipal User user){
        return addressService.updateAddress(UUID.fromString(addressId),address,user);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable String addressId,@AuthenticationPrincipal User user){
        return addressService.deleteAddress(UUID.fromString(addressId),user);
    }
}
