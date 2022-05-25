package com.$company$.$project$.$context$.services.domain;


import com.$company$.$project$.$context$.domain.$Entity$;
import com.$company$.$project$.$context$.domain.exceptions.$Entity$NotFoundException;
import com.$company$.$project$.$context$.domain.exceptions.Duplicate$Entity$Exception;
import com.$company$.$project$.$context$.ports.domain.$Entity$Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class $Entity$Service {

  private $Entity$Repository $entity$Repository;

  public Mono<$Entity$> create$Entity$($Entity$ partial$Entity$) {
    return validate(partial$Entity$)
        .flatMap($entity$Repository::insert);
  }

  public Mono<$Entity$> validate($Entity$ $entity$) {
    //TODO
    return validateUniqueness($entity$);
  }

  public Mono<$Entity$> validateUniqueness($Entity$ $entity$) {
    return similar$EntityPlural$Exist($entity$).map(duplicatesExist ->
        {
          if (duplicatesExist) {
            throw new Duplicate$Entity$Exception();
          }
          return $entity$;
        }
    );
  }

  private Mono<Boolean> similar$EntityPlural$Exist($Entity$ $entity$) {
    //TODO
    //$entity$Repository.existsBy...
    return Mono.just(false);
  }

  public Mono<$Entity$> get$Entity$ById(String id) {
    return $entity$Repository.findById(id)
        .switchIfEmpty(Mono.error(new $Entity$NotFoundException(id)));
  }

  public Flux<$Entity$> getAll$EntityPlural$() {
    return $entity$Repository.findAll();
  }

  public Mono<$Entity$> save$Entity$($Entity$ $entity$){
    return $entity$Repository.save($entity$);
  }

}