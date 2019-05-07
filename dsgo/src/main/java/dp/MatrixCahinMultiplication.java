package dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MatrixCahinMultiplication
{
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Number of Matricis:");
        try
        {
            int n = Integer.parseInt(br.readLine());
            int matrixCahin[][] = new int[n + 1][2];
            int dp[][] = new int[n + 1][n + 1];
            System.out.println("Enter Compatable Dimentsions:");
            String data[] = null;
            for (int i = 1; i <= n; i++)
            {
                data = br.readLine().split(" ");
                matrixCahin[i][0] = Integer.parseInt(data[0]);
                matrixCahin[i][1] = Integer.parseInt(data[1]);
            }
            for (int i = 0; i < n; i++)
            {
                dp[0][i] = Integer.MAX_VALUE;
                dp[i][0] = Integer.MAX_VALUE;
            }
            int value = 0, aux = 0;
            
            for (int delta = 1; delta < n; delta++)
            {
                for (int i = 1; i <= n; i++)
                {
                    int j = i + delta;
                    if (j > n)
                    {
                        break;
                    }
                    value = Integer.MAX_VALUE;
                    for (int k = i; k < j; k++)
                    {
                        aux = matrixCahin[i][0] * matrixCahin[k][1] * matrixCahin[j][1];
                        aux+=dp[i][k]+dp[k+1][j];
                        if (value > aux)
                        {
                            value = aux;
                        }
                    }
                    dp[i][j] = value;
                }
            }
            
            
            System.out.println("Result:-"+dp[1][n]);
            
        } catch (NumberFormatException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
