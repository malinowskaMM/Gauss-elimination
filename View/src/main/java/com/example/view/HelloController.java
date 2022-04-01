package com.example.view;

import com.example.model.Matrix;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class HelloController {
    Matrix matrix = new Matrix();
    int checkNumberOfEquations;
    int numberOfEquations;
    int maxNumberOfEquantions = 10;
    double eps = 0;
    double[][] matrixA;
    double[][] matrixB;
    double[][] matrixAB;

    @FXML
    TextField numberOfEquationsInput;
    @FXML
    TextField precisionInput;


    private void printResultInWarningDialog(double[] result) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Rozwiązaniem układu równań są \n");
        for (int i = 0; i < result.length; i++) {
            stringBuilder.append("x"+i+" = "+result[i]+"\n");
        }
        openWarningDialog(stringBuilder.toString());
    }

    private void openWarningDialog(String text) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Uwaga");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(text);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }


    private boolean setNumberOfEquations() {
        String inputNumber = numberOfEquationsInput.getText();
        boolean ifDigitInput = true;
        for(int i = 0; i < inputNumber.length(); i++) {
            if (!Character.isDigit(inputNumber.charAt(i))) {
                ifDigitInput = false;
            }
        }
        if (ifDigitInput) {
            checkNumberOfEquations = Integer.parseInt(inputNumber);
            if (checkNumberOfEquations > maxNumberOfEquantions) {
                openWarningDialog("Prosze zmniejszyć ilość (max 10)");
                return false;
            }
            numberOfEquations = checkNumberOfEquations;
            return true;
        } else {
            openWarningDialog("Podano złą ilość równań");
            return false;
        }
    }

    private boolean setEps() {
        String inputNumber = precisionInput.getText();
        boolean ifDigitInput = true;
        for(int i = 0; i < inputNumber.length(); i++) {
            if (!Character.isDigit(inputNumber.charAt(i)) && inputNumber.charAt(i) != '.') {
                ifDigitInput = false;
            }
        }
        if (ifDigitInput) {
            eps = Double.parseDouble(precisionInput.getText());
            return true;
        } else {
            openWarningDialog("Podano złą wartosc eps");
            return false;
        }

    }

    @FXML
    protected int onInputCoefficientButtonClick() throws IOException {
        if (!setNumberOfEquations()) {
            return -1;
        }
        if (!setEps()) {
            return -1;
        }
        JFileChooser jfc = new JFileChooser();
        openWarningDialog("Wybierz plik z macierzą współczynników");
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            matrixA = matrix.initMatrixFromTxtFile(selectedFile, numberOfEquations);
            if(matrixA == null) {
                openWarningDialog("Podano złą ilość równań wzgledem wczytanego pliku");
                return  -1;
            }
        }
        JFileChooser jfc2 = new JFileChooser();
        openWarningDialog("Wybierz plik z macierzą rozwiązań");
        int returnValue2 = jfc2.showOpenDialog(null);
        if (returnValue2 == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc2.getSelectedFile();
            matrixB = matrix.initMatrixFromTxtFile(selectedFile, numberOfEquations);
            if(matrixB == null) {
                openWarningDialog("Podano złą ilość równań wzgledem wczytanego pliku");
                return  -1;
            }
        }
        matrixAB = matrix.extendedMatrix(matrixA, matrixB);
        double[] result = matrix.elimination(matrixAB, Double.MIN_NORMAL);
        if(result == null) {
            openWarningDialog("Wyznacznik macierzy jest równy 0. Układ jest sprzeczny");
        }
        else if (Arrays.equals(result, new double[matrixAB[0].length])) {
            openWarningDialog("Wyznacznik macierzy jest równy 0. Układ jest nieoznaczony");
        }
        else {
            printResultInWarningDialog(result);
        }
        return 0;
    }

}