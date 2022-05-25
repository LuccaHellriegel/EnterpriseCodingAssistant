package com.$company$.$project$.$context$.domain.exceptions;

import com.$company$.$project$.$context$.domain.$Entity$;
import com.$company$.$project$.commons.domain.BaseException;
import org.springframework.http.HttpStatus;

public class Invalid$Entity$DeltaException extends BaseException {

  //TODO: translation
  public Invalid$Entity$DeltaException() {
    super("INVALID_$ENTITY$_DELTA", "Tried to update $Entity$ with invalid values.",
        HttpStatus.BAD_REQUEST);
  }

}