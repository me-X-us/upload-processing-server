package com.mexus.homeleisure.upload.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "frames"
})
public class Frames {

  @JsonProperty("frames")
  private List<Frame> frames = null;

  @JsonProperty("frames")
  public List<Frame> getFrames() {
    return frames;
  }

  @JsonProperty("frames")
  public void setFrames(List<Frame> frames) {
    this.frames = frames;
  }
}
