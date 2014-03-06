package cz.au.sat.sudoku;

import cz.au.sat.Formula;

import java.io.IOException;
import java.util.Arrays;

public class SudokuSAT extends Formula {

	final int[][] sudoku;

	public SudokuSAT(int[][] sudoku) {
		super(Builder.buildSudokuSAT(sudoku));
		this.sudoku = sudoku;
	}

	@Override
	public Appendable print(Appendable writer) throws IOException {
		writer.append("c SUDOKU puzzle\n\n");

		// given sudoku instance
		for (int[] row : sudoku) {
			writer.append("c ").append(Arrays.toString(row)).append('\n');
		}
		writer.append('\n');

		// variable numbers
		final int size = sudoku.length;
		for (int i = 0; i < size; i++) {
			writer.append("c ");
			for (int j = 0; j < size; j++) {
				writer.append(String.format("\tv[%d,%d]:", i, j));
				writer.append(String.format("%d-%d", Builder.var(i, j, 0).getNumber(), Builder.var(i, j, size - 1).getNumber()));
			}
			writer.append('\n');
		}
		writer.append('\n');

		return super.print(writer);
	}
}
