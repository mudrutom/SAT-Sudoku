package cz.au.sat.sudoku;

import cz.au.sat.Clause;
import cz.au.sat.Factory;
import cz.au.sat.Literal;

import java.util.ArrayList;
import java.util.List;

public class Builder {

	private static Factory factory;

	public static synchronized List<Clause> buildSudokuSAT(final int[][] sudoku) {
		if (!validateSudoku(sudoku)) throw new IllegalArgumentException();

		final int size = sudoku.length;
		factory = new Factory();
		final List<Clause> clauses = new ArrayList<Clause>();

		// at least one number needs to be selected
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// all numbers
				List<Literal> clause = new ArrayList<Literal>(size);
				for (int n = 0; n < size; n++) {
					clause.add(pos(i, j, n));
				}
				clauses.add(new Clause(clause));
			}
		}

		// only one number can be selected
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// all <n1,n2> pairs
				for (int n2 = 1; n2 < size; n2++) {
					for (int n1 = 0; n1 < n2 ; n1++) {
						clauses.add(new Clause(neg(i, j, n1), neg(i, j, n2)));
					}
				}
			}
		}

		// row - all values need to be different
		for (int i = 0; i < size; i++) {
			// for all numbers
			for (int n = 0; n < size; n++) {
				// all <j1,j2> pairs
				for (int j2 = 1; j2 < size; j2++) {
					for (int j1 = 0; j1 < j2; j1++) {
						clauses.add(new Clause(neg(i, j1, n), neg(i, j2, n)));
					}
				}
			}
		}

		// column - all values need to be different
		for (int j = 0; j < size; j++) {
			// for all numbers
			for (int n = 0; n < size; n++) {
				// all <i1,i2> pairs
				for (int i2 = 1; i2 < size; i2++) {
					for (int i1 = 0; i1 < i2; i1++) {
						clauses.add(new Clause(neg(i1, j, n), neg(i2, j, n)));
					}
				}
			}
		}

		// block - all values need to be different
		final int block = size / 3;
		for (int b_i = 0; b_i < size; b_i += block) {
			for (int b_j = 0; b_j < size; b_j += block) {
				// for all numbers
				for (int n = 0; n < size; n++) {
					// all <(i1,j1),(i2,j2)> pairs
					for (int i1 = 0; i1 < block; i1++) {
						for (int j1 = 0; j1 < block; j1++) {
							for (int i2 = 0; i2 < block; i2++) {
								for (int j2 = 0; j2 < block; j2++) {
									if (i1 != i2 || j1 != j2) {
										// TODO some clauses are redundant
										clauses.add(new Clause(neg(b_i + i1, b_j + j1, n),
															   neg(b_i + i2, b_j + j2, n)));
									}
								}
							}
						}
					}
				}
			}
		}

		// known variable values
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (sudoku[i][j] > 0) {
					int val = sudoku[i][j] - 1;
					for (int n = 0; n < size; n++) {
						clauses.add(new Clause((n == val) ? pos(i, j, n) : neg(i, j, n)));
					}
				}
			}
		}

		return clauses;
	}

	public static boolean validateSudoku(final int[][] sudoku) {
		if (sudoku == null) return false;

		final int size = sudoku.length;
		if (size < 3 || size % 3 != 0) return false;

		for (int[] row : sudoku) {
			if (row == null || row.length != size) return false;
			for (int n : row) {
				if (n < 0 || n > size) return false;
			}
		}

		return true;
	}

	private static Literal pos(int i, int j, int n) {
		return factory.positive(String.format("%d_%d_%d", i, j, n));
	}

	private static Literal neg(int i, int j, int n) {
		return factory.negative(String.format("%d_%d_%d", i, j, n));
	}
}
