package com.example.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Matrix {
    public Matrix() {
        double[][] matrixA;
    }


    public void initMatrixFromTxtFile(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String content;
        String[] numbers = null;
        List<String> numberList = new ArrayList<>();
        int colsCounter = 0;
        int rowsCounter = 0;
        while ((content = bufferedReader.readLine()) != null) {
            numbers = content.split(" ");
            colsCounter = numbers.length;
            numberList.addAll(Arrays.asList(numbers));
            rowsCounter += 1;
        }
        double[][] matrix = new double[rowsCounter][colsCounter];
        int listCursor = 0;
        for(int i = 0; i < rowsCounter; i++) {
            for(int j = 0; j < colsCounter; j++) {
                matrix[i][j] = Double.parseDouble(numberList.get(listCursor));
                listCursor += 1;
            }
        }
        System.out.println(Arrays.deepToString(matrix));
    }
}