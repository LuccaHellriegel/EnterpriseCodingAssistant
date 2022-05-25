package com.$company$.$project$.$context$.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("#{@dbPrefix.collection('$context$_$entityPlural$')}")
public class $Entity$ {

  @Id
  private String id;

}