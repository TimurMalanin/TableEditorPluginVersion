package com.example.tableeditorpluginversion.ui.utils

import javax.swing.table.DefaultTableModel

fun minTransformation(table: DefaultTableModel, columnLetter: String) {
    val columnIndex = columnLetterToIndex(columnLetter)
    if (columnIndex < 0 || columnIndex >= table.columnCount) return

    var minValue = (0 until table.rowCount)
        .mapNotNull { table.getValueAt(it, columnIndex)?.toString() }
        .minOrNull()
    if (minValue.isNullOrEmpty()) minValue = 0.toString()
    (0 until table.rowCount).forEach { row ->
        table.setValueAt(minValue, row, columnIndex)
    }
}

fun duplicatesTransformation(table: DefaultTableModel,columnLetter: String) {
    val columnIndex = columnLetterToIndex(columnLetter)
    if (columnIndex < 0 || columnIndex >= table.columnCount) return

    val seenValues = mutableSetOf<String>()
    (0 until table.rowCount).forEach { row ->
        val value = table.getValueAt(row, columnIndex)?.toString()
        if (value in seenValues) {
            table.setValueAt("", row, columnIndex)
        } else {
            seenValues.add(value!!)
        }
    }
}

fun doubleTransformation(table: DefaultTableModel,columnLetter: String) {
    val columnIndex = columnLetterToIndex(columnLetter)
    if (columnIndex < 0 || columnIndex >= table.columnCount) return

    (0 until table.rowCount).forEach { row ->
        val value = table.getValueAt(row, columnIndex)?.toString()
        val doubleValue = value?.toDoubleOrNull() ?: 0.0
        table.setValueAt(doubleValue, row, columnIndex)
    }
}

private fun columnLetterToIndex(columnLetter: String): Int = columnLetter.uppercase().first() - 'A' + 1
