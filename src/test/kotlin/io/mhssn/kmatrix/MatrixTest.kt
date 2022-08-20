package io.mhssn.kmatrix

import org.junit.jupiter.api.assertThrows
import kotlin.random.Random
import kotlin.test.Test

internal class MatrixTest {

    @Test
    fun `Count matrix row and column size`() {
        val matrix = Matrix {
            row(1, 2, 3, 4)
            row(1, 2, 3, 3)
            row(1, 2, 3, 4)
            row(1, 2, 3, 5)
        }
        assert(matrix.rowSize == 4 && matrix.columnSize == 4)
    }

    @Test
    fun `Create unequal column size matrix`() {
        assertThrows<IllegalArgumentException> {
            Matrix {
                row(1, 2, 3, 4)
                row(1, 2, 3)
                row(1, 2, 3)
            }
        }
    }

    @Test
    fun `Add two matrices`() {
        val matrix1 = Matrix {
            row(1, 2, 3.2)
            row(2, 4, 5.3)
            row(4, 5, 3.2)
        }
        val matrix2 = Matrix {
            row(34, 42, 25)
            row(40, 10, 10)
            row(20, 22, 23)
        }
        val result = matrix1 + matrix2
        val expected = Matrix {
            row(35, 44, 28.2)
            row(42, 14, 15.3)
            row(24, 27, 26.2)
        }
        assert(result == expected)
    }

    @Test
    fun `Add unequal matrices dimensions`() {
        val matrix1 = Matrix {
            row(1, 2, 3)
            row(1, 2, 3)
        }
        val matrix2 = Matrix {
            row(1, 2, 3)
            row(1, 2, 3)
            row(1, 2, 3)
        }
        assertThrows<IllegalArgumentException> { matrix1 + matrix2 }
    }

    @Test
    fun `Subtract two matrices`() {
        val matrix1 = Matrix {
            row(43, 23, 11)
            row(3, 12, 41)
            row(49, 53, 32)
        }
        val matrix2 = Matrix {
            row(13, 49, 93)
            row(44, 80, 14)
            row(21, 66, 12)
        }
        val result = matrix1 - matrix2
        val expected = Matrix {
            row(30, -26, -82)
            row(-41, -68, 27)
            row(28, -13, 20)
        }
        assert(result == expected)
    }

    @Test
    fun `Subtract unequal matrices dimensions`() {
        val matrix1 = Matrix {
            row(1, 2, 3)
            row(1, 2, 3)
        }
        val matrix2 = Matrix {
            row(1, 2, 3)
            row(1, 2, 3)
            row(1, 2, 3)
        }
        assertThrows<IllegalArgumentException> { matrix1 - matrix2 }
    }

    @Test
    fun `Get the sum of the matrix`() {
        val values = Array(20) {
            Random.nextDouble()
        }
        val matrix = Matrix {
            values.asIterable().chunked(4) {
                row(it)
            }
        }
        assert(values.sum() == matrix.sum())
    }

    @Test
    fun `get the transposed a matrix`() {
        val matrix = Matrix {
            row(1, 2, 3)
            row(4, 5, 6)
            row(7, 8, 9)
        }
        val transposed = Matrix {
            row(1, 4, 7)
            row(2, 5, 8)
            row(3, 6, 9)
        }
        assert(matrix.transpose() == transposed)
    }

    @Test
    fun `get a value form a matrix`() {
        val matrix = Matrix {
            row(0, 1, 2)
            row(3, 4, 5)
            row(6, 7, 8)
        }
        val x = Random.nextInt(matrix.columnSize)
        val y = Random.nextInt(matrix.rowSize)
        val result = x + (y * matrix.columnSize).toDouble()
        val value = matrix[x, y]
        assert(value == result)
    }

    @Test
    fun `get a value from a matrix without y index`() {
        val matrix = Matrix {
            row(1, 2, 3)
            row(4, 5, 6)
            row(7, 8, 9)
        }
        assertThrows<IllegalArgumentException> { matrix[0] }
    }

    @Test
    fun `get a value from a matrix with wrong indexes`() {
        val matrix = Matrix {
            row(1, 2, 3)
            row(4, 5, 6)
            row(7, 8, 9)
        }
        val x = matrix.columnSize
        val y = matrix.rowSize
        assertThrows<IndexOutOfBoundsException> { matrix[x, y] }
    }

    @Test
    fun `get a value from a matrix with minus indexes`() {
        val matrix = Matrix {
            row(1, 2, 3)
            row(4, 5, 6)
            row(7, 8, 9)
        }
        val x = -1
        val y = -1
        assertThrows<IndexOutOfBoundsException> { matrix[x, y] }
    }

    @Test
    fun `get a row from a matrix`() {
        val rows = arrayOf(
            arrayOf(1.0, 2.0, 3.0),
            arrayOf(4.0, 5.0, 6.0),
            arrayOf(7.0, 8.0, 9.0),
        )
        val matrix = Matrix {
            rows.forEach {
                row(*it)
            }
        }
        val index = Random.nextInt(matrix.rowSize)
        val row = matrix.getRow(index)
        assert(row == rows[index].toList())
    }

    @Test
    fun `get a column from a matrix`() {
        val rows = arrayOf(
            arrayOf(1.0, 2.0, 3.0),
            arrayOf(4.0, 5.0, 6.0),
            arrayOf(7.0, 8.0, 9.0),
        )
        val columns = rows.map { it.withIndex() }.flatten().groupBy { it.index }.map { it.value.map { it.value } }
        val matrix = Matrix {
            rows.forEach {
                row(*it)
            }
        }
        val index = Random.nextInt(matrix.columnSize)
        assert(matrix.getColumn(index) == columns[index])
    }

    @Test
    fun `map a matrix`() {
        val matrix = Matrix {
            row(1, 2, 3)
            row(4, 5, 6)
            row(7, 8, 9)
        }
        val expected = Matrix {
            row(0.5, 1, 1.5)
            row(2, 2.5, 3)
            row(3.5, 4, 4.5)
        }
        assert(matrix.map { it / 2 } == expected)
    }

    @Test
    fun `matrix contains a value`() {
        val matrix = Matrix {
            row(1, 2, 3)
            row(4, 5, 6)
            row(7, 8, 9)
        }
        val x = Random.nextInt(matrix.columnSize)
        val y = Random.nextInt(matrix.rowSize)
        assert(matrix.contains(matrix[x, y]))
    }

    @Test
    fun `matrix contains a list of values`() {
        val matrix = Matrix {
            row(1, 2, 3)
            row(4, 5, 6)
            row(7, 8, 9)
        }
        val row = matrix.getRow(Random.nextInt(matrix.rowSize))
        assert(matrix.containsAll(row))
    }

    @Test
    fun `test matrix foreach`() {
        val matrix = Matrix {
            row(1, 2, 3)
            row(4, 5, 6)
            row(7, 8, 9)
        }
        var i = 1
        matrix.forEach {
            if (it != i.toDouble()) {
                assert(false)
            }
            i++
        }
        assert(true)
    }

    @Test
    fun `test matrix foreach indexed`() {
        val matrix = Matrix {
            row(1, 2, 3)
            row(4, 5, 6)
            row(7, 8, 9)
        }
        var i = 1
        var c = 0
        var r = 0
        var counter = 0
        matrix.forEachIndexed { x, y, d ->
            if (i.toDouble() != d || x != c || y != r) {
                assert(false)
            }
            i++
            counter++
            c = (c + 1) % matrix.columnSize
            r = counter / matrix.rowSize
        }
        assert(true)
    }

    @Test
    fun `test index of`() {
        val matrix = Matrix {
            row(1, 2, 3)
            row(4, 5, 6)
            row(7, 8, 9)
        }
        val x = Random.nextInt(matrix.columnSize)
        val y = Random.nextInt(matrix.rowSize)
        val element = matrix[x, y]
        assert(matrix.indexOf(element) == x to y)
    }

    @Test
    fun `test last index of`() {
        val matrix = Matrix {
            row(1, 2, 3)
            row(1, 2, 3)
            row(1, 2, 3)
        }
        val x = Random.nextInt(matrix.columnSize)
        val y = Random.nextInt(matrix.rowSize)
        val element = matrix[x, y]
        val expectedIndex = x to matrix.rowSize - 1
        assert(matrix.lastIndexOf(element) == expectedIndex)
    }

    @Test
    fun `test matrix equals`() {
        val matrix1 = Matrix {
            row(1, 2, 3)
            row(4, 5, 6)
            row(7, 8, 9)
        }
        val matrix2 = Matrix {
            row(1, 2, 3)
            row(4, 5, 6)
            row(7, 8, 9)
        }
        assert(matrix1 == matrix2)
    }

    @Test
    fun `test to List`() {
        val values = listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        val rows = values.chunked(3)
        val matrix = Matrix {
            rows.forEach {
                row(*it.toTypedArray())
            }
        }
        assert(matrix.toList() == values)
    }

}