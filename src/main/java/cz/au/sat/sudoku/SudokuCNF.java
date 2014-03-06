package cz.au.sat.sudoku;

import cz.au.sat.Formula;

import java.io.IOException;
import java.util.Arrays;

public class SudokuCNF extends Formula {

	final int[][] sudoku;

	public SudokuCNF(int[][] sudoku) {
		super(Builder.buildSudokuSAT(sudoku));
		this.sudoku = sudoku;
	}

	@Override
	public Appendable print(Appendable writer) throws IOException {
		writer.append("c SUDOKU puzzle\n");
		for (int[] row : sudoku) {
			writer.append("c ").append(Arrays.toString(row)).append('\n');
		}
		writer.append('\n');
		return super.print(writer);
	}
}
