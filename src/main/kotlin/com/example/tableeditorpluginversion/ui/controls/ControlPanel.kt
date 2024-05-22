package com.example.tableeditorpluginversion.ui.controls

import com.example.tableeditorpluginversion.ui.UI
import com.intellij.openapi.ui.ComboBox
import javax.swing.*

class ControlPanel(private val listener: UI) {
    private val transformations = arrayOf("Min Column", "Remove Duplicates", "To Double")
    private val comboBox = ComboBox(transformations)
    private val columnField = JTextField(5)
    private val transformButton = JButton("Apply Transformation").apply {
        addActionListener {
            val selectedTransformation = comboBox.selectedItem as String
            handleTransform(selectedTransformation)
        }
    }

    fun getView(): JPanel {
        return JPanel().apply {
            add(JButton("Add Row").apply { addActionListener { listener.addRow() } })
            add(JButton("Add Column").apply { addActionListener { listener.addColumn() } })
            add(JButton("Export CSV").apply { addActionListener { listener.csv() } })
            add(transformButton)
            add(comboBox)
            add(JLabel("Column:"))
            add(columnField)
        }
    }

    private fun handleTransform(selectedTransformation: String) {
        val columnNumber = columnField.text
        when(selectedTransformation) {
            "Min Column" -> listener.transformColumnMin(columnNumber)
            "Remove Duplicates" -> listener.transformColumnRemoveDuplicates(columnNumber)
            "To Double" -> listener.transformColumnToDouble(columnNumber)
        }
    }
}
