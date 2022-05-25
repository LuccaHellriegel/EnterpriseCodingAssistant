package com.$company$.$project$.$context$.domain.exceptions;

import com.$company$.$project$.$context$.domain.$Entity$;
import com.$company$.$project$.commons.domain.BaseException;
import org.springframework.http.HttpStatus;

public class Duplicate$Entity$Exception extends BaseException {

  //TODO: translation
  public Duplicate$Entity$Exception() {
    super("DUPLICATE_$ENTITY$", "The given $Entity$ already exists.",
        HttpStatus.BAD_REQUEST);
  }

}