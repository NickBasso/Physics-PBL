package base;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    // necessary object declarations
    public MenuButton VoltageMenuButton;
    public MenuButton CapacityMenuButton;
    public MenuButton InductanceMenuButton;
    public MenuButton ResistorMenuButton;
    public MenuButton StartingPhaseMenuButton;
    public MenuButton TimeRangeMenuButton;
    public MenuButton TimeDeltaMenuButton;

    public Button ResetAllButton;
    public Button CalculateChargeButton;
    public Button ShowChartButton;

    public TextField VoltageTextField;
    public TextField CapacityTextField;
    public TextField InductanceTextField;
    public TextField ResistorTextField;
    public TextField StartingPhaseTextField;
    public TextField TimeRangeTextField;
    public TextField TimeDeltaTextField;

    public Label InitialVoltageTextLabel;
    public Label CapacityTextLabel;
    public Label InductanceTextLabel;
    public Label OscillationPeriodTextLabel;
    public Label ResistorResistanceTextLabel;
    public Label TotalResistanceTextLabel;
    public Label OscillationFrequencyTextLabel;
    public Label AttenuationCoefficientTextLabel;

    public Label InitialVoltageLabel;
    public Label CapacityLabel;
    public Label InductanceLabel;
    public Label ResistorResistanceLabel;
    public Label TotalResistanceLabel;
    public Label OscillationPeriodLabel;
    public Label OscillationFrequencyLabel;
    public Label AttenuationCoefficientLabel;

    public double tenTo9 = Math.pow(10, 9);
    public double tenTo6 = Math.pow(10, 6);
    public double tenTo3 = Math.pow(10, 3);

    public int sceneObjectCount;

    // create variable named "calculator" of type Calculator
    // for further use in calculations
    Calculator calculator;

    // app-open initializer
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeData();
    }

    public void initializeData(){
        VoltageMenuButton.setText("Volt");
        CapacityMenuButton.setText("Farad");
        InductanceMenuButton.setText("Henry");
        ResistorMenuButton.setText("Ohm");
        StartingPhaseMenuButton.setText("Radian");
        TimeRangeMenuButton.setText("seconds");
        TimeDeltaMenuButton.setText("seconds");

        VoltageTextField.setText("1500");
        CapacityTextField.setText("0.00000004");
        InductanceTextField.setText("0.68");
        ResistorTextField.setText("50");
        StartingPhaseTextField.setText("0.26179938779");
        TimeDeltaTextField.setText("0.000001");
        TimeRangeTextField.setText("0.002");

        InitialVoltageTextLabel.setVisible(false);
        CapacityTextLabel.setVisible(false);
        InductanceTextLabel.setVisible(false);
        ResistorResistanceTextLabel.setVisible(false);
        TotalResistanceTextLabel.setVisible(false);
        OscillationPeriodTextLabel.setVisible(false);
        OscillationFrequencyTextLabel.setVisible(false);
        AttenuationCoefficientTextLabel.setVisible(false);

        InitialVoltageLabel.setVisible(false);
        CapacityLabel.setVisible(false);
        InductanceLabel.setVisible(false);
        ResistorResistanceLabel.setVisible(false);
        TotalResistanceLabel.setVisible(false);
        OscillationPeriodLabel.setVisible(false);
        OscillationFrequencyLabel.setVisible(false);
        AttenuationCoefficientLabel.setVisible(false);
    }

                             // ---> variable initializer <---

    // variable initializer
    public void handleResetAllButton(){
        initializeData();
        Main.removeTable();
        Main.removeChart();
        ShowChartButton.setDisable(false);
        CalculateChargeButton.setDisable(false);
    }

                             // ---> Calculate charge handlers <---
    public void handleCalculateChargeButton(){
        CalculateChargeButton.setDisable(true);

        double V = convertVoltageToSI();
        double C = convertCapacityToSI();
        double L = convertInductanceToSI();
        double R = convertResistanceToSI();
        double startingPhase = convertStartingPhaseToSI();
        double time = convertTimeRangeToSI();
        double timeDelta = convertTimeDeltaToSI();

        calculator = new Calculator(V, C, L, R, time, timeDelta, startingPhase);
        calculator.calculateConstants();
        calculator.calculateCharge();

        Main.displayConstants(  InitialVoltageLabel, V,
                                CapacityLabel, C,
                                InductanceLabel, L,
                                ResistorResistanceLabel, R,
                                TotalResistanceLabel, calculator.getTotalResistance(),
                                OscillationPeriodLabel, calculator.getOscillationPeriod(),
                                OscillationFrequencyLabel, calculator.getOscillationFrequency(),
                                AttenuationCoefficientLabel, calculator.getAttenuationCoefficient() );

        Main.displayResultsTable(calculator.data);

        InitialVoltageTextLabel.setVisible(true);
        CapacityTextLabel.setVisible(true);
        InductanceTextLabel.setVisible(true);
        ResistorResistanceTextLabel.setVisible(true);
        TotalResistanceTextLabel.setVisible(true);
        OscillationPeriodTextLabel.setVisible(true);
        OscillationFrequencyTextLabel.setVisible(true);
        AttenuationCoefficientTextLabel.setVisible(true);

        InitialVoltageLabel.setVisible(true);
        CapacityLabel.setVisible(true);
        InductanceLabel.setVisible(true);
        ResistorResistanceLabel.setVisible(true);
        TotalResistanceLabel.setVisible(true);
        OscillationPeriodLabel.setVisible(true);
        OscillationFrequencyLabel.setVisible(true);
        AttenuationCoefficientLabel.setVisible(true);
    }
    public void handleShowChartButtonOnAction(){
        Main.displayChart(calculator.data, calculator.iterations);
        //CalculateChargeButton.setDisable(true);
        ShowChartButton.setDisable(true);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //                       ---> text to double convert handlers <---

    public double convertVoltageToSI(){
        double x = Double.valueOf(VoltageTextField.getText());

        if(VoltageMenuButton.getText().equals("nanoVolt")){
            return x/tenTo9;
        } else if(VoltageMenuButton.getText().equals("microVolt")){
            return x/tenTo6;
        } else if(VoltageMenuButton.getText().equals("milliVolt")){
            return x/tenTo3;
        } else if(VoltageMenuButton.getText().equals("GigaVolt")){
            return x*tenTo9;
        } else if(VoltageMenuButton.getText().equals("MegaVolt")){
            return x*tenTo6;
        } else if(VoltageMenuButton.getText().equals("kiloVolt")){
            return x*tenTo3;
        }

        return x;
    }

    public double convertCapacityToSI(){
        double x = Double.valueOf(CapacityTextField.getText());

        if(CapacityMenuButton.getText().equals("nanoFarad")){
            return x/tenTo9;
        } else if(CapacityMenuButton.getText().equals("microFarad")){
            return x/tenTo6;
        } else if(CapacityMenuButton.getText().equals("milliFarad")){
            return x/tenTo3;
        } else if(CapacityMenuButton.getText().equals("GigaFarad")){
            return x*tenTo9;
        } else if(CapacityMenuButton.getText().equals("MegaFarad")){
            return x*tenTo6;
        } else if(CapacityMenuButton.getText().equals("kiloFarad")){
            return x*tenTo3;
        }

        return x;
    }

    public double convertInductanceToSI(){
        double x = Double.valueOf(InductanceTextField.getText());

        if(InductanceMenuButton.getText().equals("nanoHenry")){
            return x/tenTo9;
        } else if(InductanceMenuButton.getText().equals("microHenry")){
            return x/tenTo6;
        } else if(InductanceMenuButton.getText().equals("milliHenry")){
            return x/tenTo3;
        } else if(InductanceMenuButton.getText().equals("GigaHenry")){
            return x*tenTo9;
        } else if(InductanceMenuButton.getText().equals("MegaHenry")){
            return x*tenTo6;
        } else if(InductanceMenuButton.getText().equals("kiloHenry")){
            return x*tenTo3;
        }

        return x;
    }

    public double convertResistanceToSI(){
        double x = Double.valueOf(ResistorTextField.getText());

        if(ResistorMenuButton.getText().equals("nanoOhm")){
            return x/tenTo9;
        } else if(ResistorMenuButton.getText().equals("microOhm")){
            return x/tenTo6;
        } else if(ResistorMenuButton.getText().equals("milliOhm")){
            return x/tenTo3;
        } else if(ResistorMenuButton.getText().equals("GigaOhm")){
            return x*tenTo9;
        } else if(ResistorMenuButton.getText().equals("MegaOhm")){
            return x*tenTo6;
        } else if(ResistorMenuButton.getText().equals("kiloOhm")){
            return x*tenTo3;
        }

        return x;
    }

    public double convertStartingPhaseToSI(){
        double x = Double.valueOf(StartingPhaseTextField.getText());

        if(StartingPhaseMenuButton.getText().equals("Degrees")){
            return x*(Calculator.pi/180);
        }

        return x;
    }

    public double convertTimeRangeToSI(){
        double x = Double.valueOf(TimeRangeTextField.getText());

        if(TimeRangeMenuButton.getText().equals("milliseconds")){
            return x/tenTo3;
        }

        return x;
    }

    public double convertTimeDeltaToSI(){
        double x = Double.valueOf(TimeDeltaTextField.getText());

        if(TimeDeltaMenuButton.getText().equals("milliseconds")){
            return x/tenTo3;
        }

        return x;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                             // ---> Voltage handlers <---
    public void handleVoltageNanoVoltUnitMenuItem(){
        VoltageMenuButton.setText("nanoVolt");
    }

    public void handleVoltageMircoVoltUnitMenuItem(){
        VoltageMenuButton.setText("mircoVolt");
    }

    public void handleVoltageMilliVoltUnitMenuItem(){
        VoltageMenuButton.setText("milliVolt");
    }

    public void handleVoltageVoltUnitMenuItem(){
        VoltageMenuButton.setText("Volt");
    }

    public void handleVoltageKiloVoltUnitMenuItem(){
        VoltageMenuButton.setText("kiloVolt");
    }

    public void handleVoltageMegaVoltUnitMenuItem(){
        VoltageMenuButton.setText("MegaVolt");
    }

    public void handleVoltageGigaVoltUnitMenuItem(){
        VoltageMenuButton.setText("GigaVolt");
    }

                             // ---> Capacity handlers <---
    public void handleCapacityNanoFaradUnitMenuItem(){
        CapacityMenuButton.setText("nanoFarad");
    }

    public void handleCapacityMircoFaradUnitMenuItem(){
        CapacityMenuButton.setText("microFarad");
    }

    public void handleCapacityMilliFaradUnitMenuItem(){
        CapacityMenuButton.setText("milliFarad");
    }

    public void handleCapacityFaradUnitMenuItem(){
        CapacityMenuButton.setText("Farad");
    }

    public void handleCapacityKiloFaradUnitMenuItem(){
        CapacityMenuButton.setText("kiloFarad");
    }

    public void handleCapacityMegaFaradUnitMenuItem(){
        CapacityMenuButton.setText("MegaFarad");
    }

    public void handleCapacityGigaFaradUnitMenuItem(){
        CapacityMenuButton.setText("GigaFarad");
    }

                             // ---> Inductance handlers <---
    public void handleInductanceNanoHenryUnitMenuItem(){
        InductanceMenuButton.setText("nanoHenry");
    }

    public void handleInductanceMircoHenryUnitMenuItem(){
        InductanceMenuButton.setText("microHenry");
    }

    public void handleInductanceMilliHenryUnitMenuItem(){
        InductanceMenuButton.setText("milliHenry");
    }

    public void handleInductanceHenryUnitMenuItem(){
        InductanceMenuButton.setText("Henry");
    }

    public void handleInductanceKiloHenryUnitMenuItem(){
        InductanceMenuButton.setText("kiloHenry");
    }

    public void handleInductanceMegaHenryUnitMenuItem(){
        InductanceMenuButton.setText("MegaHenry");
    }

    public void handleInductanceGigaHenryUnitMenuItem(){
        InductanceMenuButton.setText("GigaHenry");
    }

                             // ---> Resistance handlers <---
    public void handleResistanceNanoOhmUnitMenuItem(){ ResistorMenuButton.setText("nanoOhm"); }

    public void handleResistanceMicroOhmUnitMenuItem(){
        ResistorMenuButton.setText("microOhm");
    }

    public void handleResistanceMilliOhmUnitMenuItem(){ ResistorMenuButton.setText("milliOhm"); }

    public void handleResistanceOhmUnitMenuItem(){
        ResistorMenuButton.setText("Ohm");
    }

    public void handleResistanceKiloOhmUnitMenuItem(){ ResistorMenuButton.setText("kiloOhm"); }

    public void handleResistanceMegaOhmUnitMenuItem(){
        ResistorMenuButton.setText("MegaOhm");
    }

    public void handleResistanceGigaOhmUnitMenuItem(){ ResistorMenuButton.setText("GigaOhm"); }

                             // ---> Starting phase handlers <---
    public void handleStartingPhaseRadianUnitMenuItem(){
        StartingPhaseMenuButton.setText("Radian");
    }

    public void handleStartingPhaseDegreesUnitMenuItem(){
        StartingPhaseMenuButton.setText("Degree");
    }

    public void handleVoltageMenuButton(){

    }

                             // ---> Time range handlers <---
    public void handleTimeRangeSecondUnitMenuItem(){
        TimeRangeMenuButton.setText("seconds");
    }

    public void handleTimeRangeMilliSecondUnitMenuItem(){
        TimeRangeMenuButton.setText("milliseconds");
    }

                            // ---> Time delta handlers <---

    public void handleTimeDeltaRangeSecondUnitMenuItem(){
        TimeDeltaMenuButton.setText("seconds");
    }

    public void handleTimeDeltaRangeMilliSecondUnitMenuItem(){
        TimeDeltaMenuButton.setText("milliseconds");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                             // ---> testing <---

}
