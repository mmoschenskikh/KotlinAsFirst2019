@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)

    fun forEachRow(action: (MutableList<E>) -> Unit)

    fun forEachColumn(action: (MutableList<E>) -> Unit)

    fun forEachRowIndexed(action: (index: Int, MutableList<E>) -> Unit)

    fun forEachColumnIndexed(action: (index: Int, MutableList<E>) -> Unit)

    fun mapRows(transform: (MutableList<E>) -> MutableList<E>): Matrix<E>

    fun mapRowsIndexed(transform: (index: Int, MutableList<E>) -> MutableList<E>): Matrix<E>

    fun mapColumns(transform: (MutableList<E>) -> MutableList<E>): Matrix<E>

    fun mapColumnsIndexed(transform: (index: Int, MutableList<E>) -> MutableList<E>): Matrix<E>

}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    require(width > 0 && height > 0)
    return MatrixImpl(height, width, e)
}

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {

    private val list = MutableList(height) { MutableList(width) { e } }

    override fun get(row: Int, column: Int): E = list[row][column]

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        list[row][column] = value
    }

    override fun set(cell: Cell, value: E) {
        set(cell.row, cell.column, value)
    }

    override fun equals(other: Any?): Boolean {
        if (other is MatrixImpl<*> && height == other.height && width == other.width) {
            for (i in 0 until height) {
                for (j in 0 until width) {
                    if (this[i, j] != other[i, j]) {
                        return false
                    }
                }
            }
        }
        return true
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("[")
        for (row in 0 until height) {
            sb.append(list[row].joinToString(prefix = "[", postfix = "]"))
            if (row != height - 1) sb.append(",\n")
        }
        sb.append("]")
        return "$sb"
    }

    override fun forEachRow(action: (MutableList<E>) -> Unit) {
        for (row in list) action(row)
    }

    override fun forEachColumn(action: (MutableList<E>) -> Unit) {
        for (i in 0 until width) {
            val column = mutableListOf<E>()
            for (j in 0 until height) {
                column.add(this[j, i])
            }
            action(column)
        }
    }

    override fun forEachRowIndexed(action: (index: Int, MutableList<E>) -> Unit) {
        var index = 0
        for (row in list) action(index++, row)
    }

    override fun forEachColumnIndexed(action: (index: Int, MutableList<E>) -> Unit) {
        for (index in 0 until width) {
            val column = mutableListOf<E>()
            for (j in 0 until height) {
                column.add(this[j, index])
            }
            action(index, column)
        }
    }

    override fun mapRows(transform: (MutableList<E>) -> MutableList<E>): Matrix<E> {
        val matrix = createMatrix(height, width, this[0, 0])
        forEachRowIndexed { rowIndex, row ->
            transform(row).forEachIndexed { columnIndex, e -> matrix[rowIndex, columnIndex] = e }
        }
        return matrix
    }

    override fun mapRowsIndexed(transform: (index: Int, MutableList<E>) -> MutableList<E>): Matrix<E> {
        var index = 0
        val matrix = createMatrix(height, width, this[0, 0])
        forEachRowIndexed { rowIndex, row ->
            transform(index++, row).forEachIndexed { columnIndex, e -> matrix[rowIndex, columnIndex] = e }
        }
        return matrix
    }

    override fun mapColumns(transform: (MutableList<E>) -> MutableList<E>): Matrix<E> {
        val matrix = createMatrix(height, width, this[0, 0])
        forEachColumnIndexed { columnIndex, column ->
            transform(column).forEachIndexed { rowIndex, e -> matrix[rowIndex, columnIndex] = e }
        }
        return matrix
    }

    override fun mapColumnsIndexed(transform: (index: Int, MutableList<E>) -> MutableList<E>): Matrix<E> {
        val matrix = createMatrix(height, width, this[0, 0])
        forEachColumnIndexed { columnIndex, column ->
            transform(columnIndex, column).forEachIndexed { rowIndex, e -> matrix[rowIndex, columnIndex] = e }
        }
        return matrix
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + list.hashCode()
        return result
    }
}
