package com.$company$.$project$.$context$.domain.exceptions;


import com.$company$.$project$.commons.domain.BaseException;
import org.springframework.http.HttpStatus;

public class $Entity$NotFoundException extends BaseException {

  public $Entity$NotFoundException(String id) {
    super("$ENTITY$_NOT_FOUND", "The $Entity$ with the id " + id + " was not found.",
        HttpStatus.NOT_FOUND);
  }

}