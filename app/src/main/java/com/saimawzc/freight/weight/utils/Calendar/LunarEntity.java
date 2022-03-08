package com.saimawzc.freight.weight.utils.Calendar;

public class LunarEntity {

	private int year;//农历年 数字
	private int month;//农历月  数字
	private int day;//农历日 数字
	private int solarYear;//公历年
	private int solarMonth;//公历月 
	private int solarDay;//公历日
	private String lunarMonth; //农历月  
	private String lunarDay; //农历日
	private String lunarSkyAndEarth;//天干地支
	private String zodiac;//生肖
	private String constellation;//星座
	private int leap;//闰月 若无 为0
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getLunarMonth() {
		return lunarMonth;
	}
	public void setLunarMonth(String lunarMonth) {
		this.lunarMonth = lunarMonth;
	}
	public String getLunarDay() {
		return lunarDay;
	}
	public void setLunarDay(String lunarDay) {
		this.lunarDay = lunarDay;
	}
	public String getLunarSkyAndEarth() {
		return lunarSkyAndEarth;
	}
	public void setLunarSkyAndEarth(String lunarSkyAndEarth) {
		this.lunarSkyAndEarth = lunarSkyAndEarth;
	}
	public int getLeap() {
		return leap;
	}
	public void setLeap(int leap) {
		this.leap = leap;
	}
	public LunarEntity(int year, int month, int day, String lunarMonth, String lunarDay, String lunarSkyAndEarth,
			String zodiac, String constellation, int leap) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.lunarMonth = lunarMonth;
		this.lunarDay = lunarDay;
		this.lunarSkyAndEarth = lunarSkyAndEarth;
		this.zodiac = zodiac;
		this.constellation = constellation;
		this.leap = leap;
	}
	
	
	public int getSolarYear() {
		return solarYear;
	}
	public void setSolarYear(int solarYear) {
		this.solarYear = solarYear;
	}
	public int getSolarMonth() {
		return solarMonth;
	}
	public void setSolarMonth(int solarMonth) {
		this.solarMonth = solarMonth;
	}
	public int getSolarDay() {
		return solarDay;
	}
	public void setSolarDay(int solarDay) {
		this.solarDay = solarDay;
	}
	public String getZodiac() {
		return zodiac;
	}
	public void setZodiac(String zodiac) {
		this.zodiac = zodiac;
	}
	public String getConstellation() {
		return constellation;
	}
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	public LunarEntity() {
		super();
	}
}
