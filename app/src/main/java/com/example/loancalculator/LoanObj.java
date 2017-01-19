package com.example.loancalculator;

public class LoanObj {

	private int id;
	private String name;
	private float amt;
	private float rate;
	private float time;
	private String agent;
	private String location;
	private String date;
	private float emi;
	private int nofemi;
	private float rembal;

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getAmt() {
		return this.amt;
	}

	public void setAmt(float amt) {
		this.amt = amt;
	}

	public float getRate() {
		return this.rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public float getEmi() {
		return this.emi;
	}

	public void setEmi(float emi) {
		this.emi = emi;
	}

	public long getNofEmi() {
		return this.nofemi;
	}

	public void setNofEmi(int nofemi) {
		this.nofemi = nofemi;
	}

	public float getRemBal() {
		return this.rembal;
	}

	public void setRemBal(float rembal) {
		this.rembal = rembal;
	}

	public float getTime() {
		return this.time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getAgent() {
		return this.agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return name;
	}

}
