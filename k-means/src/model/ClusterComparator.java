package model;

import java.util.Comparator;

public class ClusterComparator implements Comparator<Cluster> {

  @Override
  public int compare(Cluster o1, Cluster o2) {
    if (o1.getCentroid().getX() > o2.getCentroid().getX()) {
      return 1;
    } else if (o1.getCentroid().getX() < o2.getCentroid().getX()) {
      return -1;
    } else {
      return 0;
    }
  }
}
