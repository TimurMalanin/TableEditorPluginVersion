# Table Editor

## Supported Operations

The application recognizes the following operations:
- **Unary Operators:**
    - `NEGATION`: Negates the value (e.g., `-x`).
    - `SQRT`: Computes the square root (e.g., `sqrt(x)`).
- **Binary Operators:**
    - `PLUS`: Adds two operands (e.g., `x + y`).
    - `MINUS`: Subtracts the second operand from the first (e.g., `x - y`).
    - `MULTIPLY`: Multiplies two operands (e.g., `x * y`).
    - `DIVIDE`: Divides the first operand by the second (e.g., `x / y`).
    - `POW`: Raises the first operand to the power of the second (e.g., `x ^ y`).

## Features
**Dynamic Formula Recalculation**
- Automatically updates all dependent formulas whenever the table data changes, ensuring accurate and up-to-date values.

**"Transform" Button**
- Changes all values in a specified column to the minimum value found in that column, and triggers a recalculation of all dependent formulas.

## Example of work

![Example of work GIF](media/exampleOfWork.gif)


# Running the Project

First, clone the repository using the following git command:

```bash
git clone https://github.com/TimurMalanin/TableEditorPluginVersion.git
```

Navigate to the project directory:

```bash
cd TableEditorpluginVersion
```

Run the project:

```bash
./gradlew run
```