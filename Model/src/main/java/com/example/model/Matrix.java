package com.example.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Matrix {
    public Matrix() {
        double[][] matrixA;
    }

    //wczytuje z pliczków
    public double[][] initMatrixFromTxtFile(File file, int numberOfEquations) throws IOException {
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
        if (matrix.length != numberOfEquations) {
            return null;
        }
        for (int i = 0; i < rowsCounter; i++) {
            for (int j = 0; j < colsCounter; j++) {
                matrix[i][j] = Double.parseDouble(numberList.get(listCursor));
                listCursor += 1;
            }
        }
        return matrix;
    }


    //macierz wpsołczynnika + macierz wyrazów wolnych
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
        double determinant = 1;
        double temp;
        int n = extended.length;
        double[] x = new double[n]; // tworzymy macierz wyrazow wolnych (rownie dobrze mozna przekazac ja przez patametr)
        for (int iter = 0; iter < n; iter++) {
            x[iter] = extended[iter][extended[0].length - 1];
        }

        for (int i = 0; i < n - 1; i++) {
            if (Math.abs(extended[i][i]) < epsilon) { //epsilon okresla nam jak blisko 0 moze znajdowac sie dzielnik

                for(int j = i+1; j < n; j++) {
                    if (Math.abs(extended[i][i]) < epsilon) {
                        //zamiana wierszy czyt. sprawko (zero mnie sie trafiło na przekatnej - a jak tu dzielić przez zero cholero?)
                        for(int k = 0; k < n+1; k++) {
                            temp = extended[i][k];
                            extended[i][k] = extended[j][k];
                            extended[j][k] = temp;
                        }
                        break;
                    }
                }

            }
            //proces eliminacji - próbujemy otrzymać macierz trójkątną górną i sie nawet udaje
            for (int j = i + 1; j < n; j++) {
                double m = (-1) * (extended[j][i] / extended[i][i]);
                for (int k = i; k <= n; k++) {
                    extended[j][k] +=  m * extended[i][k];
                }

            }
        }


        //w tablicy nie zawsze tam wychodziło równe zero, tylko cos bliskiego no i se rzutuje na zero jak bedzie w przedziale [-eps, eps]
        for (int i = 0; i < n; i++) {
            if(extended[i][i] < epsilon && extended[i][i] > -epsilon) {
                extended[i][i] = 0;
            }
            //tu kalukuje tereminant bo jak bedzie 0 to bedzie nieoznaczony albo sprzeczny
            determinant *= extended[i][i];
        }


        double[] zeros = new double[n + 1];
        if (determinant == 0) {
            for (int i = 0; i < n; i++) {
                double[] col = new double[n + 1];
                for (int j = 0; j < n + 1; j++) {
                    if (extended[i][j] < epsilon && extended[i][j] > -epsilon){
                        col[j] = 0.0;
                    } else {
                        col[j] = extended[i][j];
                    }
                }
                if (Arrays.equals(col, zeros)) {
                    return col; //tablice zer sobie zwracam jak jest np. 0 + 0 + 0 = 0
                }
            }
            return null; //null zwracam jak jest sprzeczy czyli mamy np. 0 + 0 + 0 = 6
        }

        //procesos odwrotny do eliminacji bom chce wyliczyć iksy
        double sum;
        for (int i = n - 1; i >= 0; i--) {
            sum = extended[i][n];
            for (int j = n - 1; j >= i + 1; j--) {
                sum -= extended[i][j] * x[j];
            }
            if (Math.abs(extended[i][i]) < epsilon) { //o znowu sie trafiło zero
                return x;
            }
            x[i] = sum / extended[i][i];
        }
        return x;
    }

}