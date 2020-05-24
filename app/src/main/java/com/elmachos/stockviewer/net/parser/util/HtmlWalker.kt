package com.elmachos.stockviewer.net.parser.util

class HtmlWalker(val lines: List<String>, var position: Int) {
    companion object{
        fun ofString(string: String): HtmlWalker {
            return HtmlWalker(
                string.lines(),
                0
            )
        }
    }

    fun skipToMatching(string: String): HtmlWalker? {
        for ( i in position until lines.size) {
            if (lines[i].trim().matches(string.toRegex())) {
                position = i
                return HtmlWalker(lines, i)
            }
        }
        return null
    }

    fun move(): HtmlWalker {
        position += 1
        return this
    }

    fun take(): String {
        return lines[position]
    }

}