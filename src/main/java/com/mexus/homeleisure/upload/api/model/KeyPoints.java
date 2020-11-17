package com.mexus.homeleisure.upload.api.model;

import com.mexus.homeleisure.upload.api.model.dto.KeyPoint;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class KeyPoints {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long KeyPointId;

  private long trainingId;
  private int frameNo;
  private double y;
  private double x;
  private String part;
  private double score;

  public KeyPoints(long trainingId, int frameNo, KeyPoint keypoint){
    this.trainingId = trainingId;
    this.frameNo = frameNo;
    this.x = keypoint.getX();
    this.y = keypoint.getY();
    this.part = keypoint.getPart();
    this.score = keypoint.getScore();
  }
}
