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

**CSV Plugin**
- The application is now available as a plugin. You can right-click on any CSV file and select "Load CSV" to transform it into a table. The plugin also supports exporting the table back to a CSV file after making any changes.

**Dynamic Formula Recalculation**
- Automatically updates all dependent formulas whenever the table data changes, ensuring accurate and up-to-date values. This is achieved through the use of a directed graph of formulas and topological sorting, which ensures that all dependencies are resolved in the correct order.

**"Transform" Button**
- Changes all values in a specified column to the minimum value found in that column, and triggers a recalculation of all dependent formulas.

## Example of work

![Example of work GIF](media/exampleOfWork.gif)

