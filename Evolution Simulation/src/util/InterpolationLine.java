package util;

import java.util.ArrayList;

public class InterpolationLine {

	// Line variables
	double m;
	double b;
	// Value between 0-1 that reflects the domain/range over the whole sample size
	double lowerBound;
	double upperBound;

	public InterpolationLine(double m, double b, double lowerBound, double upperBound) {
		this.m = m;
		this.b = b;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	public static boolean evaluationInRange(InterpolationLine line, double evaluation) {
		boolean inLowerBound = evaluation >= line.lowerBound;
		boolean inUpperBound = evaluation < line.upperBound;
		return inLowerBound && inUpperBound;
	}

	public double evaluate(double evaluation) {
		// Evaluation acts as 'x' in this linear function
		return m * evaluation + b;
	}

	public static InterpolationLine[] interpolateOverValues(double[] values) {
		// Conceptualize a 'line' between each value
		InterpolationLine[] result = new InterpolationLine[values.length - 1];
		double step = 1.0 / ((double)(values.length - 1));
		// Calculate the variables of each interpolation line
		for (int i = 0; i < values.length - 1; i++) {
			double lowerBound = i * step;
			double upperBound = lowerBound + step;
			double m = (values[i + 1] - values[i]) / step;
			double b = values[i] - (m * lowerBound);
			result[i] = new InterpolationLine(m , b, lowerBound, upperBound);
		}
		return result;
	}
	
	public static InterpolationLine[] scaledInterpolateOverValues(double scale, double[] values) {
		// Interpolate over values
		InterpolationLine[] result = interpolateOverValues(values); 
		// Scale each lines's variables
		for (int i = 0; i < result.length; i++) {
			result[i].m *= scale;
			result[i].b *= scale;
		}
		return result;
	}
	
	public static double evaluateInterpolationOverLines(double evaluation, InterpolationLine[] interpolationLines) {
		// Evaluates the conceptual 'piecewise function'
		double result = 0;
		for (int i = 0; i < interpolationLines.length; i++) {
			if (evaluationInRange(interpolationLines[i], evaluation)) {
				result += interpolationLines[i].evaluate(evaluation);
			}
		}
		return result;
	}

}
