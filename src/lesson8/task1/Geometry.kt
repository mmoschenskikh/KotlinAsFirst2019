@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import lesson1.task1.sqr
import kotlin.math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
@Suppress("MemberVisibilityCanBePrivate")
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {

    constructor(a: Point, b: Point, c: Point) : this(
        bisectorByPoints(a, b).crossPoint(bisectorByPoints(b, c)),
        a.distance(b) * b.distance(c) * c.distance(a) / (4 * Triangle(a, b, c).area())
    )

    constructor(diameter: Segment) : this(
        diameter.middlePoint(),
        diameter.begin.distance(diameter.end) / 2
    )

    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        val sumOfRadii = radius + other.radius
        val centerDistance = center.distance(other.center)
        return if (sumOfRadii < centerDistance) {
            centerDistance - sumOfRadii
        } else {
            0.0
        }
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = p.distance(center) <= radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
        other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
        begin.hashCode() + end.hashCode()

    fun middlePoint() = Point((begin.x + end.x) / 2, (begin.y + end.y) / 2)
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    require(points.size >= 2)
    val segmentPoints = points.map { point ->
        point to points.maxBy { it.distance(point) }
    }.maxBy { it.first.distance(it.second!!) }
    return Segment(segmentPoints!!.first, segmentPoints.second!!)
}


/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle = Circle(diameter)

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line) = Point(
        (b * cos(other.angle) - other.b * cos(angle)) / sin(other.angle - angle),
        (b * sin(other.angle) - other.b * sin(angle)) / sin(other.angle - angle)
    )

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {
    val incY = s.begin.y - s.end.y
    val incX = s.begin.x - s.end.x
    val angle = if (incX == 0.0) PI / 2 else (atan(incY / incX) + PI) % PI
    return Line(s.begin, angle)
}

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = lineBySegment(Segment(a, b))

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point) =
    Line(Segment(a, b).middlePoint(), (lineByPoints(a, b).angle + PI / 2) % PI)

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    require(circles.size >= 2)
    val circleSet = circles.toSet()
    val twoCircles = circleSet.map { circle ->
        circle to (circleSet - circle).minBy { it.distance(circle) }
    }.minBy { it.first.distance(it.second!!) }
    return (twoCircles!!.first to twoCircles.second!!)
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle = Circle(a, b, c)

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {
    require(points.isNotEmpty())
    val size = points.size
    if (size == 1) return Circle(points[0], 0.0)
    val circles = MutableList(3) { Circle(Segment(points[0], points[1])) }
    for (i in 3..size) {
        if (circles[i - 1].contains(points[i - 1])) {
            circles.add(circles[i - 1])
        } else {
            circles.add(minDiskWithPoint(points.take(i - 1), points[i - 1]))
        }
    }
    return circles[size]
}

fun minDiskWithPoint(s: List<Point>, q: Point): Circle {
    val circles = MutableList(2) { Circle(Segment(s[0], q))}
    for (i in 2..s.size) {
        if (circles[i - 1].contains(s[i - 1])) {
            circles.add(circles[i - 1])
        } else {
            circles.add(minDiskWith2Points(s.take(i - 1), s[i - 1], q))
        }
    }
    return circles[s.size]
}

fun minDiskWith2Points(p: List<Point>, q1: Point, q2: Point): Circle {
    val d = mutableListOf(Circle(Segment(q1, q2)))
    for (i in 1..p.size) {
        if (d[i - 1].contains(p[i - 1])) {
            d.add(d[i - 1])
        } else {
            d.add(Circle(q1, q2, p[i - 1]))
        }
    }
    return d[p.size]
}