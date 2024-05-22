package com.example.tableeditorpluginversion.ui.renderer

import com.example.tableeditorpluginversion.graph.Node
import com.example.tableeditorpluginversion.ui.formula.TableFormulaManager
import java.awt.Component
import javax.swing.JTable
import javax.swing.table.DefaultTableCellRenderer

class FormulaRenderer(private val formulaManager: TableFormulaManager) : DefaultTableCellRenderer() {
    override fun getTableCellRendererComponent(
        table: JTable, value: Any?, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int
    ): Component {
        var cellValue = if (hasFocus && row >= 0 && column > 0 && formulaManager.formulas.containsKey(Node(row, column)))
            formulaManager.formulas[Node(row, column)] else value.toString()
        if (cellValue == "null") cellValue = ""
        return super.getTableCellRendererComponent(table, cellValue, isSelected, hasFocus, row, column)
    }
}
