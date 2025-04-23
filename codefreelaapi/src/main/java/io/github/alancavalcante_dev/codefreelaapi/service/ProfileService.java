package io.github.alancavalcante_dev.codefreelaapi.service;


import io.github.alancavalcante_dev.codefreelaapi.model.Address;
import io.github.alancavalcante_dev.codefreelaapi.model.Profile;
import io.github.alancavalcante_dev.codefreelaapi.model.User;
import io.github.alancavalcante_dev.codefreelaapi.repository.AddressRepository;
import io.github.alancavalcante_dev.codefreelaapi.repository.ProfileRepository;
import io.github.alancavalcante_dev.codefreelaapi.repository.UserRepository;
import io.github.alancavalcante_dev.codefreelaapi.security.UserLogged;
import io.github.alancavalcante_dev.codefreelaapi.validate.ProfileValidate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

//    private final UserValidate userValidate;
    private final ProfileValidate profileValidate;


    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Optional<Profile> getByIdProfile(UUID uuid) { return profileRepository.findById(uuid);}

    public void delete(Profile profile){
        profileRepository.delete(profile);
    }


    @Transactional
    public Profile save(Profile profile) {
        User userLogged = UserLogged.load();
        profile.setUser(userLogged);
        profile.setBalance(BigDecimal.valueOf(0.0));
        return profileRepository.save(profile);
    }

//    @Transactional
//    public Profile update(Profile profile , User userEntity, Address addressEntity) {
//        userValidate.update(profile.getUser());
//
//        User userProfile = profile.getUser();
//        Address addressProfile = profile.getAddress();
//
//        Optional<User> userExists = userRepository.findById(userEntity.getIdUser());
//        Optional<Address> addressExists = addressRepository.findById(addressEntity.getIdAddress());
//
//        if (userExists.isPresent()) {
//            User user = userExists.get();
//            user.setUsername(userProfile.getUsername());
//            user.setPassword(userProfile.getPassword());
//
//            userProfile = userRepository.save(user);
//        } else {
//            userProfile = userRepository.save(userProfile);
//        }
//
//
//        if (addressExists.isPresent()) {
//            Address address = addressExists.get();
//            address.setState(addressProfile.getState());
//            address.setCity(addressProfile.getCity());
//            address.setNeighborhood(addressProfile.getNeighborhood());
//            address.setAddress(addressProfile.getAddress());
//            address.setAddressNumber(addressProfile.getAddressNumber());
//
//            addressProfile = addressRepository.save(address);
//        } else {
//            addressProfile = addressRepository.save(addressProfile);
//        }
//
//        profile.setUser(userProfile);
//        profile.setAddress(addressProfile);
//        return repository.save(profile);
//    }




}

