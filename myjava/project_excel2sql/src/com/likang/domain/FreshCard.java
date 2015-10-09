package com.likang.domain;


public class FreshCard {
	private String freshTime;
	private int CardId;
	private String name;
	private String duty;
	private String num;
	private int freshCount;
	private String state;

	@Override
	public String toString() {
		return "FreshCard [freshTime=" + freshTime + ", CardId=" + CardId
				+ ", name=" + name + ", duty=" + duty + ", num=" + num
				+ ", freshCount=" + freshCount + ", state=" + state + "]";
	}

	public String getFreshTime() {
		return freshTime;
	}

	public void setFreshTime(String freshTime) {
		this.freshTime = freshTime;
	}

	public int getCardId() {
		return CardId;
	}

	public void setCardId(int cardId) {
		CardId = cardId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public int getFreshCount() {
		return freshCount;
	}

	public void setFreshCount(int freshCount) {
		this.freshCount = freshCount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
