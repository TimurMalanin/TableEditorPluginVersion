package com.example.tableeditorpluginversion.ui.formula

import com.example.tableeditorpluginversion.graph.Node

class TableFormulaManager {
    val formulas = mutableMapOf<Node, String>()
    val dependencies = mutableMapOf<Node, MutableList<Node>>()

    fun addFormula(cell: Node, formula: String) {
        formulas[cell] = formula
    }

    fun removeFormula(cell: Node) {
        formulas.remove(cell)
    }

    fun getFormula(cell: Node): String? = formulas[cell]

    fun addDependency(forCell: Node, dependsOn: Node) {
        dependencies.getOrPut(dependsOn) { mutableListOf() }.add(forCell)
    }
}
