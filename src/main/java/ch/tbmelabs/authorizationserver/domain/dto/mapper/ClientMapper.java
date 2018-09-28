package ch.tbmelabs.authorizationserver.domain.dto.mapper;

import ch.tbmelabs.authorizationserver.ApplicationContextHolder;
import ch.tbmelabs.authorizationserver.domain.Client;
import ch.tbmelabs.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import ch.tbmelabs.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import ch.tbmelabs.authorizationserver.domain.dto.AuthorityDTO;
import ch.tbmelabs.authorizationserver.domain.dto.ClientDTO;
import ch.tbmelabs.authorizationserver.domain.dto.GrantTypeDTO;
import ch.tbmelabs.authorizationserver.domain.dto.ScopeDTO;
import ch.tbmelabs.authorizationserver.service.domain.AuthorityService;
import ch.tbmelabs.authorizationserver.service.domain.GrantTypeService;
import ch.tbmelabs.authorizationserver.service.domain.ScopeService;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.util.Strings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {GrantTypeMapper.class, GrantTypeService.class,
  AuthorityMapper.class, AuthorityService.class, ScopeMapper.class, ScopeService.class})
public interface ClientMapper extends EntityMapper<Client, ClientDTO> {

  GrantTypeMapper grantTypeMapper = Mappers.getMapper(GrantTypeMapper.class);
  AuthorityMapper authorityMapper = Mappers.getMapper(AuthorityMapper.class);
  ScopeMapper scopeMapper = Mappers.getMapper(ScopeMapper.class);

  @Override
  @Mapping(source = "redirectUri", target = "redirectUris")
  ClientDTO toDto(Client entity);

  // @formatter:off
  @Override
  @Mapping(source = "redirectUris", target = "redirectUri")
  @Mapping(target = "grantTypes", expression = "java("
    + "  grantTypesToGrantTypeAssociations(dto.getGrantTypes(), client)"
    + ")")
  @Mapping(target = "authorities", expression = "java("
    + "  authoritiesToAuthorityAssociations(dto.getAuthorities(), client)"
    + ")")
  @Mapping(target = "scopes", expression = "java("
    + "  scopesToScopeAssociations(dto.getScopes(), client)"
    + ")")
  // @formatter:on
  Client toEntity(ClientDTO dto);

  // @formatter:off
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "redirectUri", source = "redirectUris")
  @Mapping(target = "grantTypes", expression = "java("
    + "  grantTypesToGrantTypeAssociations(updated.getGrantTypes(), existing)"
    + ")")
  @Mapping(target = "authorities", expression = "java("
    + "  authoritiesToAuthorityAssociations(updated.getAuthorities(), existing)"
    + ")")
  @Mapping(target = "scopes", expression = "java("
    + "  scopesToScopeAssociations(updated.getScopes(), existing)"
    + ")")
  // @formatter:on
  Client updateClientFromClientDto(ClientDTO updated, @MappingTarget Client existing);

  default String[] redirectUriToRedirectUris(String redirectUri) {
    return redirectUri.split(Client.REDIRECT_URI_SPLITTERATOR);
  }

  default String redirectUrisToRedirectUri(String[] redirectUris) {
    return Strings.join(Arrays.asList(redirectUris), Client.REDIRECT_URI_SPLITTERATOR.charAt(0));
  }

  default Set<GrantTypeDTO> grantTypeAssociationsToGrantTypes(
    Set<ClientGrantTypeAssociation> grantTypes) {
    return grantTypes.stream().map(ClientGrantTypeAssociation::getGrantType)
      .map(grantTypeMapper::toDto).collect(Collectors.toSet());
  }

  default Set<ClientGrantTypeAssociation> grantTypesToGrantTypeAssociations(
    Set<GrantTypeDTO> grantTypes, @MappingTarget Client client) {
    return grantTypes.stream()
      .map(grantType -> new ClientGrantTypeAssociation(client,
        ApplicationContextHolder.getApplicationContext().getBean(GrantTypeService.class)
          .findByName(grantType.getName())))
      .collect(Collectors.toSet());
  }

  default Set<AuthorityDTO> authorityAssociationsToAuthorities(
    Set<ClientAuthorityAssociation> grantedAuthorities) {
    return grantedAuthorities.stream().map(ClientAuthorityAssociation::getAuthority)
      .map(authorityMapper::toDto).collect(Collectors.toSet());
  }

  default Set<ClientAuthorityAssociation> authoritiesToAuthorityAssociations(
    Set<AuthorityDTO> authorities, @MappingTarget Client client) {
    return authorities.stream()
      .map(authority -> new ClientAuthorityAssociation(client,
        ApplicationContextHolder.getApplicationContext().getBean(AuthorityService.class)
          .findByName(authority.getName())))
      .collect(Collectors.toSet());
  }

  default Set<ScopeDTO> scopeAssociationsToScopes(Set<ClientScopeAssociation> scopes) {
    return scopes.stream().map(ClientScopeAssociation::getScope).map(scopeMapper::toDto)
      .collect(Collectors.toSet());
  }

  default Set<ClientScopeAssociation> scopesToScopeAssociations(Set<ScopeDTO> scopes,
    @MappingTarget Client client) {
    return scopes.stream()
      .map(scope -> new ClientScopeAssociation(client, ApplicationContextHolder
        .getApplicationContext().getBean(ScopeService.class).findByName(scope.getName())))
      .collect(Collectors.toSet());
  }
}
