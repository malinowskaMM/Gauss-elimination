package com.example.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Matrix {
    public Matrix() {
        double[][] matrixA;
    }


    public double[][] initMatrixFromTxtFile(File file) throws IOException {
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
        for (int i = 0; i < rowsCounter; i++) {
            for (int j = 0; j < colsCounter; j++) {
                matrix[i][j] = Double.parseDouble(numberList.get(listCursor));
                listCursor += 1;
            }
        }
        return matrix;
    }

    public double[][] extendedMatrix(double[][] matrixA, double[][] matrixB) {
        int rowsCounter = matrixA.length;
        int colsCounter = matrixA[0].length + 1;
        double[][] exteded = new double[rowsCounter][colsCounter];
        if (matrixA.length == matrixB.length) {
            for (int i = 0; i < rowsCounter; i++) {
                for (int j = 0; j < colsCounter; j++) {
                    if (j == colsCounter - 1) {
                        exteded[i][j] = matrixB[i][0];
                    } else {
                        exteded[i][j] = matrixA[i][j];
                    }
                }
            }
        }
        return exteded;
    }


    //zeby dostac macierz trojkatna gorna
    public double[] elimination(double[][] extended, double epsilon) {
        int n = extended.length;
        double[] x = new double[n];
        for(int iter = 0; iter < n; iter++){
            x[iter] = extended[iter][extended[0].length-1];
        }
        System.out.println(Arrays.deepToString(extended));
        for (int i = 0; i < n - 1; i++) {
            for(int j = i+1; j < n; j++) {
                if( Math.abs(extended[i][i]) < epsilon) {
                    System.out.println("Wyszedłem 1");
                    return x;
                }
                double m = - extended[j][i]/extended[i][i];
                for(int k = i; k <= n; k++) {
                    extended[j][k] += m * extended[i][k];
                }
            }
        }
        double sum = 0;
        for(int i = n-1; i >= 0; i--) {
            sum = extended[i][n];
            for(int j = n -1; j >= i+1; j--) {
                sum -= extended[i][j]*x[j];
                //System.out.println("Wartosc sum: " + sum);
            }
            if( Math.abs(extended[i][i]) < epsilon) {
                System.out.println("Wyszedłem 2");
                return x;
            }
            x[i] = sum / extended[i][i];
            //System.out.println("Wartosc x[i]: " + x[i]);
        }
        return x;
    }

}