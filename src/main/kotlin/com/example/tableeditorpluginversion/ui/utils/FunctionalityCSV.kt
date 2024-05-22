package com.example.tableeditorpluginversion.ui.utils

import java.io.File
import java.io.FileWriter
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.table.DefaultTableModel

fun exportCsv(table: DefaultTableModel, frame: JFrame) {
    JFileChooser().apply {
        dialogTitle = "Specify a file to save"
        fileSelectionMode = JFileChooser.FILES_ONLY
        if (showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            selectedFile.correctExtension(".csv").apply {
                saveCsv(table, this, frame)
            }
        }
    }
}

private fun File.correctExtension(extension: String): File =
    if (!name.endsWith(extension)) File("$absolutePath$extension") else this

private fun saveCsv(table: DefaultTableModel, file: File, frame: JFrame) {
    try {
        FileWriter(file).use { writer ->
            for (row in 0 until table.rowCount) {
                val rowData = (1 until table.columnCount)
                    .joinToString(",") { table.getValueAt(row, it).toString() }
                writer.write("$rowData\n")
            }
        }
        showMessage("CSV file was saved successfully.", "Success", frame)
    } catch (ex: Exception) {
        showMessage("An error occurred while saving the CSV file.", "Error", frame)
    }
}

private fun showMessage(message: String, title: String, frame: JFrame) {
    JOptionPane.showMessageDialog(
        frame,
        message,
        title,
        if (title == "Success") JOptionPane.INFORMATION_MESSAGE else JOptionPane.ERROR_MESSAGE
    )
}