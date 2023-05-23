package com.example.marsphotosapi.service;

import com.example.marsphotosapi.exception.PhotoException;
import com.example.marsphotosapi.model.Photo;
import com.example.marsphotosapi.model.Photos;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class MarsPhotoService {

  private static final String SOL_PARAM = "sol";
  private static final String API_KEY_PARAM = "api_key";
  private static final String URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos";
  private static final String API_KEY = "60j2k2VehMbetwtUgp9iKRnCbCeoH0oRbsZFsDYB";
  private static final String LOCATION_HEADER_KEY = "Location";

  private final RestTemplate restTemplate;

  @Cacheable(cacheNames = "largest-photo")
  public byte[] getBiggestPhoto(int sol) {
    return getPhotoList(sol)
        .getPhotoList()
        .parallelStream()
        .peek(this::getPhotoLength)
        .max(Comparator.comparing(Photo::getImgSize))
        .map(this::getPhotoBytes)
        .orElseThrow(
            () -> new PhotoException("Couldn't obtain the photo")
        );
  }


  private Photos getPhotoList(int sol) {
    String urlTemplate = UriComponentsBuilder.fromHttpUrl(URL)
        .queryParam(SOL_PARAM, String.valueOf(sol))
        .queryParam(API_KEY_PARAM, API_KEY)
        .encode()
        .toUriString();
    return restTemplate.getForObject(urlTemplate, Photos.class);
  }

  private void getPhotoLength(Photo photo) {
    HttpHeaders headers = restTemplate.headForHeaders(photo.getImgSrc());
    if (headers.containsKey(LOCATION_HEADER_KEY)) {
      photo.setImgSrc(headers.get(LOCATION_HEADER_KEY).get(0));
      getPhotoLength(photo);
      return;
    }
    photo.setImgSize(headers.getContentLength());
  }

  private byte[] getPhotoBytes(Photo photo) {
    return (restTemplate.getForObject(photo.getImgSrc(), byte[].class));
  }

}
