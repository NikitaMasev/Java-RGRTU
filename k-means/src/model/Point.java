package model;

public class Point extends RawPoint{

  private int cluster=-1;
  private double distance = Double.MAX_VALUE;

  public Point(double x, double y) {
    super(x,y);
  }

  public double getX() {
    return super.x;
  }

  public double getY() {
    return super.y;
  }

  public double getDistance() {
    return distance;
  }

  public int getCluster() {
    return cluster;
  }

  public void setCluster(int cluster) {
    this.cluster = cluster;
  }

  public int getSymptoms() {
    return (super.getClass().getDeclaredFields().length);
  }
  public void setX(double x) {
    super.x = x;
  }

  public void setY(double y) {
    super.y = y;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }

}
