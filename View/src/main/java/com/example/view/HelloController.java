package com.example.view;

import com.example.model.Matrix;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.*;
import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class HelloController {
    Matrix matrix = new Matrix();
    double[][] matrixA;
    double[][] matrixB;
    double[][] matrixAB;

    @FXML
    TextField precisionInput;
    @FXML
    TextField iterationNumInput;

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

    @FXML
    protected void onPrecisionRadioButtonSelected() {
        precisionInput.setDisable(false);
        iterationNumInput.setDisable(true);
    }

    @FXML
    protected void onIterationRadioButtonSelected() {
        precisionInput.setDisable(true);
        iterationNumInput.setDisable(false);
    }

    @FXML
    protected void onInputCoefficientButtonClick() throws IOException {
        JFileChooser jfc = new JFileChooser();
        openWarningDialog("Wybierz plik z macierzą współczynników");
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            matrixA = matrix.initMatrixFromTxtFile(selectedFile);
        }
        JFileChooser jfc2 = new JFileChooser();
        openWarningDialog("Wybierz plik z macierzą rozwiązań");
        int returnValue2 = jfc2.showOpenDialog(null);
        if (returnValue2 == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc2.getSelectedFile();
            matrixB = matrix.initMatrixFromTxtFile(selectedFile);
        }
        matrixAB = matrix.extendedMatrix(matrixA, matrixB);
        System.out.println(Arrays.toString(matrix.elimination(matrixAB, Double.MIN_NORMAL)));
        printResultInWarningDialog(matrix.elimination(matrixAB, Double.MIN_NORMAL));
    }

}