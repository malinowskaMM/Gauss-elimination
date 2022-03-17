package com.example.view;

import com.example.model.Matrix;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HelloController {
    Matrix matrix = new Matrix();
    double[][] matrixA;
    double[][] matrixB;


    private void openWarningDialog(String text) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Uwaga");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(text);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
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

    }

}