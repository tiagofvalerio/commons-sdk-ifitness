package br.com.ifitness.assessment.util.strategy.pollock;

import java.io.Serializable;

import br.com.ifitness.assessment.util.info.BodyCompositionInfo;
import br.com.ifitness.assessment.util.strategy.BodyDensityCalculatorStrategy;

public class BodyDensityCalculatorPollock implements
		BodyDensityCalculatorStrategy, Serializable {

	private static final long serialVersionUID = 2626757674379048624L;

	private final Double firstMaleConstant = 1.10938D;
	private final Double firstFemaleConstant = 1.0994921D;

	private final Double secondMaleConstant = 0.0008267D;
	private final Double secondFemaleConstant = 0.0009929D;

	private final Double thirdMaleConstant = 0.0000016D;
	private final Double thirdFemaleConstant = 0.0000023D;

	private final Double fouthMaleConstant = 0.0002574D;
	private final Double fouthFemaleConstant = 0.0001392D;

	@Override
	public Double calculateBodyDensityCalculatorStrategy(
			BodyCompositionInfo bodyCompositionInfo) {

		Double result = 0.0D;

		switch (bodyCompositionInfo.getGenderInfo()) {
		case MALE:
			result = calculateByMaleFormula(bodyCompositionInfo);
			break;
		case FEMALE:
			result = calculateByFemaleFormula(bodyCompositionInfo);
			break;
		default:
			result = null;
			break;
		}

		return result;
	}

	private Double calculateByMaleFormula(
			BodyCompositionInfo bodyCompositionInfo) {

		Double maleThreeMesureSum = bodyCompositionInfo.getChest()
				+ bodyCompositionInfo.getAbdominal()
				+ bodyCompositionInfo.getCalf();

		Double bd = firstMaleConstant
				- (secondMaleConstant * maleThreeMesureSum)
				+ (thirdMaleConstant * (Math.pow(maleThreeMesureSum, 2)))
				- (fouthMaleConstant * bodyCompositionInfo.getStudantsAge());
		return bd;
	}

	private Double calculateByFemaleFormula(
			BodyCompositionInfo bodyCompositionInfo) {

		Double femaleThreeMesureSum = bodyCompositionInfo.getTriceps()
				+ bodyCompositionInfo.getSuprailiac()
				+ bodyCompositionInfo.getCalf();

		Double bd = firstFemaleConstant
				- (secondFemaleConstant * femaleThreeMesureSum)
				+ (thirdFemaleConstant * (Math.pow(femaleThreeMesureSum, 2)))
				- (fouthFemaleConstant * bodyCompositionInfo.getStudantsAge());
		return bd;
	}

}
