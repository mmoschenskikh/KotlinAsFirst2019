package lesson5.task1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Tests {
    @Test
    @Tag("Example")
    fun shoppingListCostTest() {
        val itemCosts = mapOf(
            "Хлеб" to 50.0,
            "Молоко" to 100.0
        )
        assertEquals(
            150.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко"),
                itemCosts
            )
        )
        assertEquals(
            150.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко", "Кефир"),
                itemCosts
            )
        )
        assertEquals(
            0.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко", "Кефир"),
                mapOf()
            )
        )
    }

    @Test
    @Tag("Example")
    fun filterByCountryCode() {
        val phoneBook = mutableMapOf(
            "Quagmire" to "+1-800-555-0143",
            "Adam's Ribs" to "+82-000-555-2960",
            "Pharmakon Industries" to "+1-800-555-6321"
        )

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+999")
        assertEquals(0, phoneBook.size)
    }

    @Test
    @Tag("Example")
    fun removeFillerWords() {
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я как-то люблю Котлин".split(" "),
                "как-то"
            )
        )
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я как-то люблю таки Котлин".split(" "),
                "как-то",
                "таки"
            )
        )
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я люблю Котлин".split(" "),
                "как-то",
                "таки"
            )
        )
    }

    @Test
    @Tag("Example")
    fun buildWordSet() {
        assertEquals(
            buildWordSet("Я люблю Котлин".split(" ")),
            mutableSetOf("Я", "люблю", "Котлин")
        )
        assertEquals(
            buildWordSet("Я люблю люблю Котлин".split(" ")),
            mutableSetOf("Котлин", "люблю", "Я")
        )
        assertEquals(
            buildWordSet(listOf()),
            mutableSetOf<String>()
        )
    }

    @Test
    @Tag("Easy")
    fun buildGrades() {
        assertEquals(
            mapOf<Int, List<String>>(),
            buildGrades(mapOf())
        )
        assertEquals(
            mapOf(5 to listOf("Михаил", "Семён"), 3 to listOf("Марат")),
            buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
                .mapValues { (_, v) -> v.sorted() }
        )
        assertEquals(
            mapOf(3 to listOf("Марат", "Михаил", "Семён")),
            buildGrades(mapOf("Марат" to 3, "Семён" to 3, "Михаил" to 3))
                .mapValues { (_, v) -> v.sorted() }
        )
    }

    @Test
    @Tag("Easy")
    fun containsIn() {
        assertTrue(containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")))
        assertFalse(containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")))
        assertTrue(
            containsIn(
                mapOf("a" to "z", "abcd" to "abvgd"),
                mapOf("a" to "z", "b" to "c", "lol" to "kek", "chebu" to "rek", "abcd" to "abvgd")
            )
        )
    }

    @Test
    @Tag("Easy")
    fun subtractOf() {
        val from = mutableMapOf("a" to "z", "b" to "c")

        subtractOf(from, mapOf())
        assertEquals(mapOf("a" to "z", "b" to "c"), from)

        subtractOf(from, mapOf("b" to "z"))
        assertEquals(mapOf("a" to "z", "b" to "c"), from)

        subtractOf(from, mapOf("a" to "z"))
        assertEquals(mapOf("b" to "c"), from)

        val from2 = mutableMapOf("a" to "z", "b" to "c", "lol" to "kek", "chebu" to "rek", "abcd" to "abvgd")
        subtractOf(from2, mapOf("a" to "z", "chebu" to "rek"))
        assertEquals(mapOf("b" to "c", "lol" to "kek", "abcd" to "abvgd"), from2)
    }

    @Test
    @Tag("Easy")
    fun whoAreInBoth() {
        assertEquals(
            emptyList<String>(),
            whoAreInBoth(emptyList(), emptyList())
        )
        assertEquals(
            listOf("Marat"),
            whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Marat", "Kirill"))
        )
        assertEquals(
            emptyList<String>(),
            whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Sveta", "Kirill"))
        )
        assertEquals(
            listOf("Alex", "Bob", "Marat"),
            whoAreInBoth(
                listOf("John", "Alex", "Bob", "Ann", "Marat", "Mikhail"),
                listOf("Oleg", "Marat", "Alex", "Bob", "Kirill")
            )
        )
    }

    @Test
    @Tag("Normal")
    fun mergePhoneBooks() {
        assertEquals(
            mapOf("Emergency" to "112"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "112")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "112", "Police" to "02")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112, 911", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "911", "Police" to "02")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112, 911", "Fire department" to "01", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112", "Fire department" to "01"),
                mapOf("Emergency" to "911", "Police" to "02")
            )
        )
        assertEquals(
            mapOf(
                "Emergency" to "112, 911", "Fire department" to "01", "Police" to "02",
                "Alex" to "656-023", "Ann" to "123-45-67", "Bob" to "483-55-95",
                "John" to "656-023, 228-13-37"
            ),
            mergePhoneBooks(
                mapOf(
                    "Emergency" to "112", "Fire department" to "01", "John" to "656-023", "Bob" to "483-55-95"
                ),
                mapOf(
                    "Alex" to "656-023", "John" to "228-13-37", "Ann" to "123-45-67",
                    "Emergency" to "911", "Police" to "02"
                )
            )
        )
    }

    @Test
    @Tag("Normal")
    fun averageStockPrice() {
        assertEquals(
            mapOf<String, Double>(),
            averageStockPrice(listOf())
        )
        assertEquals(
            mapOf("MSFT" to 150.0, "NFLX" to 45.0),
            averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0, "NFLX" to 50.0))
        )
        assertEquals(
            mapOf("MSFT" to 100.0, "NFLX" to 40.0),
            averageStockPrice(listOf("MSFT" to 100.0, "NFLX" to 40.0))
        )
        assertEquals(
            mapOf("MSFT" to 150.0, "NFLX" to 40.0),
            averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
        )
        assertEquals(
            mapOf("MSFT" to 150.0, "NFLX" to 40.0, "IBM" to 100.0, "AAPL" to 77.5),
            averageStockPrice(
                listOf(
                    "MSFT" to 100.0, "IBM" to 100.0, "MSFT" to 200.0, "AAPL" to 59.0, "NFLX" to 40.0,
                    "AAPL" to 96.0
                )
            )
        )
        assertEquals(
            mapOf("MSFT" to 150.0, "NFLX" to 40.0),
            averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
        )
    }

    @Test
    @Tag("Normal")
    fun findCheapestStuff() {
        assertNull(
            findCheapestStuff(
                mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                "торт"
            )
        )
        assertEquals(
            "Мария",
            findCheapestStuff(
                mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                "печенье"
            )
        )
        assertEquals(
            "Тархун",
            findCheapestStuff(
                mapOf(
                    "Coca-Cola" to ("напиток" to 79.90), "Fanta" to ("напиток" to 69.90),
                    "Шишкин лес" to ("напиток" to 35.7), "Тархун" to ("напиток" to 25.0),
                    "Дюшес" to ("напиток" to 789456.0), "Доширак" to ("лапша" to 24.99)
                ),
                "напиток"
            )
        )
    }

    @Test
    @Tag("Normal")
    fun canBuildFrom() {
        assertTrue(canBuildFrom(listOf('a'), ""))
        assertFalse(canBuildFrom(emptyList(), "foo"))
        assertTrue(canBuildFrom(listOf('a', 'b', 'o'), "baobab"))
        assertFalse(canBuildFrom(listOf('a', 'm', 'r'), "Marat"))
    }

    @Test
    @Tag("Normal")
    fun extractRepeats() {
        assertEquals(
            emptyMap<String, Int>(),
            extractRepeats(emptyList())
        )
        assertEquals(
            mapOf("a" to 2),
            extractRepeats(listOf("a", "b", "a"))
        )
        assertEquals(
            emptyMap<String, Int>(),
            extractRepeats(listOf("a", "b", "c"))
        )
        assertEquals(
            mapOf("a" to 3, "лол" to 5),
            extractRepeats(listOf("лол", "a", "a", "лол", "лол", "b", "лол", "c", "a", "лол", "kek", "chebooreck"))
        )
    }

    @Test
    @Tag("Normal")
    fun hasAnagrams() {
        assertTrue(hasAnagrams(listOf("магам", "гамам", "магма", "амагм")))
        assertFalse(hasAnagrams(listOf("ггам", "маг")))
        assertTrue(hasAnagrams(listOf("рот", "свет", "тор")))
        assertFalse(hasAnagrams(listOf("рот", "свет", "код", "дверь")))
        assertFalse(hasAnagrams(emptyList()))
        assertFalse(hasAnagrams(listOf("мафия", "рот", "город", "свет", "бухгалтер", "код", "дверь", "замок")))
    }

    @Test
    @Tag("Hard")
    fun propagateHandshakes() {
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta"),
                "Sveta" to setOf("Mikhail"),
                "Mikhail" to setOf()
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Sveta"),
                    "Sveta" to setOf("Mikhail")
                )
            )
        )
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta"),
                "Sveta" to setOf("Marat", "Mikhail"),
                "Mikhail" to setOf("Sveta", "Marat")
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Mikhail", "Sveta"),
                    "Sveta" to setOf("Marat"),
                    "Mikhail" to setOf("Sveta")
                )
            )
        )
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta"),
                "Sveta" to setOf("Marat", "Mikhail"),
                "Mikhail" to setOf("Sveta", "Marat"),
                "Oleg" to setOf(),
                "Igor" to setOf("Masha", "Oleg", "Mikhail", "Sveta", "Marat"),
                "Masha" to setOf("Oleg")
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Mikhail", "Sveta"),
                    "Sveta" to setOf("Marat"),
                    "Mikhail" to setOf("Sveta"),
                    "Oleg" to setOf(),
                    "Masha" to setOf("Oleg"),
                    "Igor" to setOf("Masha", "Mikhail")
                )
            )
        )
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta", "Vika"),
                "Sveta" to setOf("Marat", "Mikhail", "Vika"),
                "Mikhail" to setOf("Sveta", "Marat", "Vika"),
                "Oleg" to setOf(),
                "Vika" to setOf(),
                "Igor" to setOf("Masha", "Oleg", "Mikhail", "Sveta", "Marat", "Vika"),
                "Masha" to setOf("Oleg")
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Mikhail", "Sveta"),
                    "Sveta" to setOf("Marat", "Vika"),
                    "Mikhail" to setOf("Sveta"),
                    "Oleg" to setOf(),
                    "Masha" to setOf("Oleg"),
                    "Igor" to setOf("Masha", "Mikhail")
                )
            )
        )
        assertEquals(
            mapOf<String, Set<String>>(
                "Marat" to setOf(),
                "Sveta" to setOf()
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf(),
                    "Sveta" to setOf()
                )
            )
        )
        assertEquals(
            mapOf(
                "Marat" to setOf("Sveta"),
                "Sveta" to setOf("Marat")
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Sveta"),
                    "Sveta" to setOf("Marat")
                )
            )
        )
        assertEquals(
            mapOf(
                "2" to setOf(),
                "0" to setOf("2"),
                "1" to setOf("3", "0", "2"),
                "3" to setOf("0", "2")
            ),
            propagateHandshakes(
                mapOf(
                    "2" to setOf(),
                    "0" to setOf("2"),
                    "1" to setOf("3"),
                    "3" to setOf("0")
                )
            )
        )
    }

    @Test
    @Tag("Hard")
    fun findSumOfTwo() {
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(emptyList(), 1)
        )
        assertEquals(
            Pair(0, 2),
            findSumOfTwo(listOf(1, 2, 3), 4)
        )
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(listOf(1, 2, 3), 6)
        )
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(listOf(2, 4, 5, 7, 10, 1337), 10)
        )
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(listOf(98, 180, -404, 2, 4, 5, 7, 10, 1337), -123)
        )
        assertEquals(
            Pair(0, 2),
            findSumOfTwo(listOf(12, 10, 8, 6, 4, 2), 20)
        )
        assertEquals(
            Pair(0, 3),
            findSumOfTwo(listOf(12, 12, 12, 8, 8, 10, 10, 8, 6, 4, 2), 20)
        )
        assertEquals(
            Pair(0, 1),
            findSumOfTwo(listOf(9, -3, 1, 3, 2, 3, 1, 2, 3), 6)
        )
    }

    @Test
    @Tag("Impossible")
    fun bagPacking() {
        assertEquals(
            setOf("Кубок"),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                850
            )
        )
        assertEquals(
            emptySet<String>(),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                450
            )
        )
        assertEquals(
            setOf("Гитара", "Ноутбук"),
            bagPacking(
                mapOf("Магнитофон" to (4 to 3000), "Ноутбук" to (3 to 2000), "Гитара" to (1 to 1500)), 4
            )
        )
        assertEquals(
            setOf("iPhone", "Ноутбук"),
            bagPacking(
                mapOf(
                    "iPhone" to (1 to 2000),
                    "Магнитофон" to (4 to 3000),
                    "Ноутбук" to (3 to 2000),
                    "Гитара" to (1 to 1500)
                ), 4
            )
        )
    }

    // TODO: map task tests
}
