package com.mexus.homeleisure.upload.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "frameNo",
    "keyPoint"
})
public class Frame {

  @JsonProperty("frameNo")
  private Integer frameNo;
  @JsonProperty("keyPoint")
  private List<KeyPoint> keyPoints = null;

  @JsonProperty("frameNo")
  public Integer getFrameNo() {
    return frameNo;
  }

  @JsonProperty("frameNo")
  public void setFrameNo(Integer frameNo) {
    this.frameNo = frameNo;
  }

  @JsonProperty("keyPoint")
  public List<KeyPoint> getKeyPoints() {
    return keyPoints;
  }

  @JsonProperty("keyPoint")
  public void setKeyPoints(List<KeyPoint> keyPoints) {
    this.keyPoints = keyPoints;
  }
}
