package io.github.alancavalcante_dev.codefreelaapi.service;


import io.github.alancavalcante_dev.codefreelaapi.dto.StateBusiness;
import io.github.alancavalcante_dev.codefreelaapi.dto.businesproject.BusinessProjectInsertDTO;
import io.github.alancavalcante_dev.codefreelaapi.mapperstruct.BusinessProjectMapper;
import io.github.alancavalcante_dev.codefreelaapi.model.BusinessProject;
import io.github.alancavalcante_dev.codefreelaapi.model.BusinessProjectProfile;
import io.github.alancavalcante_dev.codefreelaapi.model.Profile;
import io.github.alancavalcante_dev.codefreelaapi.repository.BusinessProjectProfileRepository;
import io.github.alancavalcante_dev.codefreelaapi.repository.BusinessProjectRepository;
import io.github.alancavalcante_dev.codefreelaapi.repository.ProfileRepository;
import io.github.alancavalcante_dev.codefreelaapi.validate.BusinessProjectValidate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class BusinessProjectService {

    @Autowired
    private BusinessProjectRepository bussinesProjectRepository;

    @Autowired
    private BusinessProjectValidate businessProjectValidate;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BusinessProjectMapper businessProjectMapper;


    public List<BusinessProject> getAllBusinessProject() {
        return this.bussinesProjectRepository.findAll();
    }

    public Page<BusinessProject> findAllWithPage(Specification<BusinessProject> spec, Pageable page){
        return this.bussinesProjectRepository.findAll(spec, page);
    }

    public Optional<BusinessProject> findyByIdBusinessProject(UUID uuid) {
        return bussinesProjectRepository.findById(uuid);
    }


    @Transactional
    public BusinessProject save(BusinessProjectInsertDTO request) throws RuntimeException {
        Optional<Profile> client = profileRepository.findById(UUID.fromString(request.getIdProfile()));
        if (client.isEmpty()) { throw new RuntimeException("Perfil não encontrado");}

        BusinessProject projectMapping = businessProjectMapper.insertToEntity(request);
        projectMapping.setProfile(client.get());

        businessProjectValidate.save(projectMapping);
        BusinessProject projectSave = bussinesProjectRepository.save(projectMapping);

        BusinessProjectProfile profile = new BusinessProjectProfile();
        profile.setBusinessProject(projectSave);
        projectSave.getProfiles().add(profile);

        return bussinesProjectRepository.save(projectSave);

    }

    public BusinessProject update(BusinessProject project) {
        return bussinesProjectRepository.save(project);
    }

    public void delete(BusinessProject project) {
        bussinesProjectRepository.delete(project);
    }
}
