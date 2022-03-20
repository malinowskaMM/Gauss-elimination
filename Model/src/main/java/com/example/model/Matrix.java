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
        for(int i = 0; i < rowsCounter; i++) {
            for(int j = 0; j < colsCounter; j++) {
                matrix[i][j] = Double.parseDouble(numberList.get(listCursor));
                listCursor += 1;
            }
        }
        return matrix;
    }

    public double[][] extendedMatrix(double[][] matrixA, double[][] matrixB ) {
        int rowsCounter = matrixA.length;
        int colsCounter = matrixA[0].length + 1;
        double[][] exteded = new double[rowsCounter][colsCounter];
        if (matrixA.length == matrixB.length) {
            for(int i = 0; i < rowsCounter; i++) {
                for(int j = 0; j < colsCounter; j++) {
                    if(j == colsCounter-1) {
                        exteded[i][j] = matrixB[i][0];
                    } else {
                        exteded[i][j] = matrixA[i][j];
                    }
                }
            }
        }
        return exteded;
    }

    //cofactor = number/matrix you get when you remove the column and row of a designated element in a matrix
    // p - row of removed number
    // q - col of removed number
    public double[][] getCofactor(double[][] matrix, int p, int q) {
        int rowsCounter = matrix.length-1;
        int colsCounter = matrix[0].length-1;
        double[][] temp = new double[rowsCounter][colsCounter];
        for(int row = 0; row < rowsCounter; row++) {
            int i =0;
            if(row != q) {
                double[] oneRow = new double[colsCounter];
                for(int col = 0; col < colsCounter; col++) {
                    int j =0;
                    if(col != p) {
                        oneRow[j] = matrix[i][j];
                        j++;
                    }
                }
                temp[i] = oneRow;
                i++;
            }
        }
        return temp;
    }

    public double determinantMatrix(double[][] matrix) {
        double determinant = 0;
        if ( matrix.length == matrix[0].length) {
            if (matrix.length == 1) {
                return matrix[0][0];
            }
            int sign = 1;
            for (int i = 0; i < matrix.length; i++) {
                double[][] tempMatrix = getCofactor(matrix, 0, i);
                determinant += sign * matrix[i][0] * determinantMatrix(tempMatrix);
                sign = -sign;
            }
        }
        return determinant;
    }



    //zeby dostac macierz trojkatna gorna
    public double[][] elimination(double[][] extended) {
        double diff;
        int n = extended.length;
        for(int i = 1; i < n - 1; i++) {
            if(extended[i][i] == 0) {
                break;
            }
            for (int j = i + 1; j < n; j++) {
//                if( Math.abs(extended[i][i])< epsilon) {
//                    break;
//                }
                diff = extended[j][i] / extended[i][i];
                for(int k = 1; k < n + 1; k++) {
                    extended[j][k] = extended[j][k] - diff * extended[i][k];
                }
            }
        }
        return extended;
    }


    public double[] solution(double[][] extended) {
        int n = extended.length;
        double[] x = new double[n];
        for(int i = n-1; i >= 1; i--) {
            x[i]=extended[i][n];
            for (int j = i+1; j<n; j++) {
                if(i!=j) {
                    x[i]=x[i]-extended[i][j]*x[j];
                }
            }
            x[i]=x[i]/extended[i][i];
        }
        return x;
    }



}