package com.$company$.$project$.$context$.services.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.$company$.$project$.$context$.commons.model.$Entity$DeltaDto;
import com.$company$.$project$.$context$.domain.$Entity$;
import com.$company$.$project$.$context$.domain.exceptions.Duplicate$Entity$Exception;
import com.$company$.$project$.$context$.domain.exceptions.Invalid$Entity$DeltaException;
import com.$company$.$project$.$context$.domain.exceptions.Invalid$Entity$Exception;
import com.$company$.$project$.$context$.services.domain.$Entity$Service;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class $Entity$ApplicationServiceTest {

  private final $Entity$Service $entity$Service = mock($Entity$Service.class);
  private final $Entity$ApplicationService $entity$ApplicationService = new $Entity$ApplicationService(
      $entity$Service);

  public void mockGet$Entity$($Entity$ $entity$) {
    when($entity$Service.get$Entity$ById($entity$.getId())).thenReturn(Mono.just($entity$));
  }

  public void mockValidity($Entity$ $entity$) {
    when($entity$Service.validate($entity$)).thenReturn(Mono.just($entity$));
  }

  public void mockInvalidity($Entity$ $entity$) {
    when($entity$Service.validate($entity$)).thenReturn(
        Mono.error(new Invalid$Entity$Exception($entity$)));
  }

  public void mockDuplication($Entity$ $entity$) {
    when($entity$Service.validate($entity$)).thenReturn(
        Mono.error(new Duplicate$Entity$Exception()));
  }

  public void mockSave$Entity$() {
    when($entity$Service.save$Entity$(any())).thenAnswer(
        invocation -> Mono.just(invocation.getArguments()[0]));
  }

  @Test
  public void updating$Entity$WithInvalidDeltaShouldError() {
    // GIVEN
    //TODO
    var $entity$ = $Entity$.builder().build();
    var delta = new $Entity$DeltaDto();
    mockGet$Entity$($entity$);

    // THEN
    StepVerifier.create($entity$ApplicationService.update$Entity$(delta, $entity$.getId()))
        .expectError(Invalid$Entity$DeltaException.class);
    verify($entity$Service, times(1)).get$Entity$ById($entity$.getId());
    verify($entity$Service, times(0)).validate($entity$);
    verify($entity$Service, times(0)).save$Entity$($entity$);
  }

  @Test
  public void updating$Entity$ToInvalidShouldError() {
    // GIVEN
    //TODO
    var $entity$ = $Entity$.builder().build();
    var delta = new $Entity$DeltaDto();
    mockGet$Entity$($entity$);
    mockInvalidity($entity$);

    // THEN
    StepVerifier.create($entity$ApplicationService.update$Entity$(delta, $entity$.getId()))
        .expectError(Invalid$Entity$Exception.class);
    verify($entity$Service, times(1)).get$Entity$ById($entity$.getId());
    verify($entity$Service, times(1)).validate($entity$);
    verify($entity$Service, times(0)).save$Entity$($entity$);
  }

  @Test
  public void updating$Entity$ToDuplicateShouldError() {
    // GIVEN
    //TODO
    var $entity$ = $Entity$.builder().build();
    var delta = new $Entity$DeltaDto();
    mockGet$Entity$($entity$);
    mockDuplication($entity$);

    // THEN
    StepVerifier.create($entity$ApplicationService.update$Entity$(delta, $entity$.getId()))
        .expectError(Duplicate$Entity$Exception.class);
    verify($entity$Service, times(1)).get$Entity$ById($entity$.getId());
    verify($entity$Service, times(1)).validate($entity$);
    verify($entity$Service, times(0)).save$Entity$($entity$);
  }

  @Test
  public void updating$Entity$ShouldChangeValues() {
    // GIVEN
    //TODO
    var $entity$ = $Entity$.builder().build();
    var delta = new $Entity$DeltaDto();
    var expected$Entity$ = $Entity$.builder().build();
    mockGet$Entity$($entity$);
    mockValidity($entity$);
    mockSave$Entity$();

    // THEN
    StepVerifier.create($entity$ApplicationService.update$Entity$(delta, $entity$.getId()))
        .expectNext(expected$Entity$).verifyComplete();
    verify($entity$Service, times(1)).get$Entity$ById($entity$.getId());
    verify($entity$Service, times(1)).validate($entity$);
    verify($entity$Service, times(1)).save$Entity$($entity$);
  }

}
