package com.example.tableeditorpluginversion.ui

import com.example.tableeditorpluginversion.ui.controls.ControlPanel
import com.example.tableeditorpluginversion.ui.utils.*
import java.awt.BorderLayout
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.File
import java.io.FileWriter
import javax.swing.*

class UI(
    data: TableData,private var filePath: String
) {
    private val frame = JFrame("CSV Data Viewer").apply {
        setSize(500, 500)
        defaultCloseOperation = JFrame.HIDE_ON_CLOSE
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                saveCsvAndHide()
            }
        })
        isVisible = true
    }

    private fun saveCsvAndHide() {
        saveCsv(tableView.tableModel, File(filePath), frame)
        frame.isVisible = false
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
