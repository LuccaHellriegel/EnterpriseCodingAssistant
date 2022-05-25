package com.$company$.$project$.$context$.services.application;

import static com.$company$.$project$.commons.Utils.applyDelta;

import com.$company$.$project$.$context$.commons.model.$Entity$DeltaDto;
import com.$company$.$project$.$context$.domain.$Entity$;
import com.$company$.$project$.$context$.services.domain.$Entity$Service;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class $Entity$ApplicationService {

  private final $Entity$Service $entity$Service;

  public Mono<$Entity$> update$Entity$($Entity$DeltaDto delta, String $entity$Id) {
    return validateDelta(delta)
        .then($entity$Service.get$Entity$($entity$Id))
        .flatMap($entity$ -> apply$Entity$Delta($entity$, delta))
        //TODO
        .flatMap($entity$Service::validate)
        .flatMap($entity$Service::save$Entity$);
  }

  private Mono<$Entity$DeltaDto> validateDelta($Entity$DeltaDto delta) {
    //TODO
    return Mono.just(delta);
  }

  private Mono<$Entity$> apply$Entity$Delta($Entity$ $entity$, $Entity$DeltaDto delta) {
    //TODO
    applyDelta($entity$, delta, List.of());
    return Mono.just($entity$);
  }
}