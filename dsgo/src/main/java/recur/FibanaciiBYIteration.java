/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recur;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author samuel
 */
public class FibanaciiBYIteration {

    /**
     *
     * @param m
     * @param n
     * @param cp=1 initial value
     * @return
     */
    public static long[][] getMatrixPow(long m[][], int n, int cp, long iniM[][], ArrayList<long[][]> interm) {
        int row = m.length;
        int col = m[0].length;
        long aux[][] = new long[row][col];
        if (n == 1) {
            interm.add(m);
            long I[][] = {{1, 0}, {0, 1}};
            interm.add(I);
            return getMatrixMul(interm, row);
        }
        if (n == 0) {
            long I[][] = {{1, 0}, {0, 1}};
            interm.add(I);
            return getMatrixMul(interm, row);
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                for (int k = 0; k < row; k++) {
                    aux[i][j] += m[i][k] * m[k][j];
                }
            }
        }
        if (2 * cp < n / 2) {
            return getMatrixPow(aux, n, 2 * cp, iniM, interm);
        } else {
            interm.add(aux);
            return getMatrixPow(iniM, n - cp * 2, 1, iniM, interm);
        }

    }

    public static long[][] getMatrixMul(ArrayList<long[][]> interm, int s) {
        int row = s;
        int col = s;

        long m1[][] = interm.get(0);

        int len = interm.size();
        long aux[][] = new long[row][col];
        for (int l = 1; l < len; l++) {

            long m2[][] = interm.get(l);
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    for (int k = 0; k < row; k++) {
                        aux[i][j] += m1[i][k] * m2[k][j];
                    }
                }
            }
            m1 = aux.clone();
            if (l < len - 1) {
                aux = new long[row][col];
            }
        }

        return aux;
    }

    public static long[][] getMatrixMulToGetResult(long m1[][], long m2[][], int k) {
        long aux[][] = new long[k][k];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < 1; j++) {
                for (int l = 0; l < k; l++) {
                    aux[i][j] += m1[i][l] * m2[l][j];
                }

            }
        }
        return aux;
    }

    public static void printMatrix(long m[][], int k) {
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String arg[]) {
        long t[][] = {{0, 1}, {1, 1}};
        long t2[][] = {{0, 1}, {1, 1}};
        long bm[][] = {{1}, {1}};
        int k = 2;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the location no of Fibanocii:");
        int i = sc.nextInt();
        if (i <= 1) {
            System.out.println("1");
            return;
        }
        ArrayList<long[][]> interm = new ArrayList<long[][]>();
        long result[][] = getMatrixPow(t, i, 1, t2, interm);
        result = getMatrixMulToGetResult(result, bm, k);
        //printMatrix(result, 2);
        System.out.println("The " + i + " Th Fibanocii no:" + result[0][0]);
    }

    public static long getFibAt(int loc) {
        long t[][] = {{0, 1}, {1, 1}};
        long t2[][] = {{0, 1}, {1, 1}};
        long bm[][] = {{1}, {1}};
        int k = 2;
        Scanner sc = new Scanner(System.in);
        int i = loc;
        if (i <= 1) {
            return 1;
        }
        ArrayList<long[][]> interm = new ArrayList<long[][]>();
        long result[][] = getMatrixPow(t, i, 1, t2, interm);
        result = getMatrixMulToGetResult(result, bm, k);
        //printMatrix(result, 2);
        return result[0][0];
    }
}
