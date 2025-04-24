package io.github.alancavalcante_dev.codefreelaapi.mapper;

//import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.businesproject.BusinessProjectInsertDTO;
//import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.businesproject.BusinessProjectResponseDTO;
//import io.github.alancavalcante_dev.codefreelaapi.domain.entity.BusinessProject;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//@Mapper(componentModel = "spring")
//public interface BusinessProjectMapper {
//
//    @Mapping(target = "idBusinessProject", ignore = true)
//    @Mapping(target = "profile", ignore = true)
//    @Mapping(target = "profiles", ignore = true)
//    @Mapping(target = "stateBusiness", constant = "Open")
//    @Mapping(target = "dateCreated", ignore = true)
//    BusinessProject insertToEntity(BusinessProjectInsertDTO dto);
//
//
//    @Mapping(target = "idProfile", source = "profile.idProfile")
//    @Mapping(target = "state", source = "stateBusiness")
//    BusinessProjectResponseDTO entityToResponse(BusinessProject entity);
//}
