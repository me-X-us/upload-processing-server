package com.mexus.homeleisure.upload.api.service;

import com.mexus.homeleisure.upload.api.exception.CantCreateFileDirectoryException;
import com.mexus.homeleisure.upload.api.exception.FileNameException;
import com.mexus.homeleisure.upload.api.exception.FileUploadException;
import com.mexus.homeleisure.upload.api.model.KeyPoints;
import com.mexus.homeleisure.upload.api.model.KeyPointsRepository;
import com.mexus.homeleisure.upload.api.model.dto.Frame;
import com.mexus.homeleisure.upload.api.model.dto.Frames;
import com.mexus.homeleisure.upload.api.model.dto.KeyPoint;
import com.mexus.homeleisure.upload.configs.FileConfig;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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
  private final KeyPointsRepository keyPointsRepository;

  @Autowired
  public FileService(FileConfig config, KeyPointsRepository keyPointsRepository) {
    this.fileLocation = Paths.get(config.getUploadDir()).toAbsolutePath().normalize();
    this.BASE_URL = config.getPoseEstimationServerUrl();
    this.keyPointsRepository = keyPointsRepository;
    try {
      Files.createDirectories(this.fileLocation);
    } catch (Exception e) {
      throw new CantCreateFileDirectoryException(this.fileLocation.toString(), e);
    }
  }

  public String storeFile(MultipartFile file, long trainingId) {
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
    keyPointsRepository.saveAll(mapKeyPoints(trainingId, frames));
    return fileName;
  }

  private String checkFileNameAndExtension(MultipartFile file) {
    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    if (fileName.contains("..")) {
      throw new FileNameException(fileName);
    }
    return fileName;
  }

  private ArrayList<KeyPoints> mapKeyPoints(long trainingId, Frames frames) {
    ArrayList<KeyPoints> keyPoints = new ArrayList<KeyPoints>();
    for (Frame frame : frames.getFrames()) {
      int frameNo = frame.getFrameNo();
      for (KeyPoint keyPoint : frame.getKeyPoints()) {
        keyPoints.add(new KeyPoints(trainingId, frameNo, keyPoint));
      }
    }
    return keyPoints;
  }
}
