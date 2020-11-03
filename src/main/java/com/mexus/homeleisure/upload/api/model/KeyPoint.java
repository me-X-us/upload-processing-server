package com.mexus.homeleisure.upload.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "y",
    "x",
    "part",
    "score"
})
public class KeyPoint {

  @JsonProperty("y")
  private Double y;
  @JsonProperty("x")
  private Double x;
  @JsonProperty("part")
  private String part;
  @JsonProperty("score")
  private Double score;

  @JsonProperty("y")
  public Double getY() {
    return y;
  }

  @JsonProperty("y")
  public void setY(Double y) {
    this.y = y;
  }

  @JsonProperty("x")
  public Double getX() {
    return x;
  }

  @JsonProperty("x")
  public void setX(Double x) {
    this.x = x;
  }

  @JsonProperty("part")
  public String getPart() {
    return part;
  }

  @JsonProperty("part")
  public void setPart(String part) {
    this.part = part;
  }

  @JsonProperty("score")
  public Double getScore() {
    return score;
  }

  @JsonProperty("score")
  public void setScore(Double score) {
    this.score = score;
  }
}
