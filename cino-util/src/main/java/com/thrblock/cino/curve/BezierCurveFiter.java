package com.thrblock.cino.curve;

import java.util.stream.Stream;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import com.thrblock.cino.function.FloatUnaryOperator;
import com.thrblock.cino.util.math.CubeBezier;
import com.thrblock.cino.util.structure.Point2D;

public class BezierCurveFiter {
    private static final int DEF_PRECISION = 128;
    public FloatUnaryOperator fitPolynomial(CubeBezier cubeBezier, int degree) {
        return fitPolynomial(cubeBezier, DEF_PRECISION, degree);
    }
    public FloatUnaryOperator fitPolynomial(CubeBezier cubeBezier, int precision, int degree) {
        Point2D start = cubeBezier.getStart();
        Point2D end = cubeBezier.getEnd();
        WeightedObservedPoints obs = new WeightedObservedPoints();
        Stream.iterate(0, i -> i + 1).limit(precision).forEach(i -> {
            float t = start.getX() + (end.getX() - start.getX()) / precision;
            obs.add(cubeBezier.bezierX(t * i), cubeBezier.bezierY(t * i));
        });
        // Instantiate a polynomial fitter.
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);
        // Retrieve fitted parameters (coefficients of the polynomial function).
        double[] coeff = fitter.fit(obs.toList());
        return input -> {
            float result = 0;
            for (int i = 0; i < coeff.length; i++) {
                result += coeff[i] * Math.pow(input, i);
            }
            return result;
        };
    }
}