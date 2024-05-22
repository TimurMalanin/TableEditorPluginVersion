package com.example.tableeditorpluginversion.ui.formula

import com.example.tableeditorpluginversion.graph.DependencyGraph
import com.example.tableeditorpluginversion.graph.Node
import javax.swing.event.TableModelEvent
import javax.swing.event.TableModelListener
import javax.swing.table.DefaultTableModel

class TableModelListener(private val model: DefaultTableModel, private val formulaManager: TableFormulaManager) :
    TableModelListener {
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
        val cellNode = Node(row, column)
        val cellValue = model.getValueAt(row, column) as? String ?: return
        val previousIsFormula = formulaManager.getFormula(cellNode) != null

        if (cellValue.startsWith("=")) {
            formulaManager.addFormula(cellNode, cellValue)
            formulaProcessor.processFormula(cellValue, row, column)
        } else if (previousIsFormula) {
            formulaManager.removeFormula(cellNode)
        }

        updateDependentCells()
    }

    private fun updateDependentCells() {
        val graph = DependencyGraph(formulaManager.dependencies)
        val (sortedNodes, cycleNodes) = graph.buildAndSort()

        if (cycleNodes.isNotEmpty()) {
            cycleNodes.forEach { cycleNode ->
                showErrorInCell(cycleNode.row, cycleNode.col)
            }
        } else {
            sortedNodes.forEach { sortedNode ->
                formulaManager.getFormula(Node(sortedNode.row, sortedNode.col))?.let { formula ->
                    formulaProcessor.processFormula(formula, sortedNode.row, sortedNode.col)
                }
            }
        }
    }

    private fun showErrorInCell(row: Int, column: Int) {
        model.removeTableModelListener(this)
        model.setValueAt("Error: Cycle detected", row, column)
        model.addTableModelListener(this)
    }
}
