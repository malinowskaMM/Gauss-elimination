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
        int i =0;
        int j =0;
        int rowsCounter = matrix.length;
        int colsCounter = matrix[0].length;
        double[][] temp = new double[rowsCounter][colsCounter];
        for(int row = 0; row < rowsCounter; row++) {
            for(int col = 0; col < colsCounter; col++) {
                if(row != p && col != q) {
                    temp[i][j++] = matrix[row][col];
                    if (j == colsCounter -1) {
                        j=0;
                        i++;
                    }
                }
            }
        }
        return temp;
    }

    public double determinantMatrix(double[][] matrix , int dimension) {
        double determiant = 0;
        if (dimension == 1) {
            return matrix[0][0];
        }
        int sign = 1;
        for (int i = 0; i < dimension; i++) {
            determiant += sign * matrix[0][i] * determinantMatrix(getCofactor(matrix, 0, i), dimension-1 );
            sign = - sign;
        }
        return determiant;
    }



    //zeby dostac macierz trojkatna gorna
    public double[][] elimination(double[][] extended, double epsilon, double[][] matrixB) {
        double diff;
        int rowsCounter = extended.length;
        int colsCounter = extended[0].length;
        for(int i = 1; i < rowsCounter-1; i++) {
            for (int j = i + 1; j < colsCounter - 1; j++) {
                if( Math.abs(extended[i][i])< epsilon) {
                    break;
                }
                diff = - extended[j][i] / extended[i][i];
            }
        }
        return extended;
    }






}