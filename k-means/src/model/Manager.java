package model;

import Constants.ConstanterData;
import Constants.ConstanterError;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import view.FileDialogViewer;

public class Manager {

  private List<Cluster> listClusters;
  private List<Point> listPoints;
  private Stage primaryStage;
  private final String PATTERN_CLUSTERS = "^([0-9]+)$";

  public Manager(Stage primaryStage) {
    this.listClusters = new ArrayList<>();
    this.primaryStage = primaryStage;
  }

  public List<Point> getLoadedData() throws Exception {
    FileDialogViewer fileDialogViewer = new FileDialogViewer(ConstanterData.FILE_EXTENSION_FILTER,
        ConstanterData.FILE_DESCRIBTIONS, this.primaryStage);
    File pointsFile = fileDialogViewer.getFile();
    ReaderFile readerFile = new ReaderFile(pointsFile);
    this.listPoints = readerFile.getListPoints();
    return listPoints;
  }

  private boolean isNumClustersAllowed(String numClusters) {
    Pattern pattern = Pattern.compile(PATTERN_CLUSTERS);
    Matcher matcher = pattern.matcher(numClusters);
    return matcher.matches();
  }

  public void initStartsClusters(String numClusters) throws Exception{
    if (!isNumClustersAllowed(numClusters)) {
      throw new Exception(ConstanterError.ERROR_NUM_CLUSTERS);
    }
    int nClusters = Integer.parseInt(numClusters);
    Random random = new Random();
    int randomPoint = random.nextInt(listPoints.size() - 1);
    for (int i = 0; i < nClusters; i++) {
      listClusters.add(new Cluster(i));
      while (listPoints.get(randomPoint).getCluster() != -1) {
        randomPoint = random.nextInt(listPoints.size() - 1);
      }
      listPoints.get(randomPoint).setCluster(i);
      listClusters.get(i).setCentroid(
          new RawPoint(listPoints.get(randomPoint).getX(), listPoints.get(randomPoint).getY()));
    }
  }

  private double getEuclideanDistance(Point point, Cluster cluster) {
    double euclD = Math.sqrt(Math.pow((point.getX() - cluster.getCentroid().getX()), 2) + Math
        .pow((point.getY() - cluster.getCentroid().getY()), 2));
    return euclD;
  }

  private void computeCentroids() {
    double sumX;
    double sumY;
    int size;
    for (Cluster cluster : listClusters) {
      sumX = 0.0;
      sumY = 0.0;
      size = 0;
      for (Point point : listPoints) {
        if (cluster.getNum() == point.getCluster()) {
          sumX += point.getX();
          sumY += point.getY();
          size++;
        }
      }
      cluster.setCentroid(new RawPoint((sumX / (double) size), sumY / (double) size));
    }
  }

  private double getSquareError() {
    double err = 0.0;
    for (Point point : listPoints) {
      err += Math.pow(point.getDistance(), 2);
    }
    return err;
  }

  public List<Point> runClustering(String numClusters, int epoch)
      throws Exception {
    if (!isNumClustersAllowed(numClusters)) {
      throw new Exception(ConstanterError.ERROR_NUM_CLUSTERS);
    }
    int nClusters = Integer.parseInt(numClusters);
    double oldSquareError = 0.0;
    while (epoch != 0) {
      for (Point point : listPoints) {
        for (Cluster cluster : listClusters) {
          double euclD = getEuclideanDistance(point, cluster);
          if (point.getDistance() > euclD) {
            point.setDistance(euclD);
            point.setCluster(cluster.getNum());
          }
        }
      }
      computeCentroids();
      epoch--;
      if (oldSquareError == getSquareError()) { break; } else {
        oldSquareError = getSquareError();
      }
    }
    return listPoints;
  }

  public List<Cluster> getListClusters() {
    return listClusters;
  }

  public void clear() {
    for (Point point : listPoints) {
      point.setDistance(Double.MAX_VALUE);
      point.setCluster(-1);
    }
    listClusters.clear();
  }

}
