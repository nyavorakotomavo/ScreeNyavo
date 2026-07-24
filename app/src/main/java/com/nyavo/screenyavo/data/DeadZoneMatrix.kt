package com.nyavo.screenyavo.data

enum class CellState { UNTESTED, RESPONSIVE, DEAD }

data class GridCell(
    val row: Int,
    val col: Int,
    var state: CellState = CellState.UNTESTED
)

data class DeadZoneMatrix(
    val rows: Int = 20,
    val cols: Int = 10,
    val cells: List<GridCell> = List(rows * cols) { index ->
        GridCell(row = index / cols, col = index % cols)
    }
)
