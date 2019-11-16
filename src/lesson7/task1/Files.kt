@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import lesson3.task1.digitNumber
import java.io.File

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val output = substrings.toSet().map { it to 0 }.toMap().toMutableMap()
    File(inputName).readLines().forEach { line ->
        val string = line.toLowerCase()
        substrings.toSet().forEach { substring ->
            for (i in string.indices) {
                if (
                    substring.length + i <= string.length &&
                    string.substring(i, i + substring.length) == substring.toLowerCase()
                ) {
                    output[substring] = output.getValue(substring) + 1
                }
            }
        }
    }
    return output
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) =
    File(outputName).bufferedWriter().use {
        File(inputName).readLines().forEach { line ->
            it.write(
                Regex("""[ЖжЧчШшЩщ][ЫыЯяЮю]""").replace(line) { found ->
                    val match = found.value[0] to found.value[1] // Всегда находятся ровно две буквы
                    match.first + when (match.second.toLowerCase()) {
                        'ы' -> "и"
                        'я' -> "а"
                        else -> "у"
                    }.run { if (match.second.isUpperCase()) this.toUpperCase() else this }
                }
            )
            it.newLine()
        }
    }

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val inputStream = File(inputName).readLines()
    val maxLength = inputStream.maxBy { it.trim().length }?.trim()?.length ?: 0
    File(outputName).bufferedWriter().use {
        inputStream.forEach { line ->
            val string = line.trim()
            val length = (maxLength + string.length) / 2
            it.write(String.format("%${length}s", string))
            it.newLine()
        }
    }
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов между словами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val inputStream = File(inputName).readLines()
    val maxLength =
        inputStream.map { Regex(""" +""").split(it.trim()).joinToString(separator = " ") }
            .maxBy { it.length }?.length ?: 0
    File(outputName).bufferedWriter().use {
        inputStream.forEach { line ->
            val words = Regex(""" +""").split(line.trim())
            when {
                line.isEmpty() -> it.newLine()
                words.size == 1 -> {
                    it.write(words[0])
                    it.newLine()
                }
                else -> {
                    val wordsLength = words.fold(0) { prev, current -> prev + current.length }
                    val gapsLength = maxLength - wordsLength
                    val gapsCount = words.size - 1
                    val gapSize = (gapsLength) / gapsCount
                    val biggerGapsCount = gapsLength - gapSize * gapsCount
                    val gaps =
                        List(biggerGapsCount) { ' ' * (gapSize + 1) } + List(gapsCount - biggerGapsCount) { ' ' * gapSize }
                    for (i in gaps.indices) {
                        it.write(words[i] + gaps[i])
                    }
                    it.write(words.last())
                    it.newLine()
                }
            }
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val top = mutableMapOf<String, Int>()
    File(inputName).readLines().forEach { line ->
        Regex("""[a-zA-Zа-яА-ЯёЁ]+""").findAll(line)
            .map { it.value.toLowerCase() }
            .forEach { word ->
                top[word] = top.getOrDefault(word, 0) + 1
            }
    }
    return with(top.toList().sortedByDescending { it.second }) {
        try {
            this.subList(0, 20).toMap()
        } catch (e: IndexOutOfBoundsException) {
            this.toMap()
        }
    }
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    if (dictionary.isNotEmpty()) {
        val text = File(inputName).readText()
        val dict = dictionary.map { it.key.toLowerCase() to it.value.toLowerCase() }.toMap()
        val searchFor = dictionary.keys.joinToString(separator = """|""") { Regex.escape(it.toString()) }
        File(outputName).bufferedWriter().use {
            it.write(
                Regex(searchFor, RegexOption.IGNORE_CASE).replace(text) { found ->
                    val match = found.value.first() // Всегда находится ровно один символ
                    with(dict.getValue(match.toLowerCase())) {
                        if (this.isNotEmpty()) {
                            (if (match.isUpperCase())
                                this.first().toUpperCase()
                            else
                                this.first()) + this.substring(1)
                        } else {
                            ""
                        }
                    }
                }
            )
            it.newLine()

        }
    } else {
        File(outputName).bufferedWriter().use {
            it.write(File(inputName).readText())
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val wordList = mutableListOf<String>()
    File(inputName).readLines().forEach {
        val word = it.toLowerCase()
        if (word.toSet().size == word.length) {
            wordList.add(it)
        }
    }
    val output = wordList.groupBy { it.length }.maxBy { it.key }?.value?.joinToString() ?: ""
    File(outputName).bufferedWriter().use {
        it.write(output)
    }
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val source = File(inputName).readLines()
    val tagState = mutableMapOf("i" to true, "b" to true, "s" to true, "p" to false)
    val tagPlace = mutableMapOf("i" to -1, "b" to -1)
    File(outputName).bufferedWriter().use {
        it.write("<html>\n<body>\n<p>\n")
        source.forEach { line ->
            if (line.isEmpty()) {
                if (tagState["p"] == false) it.write("\n</p>\n<p>\n")
                tagState["p"] = true
            } else {
                tagState["p"] = false
                var tag = ""
                val parts = Regex("""\*{1,3}|~~""").split(line)
                val tags = Regex("""\*{1,3}|~~""").findAll(line).map { matchResult ->
                    if (matchResult.value != "***") {
                        when (matchResult.value) {
                            "*" -> tag = "i"
                            "**" -> tag = "b"
                            "~~" -> tag = "s"
                        }
                        tagState[tag] = !tagState[tag]!!
                        if (tagState[tag] == false) {
                            tagPlace[tag] = matchResult.range.first
                            "<$tag>"
                        } else {
                            tagPlace[tag] = -1
                            "</$tag>"
                        }
                    } else {
                        tagState["i"] = !tagState["i"]!!
                        tagState["b"] = !tagState["b"]!!
                        if (tagState["i"] == false) {
                            if (tagState["b"] == false) {
                                "<b><i>"
                            } else {
                                "</b><i>"
                            }
                        } else {
                            if (tagState["b"] == false) {
                                "</i><b>"
                            } else {
                                if (tagPlace["i"]!! > tagPlace["b"]!!) "</i></b>" else "</b></i>"
                            }
                        }
                    }
                }.toList()
                for (i in tags.indices) {
                    it.write(parts[i] + tags[i])
                }
                it.write(parts.last())
            }
        }
        it.write("\n</p>\n</body>\n</html>")
    }
}

fun main() {
    markdownToHtmlSimple("input/markdown_simple — копия.md", "temp.txt")
    println(File("temp.txt").readText())
    File("temp.txt").delete()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val result = lhv * rhv
    val width = digitNumber(result) + 1
    val digits = rhv.toString().toList().map { it.toString().toInt() }.reversed()
    File(outputName).bufferedWriter().use {
        it.write(String.format("%${width}d\n", lhv))
        it.write(String.format("*%${width - 1}d\n", rhv))
        it.write('-' * width + '\n')
        it.write(String.format("%${width}d\n", lhv * digits[0]))
        var i = width - 2
        for (digit in digits.subList(1, digits.size)) {
            it.write(String.format("+%${i}d\n", lhv * digit))
            i--
        }
        it.write('-' * width + '\n')
        it.write(String.format("%${width}d", result))
    }
}

operator fun Char.times(multiplier: Int): String =
    List(multiplier) { this }.joinToString(separator = "")

operator fun Int.times(char: Char): String =
    List(this) { char }.joinToString(separator = "")

operator fun CharSequence.times(multiplier: Int): String =
    List(multiplier) { this }.joinToString(separator = "")

operator fun Int.times(charSequence: CharSequence): String =
    List(this) { charSequence }.joinToString(separator = "")

/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198    906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val result = lhv / rhv
    var width = digitNumber(lhv) + 1
    val digits = lhv.toString().toList().map { it.toString() }
    File(outputName).bufferedWriter().use {
        var minuend = 0
        var digitsTaken = 0
        while (minuend < rhv && digitsTaken < digits.size) {
            minuend = digits.subList(0, digitsTaken + 1).joinToString(separator = "").toInt()
            digitsTaken++
        }
        var strMinuend = "$minuend"
        var subtrahend = minuend - minuend % rhv
        var strSubtrahend = "-$subtrahend"
        var remainder = minuend - subtrahend
        var gap = 2 + width - digitNumber(subtrahend)
        if ((digitsTaken == digits.size || minuend < rhv) && strMinuend.length >= strSubtrahend.length) {
            val shift = if (digitsTaken >= strSubtrahend.length) digitsTaken - strSubtrahend.length else 0
            width -= 1
            it.write(String.format("%${width}d | %d\n", lhv, rhv))
            it.write(' ' * shift + strSubtrahend + ' ' * 3 + "$result\n")
            it.write(String.format("%${width}s", '-' * (maxOf(strSubtrahend.length, digitNumber(remainder))) + '\n'))
        } else {
            it.write(String.format("%${width}d | %d\n", lhv, rhv))
            it.write(strSubtrahend + ' ' * gap + "$result\n")
            it.write('-' * (maxOf(strSubtrahend.length, digitNumber(remainder))) + '\n')
        }
        while (digitsTaken < digits.size) {
            if (remainder < rhv) {
                strMinuend = remainder.toString() + digits[digitsTaken]
                minuend = strMinuend.toInt()
            }
            gap = 2 + digitsTaken
            subtrahend = minuend - minuend % rhv
            strSubtrahend = "-$subtrahend"
            remainder = minuend - subtrahend
            digitsTaken++
            it.write(String.format("%${gap}s\n", strMinuend))
            it.write(String.format("%${gap}s\n", strSubtrahend))
            it.write(String.format("%${gap}s\n", '-' * maxOf(strMinuend.length, strSubtrahend.length)))
        }
        it.write(String.format("%${width}d", remainder))
    }
}
