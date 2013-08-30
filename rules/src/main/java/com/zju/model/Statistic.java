package com.zju.model;

/**
 * 检验项目质量管理
 */
public class Statistic {
	private String testid;
	private int num;
	private Double average;
	private Double max;
	private Double min;
	private Double standardDeviation;
	private Double coefficientOfVariation;
	
	/**
	 * 检验项目id
	 */
	public String getTestid() {
		return testid;
	}
	
	public void setTestid(String testid) {
		this.testid = testid;
	}
	
	/**
	 * 检验项目个数
	 */
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * 检验项目平均值
	 */
	public Double getAverage() {
		return average;
	}
	
	public void setAverage(Double average) {
		this.average = average;
	}
	
	/**
	 * 检验项目最大值
	 */
	public Double getMax() {
		return max;
	}
	
	public void setMax(Double max) {
		this.max = max;
	}
	
	/**
	 * 检验项目最小值
	 */
	public Double getMin() {
		return min;
	}
	
	public void setMin(Double min) {
		this.min = min;
	}
	
	/**
	 * 检验项目标准差
	 */
	public Double getStandardDeviation() {
		return standardDeviation;
	}
	
	public void setStandardDeviation(Double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}
	
	/**
	 * 检验项目变异系数
	 */
	public Double getCoefficientOfVariation() {
		return coefficientOfVariation;
	}
	
	public void setCoefficientOfVariation(Double coefficientOfVariation) {
		this.coefficientOfVariation = coefficientOfVariation;
	}
}
