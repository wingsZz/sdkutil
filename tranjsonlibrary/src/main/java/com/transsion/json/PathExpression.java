package com.transsion.json;

import java.util.Arrays;


public class PathExpression {
    final String[] expression;
    boolean wildcard = false;
    boolean included = true;

    public PathExpression(String expr, boolean anInclude) {
        expression = expr.split("\\.");
        wildcard = expr.indexOf('*') >= 0;
        included = anInclude;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < expression.length; i++) {
            builder.append(expression[i]);
            if (i < expression.length - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    public boolean matches(Path path) {
        int exprCurrentIndex = 0;
        int pathCurrentIndex = 0;
        while (pathCurrentIndex < path.length()) {
            String current = path.getPath().get(pathCurrentIndex);
            if (exprCurrentIndex < expression.length && expression[exprCurrentIndex].equals("*")) {
                exprCurrentIndex++;
            } else if (exprCurrentIndex < expression.length && expression[exprCurrentIndex].equals(current)) {
                pathCurrentIndex++;
                exprCurrentIndex++;
            } else if (exprCurrentIndex - 1 >= 0 && expression[exprCurrentIndex - 1].equals("*")) {
                pathCurrentIndex++;
            } else {
                return false;
            }
        }
        if (exprCurrentIndex > 0 && expression[exprCurrentIndex - 1].equals("*")) {
            return pathCurrentIndex >= path.length() && exprCurrentIndex >= expression.length;
        } else {
            return pathCurrentIndex >= path.length() && path.length() > 0;
        }
    }

    public boolean isWildcard() {
        return wildcard;
    }

    public boolean isIncluded() {
        return included;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathExpression that = (PathExpression) o;
        return Arrays.equals(expression, that.expression);
    }

    public int hashCode() {
        return (expression != null ? Arrays.hashCode(expression) : 0);
    }
}
