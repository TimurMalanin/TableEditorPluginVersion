package com.example.tableeditorpluginversion.ui

import com.example.tableeditorpluginversion.ui.constrols.ControlPanel
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

    fun exportCsv() {
        JFileChooser().apply {
            dialogTitle = "Specify a file to save"
            fileSelectionMode = JFileChooser.FILES_ONLY
            if (showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                selectedFile.correctExtension(".csv").apply {
                    saveCsv(this)
                }
            }
        }
    }

    private fun File.correctExtension(extension: String): File =
        if (!name.endsWith(extension)) File("$absolutePath$extension") else this

    private fun saveCsv(file: File) {
        try {
            FileWriter(file).use { writer ->
                for (row in 0 until tableView.tableModel.rowCount) {
                    val rowData = (1 until tableView.tableModel.columnCount)
                        .joinToString(",") { tableView.tableModel.getValueAt(row, it).toString() }
                    writer.write("$rowData\n")
                }
            }
            showMessage("CSV file was saved successfully.", "Success")
        } catch (ex: Exception) {
            showMessage("An error occurred while saving the CSV file.", "Error")
        }
    }

    private fun showMessage(message: String, title: String) {
        JOptionPane.showMessageDialog(
            frame,
            message,
            title,
            if (title == "Success") JOptionPane.INFORMATION_MESSAGE else JOptionPane.ERROR_MESSAGE
        )
    }

    fun transformColumnMin(columnLetter: String) {
        val columnIndex = columnLetterToIndex(columnLetter)
        if (columnIndex < 0 || columnIndex >= tableView.tableModel.columnCount) return

        var minValue = (0 until tableView.tableModel.rowCount)
            .mapNotNull { tableView.tableModel.getValueAt(it, columnIndex)?.toString() }
            .minOrNull()
        if (minValue.isNullOrEmpty()) minValue = 0.toString()
        (0 until tableView.tableModel.rowCount).forEach { row ->
            tableView.tableModel.setValueAt(minValue, row, columnIndex)
        }
    }

    private fun columnLetterToIndex(columnLetter: String): Int = columnLetter.uppercase().first() - 'A' + 1
}
