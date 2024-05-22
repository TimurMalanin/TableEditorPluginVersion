package com.example.tableeditorpluginversion.ui.renderer

import com.example.tableeditorpluginversion.graph.Node
import com.example.tableeditorpluginversion.ui.formula.TableFormulaManager
import java.awt.Component
import javax.swing.AbstractCellEditor
import javax.swing.JTable
import javax.swing.JTextField
import javax.swing.table.TableCellEditor

class FormulaCellEditor(private val formulaManager: TableFormulaManager) : AbstractCellEditor(), TableCellEditor {
    private val textField = JTextField()

    override fun getTableCellEditorComponent(
        table: JTable, value: Any?, isSelected: Boolean, row: Int, column: Int
    ): Component {
        val key = Node(row, column)
        textField.text = formulaManager.getFormula((key)) ?: value.toString()

        return textField
    }

    override fun getCellEditorValue(): Any {
        return textField.text
    }
}
