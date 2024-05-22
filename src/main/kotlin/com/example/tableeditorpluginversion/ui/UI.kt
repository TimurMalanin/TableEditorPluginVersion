package com.example.tableeditorpluginversion.ui

import com.example.tableeditorpluginversion.ui.controls.ControlPanel
import com.example.tableeditorpluginversion.ui.utils.doubleTransformation
import com.example.tableeditorpluginversion.ui.utils.duplicatesTransformation
import com.example.tableeditorpluginversion.ui.utils.exportCsv
import com.example.tableeditorpluginversion.ui.utils.minTransformation
import java.awt.BorderLayout
import java.io.File
import java.io.FileWriter
import javax.swing.*

class UI(
    data: TableData,
) {
    private val frame = JFrame("CSV Data Viewer").apply {
        setSize(500, 500)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
    }

    private val tableView = TableView(data)
    private val controlPanel = ControlPanel(this)

    init {
        frame.add(tableView.getView(), BorderLayout.CENTER)
        frame.add(controlPanel.getView(), BorderLayout.SOUTH)
        frame.pack()
    }

    fun addRow() = tableView.addRow()

    fun addColumn() = tableView.addColumn()

    fun csv() = exportCsv(tableView.tableModel, frame)

    fun transformColumnMin(columnLetter: String) = minTransformation(tableView.tableModel, columnLetter)

    fun transformColumnRemoveDuplicates(columnLetter: String) = duplicatesTransformation(tableView.tableModel, columnLetter)

    fun transformColumnToDouble(columnLetter: String) = doubleTransformation(tableView.tableModel, columnLetter)
}
