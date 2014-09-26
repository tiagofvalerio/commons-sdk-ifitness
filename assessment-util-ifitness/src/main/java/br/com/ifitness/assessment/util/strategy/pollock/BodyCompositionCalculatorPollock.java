package br.com.ifitness.assessment.util.strategy.pollock;

import java.io.Serializable;

import br.com.ifitness.assessment.util.info.BodyCompositionInfo;
import br.com.ifitness.assessment.util.strategy.BodyCompositionCalculatorStrategy;

public class BodyCompositionCalculatorPollock implements BodyCompositionCalculatorStrategy,
		Serializable {

	private static final long serialVersionUID = 2924333944636514382L;

	private BodyCompositionInfo bodyCompositionInfo;
	
	public BodyCompositionCalculatorPollock() {
	}
	
	public BodyCompositionCalculatorPollock(BodyCompositionInfo bodyCompositionInfo) {
		this.bodyCompositionInfo = bodyCompositionInfo;
	}

	@Override
	public void calculateBodyComposition() {
		// TODO Auto-generated method stub

	}

}
