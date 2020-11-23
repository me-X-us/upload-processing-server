package com.mexus.homeleisure.upload.api.service;

import com.mexus.homeleisure.upload.api.exception.CantCreateFileDirectoryException;
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
import java.util.StringTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  private final Path fileLocation;
  private final String POSE_BASE_URL;
  private final String SHAPE_BASE_URL;
  private final RestTemplate restTemplate;
  private final KeyPointsRepository keyPointsRepository;

  @Autowired
  public FileService(FileConfig config, KeyPointsRepository keyPointsRepository,
                     RestTemplate restTemplate) {
    this.fileLocation = Paths.get(config.getUploadDir()).toAbsolutePath().normalize();
    this.POSE_BASE_URL = config.getPoseEstimationServerUrl();
    this.SHAPE_BASE_URL = config.getShapeEstimationServerUrl();
    this.keyPointsRepository = keyPointsRepository;
    this.restTemplate = restTemplate;
    try {
      Files.createDirectories(this.fileLocation);
    } catch (Exception e) {
      throw new CantCreateFileDirectoryException(this.fileLocation.toString(), e);
    }
  }

  public String storeFile(MultipartFile file, long trainingId) {
    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    StringTokenizer tockens = new StringTokenizer(fileName);
    tockens.nextToken(".");
    fileName = trainingId + "." + tockens.nextToken();
    try {
      Files.copy(file.getInputStream(), fileLocation.resolve(fileName),
          StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      throw new FileUploadException(fileName, e);
    }
    Frames frames = restTemplate
        .getForObject(POSE_BASE_URL + "/?video_path=./video/"+trainingId+".mp4", Frames.class);
    keyPointsRepository.saveAll(mapKeyPoints(trainingId, frames));
    restTemplate
        .getForObject(SHAPE_BASE_URL + "/?video_path=./video/"+trainingId+".mp4", String.class);
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
