package com.example.tableeditorpluginversion.ui.formula

import com.example.tableeditorpluginversion.graph.Node
import com.example.tableeditorpluginversion.parser.TermSolver.solvePostfix
import com.example.tableeditorpluginversion.parser.TermSolver.transformInfixToPostfix
import javax.swing.event.TableModelListener
import javax.swing.table.DefaultTableModel

class InvalidCellReferenceException(message: String) : Exception(message)

class FormulaProcessor(
    private val model: DefaultTableModel,
    private val tableModelListener: TableModelListener,
    private val formulaManager: TableFormulaManager
) {
    fun processFormula(formula: String, row: Int, col: Int) {
        try {
            val result = splitFormula(formula).joinToString("") { processPart(it, row, col) }
            print(result)
            transformInfixToPostfix(result).takeIf { it.isPresent }?.let {
                val resultNum = solvePostfix(it.get())

                if (resultNum.isInfinite() || resultNum.isNaN()) {
                    throw ArithmeticException("Division by zero")
                }

                val valueToSet = if (resultNum % 1.0 == 0.0) resultNum.toInt() else resultNum
                updateModel(valueToSet, row, col)
            }
        } catch (e: InvalidCellReferenceException) {
            model.setValueAt("Error: Invalid cell reference", row, col)
        } catch (e: ArithmeticException) {
            model.setValueAt("Error: Division by zero", row, col)
        } catch (e: Exception) {
            model.setValueAt("Error: ${e.message}", row, col)
        }
    }

    private fun splitFormula(formula: String): List<String> =
        formula.substring(1).split(Regex("\\s+|(?<=[+\\-*/^()])|(?=[+\\-*/^()])|(?<=sqrt\\()|(?=\\))"))
            .filter { it.isNotEmpty() }
            .map { it.trim() }

    private fun processPart(part: String, row: Int, col: Int): String = when {
        part.matches(Regex("[A-Z]+\\d+")) -> {
            val columnLetters = part.takeWhile { it.isLetter() }
            val rowNumber = part.dropWhile { it.isLetter() }.toInt() - 1
            val columnNumber = columnLetters.fold(0) { acc, c -> acc * 26 + (c - 'A' + 1) }

            val currentCellNode = Node(row, col)
            val dependentCellNode = Node(rowNumber, columnNumber)

            formulaManager.addDependency(currentCellNode, dependentCellNode)

            try {
                val value = model.getValueAt(rowNumber, columnNumber)?.toString()
                value ?: throw InvalidCellReferenceException("Invalid cell reference: $part not found.")
            } catch (e: ArrayIndexOutOfBoundsException) {
                throw InvalidCellReferenceException("Invalid cell reference: $part is out of table bounds.")
            }
        }

        else -> part
    }


    private fun updateModel(resultNum: Number, row: Int, col: Int) {
        model.removeTableModelListener(tableModelListener)
        model.setValueAt(resultNum, row, col)
        model.addTableModelListener(tableModelListener)
    }
}
