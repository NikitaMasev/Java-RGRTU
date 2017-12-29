package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import launcher.Main;
import model.Cluster;
import model.Manager;
import model.Point;

public class Controller implements Initializable, EventHandler<ActionEvent> {

  @FXML
  public Button btnOpenFile;
  @FXML
  public Button btnStep;
  @FXML
  public Button btnRun;
  @FXML
  public Button btnClear;
  @FXML
  public TextField textFieldClusters;
  public ScatterChart<Number, Number> scatterChart;
  @FXML
  public TextArea textInfo;
  @FXML
  public HBox topHbox;

  private Manager manager;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    scatterChart = new ScatterChart<Number, Number>(new NumberAxis(), new NumberAxis());
    topHbox.setAlignment(Pos.BASELINE_RIGHT);
    topHbox.getChildren().addAll(scatterChart);
    manager = new Manager(Main.primaryStage);
    btnStep.setDisable(true);
    btnRun.setDisable(true);
  }

  private void drawCentroids() {
    Series<Number, Number> series = new Series<>();
    series.setName("Centroids");
    for (Cluster cluster : manager.getListClusters()) {
      series.getData().add(new Data<>(cluster.getCentroid().getX(), cluster.getCentroid().getY()));
    }
    scatterChart.getData().add(series);
  }

  private void drawPoints(List<Point> pointList) {
    textInfo.setText("");
    for (Cluster cluster : manager.getListClusters()) {
      XYChart.Series series = new XYChart.Series();
      series.setName(cluster.getNum() + " Cluster");
      textInfo.setText(textInfo.getText() + "\n" + cluster.getNum() + " cluster: " + "\n"
          + "{Centroid : " + "\n" + cluster.getCentroid().getX() + " " + cluster.getCentroid()
          .getY() + "}" + "\n");
      for (Point point : pointList) {
        if (cluster.getNum() == point.getCluster()) {
          series.getData().add(new Data<>(point.getX(), point.getY()));
          textInfo.setText(textInfo.getText() + point.getX() + " " + point.getY() + "\n");
        }
      }
      scatterChart.getData().add(series);
    }
  }

  private void printRawData(List<Point> pointList) {
    for (Point point : pointList) {
      textInfo.setText(textInfo.getText() + "\n" + point.getX() + " " + point.getY() + "\n");
    }
  }

  private void printInfo(String info) {
    textInfo.setText(info);
  }

  @Override
  public void handle(ActionEvent event) {
    if (event.getSource() == btnOpenFile) {
      try {
        List<Point> pointList = manager.getLoadedData();
        manager.initStartsClusters(textFieldClusters.getText());
        printRawData(pointList);
        drawPoints(pointList);
        drawCentroids();
        btnStep.setDisable(false);
        btnRun.setDisable(false);
      } catch (Exception e) {
        printInfo(e.toString().split(":")[1]);
      }
    }
    if (event.getSource() == btnRun) {
      try {
        scatterChart.getData().clear();
        List<Point> pointList = manager
            .runClustering(textFieldClusters.getText(), Integer.MAX_VALUE);
        drawPoints(pointList);
        drawCentroids();
        btnRun.setDisable(true);
      } catch (Exception e) {
        System.out.println(e.toString());
        printInfo(e.toString().split(":")[1]);
      }
    }
    if (event.getSource() == btnStep) {
      try {
        scatterChart.getData().clear();
        List<Point> pointList = manager.runClustering(textFieldClusters.getText(), 1);
        textInfo.setText("");
        drawPoints(pointList);
        drawCentroids();
      } catch (Exception e) {
        printInfo(e.toString().split(":")[1]);
      }
    }

    if (event.getSource() == btnClear) {
      clearAll();
      btnRun.setDisable(false);
    }
  }

  private void clearAll() {
    textInfo.setText("");
    textFieldClusters.setText("");
    manager.clear();
    scatterChart.getData().clear();
  }
}
