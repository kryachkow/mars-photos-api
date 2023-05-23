package com.example.marsphotosapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Photo {

  @JsonProperty("img_src")
  private String imgSrc;
  private long imgSize;
  private byte[] img;

}
