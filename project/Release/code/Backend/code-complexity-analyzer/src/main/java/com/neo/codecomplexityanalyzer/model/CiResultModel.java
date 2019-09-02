package com.neo.codecomplexityanalyzer.model;

public class CiResultModel {
	private String className;
	private Integer totalCiValue;
	private Integer numberOfAncestors;
	private String classHierachy;

	public CiResultModel() {
	}

	public CiResultModel(String className, Integer totalCiValue, Integer numberOfAncestors, String classHierachy) {
		super();
		this.className = className;
		this.totalCiValue = totalCiValue;
		this.numberOfAncestors = numberOfAncestors;
		this.classHierachy = classHierachy;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the totalCiValue
	 */
	public Integer getTotalCiValue() {
		return totalCiValue;
	}

	/**
	 * @param totalCiValue the totalCiValue to set
	 */
	public void setTotalCiValue(Integer totalCiValue) {
		this.totalCiValue = totalCiValue;
	}

	/**
	 * @return the numberOfAncestors
	 */
	public Integer getNumberOfAncestors() {
		return numberOfAncestors;
	}

	/**
	 * @param numberOfAncestors the numberOfAncestors to set
	 */
	public void setNumberOfAncestors(Integer numberOfAncestors) {
		this.numberOfAncestors = numberOfAncestors;
	}

	/**
	 * @return the classHierachy
	 */
	public String getClassHierachy() {
		return classHierachy;
	}

	/**
	 * @param classHierachy the classHierachy to set
	 */
	public void setClassHierachy(String classHierachy) {
		this.classHierachy = classHierachy;
	}

}
