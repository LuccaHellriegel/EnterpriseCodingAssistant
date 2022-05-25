package com.$company$.$project$.$context$.ports.application;

import com.$company$.$project$.$context$.commons.model.$Entity$CreateDto;
import com.$company$.$project$.$context$.commons.model.$Entity$DeltaDto;
import com.$company$.$project$.$context$.commons.model.$Entity$Dto;
import com.$company$.$project$.$context$.commons.ports.application.$api$;
import com.$company$.$project$.$context$.ports.application.mapper.$Entity$Mapper;
import com.$company$.$project$.$context$.services.application.$Entity$ApplicationService;
import com.$company$.$project$.$context$.services.domain.$Entity$Service;
import com.$company$.$project$.commons.ports.application.BaseController;
import com.$company$.$project$.commons.security.Permission.Entitlements;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class $Entity$Controller extends BaseController implements $api$ {

  private $Entity$Service $entity$Service;
  private $Entity$ApplicationService $entity$ApplicationService;
  private $Entity$Mapper $entity$Mapper;

  @Override
  @PreAuthorize("hasAnyAuthority('" + Entitlements.CDS_ADMIN + "')") //TODO
  public Mono<ResponseEntity<$Entity$Dto>> create$Entity$(Mono<$Entity$CreateDto> $entity$CreateDto,
      ServerWebExchange exchange) {
    return $entity$CreateDto
        .map($entity$Mapper::fromDto)
        .flatMap($entity$Service::create$Entity$)
        .map($entity$Mapper::toDto)
        .map(dto -> ResponseEntity.created(
            URI.create(BASE_PATH + "/$entityPlural$/" + dto.getId())).body(dto));
  }

  @Override
  @PreAuthorize("hasAnyAuthority('" + Entitlements.CDS_ADMIN + "')") //TODO
  public Mono<ResponseEntity<Flux<$Entity$Dto>>> getAll$EntityPlural$(ServerWebExchange exchange) {
    return Mono.just(
        ResponseEntity.ok($entity$Service.getAll$EntityPlural$().map($entity$Mapper::toDto)));
  }

  @Override
  @PreAuthorize("hasAnyAuthority('" + Entitlements.CDS_ADMIN + "')") //TODO
  public Mono<ResponseEntity<$Entity$Dto>> get$Entity$(String $entity$Id,
      ServerWebExchange exchange) {
    return $entity$Service
        .get$Entity$ById($entity$Id)
        .map($entity$Mapper::toDto)
        .map(ResponseEntity::ok);
  }

  @Override
  @PreAuthorize("hasAnyAuthority('" + Entitlements.CDS_ADMIN + "')") //TODO
  public Mono<ResponseEntity<$Entity$Dto>> update$Entity$(String $entity$Id,
      Mono<$Entity$DeltaDto> $entity$DeltaDto, ServerWebExchange exchange) {
    return $entity$DeltaDto
        .flatMap(delta -> $entity$ApplicationService.update$Entity$(delta, $entity$Id))
        .map($entity$Mapper::toDto)
        .map(ResponseEntity::ok);
  }
}