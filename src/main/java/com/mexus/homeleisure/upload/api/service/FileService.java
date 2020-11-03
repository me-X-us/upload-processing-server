package com.mexus.homeleisure.upload.api.service;

import com.mexus.homeleisure.upload.api.exception.CantCreateFileDirectoryException;
import com.mexus.homeleisure.upload.api.exception.FileNameException;
import com.mexus.homeleisure.upload.api.exception.FileUploadException;
import com.mexus.homeleisure.upload.api.model.Frames;
import com.mexus.homeleisure.upload.configs.FileConfig;
import java.awt.SystemTray;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  private final Path fileLocation;
  private final String BASE_URL;
  private final RestTemplate restTemplate = new RestTemplate();

  @Autowired
  public FileService(FileConfig config) {
    this.fileLocation = Paths.get(config.getUploadDir()).toAbsolutePath().normalize();
    this.BASE_URL = config.getPoseEstimationServerUrl();
    try {
      Files.createDirectories(this.fileLocation);
    } catch (Exception e) {
      throw new CantCreateFileDirectoryException(this.fileLocation.toString(), e);
    }
  }

  public String storeFile(MultipartFile file) {
    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    try {
      fileName = checkFileNameAndExtension(file);
      Files.copy(file.getInputStream(), fileLocation.resolve(fileName),
          StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      throw new FileUploadException(fileName, e);
    }
    Frames frames = restTemplate
        .getForObject(BASE_URL + "/?video_path=" + fileLocation + fileName, Frames.class);
    System.out.println(frames);
    return fileName;
  }

  private String checkFileNameAndExtension(MultipartFile file) {
    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    if (fileName.contains("..")) {
      throw new FileNameException(fileName);
    }
    return fileName;
  }
}
