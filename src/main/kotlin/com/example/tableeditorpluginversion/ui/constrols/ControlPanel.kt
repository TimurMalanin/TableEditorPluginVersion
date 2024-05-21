package com.example.tableeditorpluginversion.ui.constrols

import com.example.tableeditorpluginversion.ui.UI
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class ControlPanel(private val listener: UI) {
    fun getView(): JPanel {
        return JPanel().apply {
            add(JButton("Add Row").apply { addActionListener { listener.addRow() } })
            add(JButton("Add Column").apply { addActionListener { listener.addColumn() } })
            add(JButton("Export CSV").apply { addActionListener { listener.exportCsv() } })
            add(JLabel("Column:"))
            add(columnField)
            add(JButton("Transform").apply { addActionListener { handleTransform() } })
        }
    }

    private val columnField = JTextField(5)

    private fun handleTransform() {
        val columnNumber = columnField.text
        if (columnNumber != null) {
            listener.transformColumnMin(columnNumber)
        } else {
            println("Invalid column number")
        }
    }
}
