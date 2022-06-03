package com.$company$.$project$.$context$.ports.application;

import static com.$company$.$project$.commons.ports.application.BaseController.BASE_PATH;
import static io.restassured.module.webtestclient.RestAssuredWebTestClient.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.$company$.$project$.$context$.commons.model.$Entity$CreateDto;
import com.$company$.$project$.$context$.commons.model.$Entity$DeltaDto;
import com.$company$.$project$.$context$.commons.model.$Entity$Dto;
import com.$company$.$project$.$context$.domain.$Entity$;
import com.$company$.$project$.$context$.domain.exceptions.$Entity$NotFoundException;
import com.$company$.$project$.$context$.domain.exceptions.Duplicate$Entity$Exception;
import com.$company$.$project$.$context$.domain.exceptions.Invalid$Entity$Exception;
import com.$company$.$project$.$context$.ports.application.mapper.$Entity$Mapper;
import com.$company$.$project$.$context$.ports.application.mapper.$Entity$MapperImpl;
import com.$company$.$project$.$context$.services.application.$Entity$ApplicationService;
import com.$company$.$project$.$context$.services.domain.$Entity$Service;
import com.$company$.$project$.commons.ports.application.RestExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.module.webtestclient.specification.WebTestClientRequestSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class $Entity$ControllerTest {

  private final $Entity$Mapper mapper = new $Entity$MapperImpl();
  private final $Entity$Service $entity$Service = mock($Entity$Service.class);
  private final $Entity$ApplicationService $entity$ApplicationService = mock(
      $Entity$ApplicationService.class);
  private final $Entity$Controller $entity$Controller = new $Entity$Controller(
      $entity$Service, $entity$ApplicationService, mapper);

  private WebTestClientRequestSpecification givenController() {
    return given().standaloneSetup($entity$Controller,
        new RestExceptionHandler(new ObjectMapper())).contentType(
        ContentType.JSON);
  }

  public $Entity$ createValid$Entity$() {
    return $Entity$.builder().build();
  }

  public $Entity$CreateDto createValid$Entity$CreateDto() {
    return new $Entity$CreateDto();
  }

  public $Entity$DeltaDto createValid$Entity$DeltaDto() {
    return new $Entity$DeltaDto();
  }

  @Test
  public void gettingAll$EntityPlural$ShouldReturn200() {
    // GIVEN
    when($entity$Service.getAll$EntityPlural$()).thenReturn(Flux.just());

    // THEN
    givenController().when()
        .get(BASE_PATH + "/$apicontext$/")
        .then().status(HttpStatus.OK).extract().as($Entity$Dto[].class);
  }

  @Test
  public void creating$Entity$ShouldReturn201() {
    // GIVEN
    var $entity$Id = "$entity$Id";
    var $entity$CreateDto = createValid$Entity$CreateDto();
    var $entity$ = createValid$Entity$();
    when($entity$Service.create$Entity$(any())).thenReturn(Mono.just($entity$));

    // THEN
    givenController()
        .body($entity$CreateDto).when()
        .post(BASE_PATH + "/$apicontext$/")
        .then().status(HttpStatus.CREATED)
        .header("location", BASE_PATH + "/$apicontext$/" + $entity$Id);
  }

  @Test
  public void creatingInvalid$Entity$ShouldReturn400() {
    // GIVEN
    var $entity$CreateDto = createValid$Entity$CreateDto();
    var $entity$ = createValid$Entity$();
    when($entity$Service.create$Entity$(any())).thenReturn(
        Mono.error(new Invalid$Entity$Exception($entity$)));

    // THEN
    givenController()
        .body($entity$CreateDto).when()
        .post(BASE_PATH + "/$apicontext$/")
        .then().status(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void creatingDuplicate$Entity$ShouldReturn400() {
    // GIVEN
    var $entity$CreateDto = createValid$Entity$CreateDto();
    when($entity$Service.create$Entity$(any())).thenReturn(
        Mono.error(new Duplicate$Entity$Exception()));

    // THEN
    givenController()
        .body($entity$CreateDto).when()
        .post(BASE_PATH + "/$apicontext$/")
        .then().status(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void getting$Entity$ShouldReturn200() {
    // GIVEN
    var $entity$Id = "$entity$Id";
    var $entity$ = createValid$Entity$();
    when($entity$Service.get$Entity$($entity$Id)).thenReturn(Mono.just($entity$));

    // THEN
    givenController().when()
        .get(BASE_PATH + "/$apicontext$/" + $entity$Id)
        .then().status(HttpStatus.OK).extract().as($Entity$Dto.class);
  }

  @Test
  public void gettingMissing$Entity$ShouldReturn404() {
    // GIVEN
    var $entity$Id = "$entity$Id";
    when($entity$Service.get$Entity$($entity$Id)).thenReturn(
        Mono.error(new $Entity$NotFoundException($entity$Id)));

    // THEN
    givenController().when()
        .get(BASE_PATH + "/$apicontext$/" + $entity$Id)
        .then().status(HttpStatus.NOT_FOUND);
  }

  @Test
  public void updating$Entity$ShouldReturn200() {
    // GIVEN
    var $entity$Id = "$entity$Id";
    var $entity$DeltaDto = createValid$Entity$DeltaDto();
    var $entity$ = createValid$Entity$();
    when($entity$ApplicationService.update$Entity$(any(), any())).thenReturn(
        Mono.just($entity$));

    // THEN
    givenController()
        .body($entity$DeltaDto).when()
        .patch(BASE_PATH + "/$apicontext$/" + $entity$Id)
        .then().status(HttpStatus.OK).extract().as($Entity$Dto.class);
  }

  @Test
  public void updating$Entity$ToInvalidShouldReturn400() {
    // GIVEN
    var $entity$Id = "$entity$Id";
    var $entity$DeltaDto = createValid$Entity$DeltaDto();
    var $entity$ = createValid$Entity$();
    when($entity$ApplicationService.update$Entity$(any(), any())).thenReturn(
        Mono.error(new Invalid$Entity$Exception($entity$)));

    // THEN
    givenController()
        .body($entity$DeltaDto).when()
        .patch(BASE_PATH + "/$apicontext$/" + $entity$Id)
        .then().status(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void updating$Entity$ToDuplicateShouldReturn400() {
    // GIVEN
    var $entity$Id = "$entity$Id";
    var $entity$DeltaDto = createValid$Entity$DeltaDto();
    when($entity$ApplicationService.update$Entity$(any(), any())).thenReturn(
        Mono.error(new Duplicate$Entity$Exception()));

    // THEN
    givenController()
        .body($entity$DeltaDto).when()
        .patch(BASE_PATH + "/$apicontext$/" + $entity$Id)
        .then().status(HttpStatus.BAD_REQUEST);
  }
}