package Matrix;

import java.security.InvalidParameterException;

public class Matrix {
    private double[][] mat;
    private int rows, columns;

    public Matrix(int rows, int columns) {
        this.mat = new double[rows][columns];
    }

    public Matrix(int rows, int columns, double[][] mat)
    {
        this.rows = rows;
        this.columns = columns;
        this.mat = mat;
    }

    public Matrix(int rows, int columns, double val) {
        this.mat = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.mat[i][j] = val;
            }
        }
    }

    public static Matrix identity(int rows)
    {
        double[][] m = new double[rows][rows];
        for(int i = 0; i < rows; i++)
        {
            m[i][i] = 1.0;
        }
        return new Matrix(rows, rows, m);
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns()
    {
        return this.columns;
    }

    public double at(int x, int y)
    {
        if(x < 0 || x > this.rows || y < 0 || y > this.columns)
        {
            throw new IllegalArgumentException("Invalid index passed to at");
        }

        return this.mat[x][y];
    }

    private boolean isSquare()
    {
        return this.rows == this.columns;
    }

    private Matrix createSubMatrix(int exclude_row, int exclude_col)
    {
        double mat[][] = new double[this.rows - 1][this.columns - 1];
        int r = -1;
        for (int i = 0; i < this.rows; i++) {
            if (i == exclude_row) {
                continue;
            }
            r++;
            int c = -1;
            for (int j = 0; j < this.columns; j++) {
                if(j == exclude_col)
                {
                    continue;
                }
                mat[r][++c] = this.at(i, j);
            }
        }
        return new Matrix(this.rows - 1, this.columns - 1, mat);
    }

    private double determinant()
    {
        if(!this.isSquare())
        {
            throw new IllegalArgumentException("Matrix is not square");
        }
        if(this.rows == 1)
        {
            return this.at(0, 0);
        }

        if(this.rows == 2)
        {
            return (this.at(0, 0) * this.at(1,1)) - (this.at(0,1) * this.at(1, 0));
        }

        double sum = 0.0;
        int x = 1;
        for (int i = 0; i < this.columns; i++)
        {
            x = (i % 2 == 0)? 1 : -1;
            sum += x * this.at(0, i) * (createSubMatrix(0, i).determinant());
        }
        return sum;
    }

    private Matrix cofactor()
    {
        double mat[][] = new double[this.rows][this.columns];
        for (int i = 0; i < this.rows; i++)
        {
            for (int j = 0; j < this.columns; j++)
            {
                int x = (i % 2 == 0)? 1 : -1;
                int y = (j % 2 == 0)? 1 : -1;
                mat[i][j] = x * y * this.createSubMatrix(i, j).determinant();
            }
        }
        return new Matrix(this.rows, this.columns, mat);
    }

    public Matrix multBy(double by)
    {
        double mat[][] = new double[this.rows][this.columns];
        for (int i = 0; i < this.rows; i++)
        {
            for(int j = 0; j < this.columns; j++)
            {
                mat[i][j] = this.at(i, j) * by;
            }
        }
        return new Matrix(this.rows, this.columns, mat);
    }

    public Matrix inverse()
    {
        return (this.cofactor().multBy(1.0 / this.determinant())).transpose();
    }

    public Matrix transpose()
    {
        double T[][] = new double[this.rows][this.columns];
        for (int i = 0; i < this.rows; i++)
        {
            for (int j = 0; j < this.columns; j++)
            {
                T[j][i] = this.at(i, j);
            }
        }
        return new Matrix(this.rows, this.columns, T);
    }

    public Matrix add(Matrix m)
    {
        if(this.rows != m.getRows() || this.columns != m.getColumns())
        {
            throw new IllegalArgumentException("Both matrices being added must have the same dimensions");
        }
        double res[][] = new double[this.rows][this.columns];
        for (int i = 0; i < this.rows; i++)
        {
            for (int j = 0; j < this.columns; j++)
            {
                res[i][j] = this.at(i, j) + m.at(i, j);
            }
        }
        return new Matrix(this.rows, this.columns, res);
    }

    public Matrix mult(Matrix m)
    {
        if(this.columns != m.getRows())
        {
            throw new IllegalArgumentException("Columns does not equals rows");
        }
        double res[][] = new double[this.rows][m.getColumns()];
        for (int i = 0; i < this.rows; i++)
        {
            for (int j = 0; j < m.getColumns(); j++)
            {
                for (int k = 0; k < this.columns; k++)
                {
                    res[i][j] = res[i][j] + this.at(i, k) * m.at(k, j);
                }
            }
        }

        return new Matrix(this.rows, m.getColumns(), res);
    }

    public Matrix pow(int n) {
        Matrix x = new Matrix(this.rows, this.columns, this.mat);
        if(n < 0)
        {
            // TODO: Implement inverse?
            throw new IllegalArgumentException("Negative exponents are not implemented.");
        }

        if(n == 0)
        {
            return new Matrix(1, 1, 1.0);
        }

        Matrix y = new Matrix(this.rows, this.columns, 1.0);
        while(n > 1) {
            if(n % 2 == 0)
            {
                x = x.mult(x);
                n /= 2;
            }else
            {
                y = x.mult(y);
                x = x.mult(x);
                n = (n - 1) / 2;
            }
        }
        return x.mult(y);
    }
}
