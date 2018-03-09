package Matrix;

import java.util.Random;

import static org.junit.Assert.*;

public class MatrixTest {

    @org.junit.Test
    public void Matrix_Test() throws Exception {
        Matrix test_matrix= new Matrix(3,4);
        assertEquals(test_matrix.rows, 3);
        assertEquals(test_matrix.cols, 4);
    }

    @org.junit.Test
    public void Matrix2_Test() throws Exception {
        double[][] mat= {{1,2},{3,4,5}};
        Matrix test_matrix= new Matrix(mat);
        mat=test_matrix.asArray();
        for(int i=0; i<mat.length; i++)
        {
            for(int j=0; j<mat[i].length; j++)
            {
                assertEquals(test_matrix.get(i,j), mat[i][j] , 1e-5);
            }
        }
        assertEquals(test_matrix.rows, mat.length);

    }

    @org.junit.Test
    public void asArray() throws Exception {
        double[][] mat= {{1,2},{3,4,5}};
        Matrix test_matrix= new Matrix(mat);
        assertEquals(test_matrix.rows, mat.length, 1e-5);

    }

    @org.junit.Test
    public void get() throws Exception {
        double[][] mat= {{1,2},{3,4}};
        Matrix test_matrix= new Matrix(mat);

        for(int i=0; i<mat.length; i++)
        {
            for(int j=0; j<mat[i].length; j++)
            {
                assertEquals(test_matrix.get(i,j), mat[i][j], 1e-5);
            }
        }


    }

    @org.junit.Test
    public void set() throws Exception {
        Matrix test_matrix= new Matrix(new double[][]{{1,2},{3,4}});
        test_matrix.set(1,1,3);
        assertEquals(test_matrix.data[2], 3,1e-5);
    }

    @org.junit.Test
    public void testtoString() throws Exception {
        double[][] mat= {{1,2},{3,4}};
        Matrix test_matrix= new Matrix(mat);
        String str="[[1.0, 2.0]\n[3.0, 4.0]]";
        assertEquals(test_matrix.toString(),  str);

        String test_str=test_matrix.toString();
        int licznik=0;
        int licznik_przecinkow=0;
        for(int i=0; i<test_str.length(); i++)
        {
            if(test_str.charAt(i)==']' || test_str.charAt(i)=='[') licznik++;
            if(test_str.charAt(i)==',') licznik_przecinkow++;
        }
        assertEquals(licznik,  6);
        assertEquals(licznik_przecinkow,2);
    }

    @org.junit.Test(expected = RuntimeException.class)
    public void reshape() throws Exception {
        double[][] mat= {{1,2},{3,4}};
        Matrix test_matrix= new Matrix(mat);
        test_matrix.reshape(3,4);
    }

    @org.junit.Test
    public void add() throws Exception {
        double[][] mat= {{1,2},{3,4}};
        Matrix test_matrix= new Matrix(mat);
        Matrix test_matrix2= new Matrix(mat);
        test_matrix.add(test_matrix2);
        test_matrix2.mul(2);
        for(int i=1; i<mat.length; i++)
        {
            for(int j=1; j<mat[i].length; j++)
            {
                assertEquals(test_matrix.get(i,j), test_matrix2.get(i,j) , 1e-5);
            }
        }
    }

    @org.junit.Test
    public void sub() throws Exception {
        double[][] mat= {{1,2},{3,4}};
        Matrix test_matrix= new Matrix(mat);
        Matrix test_matrix2= new Matrix(mat);
        test_matrix.sub(test_matrix2);
        test_matrix2.mul(0);
        for(int i=1; i<mat.length; i++)
        {
            for(int j=1; j<mat[i].length; j++)
            {
                assertEquals(test_matrix.get(i,j), test_matrix2.get(i,j) , 1e-5);
            }
        }
        assertEquals(test_matrix.frobenius(), 0, 1e-5);
    }

    @org.junit.Test
    public void mul() throws Exception {
        double[][] mat= {{1,2},{3,4}};
        Matrix test_matrix= new Matrix(mat);
        Matrix test_matrix2= new Matrix(mat);
        test_matrix.mul(test_matrix2);
        double[][] mat2={{1,4},{9,16}};
        for(int i=0; i<mat.length; i++)
        {
            for(int j=0; j<mat[i].length; j++)
            {
                assertEquals(test_matrix.get(i,j), mat2[i][j] , 1e-5);
            }
        }
    }

    @org.junit.Test
    public void div() throws Exception {
        double[][] mat= {{1,2},{3,4}};
        Matrix test_matrix= new Matrix(mat);
        Matrix test_matrix2= new Matrix(mat);
        test_matrix.div(test_matrix2);
        assertEquals(test_matrix.frobenius(), Math.sqrt(test_matrix.cols*test_matrix.rows), 1e-5);
    }

    @org.junit.Test
    public void add1() throws Exception {
        double[][] mat= {{1,2},{3,4}};
        Matrix test_matrix= new Matrix(mat);
        double[][] mat2={{2,3},{4,5}};
        test_matrix.add(1);
        for(int i=0; i<mat.length; i++)
        {
            for(int j=0; j<mat[i].length; j++)
            {
                assertEquals(test_matrix.get(i,j), mat2[i][j] , 1e-5);
            }
        }
    }

    @org.junit.Test
    public void sub1() throws Exception {
        double[][] mat= {{1,2},{3,4}};
        Matrix test_matrix= new Matrix(mat);
        double[][] mat2={{0,1},{2,3}};
        test_matrix.sub(1);
        for(int i=0; i<mat.length; i++)
        {
            for(int j=0; j<mat[i].length; j++)
            {
                assertEquals(test_matrix.get(i,j), mat2[i][j] , 1e-5);
            }
        }
    }


    @org.junit.Test
    public void mul1() throws Exception {
        double[][] mat= {{1,2},{3,4}};
        Matrix test_matrix= new Matrix(mat);
        double[][] mat2={{2,4},{6,8}};
        test_matrix.mul(2);
        for(int i=0; i<mat.length; i++)
        {
            for(int j=0; j<mat[i].length; j++)
            {
                assertEquals(test_matrix.get(i,j), mat2[i][j] , 1e-5);
            }
        }
    }

    @org.junit.Test
    public void div1() throws Exception {
        double[][] mat= {{2,2},{4,6}};
        Matrix test_matrix= new Matrix(mat);
        double[][] mat2={{1,1},{2,3}};
        test_matrix.div(2);
        for(int i=0; i<mat.length; i++)
        {
            for(int j=0; j<mat[i].length; j++)
            {
                assertEquals(test_matrix.get(i,j), mat2[i][j] , 1e-5);
            }
        }
    }

    @org.junit.Test
    public void dot() throws Exception {
        double[][] mat= {{1,2},{3,4}};
        Matrix test_matrix= new Matrix(mat);
        double[][] mat2={{1,2},{2,1}};
        Matrix test_matrix2= new Matrix(mat2);
        test_matrix=test_matrix.dot(test_matrix2);
        double[][] mat3={{5,4},{11,10}};
        for(int i=0; i<mat.length; i++)
        {
            for(int j=0; j<mat[i].length; j++)
            {
                assertEquals(test_matrix.get(i,j), mat3[i][j] , 1e-5);
            }
        }
    }

    /////////////GRUPA C//////////////
    @org.junit.Test
    public void sumRows() throws Exception {

        Random rn = new Random();
        int col = rn.nextInt(100) + 1;
        int row = rn.nextInt(100) + 1;
        Matrix mat = new Matrix(row,col);
        double delta = rn.nextDouble();
        double p=0;
        for(int c=0; c<mat.data.length ;c++)
        {
            mat.data[c]=p;
            p=p+delta;
        }

        Matrix sumR = mat.sumRows();

        assertEquals(sumR.rows, 1 ,1e-5);
        assertEquals(sumR.cols, mat.cols ,1e-5);
        for(int c=0; c<mat.cols ;c++)
        {
            double a=((mat.asArray()[0][c]+mat.asArray()[mat.rows-1][c])/2)*mat.rows;
            assertEquals(a, sumR.get(0,c) ,1e-5);
        }

    }
    ////////////////////////////////////
}