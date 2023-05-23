package com.example.marsphotosapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class Photos {

  @JsonProperty("photos")
  private List<Photo> photoList;

}
