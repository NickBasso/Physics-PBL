package base;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.util.Arrays;

public class Main extends Application {

    static Pane root;
    static LineChart<Number, Number> lineChart;
    static TableView<String[]> table;

    @Override
    public void start(Stage primaryStage) throws Exception{
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root.getStylesheets().add("styles.css");

        ScrollPane sp = new ScrollPane();
        sp.setContent(root);

        primaryStage.setTitle("Free Electromagnetic Oscillations");
        primaryStage.setScene(new Scene(sp, 1820, 980));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // displays constant variables from calculations
    public static void displayConstants(    Label InitialVoltageLabel, double V,
                                            Label CapacityLabel, double C,
                                            Label InductanceLabel, Double L,
                                            Label ResistorResistanceLabel, double RR,
                                            Label TotalResistanceLabel, double R,
                                            Label OscillationPeriodLabel, double T,
                                            Label OscillationFrequencyLabel, double W,
                                            Label AttenuationCoefficientLabel, double k
                                       ){
        InitialVoltageLabel.setText(V + " (Volt)");
        CapacityLabel.setText(C + " (Farad)");
        InductanceLabel.setText(L + " (Henry)");
        ResistorResistanceLabel.setText(R + " (Ohm)");
        TotalResistanceLabel.setText(R + " (Ohm)");
        OscillationPeriodLabel.setText(T + " (s)");
        OscillationFrequencyLabel.setText(W + " (Hz)");
        AttenuationCoefficientLabel.setText("" + k);
    }

    // displays changing variables from calculations on each step
    public static void displayResultsTable(String[][] data){
        ObservableList<String[]> list = FXCollections.observableArrayList();

        list.addAll(Arrays.asList(data));
        list.remove(0);//remove titles from data

        table = new TableView<String[]>();
        table.setLayoutX(410);
        table.setLayoutY(74);
        table.setMaxHeight(272);
        table.setPrefWidth(475);
        table.setEditable(false);

        for (int i = 0; i < data[0].length; i++) {
            TableColumn tc = new TableColumn(data[0][i]);
            final int colNo = i;

            tc.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>)
                    p -> new SimpleStringProperty((p.getValue()[colNo])));

            tc.setPrefWidth(90);
            tc.setSortable(false);

            if(i == 0)
                tc.getStyleClass().add("fx-font-column1");
            else
                tc.getStyleClass().add("fx-font-columns");

            table.getColumns().add(tc);
        }

        table.setItems(list);
        root.getChildren().add(table);
    }

    // removes table from scene if exists
    public static void removeTable(){
        root.getChildren().remove(table);
    }

    // displays chart which represents charge dependency of time
    public static void displayChart(String[][] data, int iterations){
        //defining the axes
        final NumberAxis xAxis = new NumberAxis(); // we are gonna plot against time
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time (s)");
        yAxis.setLabel("Charge (coulomb)");

        //creating the line chart with two axis created above
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setLayoutX(100);
        lineChart.setLayoutY(500);
        lineChart.setPrefWidth(1000);
        lineChart.setTitle("Charge (Time)");
        lineChart.setCreateSymbols(false);
        lineChart.getStyleClass().add("chart-series-line");
        lineChart.getStyleClass().add("chart-set-font");
        makeChartZoomable(lineChart);

        //defining a series to display data
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("q(t)");

        for(int i = 1; i < iterations; i++){
            series.getData().add(new XYChart.Data(Double.valueOf(data[i][1]), Double.valueOf(data[i][2])));
        }

        // add series to chart
        lineChart.getData().add(series);

        // add the graph to the root
        root.getChildren().add(lineChart);
    }

    // removes chart from scene if exists
    public static void removeChart(){
        root.getChildren().remove(lineChart);
    }

    // makes possible to zoom the chart
    public static void makeChartZoomable(LineChart<Number, Number> lineChart){
        final double SCALE_DELTA = 1.1;

        lineChart.setOnScroll(new EventHandler<ScrollEvent>() {
            public void handle(ScrollEvent event) {
                event.consume();

                if (event.getDeltaY() == 0)
                    return;

                double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;

                lineChart.setScaleX(lineChart.getScaleX() * scaleFactor);
                lineChart.setScaleY(lineChart.getScaleY() * scaleFactor);
            }
        });

        lineChart.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    lineChart.setScaleX(1.0);
                    lineChart.setScaleY(1.0);
                }
            }
        });
    }

}
