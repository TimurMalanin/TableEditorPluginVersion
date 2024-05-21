package com.example.tableeditorpluginversion.ui.formula

class TableFormulaManager {
    val formulas = mutableMapOf<Pair<Int, Int>, String>()
    private val dependencies = mutableMapOf<Pair<Int, Int>, MutableList<Pair<Int, Int>>>()

    fun addFormula(cell: Pair<Int, Int>, formula: String) {
        formulas[cell] = formula
    }

    fun removeFormula(cell: Pair<Int, Int>) {
        formulas.remove(cell)
    }

    fun getFormula(cell: Pair<Int, Int>): String? = formulas[cell]

    fun addDependency(forCell: Pair<Int, Int>, dependsOn: Pair<Int, Int>) {
        dependencies.getOrPut(dependsOn) { mutableListOf() }.add(forCell)
    }

    fun getDependencies(cell: Pair<Int, Int>): List<Pair<Int, Int>> =
        dependencies.filterKeys { it == cell }.values.flatten()
}
