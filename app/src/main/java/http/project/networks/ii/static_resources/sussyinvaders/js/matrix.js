function createMatrix(rows, columns) {
	const matrix = [];
	for (let i = 0; i < rows; i++) {
		matrix[i] = [];
		for (let j = 0; j < columns; j++) {
			matrix[i][j] = null;
		}
	}
	return matrix;
}

function clearMatrix(matrix) {
	for (let i = 0; i < matrix.length; i++) {
		for (let j = 0; j < matrix[i].length; j++) {
			matrix[i][j] = null;
		}
	}
}

function clearAlienMatrix(matrix) {
	for (let i = 0; i < matrix.length; i++) {
		for (let j = 0; j < matrix[i].length; j++) {
			matrix[i][j].active = false;
		}
	}
}

function isMatrixClear(matrix) {
	for (let i = 0; i < matrix.length; i++) {
		for (let j = 0; j < matrix[i].length; j++) {
			if (matrix[i][j] !== null) {
				return false;
			}
		}
	}
	return true;
}

function isAliensMatrixClear(matrix) {
	for (let i = 0; i < matrix.length; i++) {
		for (let j = 0; j < matrix[i].length; j++) {
			if (matrix[i][j].active === true) {
				return false;
			}
		}
	}
	return true;
}


function matrixLength(matrix) {
	return matrix.length * matrix[0].length;
}

function matrixInsertObject(matrix, object, x, y) {
	matrix[y][x] = object;
}

function matrixInsertObjectHorizontaly(matrix, object, x, y) {
  matrix[x][y] = object;
}