package com.hyc.one.widget;

import android.animation.TypeEvaluator;

public class LinearEvaluator implements TypeEvaluator<Float> {

    @Override
    public Float evaluate(float fraction, Float startValue, Float endValue) {
        return (endValue - startValue) * fraction + startValue;
    }
}
