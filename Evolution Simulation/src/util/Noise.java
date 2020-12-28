package util;

import java.awt.Color;
import java.util.ArrayList;

public class Noise {
	
	// Increasing the sample size increases the resolution of the noise
	int sampleSize = 7; // 12 is coolest. I like the look of low resolution though
	
	// Size of each row/column relative to total size
	double step = 1.0 / ((double)(sampleSize - 1));
	
	// Interpolation
	InterpolationLine[][] horizontalInterpolationLines = new InterpolationLine[sampleSize][sampleSize - 1];
	InterpolationLine[][] verticalInterpolationLines = new InterpolationLine[sampleSize][sampleSize - 1];
	
	public Noise() {
		// Base random numbers
		double[][] randomSamples = new double[sampleSize][sampleSize];
		
		// Create random samples
		for (int j = 0; j < sampleSize; j++) {
			for (int i = 0; i < sampleSize; i++) {
				// Populates sample with random numbers
				randomSamples[j][i] = Math.random();
				// Makes the borders 0 so each world is like an island
				boolean isBorder = i == 0 || j == 0 || i == sampleSize - 1 || j == sampleSize - 1;
				if (isBorder) randomSamples[j][i] = 0;
			}
		}

		// Horizontal interpolation
		for (int i = 0; i < sampleSize; i++) {
			// Interpolates each row
			horizontalInterpolationLines[i] = InterpolationLine.interpolateOverValues(randomSamples[i]);
		}
		
		// Vertical interpolation
		for (int i = 0; i < sampleSize; i++) {
			// First creates the column as an array to be interpolated over
			double[] columns = new double[randomSamples.length]; 
			for (int r = 0; r < randomSamples.length; r++) {
				columns[r] = randomSamples[r][i];
			}
			// Interpolates each column
			verticalInterpolationLines[i] = InterpolationLine.interpolateOverValues(columns);
		}
		
		
	}
	
	public double evaluate(double x, double y) {
		// Pick the closest index of set of lines
		int interpolationRowIndex = (int) (y / step);
		int interpolationColumnIndex = (int) (x / step);
		InterpolationLine[] interpolationRow = horizontalInterpolationLines[interpolationRowIndex];
		InterpolationLine[] interpolationColumn = verticalInterpolationLines[interpolationColumnIndex];
		// Pick the next closest of set of lines
		InterpolationLine[] nextInterpolationRow = horizontalInterpolationLines[interpolationRowIndex + 1];
		InterpolationLine[] nextInterpolationColumn = verticalInterpolationLines[interpolationColumnIndex + 1];
		// Result from row
		double resultFromRow = InterpolationLine.evaluateInterpolationOverLines(x, interpolationRow);
		double nextResultFromRow = InterpolationLine.evaluateInterpolationOverLines(x, nextInterpolationRow);
		double rowWeightedRatio = 1 - ((y % step)/step);
		// Result from column
		double resultFromColumn = InterpolationLine.evaluateInterpolationOverLines(y, interpolationColumn);
		double nextResultFromColumn = InterpolationLine.evaluateInterpolationOverLines(y, nextInterpolationColumn);
		double columnWeightedRatio = 1 - ((x % step)/step);
		// Weighted average to fill the space between each set of interpolation lines
		double weightedRowResult = (resultFromRow * (rowWeightedRatio)) + (nextResultFromRow * (1.0 - rowWeightedRatio));
		double weightedColumnResult = (resultFromColumn * (columnWeightedRatio)) + (nextResultFromColumn * (1.0 - columnWeightedRatio));
		// Average the two
		return (weightedRowResult + weightedColumnResult) / 2;
	}
	


}
