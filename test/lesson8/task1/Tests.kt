package lesson8.task1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.lang.Math.ulp
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sqrt

class Tests {
    @Test
    @Tag("Example")
    fun pointDistance() {
        assertEquals(0.0, Point(0.0, 0.0).distance(Point(0.0, 0.0)), 1e-5)
        assertEquals(5.0, Point(3.0, 0.0).distance(Point(0.0, 4.0)), 1e-5)
        assertEquals(50.0, Point(0.0, -30.0).distance(Point(-40.0, 0.0)), 1e-5)
    }

    @Test
    @Tag("Example")
    fun halfPerimeter() {
        assertEquals(6.0, Triangle(Point(0.0, 0.0), Point(0.0, 3.0), Point(4.0, 0.0)).halfPerimeter(), 1e-5)
        assertEquals(2.0, Triangle(Point(0.0, 0.0), Point(0.0, 1.0), Point(0.0, 2.0)).halfPerimeter(), 1e-5)
    }

    @Test
    @Tag("Example")
    fun triangleArea() {
        assertEquals(6.0, Triangle(Point(0.0, 0.0), Point(0.0, 3.0), Point(4.0, 0.0)).area(), 1e-5)
        assertEquals(0.0, Triangle(Point(0.0, 0.0), Point(0.0, 1.0), Point(0.0, 2.0)).area(), 1e-5)
    }

    @Test
    @Tag("Example")
    fun triangleContains() {
        assertTrue(Triangle(Point(0.0, 0.0), Point(0.0, 3.0), Point(4.0, 0.0)).contains(Point(1.5, 1.5)))
        assertFalse(Triangle(Point(0.0, 0.0), Point(0.0, 3.0), Point(4.0, 0.0)).contains(Point(2.5, 2.5)))
    }

    @Test
    @Tag("Example")
    fun segmentEquals() {
        val first = Segment(Point(1.0, 2.0), Point(3.0, 4.0))
        val second = Segment(Point(1.0, 2.0), Point(3.0, 4.0))
        val third = Segment(Point(3.0, 4.0), Point(1.0, 2.0))
        assertEquals(first, second)
        assertEquals(second, third)
        assertEquals(third, first)
    }

    private fun approxEquals(expected: Line, actual: Line, delta: Double): Boolean =
        abs(expected.angle - actual.angle) <= delta && abs(expected.b - actual.b) <= delta

    private fun assertApproxEquals(expected: Line, actual: Line, delta: Double = ulp(10.0)) {
        assertTrue(approxEquals(expected, actual, delta))
    }

    private fun assertApproxNotEquals(expected: Line, actual: Line, delta: Double = ulp(10.0)) {
        assertFalse(approxEquals(expected, actual, delta))
    }

    @Test
    @Tag("Example")
    fun lineEquals() {
        run {
            val first = Line(Point(0.0, 0.0), 0.0)
            val second = Line(Point(3.0, 0.0), 0.0)
            val third = Line(Point(-5.0, 0.0), 0.0)
            val fourth = Line(Point(3.0, 1.0), 0.0)
            assertApproxEquals(first, second)
            assertApproxEquals(second, third)
            assertApproxEquals(third, first)
            assertApproxNotEquals(fourth, first)
        }
        run {
            val first = Line(Point(0.0, 0.0), PI / 2)
            val second = Line(Point(0.0, 3.0), PI / 2)
            val third = Line(Point(0.0, -5.0), PI / 2)
            val fourth = Line(Point(1.0, 3.0), PI / 2)
            assertApproxEquals(first, second)
            assertApproxEquals(second, third)
            assertApproxEquals(third, first)
            assertApproxNotEquals(fourth, first)
        }
        run {
            val first = Line(Point(0.0, 0.0), PI / 4)
            val second = Line(Point(3.0, 3.0), PI / 4)
            val third = Line(Point(-5.0, -5.0), PI / 4)
            val fourth = Line(Point(3.00001, 3.0), PI / 4)
            assertApproxEquals(first, second)
            assertApproxEquals(second, third)
            assertApproxEquals(third, first)
            assertApproxNotEquals(fourth, first)
        }
    }

    @Test
    @Tag("Example")
    fun triangleEquals() {
        val first = Triangle(Point(0.0, 0.0), Point(3.0, 0.0), Point(0.0, 4.0))
        val second = Triangle(Point(0.0, 0.0), Point(0.0, 4.0), Point(3.0, 0.0))
        val third = Triangle(Point(0.0, 4.0), Point(0.0, 0.0), Point(3.0, 0.0))
        val fourth = Triangle(Point(0.0, 4.0), Point(0.0, 3.0), Point(3.0, 0.0))
        assertEquals(first, second)
        assertEquals(second, third)
        assertEquals(third, first)
        assertNotEquals(fourth, first)
    }

    @Test
    @Tag("Easy")
    fun circleDistance() {
        assertEquals(0.0, Circle(Point(0.0, 0.0), 1.0).distance(Circle(Point(1.0, 0.0), 1.0)), 1e-5)
        assertEquals(0.0, Circle(Point(0.0, 0.0), 1.0).distance(Circle(Point(0.0, 2.0), 1.0)), 1e-5)
        assertEquals(1.0, Circle(Point(0.0, 0.0), 1.0).distance(Circle(Point(-4.0, 0.0), 2.0)), 1e-5)
        assertEquals(2.0 * sqrt(2.0) - 2.0, Circle(Point(0.0, 0.0), 1.0).distance(Circle(Point(2.0, 2.0), 1.0)), 1e-5)
    }

    @Test
    @Tag("Trivial")
    fun circleContains() {
        val center = Point(1.0, 2.0)
        assertTrue(Circle(center, 1.0).contains(center))
        assertFalse(Circle(center, 2.0).contains(Point(0.0, 0.0)))
        assertTrue(Circle(Point(0.0, 3.0), 5.01).contains(Point(-4.0, 0.0)))
    }

    @Test
    @Tag("Normal")
    fun diameter() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(1.0, 4.0)
        val p3 = Point(-2.0, 2.0)
        val p4 = Point(3.0, -1.0)
        val p5 = Point(-3.0, -2.0)
        val p6 = Point(0.0, 5.0)
        assertEquals(Segment(p5, p6), diameter(p1, p2, p3, p4, p5, p6))
        assertEquals(Segment(p4, p6), diameter(p1, p2, p3, p4, p6))
        assertEquals(Segment(p3, p4), diameter(p1, p2, p3, p4))
        assertEquals(Segment(p2, p4), diameter(p1, p2, p4))
        assertEquals(Segment(p1, p4), diameter(p1, p4))
    }

    @Test
    @Tag("Easy")
    fun circleByDiameter() {
        assertEquals(Circle(Point(0.0, 1.0), 1.0), circleByDiameter(Segment(Point(0.0, 0.0), Point(0.0, 2.0))))
        assertEquals(Circle(Point(2.0, 1.5), 2.5), circleByDiameter(Segment(Point(4.0, 0.0), Point(0.0, 3.0))))
    }

    @Test
    @Tag("Normal")
    fun crossPoint() {
        assertTrue(
            Point(2.0, 3.0).distance(
                Line(Point(2.0, 0.0), PI / 2).crossPoint(
                    Line(Point(0.0, 3.0), 0.0)
                )
            ) < 1e-5
        )
        assertTrue(
            Point(2.0, 2.0).distance(
                Line(Point(0.0, 0.0), PI / 4).crossPoint(
                    Line(Point(0.0, 4.0), 3 * PI / 4)
                )
            ) < 1e-5
        )
        val p = Point(1.0, 3.0)
        assertTrue(p.distance(Line(p, 1.0).crossPoint(Line(p, 2.0))) < 1e-5)
    }

    @Test
    @Tag("Normal")
    fun lineBySegment() {
        assertApproxEquals(Line(Point(0.0, 0.0), 0.0), lineBySegment(Segment(Point(0.0, 0.0), Point(7.0, 0.0))))
        assertApproxEquals(Line(Point(0.0, 0.0), PI / 2), lineBySegment(Segment(Point(0.0, 0.0), Point(0.0, 8.0))))
        assertApproxEquals(Line(Point(1.0, 1.0), PI / 4), lineBySegment(Segment(Point(1.0, 1.0), Point(3.0, 3.0))))
        assertApproxEquals(Line(Point(1.0, 0.0), 3 * PI / 4), lineBySegment(Segment(Point(1.0, 0.0), Point(0.0, 1.0))))
    }

    @Test
    @Tag("Normal")
    fun lineByPoint() {
        assertApproxEquals(Line(Point(0.0, 0.0), PI / 2), lineByPoints(Point(0.0, 0.0), Point(0.0, 2.0)))
        assertApproxEquals(Line(Point(1.0, 1.0), PI / 4), lineByPoints(Point(1.0, 1.0), Point(3.0, 3.0)))
    }

    @Test
    @Tag("Hard")
    fun bisectorByPoints() {
        assertApproxEquals(Line(Point(2.0, 0.0), PI / 2), bisectorByPoints(Point(0.0, 0.0), Point(4.0, 0.0)))
        assertApproxEquals(Line(Point(1.0, 2.0), 0.0), bisectorByPoints(Point(1.0, 5.0), Point(1.0, -1.0)))
    }

    @Test
    @Tag("Normal")
    fun findNearestCirclePair() {
        val c1 = Circle(Point(0.0, 0.0), 1.0)
        val c2 = Circle(Point(3.0, 0.0), 5.0)
        val c3 = Circle(Point(-5.0, 0.0), 2.0)
        val c4 = Circle(Point(0.0, 7.0), 3.0)
        val c5 = Circle(Point(0.0, -6.0), 4.0)
        assertEquals(Pair(c1, c5), findNearestCirclePair(c1, c3, c4, c5))
        assertEquals(Pair(c2, c4), findNearestCirclePair(c2, c4, c5))
        assertEquals(Pair(c1, c2), findNearestCirclePair(c1, c2, c4, c5))
    }

    @Test
    @Tag("Hard")
    fun circleByThreePoints() {
        val result = circleByThreePoints(Point(5.0, 0.0), Point(3.0, 4.0), Point(0.0, -5.0))
        assertTrue(result.center.distance(Point(0.0, 0.0)) < 1e-5)
        assertEquals(5.0, result.radius, 1e-5)
    }

    @Test
    @Tag("Impossible")
    fun minContainingCircle() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(1.0, 4.0)
        val p3 = Point(-2.0, 2.0)
        val p4 = Point(3.0, -1.0)
        val p5 = Point(-3.0, -2.0)
        val p6 = Point(0.0, 5.0)
        val result = minContainingCircle(p1, p2, p3, p4, p5, p6)
        assertEquals(4.0, result.radius, 0.02)
        for (p in listOf(p1, p2, p3, p4, p5, p6)) {
            assertTrue(result.contains(p))
        }
        val pointList = listOf(
            Point(-632.0, 0.5910215963291883),
            Point(0.06748433240866158, 0.307215805133887),
            Point(0.7588421116968532, 0.23044710679908065),
            Point(0.6664837064303354, 0.6604467788075704),
            Point(0.0, 0.7512984896249393),
            Point(0.9260740090248182, -632.0),
            Point(0.12421542319094392, 0.3991260559324491),
            Point(-632.0, 0.18507616588213294),
            Point(-632.0, -632.0),
            Point(-5e-324, 0.28789043613600085),
            Point(0.4306695417159522, -632.0),
            Point(0.6226104634194284, 0.3785820080324426),
            Point(0.8163932151226277, -5e-324),
            Point(0.8613290426925668, 2.220446049250313e-16),
            Point(0.995477113784152, -63.0),
            Point(-632.0, -2.220446049250313e-16),
            Point(-2.220446049250313e-16, 0.4511854516305388),
            Point(-2.220446049250313e-16, 0.21182551019384255),
            Point(0.0, 0.7227500042569832),
            Point(0.3702783321621441, 0.7317229518820721),
            Point(0.301525767029609, 0.9488584311989561),
            Point(0.0, -2.220446049250313e-16),
            Point(0.12246387893135913, 0.6741136560246903),
            Point(0.33222249336823906, 0.6116074140373909),
            Point(-632.0, 0.5745988401692347),
            Point(0.8420581585516699, -632.0),
            Point(-5e-324, 0.9797444044247222),
            Point(2.220446049250313e-16, 0.40422479177474424),
            Point(-5e-324, 0.993497066095947),
            Point(-632.0, 5e-324),
            Point(0.830245886884665, 0.7884079392961695),
            Point(5e-324, 5e-324),
            Point(-632.0, 0.18600267854280983),
            Point(-632.0, 0.04674517702045222),
            Point(2.220446049250313e-16, -2.220446049250313e-16),
            Point(0.9317160430862177, 0.043472316049603776),
            Point(-632.0, 0.0),
            Point(-632.0, 0.4032673115442139),
            Point(0.0, -632.0),
            Point(5e-324, 5e-324),
            Point(0.38876702652168893, 0.0),
            Point(0.16066340251141853, 0.17798968774018087),
            Point(0.9681428596975281, 0.108332656292078),
            Point(0.9907755062164451, 0.0),
            Point(-632.0, 5e-324),
            Point(-632.0, 0.6677960893786803),
            Point(-632.0, -632.0),
            Point(0.7267507349305136, 0.22301506537947668),
            Point(0.9729992499557317, 0.3550708044201022),
            Point(-632.0, -632.0),
            Point(0.7187238601329652, 0.07329794827623182),
            Point(0.2776871709372497, -632.0),
            Point(0.9429076639195073, -5e-324),
            Point(2.220446049250313e-16, -5e-324),
            Point(-632.0, 0.25217889332353616),
            Point(0.14539235916242033, 0.0),
            Point(-632.0, 0.8133809045502778),
            Point(-2.220446049250313e-16, 0.9638552811303782),
            Point(0.734528057682652, 2.220446049250313e-16),
            Point(-632.0, -632.0),
            Point(0.28698110742461047, -632.0),
            Point(0.0, 0.0),
            Point(0.6092931322009694, -632.0),
            Point(0.0294564877479494, 2.220446049250313e-16),
            Point(0.9545172685553143, 0.8942488006483227),
            Point(-5e-324, 0.7954028976377641),
            Point(2.220446049250313e-16, 0.5800492699789516),
            Point(0.37039736325430117, 0.6744621532015139)
        )
        val result2 = minContainingCircle(*pointList.toTypedArray())
        assertEquals(447.5451246665927, result2.radius, 0.01)
        val pointList2 = listOf(
            Point(0.12948926196037225, 0.5611574594487859),
            Point(0.3765947025516432, 0.8412427409147525),
            Point(0.2151671001999752, -2.220446049250313e-16),
            Point(0.448542865245093, 0.09783942883871521),
            Point(-2.220446049250313e-16, -5e-324),
            Point(-632.0, -632.0),
            Point(0.9259887773013797, 0.17947595176985676),
            Point(0.4748877009760245, 0.5959278714328916),
            Point(0.0774343424122853, -632.0),
            Point(0.00959882852168814, 0.07979019826226197),
            Point(5e-324, 0.36644539369767115),
            Point(0.09684879626223286, -632.0),
            Point(0.6628279467022659, -632.0),
            Point(0.3426158119004463, 0.1420638403261013),
            Point(0.9561320449614241, 0.0),
            Point(-5e-324, -5e-324),
            Point(0.0, 5e-324),
            Point(0.033934962630033594, 0.9184033481205827),
            Point(5e-324, -632.0),
            Point(5e-324, 0.0),
            Point(0.08574232974619611, -632.0),
            Point(0.9834321410032186, -632.0),
            Point(-632.0, 0.8480387767356867),
            Point(-632.0, -632.0),
            Point(0.49179680077505683, -632.0),
            Point(0.9799207937726708, 0.9777240747814058),
            Point(-5e-324, -632.0),
            Point(-632.0, 0.8275463724786956),
            Point(0.1442255927887316, 0.7181083122385215),
            Point(-632.0, -2.220446049250313e-16),
            Point(0.05935231658533935, 0.7388907414420195),
            Point(0.0, 0.16257953832102656),
            Point(-632.0, 2.220446049250313e-16),
            Point(0.031011485538445793, 0.9212118499041141),
            Point(2.220446049250313e-16, -2.220446049250313e-16),
            Point(0.0, -632.0),
            Point(0.0, 0.7495164953694871),
            Point(0.21770472140866892, 0.11842813738127611),
            Point(0.7864439027306626, 0.9980260902052045),
            Point(-632.0, 0.7792005833993221),
            Point(0.30824271616256205, 0.9871415581503811),
            Point(2.220446049250313e-16, 0.0),
            Point(0.8566894765367207, 0.9500628899388676),
            Point(0.07664718122829572, 0.685210893807198),
            Point(-632.0, -632.0),
            Point(0.8849526366071616, 0.5952040868494485),
            Point(2.220446049250313e-16, 0.15200053242351463),
            Point(-5e-324, 2.220446049250313e-16),
            Point(0.7965788788444879, -632.0),
            Point(0.8870018195534899, 2.220446049250313e-16),
            Point(-632.0, 0.9074530983787832),
            Point(-632.0, 0.3313126241186066),
            Point(-5e-324, 0.9375181336777626),
            Point(0.17559930668802315, -632.0),
            Point(0.09703482212979964, 0.4402967137233933),
            Point(0.2236063979302666, -5e-324),
            Point(5e-324, 0.6254343987139674),
            Point(-5e-324, 0.8381686906781071),
            Point(0.3902993223009529, 0.29928919202513216),
            Point(0.0, 0.2516403871007289),
            Point(0.0, -2.220446049250313e-16),
            Point(0.9504606525153741, 0.0),
            Point(2.220446049250313e-16, -632.0),
            Point(0.9418075449853887, -632.0),
            Point(-5e-324, 0.9359157009530917),
            Point(-632.0, 5e-324),
            Point(0.0, 0.0),
            Point(0.0, 0.9644365749573502),
            Point(-5e-324, 0.39848967003495184),
            Point(-632.0, 0.2852710691611088),
            Point(0.8810489863053613, 0.0),
            Point(0.6214720394510884, 0.0),
            Point(0.19145520732661636, 5e-324),
            Point(0.875728833326125, 0.6732013234514287),
            Point(0.4785549644820194, 2.220446049250313e-16),
            Point(0.5701371806408486, -5e-324)
        )
        val result3 = minContainingCircle(*pointList2.toTypedArray())
        assertEquals(447.58361769831333, result3.radius, 0.01)

        val pointList3 = listOf(
            Point(-2.220446049250313e-16, -632.0),
            Point(0.0, -2.220446049250313e-16),
            Point(-2.220446049250313e-16, 0.7090849884249271),
            Point(0.5295011519852262, 0.042202174186386276),
            Point(0.0, -632.0),
            Point(0.6277766378201417, 0.05537131913555515),
            Point(0.6707765883675734, 0.9151776786581015),
            Point(2.220446049250313e-16, 0.3116946619049783),
            Point(0.7233392303094994, -632.0),
            Point(0.6385371531321622, -632.0),
            Point(0.0, 5e-324),
            Point(5e-324, -5e-324),
            Point(-5e-324, 0.0),
            Point(5e-324, 5e-324),
            Point(-632.0, 0.0),
            Point(0.7011607018761238, 0.9204687526008027),
            Point(0.24900271482534486, 5e-324),
            Point(-632.0, 2.220446049250313e-16),
            Point(0.026099945814282877, 0.40404107399922584),
            Point(0.7791501059042018, 0.2223297123436223),
            Point(0.9577876881387395, 0.5406462472752642),
            Point(0.6186737803221529, 0.07856701329520466),
            Point(0.0, -2.220446049250313e-16),
            Point(0.01856528869703855, 0.0),
            Point(5e-324, 0.10157034036674095),
            Point(0.04487465063675766, 0.839091330449286),
            Point(-632.0, -632.0),
            Point(0.0, -632.0),
            Point(0.7966019779458872, 0.9835909776529199),
            Point(-5e-324, 2.220446049250313e-16),
            Point(5e-324, -2.220446049250313e-16),
            Point(0.0, 0.9618632613223554),
            Point(0.7624237559248623, 0.0),
            Point(0.5006302161675604, -2.220446049250313e-16),
            Point(0.7137550677801472, 0.36997374523391324),
            Point(-632.0, -632.0),
            Point(0.7939887872000058, -632.0),
            Point(0.20117917984519273, 5e-324),
            Point(0.0, 0.7039858238639866),
            Point(0.3879245434551736, 5e-324),
            Point(-632.0, 0.6200180577707028),
            Point(-2.220446049250313e-16, 0.6589362511949625),
            Point(0.7952779574098636, 0.0),
            Point(0.0, -632.0),
            Point(0.46529456571283634, 2.220446049250313e-16),
            Point(0.8327414250403521, -632.0),
            Point(-5e-324, 0.5636494831254036),
            Point(-632.0, -632.0),
            Point(-632.0, 0.7860732317128741)
        )
        val result4 = minContainingCircle(*pointList3.toTypedArray())
        assertEquals(447.52088457778905, result4.radius, 0.001)
    }
}