package io.github.alancavalcante_dev.codefreelaapi.service;


import io.github.alancavalcante_dev.codefreelaapi.model.Address;
import io.github.alancavalcante_dev.codefreelaapi.model.Profile;
import io.github.alancavalcante_dev.codefreelaapi.model.User;
import io.github.alancavalcante_dev.codefreelaapi.repository.AddressRepository;
import io.github.alancavalcante_dev.codefreelaapi.repository.ProfileRepository;
import io.github.alancavalcante_dev.codefreelaapi.repository.UserRepository;
import io.github.alancavalcante_dev.codefreelaapi.validate.ProfileValidate;
import io.github.alancavalcante_dev.codefreelaapi.validate.UserValidate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository repository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    private final UserValidate userValidate;
    private final ProfileValidate profileValidate;


    public List<Profile> getAllProfiles() {
        return repository.findAll();
    }

    public Optional<Profile> getByIdProfile(UUID uuid) { return repository.findById(uuid);}

    public void delete(Profile profile){
        repository.delete(profile);
    }

    @Transactional
    public Profile update(Profile profile , User userEntity, Address addressEntity) {
        userValidate.update(profile.getUser());

        User userProfile = profile.getUser();
        Address addressProfile = profile.getAddress();

        Optional<User> userExists = userRepository.findById(userEntity.getIdUser());
        Optional<Address> addressExists = addressRepository.findById(addressEntity.getIdAddress());

        if (userExists.isPresent()) {
            User user = userExists.get();
            user.setUsername(userProfile.getUsername());
            user.setPassword(userProfile.getPassword());

            userProfile = userRepository.save(user);
        } else {
            userProfile = userRepository.save(userProfile);
        }


        if (addressExists.isPresent()) {
            Address address = addressExists.get();
            address.setState(addressProfile.getState());
            address.setCity(addressProfile.getCity());
            address.setNeighborhood(addressProfile.getNeighborhood());
            address.setAddress(addressProfile.getAddress());
            address.setAddressNumber(addressProfile.getAddressNumber());

            addressProfile = addressRepository.save(address);
        } else {
            addressProfile = addressRepository.save(addressProfile);
        }

        profile.setUser(userProfile);
        profile.setAddress(addressProfile);
        return repository.save(profile);
    }



    @Transactional
    public Profile save(Profile profile) {
        userValidate.save(profile.getUser());
        profileValidate.save(profile);

        profile.setBalance(BigDecimal.valueOf(0.0));
        User userSave = userRepository.save(profile.getUser());
        Address addressSave = addressRepository.save(profile.getAddress());

        profile.setUser(userSave);
        profile.setAddress(addressSave);
        return repository.save(profile);
    }
}

