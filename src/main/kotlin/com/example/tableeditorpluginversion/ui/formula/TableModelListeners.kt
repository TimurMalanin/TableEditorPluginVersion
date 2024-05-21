package com.example.tableeditorpluginversion.ui.formula

import javax.swing.event.TableModelEvent
import javax.swing.event.TableModelListener
import javax.swing.table.DefaultTableModel

class TableModelListener(private val model: DefaultTableModel, private val formulaManager: TableFormulaManager) : TableModelListener {
    private val formulaProcessor = FormulaProcessor(model, this, formulaManager)

    override fun tableChanged(event: TableModelEvent) {
        if (event.type == TableModelEvent.UPDATE) {
            val row = event.firstRow
            val column = event.column
            if (row >= 0 && column >= 0) {
                processCellUpdate(row, column)
            }
        }
    }

    private fun processCellUpdate(row: Int, column: Int) {
        val cellKey = Pair(row, column)
        val cellValue = model.getValueAt(row, column) as? String ?: return
        val previousIsFormula = formulaManager.getFormula(cellKey) != null

        if (cellValue.startsWith("=")) {
            formulaManager.addFormula(cellKey, cellValue)
            formulaProcessor.processFormula(cellValue, row, column)
        } else if (previousIsFormula) {
            formulaManager.removeFormula(cellKey)
        }

        updateDependentCells(row, column)
    }

    private fun updateDependentCells(row: Int, column: Int) {
        formulaManager.getDependencies(Pair(row, column)).forEach { (depRow, depCol) ->
            formulaManager.getFormula(Pair(depRow, depCol))?.let { formula ->
                formulaProcessor.processFormula(formula, depRow, depCol)
                updateDependentCells(depRow, depCol)
            }
        }
    }
}
