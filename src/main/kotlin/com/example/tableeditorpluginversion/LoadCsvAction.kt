package com.example.tableeditorpluginversion

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.example.tableeditorpluginversion.ui.UI
import java.io.FileReader
import com.opencsv.CSVReader
import java.nio.file.Paths

class LoadCsvAction : AnAction() {

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun actionPerformed(e: AnActionEvent) {
        val file = e.getData(com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE)
        file?.let {
            if (it.extension == "csv") {
                readCsvFile(it.path)
            }
        }
    }

    override fun update(e: AnActionEvent) {
        val file = e.getData(com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabledAndVisible = file?.extension?.equals("csv", ignoreCase = true) == true
    }

    private fun readCsvFile(filePath: String) {
        val data = readCsvToArray(filePath)
        UI(data)
    }

    private fun readCsvToArray(filePath: String): Array<Array<String>> {
        FileReader(Paths.get(filePath).toFile()).use { fr ->
            CSVReader(fr).use { reader ->
                return reader.readAll().map { arrayOf("") + it }.toTypedArray()
            }
        }
    }
}
