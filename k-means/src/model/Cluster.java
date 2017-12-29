package model;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
  private int num;
  private RawPoint centroid;

  public Cluster(int num) {
    this.num = num;
  }

  public int getNum() {
    return num;
  }

  public RawPoint getCentroid() {
    return centroid;
  }

  public void setCentroid(RawPoint centroid) {
    this.centroid = centroid;
  }

}
