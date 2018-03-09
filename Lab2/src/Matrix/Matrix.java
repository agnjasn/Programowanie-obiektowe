package Matrix;
import static java.lang.Math.*;

public class Matrix {
    double [] data;
    int rows;
    int cols;

    Matrix(int rows, int cols)
    {
        this.rows=rows;
        this.cols=cols;
        data = new double[rows*cols];
    }

    Matrix(double[][] d)
    {
        this.rows=d.length;
        int max_col=0;
        for(int i=0; i<d.length; i++)
        {
            if(max_col<d[i].length) max_col=d[i].length;
        }
        this.cols=max_col;
        this.data=new double[rows*cols];

        for(int i=0; i<rows; i++)
        {
            for(int j=0; j<cols; j++)
            {
                if(d[i].length<=j)
                    data[i*cols+j]=0;
                else data[i*cols+j]=d[i][j];
            }
        }
    }

    double[][] asArray()
    {
        double[][] ta;
        ta= new double[rows][cols];
        for(int i=0; i<rows; i++)
        {
            for(int j=0; j<cols; j++)
            {
                ta[i][j]=data[i*cols+j];
            }
        }
        return ta;

    }



    double get(int r,int c)
    {
        return data[(r)*cols+(c)];
    }
    void set (int r,int c, double value)
    {
        data[(r)*cols+(c)]=value;
    }


    public String toString(){
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for(int i=0;i<rows;i++){
            buf.append("[");
            for(int j=0; j<cols;j++)
            {
                buf.append(data[i*cols+j]);
                if(j+1!=cols) buf.append(", ");
            }
            buf.append("]");
            if(i+1!=rows){buf.append("\n");}
        }
        buf.append("]");
        return buf.toString();
    }

    void reshape(int newRows,int newCols){
        if(rows*cols != newRows*newCols)
            throw new RuntimeException(String.format("%d x %d matrix can't be reshaped to %d x %d",rows,cols,newRows,newCols));
        else
        {
            rows=newRows;
            cols=newCols;
        }
    }

   int[] shape()
    {
        int[] sh=new int[2];
        sh[0]=rows;
        sh[1]=cols;
        return sh;
    }

     Matrix add(Matrix m)
    {
        if(rows!= m.rows && cols!=m.cols)
            throw new RuntimeException(String.format("%d x %d matrix can't be added to %d x %d",rows,cols,m.rows,m.cols));
        else {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    data[i * cols + j] = data[i * cols + j] + m.data[i * cols + j];
                }
            }
            return this;
        }
    }

    Matrix sub(Matrix m)
    {
        if(rows!= m.rows && cols!=m.cols)
            throw new RuntimeException(String.format("%d x %d matrix can't be subdivided to %d x %d",rows,cols,m.rows,m.cols));
        else {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    data[i * cols + j] = data[i * cols + j] - m.data[i * cols + j];
                }
            }
            return this;
        }
    }

    //nie jest to właściwe mnożenie i dzielenie macierzy
    Matrix mul(Matrix m)
    {
        if(rows!= m.rows && cols!=m.cols)
            throw new RuntimeException(String.format("%d x %d matrix can't be multiplied to %d x %d",rows,cols,m.rows,m.cols));
        else {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    data[i * cols + j] = data[i * cols + j] * m.data[i * cols + j];
                }
            }
            return this;
        }
    }

    Matrix div(Matrix m)
    {
        if(rows!= m.rows && cols!=m.cols)
            throw new RuntimeException(String.format("%d x %d matrix can't be divided to %d x %d",rows,cols,m.rows,m.cols));
        else {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    data[i * cols + j] = data[i * cols + j] / m.data[i * cols + j];
                }
            }
            return this;
        }
    }

    Matrix add(double w)
    {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    data[i * cols + j] = data[i * cols + j] + w;
                }
            }
            return this;
    }

    Matrix sub(double w)
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i * cols + j] = data[i * cols + j] - w;
            }
        }
        return this;
    }
    Matrix mul(double w)
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i * cols + j] = data[i * cols + j] * w;
            }
        }
        return this;
    }
    Matrix div(double w)
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i * cols + j] = data[i * cols + j] / w;
            }
        }
        return this;
    }

    Matrix dot(Matrix m)
    {
        if(m.rows!=cols)
            throw new RuntimeException(String.format("%d x %d matrix can't be multiplied with %d x %d",rows,cols,m.rows,m.cols));
        else {
            Matrix c = new Matrix(rows, m.cols);
            for(int i=0; i<rows; i++)
            {
                for(int j=0; j<m.cols; j++)
                {
                    double s=0;
                    for(int k=0; k<cols; k++)
                    {
                        s=s+data[i*cols+k]*m.data[k*m.cols+j];
                    }
                    c.data[i*c.cols+j]=s;
                }
            }
            return c;
        }
    }

    double frobenius()
    {
        double w=0;
        for(int i=0; i<rows; i++)
        {
            for(int j=0; j<cols; j++)
            {
                w=w+Math.pow(data[i*cols+j],2);
            }
        }
        w=Math.sqrt(w);
        return w;
    }


    //////// GRUPA C /////////
    Matrix sumRows()
    {
        Matrix a= new Matrix(1, this.cols);

        for(int i=0;i<this.cols;i++)
        {
            double sum=0;
            for(int j=0;j<this.rows;j++)
            {
                sum+=this.data[j*this.cols+i];
            }
            a.data[i] = sum;
        }
        return a;
    }
    /////////////////////////



    public static void main(String[] args)
    {

        Matrix m = new Matrix(new double[][]{{1,2,3},{4,5,6},{7,8,9}});
        Matrix col = m.sumRows();
        col.toString();
        System.out.print(col);
    }
}