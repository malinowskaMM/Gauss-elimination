package com.example.view;

import com.example.model.Matrix;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HelloController {
    Matrix matrix = new Matrix();
    @FXML
    private Button inputCoefficientButton;

    @FXML
    protected void onInputCoefficientButtonClick() throws IOException {
        JFileChooser jfc = new JFileChooser();
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            matrix.initMatrixFromTxtFile(selectedFile);

        }
    }

}