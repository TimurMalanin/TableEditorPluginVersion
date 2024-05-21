import com.example.tableeditorpluginversion.ui.formula.FormulaProcessor
import com.example.tableeditorpluginversion.ui.formula.TableFormulaManager
import org.mockito.kotlin.*
import javax.swing.event.TableModelListener
import javax.swing.table.DefaultTableModel
import kotlin.test.Test


class FormulaProcessorTest {
    private var model: DefaultTableModel = mock()
    private var listener: TableModelListener = mock()
    private var formulaManager = TableFormulaManager()
    private var processor = FormulaProcessor(model, listener, formulaManager)

    @Test
    fun testProcessSimpleFormula() {
        processor.processFormula("=2 + 3", 0, 0)
        verify(model).setValueAt(5, 0, 0)
    }

    @Test
    fun testProcessComplexFormula() {
        processor.processFormula("=(2 + 3) * 5", 0, 0)
        verify(model).setValueAt(25, 0, 0)
    }

    @Test
    fun testProcessEmptyFormula() {
        processor.processFormula("=", 0, 0)
        verify(model).setValueAt("Error: Invalid expression", 0, 0)
    }

    @Test
    fun testErrorHandlingDivisionByZero() {
        processor.processFormula("=1 / 0", 0, 0)
        verify(model).setValueAt("Error: Division by zero", 0, 0)
    }

    @Test
    fun testFormulaWithFunction() {
        processor.processFormula("=sqrt(16)", 0, 0)
        verify(model).setValueAt(4, 0, 0)
    }

    @Test
    fun testProcessFormulaWithInvalidReference() {
        processor.processFormula("=A10", 0, 0)
        verify(model).setValueAt("Error: Invalid cell reference", 0, 0)
    }
}
