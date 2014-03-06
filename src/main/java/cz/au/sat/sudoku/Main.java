package cz.au.sat.sudoku;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static String FILE = "sudoku.cnf";

	public static void main(String[] args) {
		final int[][] sudoku = {
				{0, 0, 5,  3, 0, 0,  0, 0, 0},
				{8, 0, 0,  0, 0, 0,  0, 2, 0},
				{0, 7, 0,  0, 1, 0,  5, 0, 0},

				{4, 0, 0,  0, 0, 5,  3, 0, 0},
				{0, 1, 0,  0, 7, 0,  0, 0, 6},
				{0, 0, 3,  2, 0, 0,  0, 8, 0},

				{0, 6, 0,  5, 0, 0,  0, 0, 9},
				{0, 0, 4,  0, 0, 0,  0, 3, 0},
				{0, 0, 0,  0, 0, 9,  7, 0, 0},
		};
		final SudokuSAT sudokuSAT = new SudokuSAT(sudoku);

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(FILE));

			sudokuSAT.print(writer);

			writer.flush();
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			close(writer);
		}
	}

	private static void close(Closeable closeable) {
		try {
			if (closeable != null) closeable.close();
		} catch (IOException e) {
			// swallow
		}
	}

}
