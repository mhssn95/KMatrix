package io.mhssn.kmatrix

import com.jakewharton.picnic.table

class Matrix {

    var rowSize: Int = 0
        private set
    var columnSize: Int = 0
        private set

    private var values = mutableListOf<Double>()

    /**
     * @param matrix A matrix creator function
     * call [row] to insert a row in the matrix
     */
    constructor(matrix: Matrix.() -> Unit) : super() {
        matrix()
    }

    private constructor(rowSize: Int, columnSize: Int, values: DoubleArray) : super() {
        this.rowSize = rowSize
        this.columnSize = columnSize
        this.values.addAll(values.toList())
        values.toList()
    }

    init {
        if (values.size != columnSize * rowSize) {
            throw IllegalArgumentException("Incorrect matrix values")
        }
    }

    /**
     * Add a new row to the matrix
     * @param columns the new row columns
     * @throws IllegalArgumentException if the size of the columns is not equal for all rows
     */
    @RowMarker
    fun Matrix.row(vararg columns: Number) {
        if (columnSize == 0) {
            columnSize = columns.size
        } else if (columnSize != columns.size) {
            throw IllegalArgumentException("Column size must be equal")
        }
        rowSize++
        this.values.plusAssign(columns.map { it.toDouble() }.toList())
    }

    @RowMarker
    fun Matrix.row(columns: List<Number>) {
        row(*columns.toTypedArray())
    }

    /**
     * Return a transposed matrix
     */
    fun transpose(): Matrix {
        val newValues = ArrayList<Double>()
        repeat(columnSize) {
            newValues.addAll(getColumn(it))
        }
        return Matrix(columnSize, rowSize, newValues.toDoubleArray())
    }

    /**
     * Return a result [matrix] from the two matrices multiplication
     */
    operator fun times(matrix: Matrix): Matrix {
        if (columnSize != matrix.rowSize) {
            throw IllegalArgumentException("can't multiply ($rowSize, $columnSize) matrix to (${matrix.rowSize}, ${matrix.columnSize}) matrix")
        }
        var row = 0
        val result = Array(rowSize * matrix.columnSize) {
            if (it != 0 && it % matrix.columnSize == 0) {
                row++
            }
            getRow(row).zip(matrix.getColumn(it % matrix.columnSize)) { r, c ->
                r * c
            }.sum()
        }
        return Matrix(rowSize, matrix.columnSize, result.toDoubleArray())
    }

    /**
     * Return a result [matrix] from adding the two matrices
     */
    operator fun plus(matrix: Matrix): Matrix {
        if (columnSize != matrix.columnSize || rowSize != matrix.rowSize) {
            throw IllegalArgumentException("The dimensions of the two matrices must be equal")
        }
        val result = values.zip(matrix.values) { a, b ->
            a + b
        }
        return Matrix(rowSize, columnSize, result.toDoubleArray())
    }

    /**
     * Return a result [matrix] from subtracting the two matrices
     */
    operator fun minus(matrix: Matrix): Matrix {
        if (columnSize != matrix.columnSize || rowSize != matrix.rowSize) {
            throw IllegalArgumentException("The dimensions of the two matrices must be equal")
        }
        val result = values.zip(matrix.values) { a, b ->
            a - b
        }
        return Matrix(rowSize, columnSize, result.toDoubleArray())
    }

    /**
     * Return the sum of all the elements in the matrix
     */
    fun sum(): Double {
        return values.sum()
    }

    /**
     * Get a single row from the matrix
     * @param location the value row index, column index
     * @return the row numbers
     */
    operator fun get(vararg location: Int): Double {
        if (location.size < 2) {
            throw IllegalArgumentException("x, y indexes must be provided")
        }
        if (location[1] !in 0 until rowSize) {
            throw IndexOutOfBoundsException("y value is out of rows bound")
        }
        if (location[0] !in 0 until columnSize) {
            throw IndexOutOfBoundsException("x value is out of columns bound")
        }
        return getRow(location[1])[location[0]]
    }

    /**
     * Get a single row from the matrix
     * @param index the row index
     * @return the row numbers
     */
    fun getRow(index: Int): List<Double> {
        if (index !in 0 until rowSize) {
            throw IndexOutOfBoundsException("y value is out of rows bound")
        }
        return values.slice(index * columnSize until index * columnSize + columnSize)
    }

    /**
     * Get a single column from the matrix
     * @param index the column index
     * @return the column numbers
     */
    fun getColumn(index: Int): List<Double> {
        if (index !in 0 until columnSize) {
            throw IndexOutOfBoundsException("x value is out of columns bound")
        }
        return List(rowSize) {
            getRow(it)[index]
        }
    }

    /**
     * Returns a Matrix containing the results of applying the given [transform] function
     * to each element in the original Matrix.
     * @param transform the transformation function
     * @return [Matrix]
     */
    fun map(transform: (Double) -> Number): Matrix {
        val values = values.map { transform(it).toDouble() }
        return Matrix(rowSize, columnSize, values.toDoubleArray())
    }

    /**
     * Performs the given [action] on each element.
     */
    inline fun forEach(action: (d: Double) -> Unit) {
        iterator().forEach {
            action(it)
        }
    }

    /**
     * Performs the given [action] on each element, providing sequential x, y location with the element.
     * @param [action] function that takes the x, y of an element and the element itself
     * and performs the action on the element.
     */
    inline fun forEachIndexed(action: (x: Int, y: Int, d: Double) -> Unit) {
        var index = 0
        iterator().forEach {
            action(index % columnSize, index / rowSize, it)
            index++
        }
    }

    /**
     * Checks if the specified element is contained in this matrix.
     */
    fun contains(element: Number): Boolean {
        return values.contains(element.toDouble())
    }

    /**
     * Checks if all elements in the specified collection are contained in this matrix.
     */
    fun containsAll(elements: Collection<Number>): Boolean {
        return values.containsAll(elements.map { it.toDouble() })
    }

    /**
     * Returns the x, y location of the first occurrence of the specified element in the matrix, or null if the specified
     * element is not contained in the matrix.
     */
    fun indexOf(element: Double): Pair<Int, Int>? {
        val index = values.indexOf(element)
        if (index != -1) {
            return index % columnSize to index / rowSize
        }
        return null
    }

    /**
     * Returns the x, y of the last occurrence of the specified element in the matrix, or null if the specified
     * element is not contained in the matrix.
     * @param element the element whose location is to be found
     * @return [Pair] x, y location
     */
    fun lastIndexOf(element: Double): Pair<Int, Int>? {
        val index = values.lastIndexOf(element)
        if (index != -1) {
            return index % columnSize to index / rowSize
        }
        return null
    }

    /**
     * Returns all matrix elements as a list
     */
    fun toList(): List<Double> {
        return values.toList()
    }

    /**
     * Return iterator for all matrix elements
     * @return [Iterator]
     */
    fun iterator(): Iterator<Double> {
        return values.iterator()
    }

    override fun toString(): String {
        val table = table {
            cellStyle {
                border = true
                paddingLeft = 1
                paddingRight = 1
            }
            repeat(rowSize) { row ->
                row(*getRow(row).toTypedArray())
            }
        }
        return table.toString()
    }

    override fun equals(other: Any?): Boolean {
        other?.let {
            if (it is Matrix) {
                return values == it.values && rowSize == it.rowSize && columnSize == it.columnSize
            }
        }
        return false
    }

    override fun hashCode(): Int {
        var result = rowSize
        result = 31 * result + columnSize
        result = 31 * result + values.hashCode()
        return result
    }

}

@DslMarker
annotation class RowMarker