package com.$company$.$project$.$context$.domain.exceptions;

import com.$company$.$project$.$context$.domain.$Entity$;
import com.$company$.$project$.commons.domain.BaseException;
import org.springframework.http.HttpStatus;

public class Invalid$Entity$Exception extends BaseException {

  //TODO: translation
  public Invalid$Entity$Exception($Entity$ $entity$) {
    super("INVALID_$ENTITY$", "The given $Entity$ was invalid: " + $entity$,
        HttpStatus.BAD_REQUEST);
  }

}