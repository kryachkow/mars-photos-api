package com.example.marsphotosapi.controller;


import com.example.marsphotosapi.service.MarsPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@RequestMapping("/marsPhotos")
public class MarsRestController {

  private final MarsPhotoService marsPhotoService;


  @GetMapping(path = "/getBiggestPhoto", produces = MediaType.IMAGE_JPEG_VALUE)
  public byte[] getBiggestPhoto(@RequestParam("sol") int sol) {
    return marsPhotoService.getBiggestPhoto(sol);
  }
}
