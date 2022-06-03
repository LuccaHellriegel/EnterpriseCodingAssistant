package com.$company$.$project$.$context$.services.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.$company$.$project$.$context$.domain.$Entity$;
import com.$company$.$project$.$context$.domain.exceptions.$Entity$NotFoundException;
import com.$company$.$project$.$context$.domain.exceptions.Duplicate$Entity$Exception;
import com.$company$.$project$.$context$.domain.exceptions.Invalid$Entity$Exception;
import com.$company$.$project$.$context$.ports.domain.$Entity$Repository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class $Entity$ServiceTest {

  private final $Entity$Repository $entity$Repository = mock($Entity$Repository.class);
  private final $Entity$Service $entity$Service = new $Entity$Service($entity$Repository);

  public void mockGet$Entity$($Entity$ $entity$) {
    when($entity$Repository.findById($entity$.getId())).thenReturn(Mono.just($entity$));
  }

  public void mockSave$Entity$() {
    when($entity$Repository.save(any())).thenAnswer(
        invocation -> Mono.just(invocation.getArguments()[0]));
  }

  public void mockInsert$Entity$() {
    when($entity$Repository.insert(($Entity$) any())).thenAnswer(
        invocation -> Mono.just(invocation.getArguments()[0]));
  }

  @Test
  public void createValid$Entity$ShouldSave() {
    // GIVEN
    //TODO
    var valid$Entity$ = $Entity$.builder().build();
    mockInsert$Entity$();

    // THEN
    StepVerifier.create($entity$Service.create$Entity$(valid$Entity$)).expectNextCount(1)
        .verifyComplete();
    verify($entity$Repository, times(1)).insert(valid$Entity$);
  }

  @Test
  public void createDuplicate$Entity$ShouldError() {
    // GIVEN
    //TODO
    var duplicate$Entity$ = $Entity$.builder().build();

    // THEN
    StepVerifier.create($entity$Service.create$Entity$(duplicate$Entity$)).expectError(
        Duplicate$Entity$Exception.class).verify();
  }

  @Test
  public void createInvalid$Entity$ShouldError() {
    // GIVEN
    //TODO
    var invalid$Entity$ = $Entity$.builder().build();

    // THEN
    StepVerifier.create($entity$Service.create$Entity$(invalid$Entity$)).expectError(
        Invalid$Entity$Exception.class).verify();
  }

  @Test
  public void valid$Entity$ShouldNotError() {
    // GIVEN
    //TODO
    var valid$Entity$ = $Entity$.builder().build();

    // THEN
    StepVerifier.create($entity$Service.validate(valid$Entity$)).expectNextCount(1)
        .verifyComplete();
  }

  @Test
  public void duplicate$Entity$ShouldError() {
    // GIVEN
    //TODO
    var duplicate$Entity$ = $Entity$.builder().build();

    // THEN
    StepVerifier.create($entity$Service.validate(duplicate$Entity$)).expectError(
        Duplicate$Entity$Exception.class).verify();
  }

  @Test
  public void invalid$Entity$ShouldError() {
    // GIVEN
    //TODO
    var duplicate$Entity$ = $Entity$.builder().build();

    // THEN
    StepVerifier.create($entity$Service.validate(duplicate$Entity$)).expectError(
        Invalid$Entity$Exception.class).verify();
  }

  @Test
  public void getMissing$Entity$ShouldError() {
    // GIVEN
    when($entity$Repository.findById("id")).thenReturn(Mono.empty());

    // THEN
    StepVerifier.create($entity$Service.get$Entity$("id")).expectError(
        $Entity$NotFoundException.class).verify();
  }
}