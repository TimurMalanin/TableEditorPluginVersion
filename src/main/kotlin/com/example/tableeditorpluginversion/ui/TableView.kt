package com.example.tableeditorpluginversion.ui

import com.example.tableeditorpluginversion.ui.formula.TableFormulaManager
import com.intellij.ui.table.JBTable
import com.example.tableeditorpluginversion.ui.formula.TableModelListener
import com.example.tableeditorpluginversion.ui.renderer.CenterGrayRenderer
import com.example.tableeditorpluginversion.ui.renderer.FormulaRenderer
import com.example.tableeditorpluginversion.ui.renderer.FormulaCellEditor
import com.intellij.ui.components.JBScrollPane
import javax.swing.DefaultListSelectionModel
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.table.DefaultTableModel

typealias TableData = Array<Array<String>>

class TableView(private val data: TableData) {
    val tableModel = createTableModel()
    private val formula = TableFormulaManager()
    private val table = JBTable(tableModel).apply {
        autoResizeMode = JTable.AUTO_RESIZE_OFF
    }

    init {
        configureTableView()
    }

    fun getView(): JScrollPane = JBScrollPane(table).apply {
        verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    }

    fun addRow() {
        tableModel.addRow( Array(tableModel.columnCount) { "" })
        updateRowNumbers()
    }

    fun addColumn() {
        val columnName = getColumnLabel(tableModel.columnCount - 1)
        tableModel.addColumn(columnName)
        for (i in 0 until tableModel.rowCount)
            tableModel.setValueAt("", i, tableModel.columnCount - 1)
        restoreColumnSettings()
    }

    private fun configureTableView() {
        setupTableSelectionModel()
        table.model.addTableModelListener(TableModelListener(tableModel, formula))

        table.getColumn("#").cellRenderer = CenterGrayRenderer(table)
        for (i in 1 until table.columnCount) {
            table.columnModel.getColumn(i).cellRenderer = FormulaRenderer(formula)
            table.columnModel.getColumn(i).cellEditor = FormulaCellEditor(formula)
        }
    }

    private fun setupTableSelectionModel() {
        val columnModel = table.columnModel
        columnModel.selectionModel = object : DefaultListSelectionModel() {
            override fun setSelectionInterval(index0: Int, index1: Int) {
                super.setSelectionInterval(maxOf(index0, 1), maxOf(index1, 1))
            }
        }
        table.setColumnSelectionAllowed(true)
    }

    private fun createTableModel(): DefaultTableModel {
        val sampleData = arrayOf(
            arrayOf("", "1", "2", "=A1+B1"),
            arrayOf("", "4", "5", "=A2+B2")
        )

        val tableContent = if (data.isEmpty()) sampleData else data

        val columnNames = arrayOf("#", "A", "B", "C")
        return object : DefaultTableModel(tableContent, columnNames) {
            override fun isCellEditable(row: Int, column: Int) = column != 0

            init {
                tableContent.indices.forEach { rowIndex ->
                    setValueAt(rowIndex + 1, rowIndex, 0)
                }
            }
        }
    }

    private fun updateRowNumbers() {
        for (i in 0 until tableModel.rowCount) {
            tableModel.setValueAt((i + 1).toString(), i, 0)
        }
    }

    private fun getColumnLabel(index: Int) = generateSequence(index + 1) { (it - 1) / 26 }
        .map { 'A' + (it - 1) % 26 }
        .takeWhile { it != 'A' - 1 }
        .toList()
        .reversed()
        .joinToString("")

    private fun restoreColumnSettings() {
        table.getColumn("#").cellRenderer = CenterGrayRenderer(table)
        updateRowNumbers()
        for (i in 1 until table.columnCount) {
            table.columnModel.getColumn(i).cellRenderer = FormulaRenderer(formula)
        }
    }
}
