@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainInto Sequence")

package lesson6.task1

import lesson2.task2.daysInMonth

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val date = str.split(" ")
    if (date.size == 3) {
        val day: Int
        val month = when (date[1]) {
            "января" -> 1
            "февраля" -> 2
            "марта" -> 3
            "апреля" -> 4
            "мая" -> 5
            "июня" -> 6
            "июля" -> 7
            "августа" -> 8
            "сентября" -> 9
            "октября" -> 10
            "ноября" -> 11
            "декабря" -> 12
            else -> -1
        }
        val year: Int
        try {
            day = date[0].toInt()
            year = date[2].toInt()
        } catch (e: NumberFormatException) {
            return ""
        }
        if (month != -1 && day in 1..daysInMonth(month, year)) {
            return String.format("%02d.%02d.%d", day, month, year)
        }

    }
    return ""
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val date = try {
        digital.split(".").map { it.toInt() }
    } catch (e: java.lang.NumberFormatException) {
        return ""
    }
    if (date.size == 3) {
        val day = date[0]
        val month = when (date[1]) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            12 -> "декабря"
            else -> ""
        }
        val year = date[2]
        if (month != "" && day in 1..daysInMonth(date[1], year)) {
            return String.format("%d %s %d", day, month, year)
        }
    }
    return ""
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    val unimportant = listOf(' ', '-')
    return if (phone.filterNot { it in unimportant }.matches(Regex("""^(\+?\d+)?(\(\d+\))?(\d+)$"""))) {
        val list = ('0'..'9').toList() + '+'
        phone.filter { it in list }
    } else {
        ""
    }
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String) =
    if (jumps.matches(Regex("""^(\d+|[ %-]+)+$"""))) {
        Regex("""\d+""").findAll(jumps).map { it.value.toInt() }.max() ?: -1
    } else {
        -1
    }

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int =
    if (jumps.matches(Regex("""^(\d+ [+%-]+ )*\d+ [+%-]+$"""))) {
        Regex("""\d+ [+%-]+""").findAll(jumps).map {
            with(it.value.split(" ")) {
                this[0].toInt() to this[1].toSet()
            }
        }.toMap().filterValues { '+' in it }.keys.max() ?: -1
    } else {
        -1
    }

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    require(expression.matches(Regex("""^\d+( [+-] \d+)*$""")))
    var sum = Regex("""^\d+""").find(expression)!!.value.toInt()
    Regex("""[+-] \d+""").findAll(expression).forEach {
        with(it.value.split(" ")) {
            sum += this[1].toInt() * if (this[0] == "+") 1 else -1
        }
    }
    return sum
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String) = Regex("""([^\s]+) \1""").find(str.toLowerCase())?.range?.first ?: -1

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val goods = description.split("; ")
    return if (goods.all { Regex(""".+ \d+(\.\d+)?""").matches(it) }) {
        goods.map {
            with(it.split(" ")) {
                this[0] to this[1].toDouble()
            }
        }.maxBy { it.second }?.first ?: ""
    } else {
        ""
    }
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    val pattern = listOf("^(M{0,3})", "(C[DM]|D?C{0,3})", "(X[LC]|L?X{0,3})", "(I[VX]|V?I{0,3})$")
    if (Regex(pattern.joinToString(separator = "")).matches(roman) && roman.isNotEmpty()) {
        val values = listOf(
            listOf(1000),
            listOf(900, 400, 500, 100),
            listOf(90, 40, 50, 10),
            listOf(9, 4, 5, 1)
        )
        val romanNumbers = listOf(
            listOf("M"),
            listOf("CM", "CD", "D", "C"),
            listOf("XC", "XL", "L", "X"),
            listOf("IX", "IV", "V", "I")
        )
        var romanForm = roman
        var result = 0
        for (i in pattern.size - 1 downTo 0) {
            var part = Regex(pattern[i]).findAll(romanForm).filter { it.value.isNotEmpty() }.firstOrNull()?.value ?: ""
            for (j in romanNumbers[i].indices) {
                if (part != "") {
                    Regex(romanNumbers[i][j]).findAll(part).forEach {
                        result += values[i][j]
                        part = part.replaceFirst(it.value, "")
                        romanForm = romanForm.replaceFirst(it.value, "")
                    }
                }
            }
        }
        return result
    } else {
        return -1
    }
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    require(isValidExpression(commands))
    val braces = braceScanner(commands)
    val state = MutableList(cells) { 0 }
    var count = 0 // число выполненных команд
    var command = 0 // индекс инструкции в commands
    var position = cells / 2
    while (count < limit && command < commands.length) {
        try {
            when (commands[command]) {
                '>' -> position++
                '<' -> position--
                '+' -> state[position]++
                '-' -> state[position]--
                '[' -> if (state[position] == 0) {
                    command = braces.getValue(command)
                }
                ']' -> if (state[position] != 0) {
                    command = braces.getValue(command)
                }
            }
            state[position]
        } catch (e: IndexOutOfBoundsException) {
            throw IllegalStateException()
        }
        count++
        command++
    }
    return state
}

fun braceScanner(commands: String): Map<Int, Int> {
    var depth = 0
    val output = mutableMapOf<Int, Int>()
    val braces = commands.toMutableList().mapIndexed { index, char ->
        when (char) {
            '[' -> depth++
            ']' -> depth--
        }
        index to depth
    }.filter { commands[it.first] in "[]" }
    for (i in braces.indices) {
        if (braces[i].first !in output.values) {
            val pair = braces.first { it.second == braces[i].second - 1 && it.first !in output.keys }.first
            output[braces[i].first] = pair
            output[pair] = braces[i].first
        }
    }
    return output
}

fun isValidExpression(commands: String): Boolean {
    var rightBraces = 0
    var leftBraces = 0
    if (Regex("""^[\[\] <>+-]+$""").matches(commands)) {
        val onlyBraces = commands.filter { it in "[]" }.toList()
        for (char in onlyBraces) {
            when (char) {
                '[' -> leftBraces++
                ']' -> rightBraces++
            }
            if (rightBraces > leftBraces) return false
        }
        return rightBraces == leftBraces
    } else {
        return false
    }
}