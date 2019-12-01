@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

/**
 * Класс "полином с вещественными коэффициентами".
 *
 * Общая сложность задания -- сложная.
 * Объект класса -- полином от одной переменной (x) вида 7x^4+3x^3-6x^2+x-8.
 * Количество слагаемых неограничено.
 *
 * Полиномы можно складывать -- (x^2+3x+2) + (x^3-2x^2-x+4) = x^3-x^2+2x+6,
 * вычитать -- (x^3-2x^2-x+4) - (x^2+3x+2) = x^3-3x^2-4x+2,
 * умножать -- (x^2+3x+2) * (x^3-2x^2-x+4) = x^5+x^4-5x^3-3x^2+10x+8,
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+16
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 2.0, 0.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 */
class Polynom(vararg coeffs: Double) {

    private val coeffList = coeffs

    private val significantCoeffs =
        if (coeffList.all { it == 0.0 }) listOf(0.0) else coeffList.dropWhile { it == 0.0 }.reversed()

    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int): Double = significantCoeffs.reversed()[i]

    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double {
        var value = 0.0
        var variable = 1.0
        significantCoeffs.forEach { coeff ->
            value += coeff * variable
            variable *= x
        }
        return value
    }

    /**
     * Степень (максимальная степень x при ненулевом слагаемом, например 2 для x^2+x+1).
     *
     * Степень полинома с нулевыми коэффициентами считать равной 0.
     * Слагаемые с нулевыми коэффициентами игнорировать, т.е.
     * степень 0x^2+0x+2 также равна 0.
     */
    fun degree(): Int = significantCoeffs.size - 1

    /**
     * Сложение
     */
    operator fun plus(other: Polynom): Polynom =
        if (degree() >= other.degree()) {
            Polynom(
                *(significantCoeffs
                    .take(other.degree() + 1).zip(other.significantCoeffs).map { it.first + it.second }
                        + significantCoeffs.takeLast(degree() - other.degree()))
                    .reversed().toDoubleArray())
        } else {
            (other + this)
        }

    /**
     * Смена знака (при всех слагаемых)
     */
    operator fun unaryMinus(): Polynom = Polynom(*significantCoeffs.reversed().map { -it }.toDoubleArray())

    /**
     * Вычитание
     */
    operator fun minus(other: Polynom): Polynom = this + -other

    /**
     * Умножение
     */
    operator fun times(other: Polynom): Polynom {
        val result = mutableMapOf<Int, MutableList<Double>>()
        for (i in other.significantCoeffs.indices) {
            for (j in significantCoeffs.indices) {
                if (result[i + j] == null) result[i + j] = mutableListOf()
                result[i + j]!!.add(other.coeff(i) * coeff(j))
            }
        }
        return Polynom(
            *result.mapValues { it.value.sum() }.toList().sortedByDescending { it.first }.map { it.second }.reversed().toDoubleArray()
        )
    }

    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     */
    operator fun div(other: Polynom): Polynom {
        val result = MutableList(degree() - other.degree() + 1) { 0.0 }
        var minuend: Polynom
        var subtrahend: Polynom
        var remainder = this
        while (remainder.degree() >= other.degree() && remainder != Polynom(0.0)) {
            minuend = remainder
            val k = remainder.degree() - other.degree()
            result[k] = remainder.coeff(0) / other.coeff(0)
            subtrahend = polynomByLeading(result[k], k) * other
            remainder = minuend - subtrahend
        }
        return Polynom(*result.reversed().toDoubleArray())
    }

    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom): Polynom = this - other * (this / other)

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean =
        other is Polynom && significantCoeffs == other.significantCoeffs

    /**
     * Получение хеш-кода
     */
    override fun hashCode(): Int {
        var result = degree()
        significantCoeffs.forEach {
            result = result * 31 + it.hashCode()
        }
        return result
    }

    override fun toString(): String = significantCoeffs.reversed().joinToString()
}

fun polynomByLeading(coeff: Double, degree: Int) = Polynom(coeff, *List(degree) { 0.0 }.toDoubleArray())
