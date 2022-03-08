package com.saimawzc.freight.weight.utils.Calendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 该工具类实现公历与农历之间的相互转换 
 * @author dsltyyz
 * @time 2015-4-8 17:05:11
 * @qq 278750600
 * @email 13684008780@163.com
 * @version 1.0.0
 * @description 根据php公历与农历之间的相互转换 
 */

public class Lunar {
	//最小年份
	private static final int MIN_YEAR = 1891;
	//最大年份
	private static final int MAX_YEAR = 2100;
	//LUNAR 计算参数 {闰月, 每年农历开始月份, 日期, 农历月份天数(转二进制 +对应相加29天)}
	private static final int[][] LUNAR_INFO = {
        {0,2,9,21936},{6,1,30,9656},{0,2,17,9584},{0,2,6,21168},{5,1,26,43344},{0,2,13,59728},
        {0,2,2,27296},{3,1,22,44368},{0,2,10,43856},{8,1,30,19304},{0,2,19,19168},{0,2,8,42352},
        {5,1,29,21096},{0,2,16,53856},{0,2,4,55632},{4,1,25,27304},{0,2,13,22176},{0,2,2,39632},
        {2,1,22,19176},{0,2,10,19168},{6,1,30,42200},{0,2,18,42192},{0,2,6,53840},{5,1,26,54568},
        {0,2,14,46400},{0,2,3,54944},{2,1,23,38608},{0,2,11,38320},{7,2,1,18872},{0,2,20,18800},
        {0,2,8,42160},{5,1,28,45656},{0,2,16,27216},{0,2,5,27968},{4,1,24,44456},{0,2,13,11104},
        {0,2,2,38256},{2,1,23,18808},{0,2,10,18800},{6,1,30,25776},{0,2,17,54432},{0,2,6,59984},
        {5,1,26,27976},{0,2,14,23248},{0,2,4,11104},{3,1,24,37744},{0,2,11,37600},{7,1,31,51560},
        {0,2,19,51536},{0,2,8,54432},{6,1,27,55888},{0,2,15,46416},{0,2,5,22176},{4,1,25,43736},
        {0,2,13,9680},{0,2,2,37584},{2,1,22,51544},{0,2,10,43344},{7,1,29,46248},{0,2,17,27808},
        {0,2,6,46416},{5,1,27,21928},{0,2,14,19872},{0,2,3,42416},{3,1,24,21176},{0,2,12,21168},
        {8,1,31,43344},{0,2,18,59728},{0,2,8,27296},{6,1,28,44368},{0,2,15,43856},{0,2,5,19296},
        {4,1,25,42352},{0,2,13,42352},{0,2,2,21088},{3,1,21,59696},{0,2,9,55632},{7,1,30,23208},
        {0,2,17,22176},{0,2,6,38608},{5,1,27,19176},{0,2,15,19152},{0,2,3,42192},{4,1,23,53864},
        {0,2,11,53840},{8,1,31,54568},{0,2,18,46400},{0,2,7,46752},{6,1,28,38608},{0,2,16,38320},
        {0,2,5,18864},{4,1,25,42168},{0,2,13,42160},{10,2,2,45656},{0,2,20,27216},{0,2,9,27968},
        {6,1,29,44448},{0,2,17,43872},{0,2,6,38256},{5,1,27,18808},{0,2,15,18800},{0,2,4,25776},
        {3,1,23,27216},{0,2,10,59984},{8,1,31,27432},{0,2,19,23232},{0,2,7,43872},{5,1,28,37736},
        {0,2,16,37600},{0,2,5,51552},{4,1,24,54440},{0,2,12,54432},{0,2,1,55888},{2,1,22,23208},
        {0,2,9,22176},{7,1,29,43736},{0,2,18,9680},{0,2,7,37584},{5,1,26,51544},{0,2,14,43344},
        {0,2,3,46240},{4,1,23,46416},{0,2,10,44368},{9,1,31,21928},{0,2,19,19360},{0,2,8,42416},
        {6,1,28,21176},{0,2,16,21168},{0,2,5,43312},{4,1,25,29864},{0,2,12,27296},{0,2,1,44368},
        {2,1,22,19880},{0,2,10,19296},{6,1,29,42352},{0,2,17,42208},{0,2,6,53856},{5,1,26,59696},
        {0,2,13,54576},{0,2,3,23200},{3,1,23,27472},{0,2,11,38608},{11,1,31,19176},{0,2,19,19152},
        {0,2,8,42192},{6,1,28,53848},{0,2,15,53840},{0,2,4,54560},{5,1,24,55968},{0,2,12,46496},
        {0,2,1,22224},{2,1,22,19160},{0,2,10,18864},{7,1,30,42168},{0,2,17,42160},{0,2,6,43600},
        {5,1,26,46376},{0,2,14,27936},{0,2,2,44448},{3,1,23,21936},{0,2,11,37744},{8,2,1,18808},
        {0,2,19,18800},{0,2,8,25776},{6,1,28,27216},{0,2,15,59984},{0,2,4,27424},{4,1,24,43872},
        {0,2,12,43744},{0,2,2,37600},{3,1,21,51568},{0,2,9,51552},{7,1,29,54440},{0,2,17,54432},
        {0,2,5,55888},{5,1,26,23208},{0,2,14,22176},{0,2,3,42704},{4,1,23,21224},{0,2,11,21200},
        {8,1,31,43352},{0,2,19,43344},{0,2,7,46240},{6,1,27,46416},{0,2,15,44368},{0,2,5,21920},
        {4,1,24,42448},{0,2,12,42416},{0,2,2,21168},{3,1,22,43320},{0,2,9,26928},{7,1,29,29336},
        {0,2,17,27296},{0,2,6,44368},{5,1,26,19880},{0,2,14,19296},{0,2,3,42352},{4,1,24,21104},
        {0,2,10,53856},{8,1,30,59696},{0,2,18,54560},{0,2,7,55968},{6,1,27,27472},{0,2,15,22224},
        {0,2,5,19168},{4,1,25,42216},{0,2,12,42192},{0,2,1,53584},{2,1,21,55592},{0,2,9,54560}
    };
	
	//日期汉字
	private static final String[] DATE = {"", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
	//月份汉字
	private static final String[] MONTH = {"", "正月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "冬月", "腊月"};
	//天干
	private static final String[] SKY = {"庚","辛","壬","癸","甲","乙","丙","丁","戊","己"};
	//地支
	private static final String[] EARTH = {"申","酉","戌","亥","子","丑","寅","卯","辰","巳","午","未"};
	//生肖
	private static final String[] ZODIAC = {"猴","鸡","狗","猪","鼠","牛","虎","兔","龙","蛇","马","羊"};
	//星座
	private final static String[][] CONSTELLATION = {
		{"01-20", "02-18", "1", "水瓶座"},
        {"02-19", "03-20", "2", "双鱼座"},
        {"03-21", "04-19", "3", "白羊座"},
        {"04-20", "05-20", "4", "金牛座"},
        {"05-21", "06-21", "5", "双子座"},
        {"06-22", "07-22", "6", "巨蟹座"},
        {"07-23", "08-22", "7", "狮子座"},
        {"08-23", "09-22", "8", "处女座"},
        {"09-23", "10-23", "9", "天秤座"},
        {"10-24", "11-22", "10", "天蝎座"},
        {"11-23", "12-21", "11", "射手座"},
        {"12-22", "01-19", "12", "摩羯座"}
	};
	

	/**
	 * 字符串得到日期
	 * @param date
	 * @return Date
	 */
	private static Date getDate(String date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dd = null;

		try {
			dd = format.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dd;
	}
	
	/**
	 * 获取最近生日距离现在多少天
	 * @param birthday 出生年月
	 * @param flag true 获取星座id false 获取星座名称
	 * @return String
	 */
	private static String getConstellation(String birthday, boolean flag) {
		String year = birthday.substring(0, 4);
		String result = null;
		for (String[] constellation : CONSTELLATION) {
			  if (!constellation[2].equals("12")) {
	                if (!getDate(birthday).before(getDate(year+"-"+constellation[0])) && !getDate(birthday).after(getDate(year+"-"+constellation[1]))) {
	                    result = flag ? constellation[2]:constellation[3];
	                }
	            }else{
	                if (!getDate(birthday).before(getDate(year+"-"+constellation[0])) || !getDate(birthday).after(getDate(year+"-"+constellation[1]))) {
	                	result = flag ? constellation[2]:constellation[3];
	                }
	            }
		}
		return result;
	}
	/**
	 * 农历转公历
	 * @param year
	 * @param month 闰月该月份+1 之后的都加1
	 * @param day
	 * @return LunarEntity
	 * @throws ParseException
	 */
	private static LunarEntity convertLunarToSolar(int year, int month, int day) throws ParseException{
		//超出范围 
		if(year>MAX_YEAR||year<MIN_YEAR){
			return null;
		}
		int[] yearDate = LUNAR_INFO[year-MIN_YEAR];
		int between  = getDaysBetweenLunar(year, month, day);
		Date date = getDate(year+"-"+yearDate[1]+"-"+yearDate[2]);
		//注意数字越界
		//date = new Date(date.getTime()+between*24*60*60*1000);//越界了
		date = new Date((date.getTime()/1000+between*24*60*60)*1000);
		String[] d = dateToStr(date, 1).split("-");
		LunarEntity le = convertSolarToLunar(Integer.valueOf(d[0]), Integer.valueOf(d[1]), Integer.valueOf(d[2]));
		return le;
	}
	
	/**
	 * 日期转字符串
	 * @param d
	 * @param type
	 * @return String
	 */
	private static String dateToStr(Date d, int type) {
		String s = "";
		SimpleDateFormat sdf = null;
		switch (type) {
		case 1:
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			break;
		case 2:
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		case 3:
			sdf = new SimpleDateFormat("yyyy.MM.dd");
			break;
		case 4:
			sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			break;
		default:
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			break;
		}
		s = sdf.format(d);
		return s;
	}

	/**
	 * 公历转农历
	 * @param year
	 * @param month
	 * @param day
	 * @return LunarEntity
	 * @throws ParseException
	 */
	private static LunarEntity convertSolarToLunar(int year, int month,int day) throws ParseException{
		//超出范围
		if(year>MAX_YEAR||year<MIN_YEAR){
			return null;
		}
		int[] yearDate = LUNAR_INFO[year-MIN_YEAR];
		if(year==MIN_YEAR && month<=2 && day<=9){
			LunarEntity le = new LunarEntity(1891,1,1,"正月","初一","辛卯","兔","摩羯座",0);
			return le;
		}
		LunarEntity le = getLunarByBetween(year, getDaysBetweenSolar(year, month, day, yearDate[1], yearDate[2]));
		le.setConstellation(Lunar.getConstellation(year+"-"+month+"-"+day, false));
		le.setSolarYear(year);
		le.setSolarMonth(month);
		le.setSolarDay(day);
		return le;
	}
	
	/**
	 * 计算同一天 阳历阳历日期之间的天数
	 * @param year 阳历年
	 * @param cMonth 阳历月
	 * @param cDay 阳历日
	 * @param dMonth 阴历月
	 * @param dDay 阴历日
	 * @return int 相差天数
	 * @throws ParseException
	 */
	private static int getDaysBetweenSolar(int year, int cMonth, int cDay, int dMonth, int dDay) throws ParseException{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date solar = format.parse(year+"-"+cMonth+"-"+cDay);
		Date lunar = format.parse(year+"-"+dMonth+"-"+dDay);
		return (int) ((solar.getTime()-lunar.getTime())/(24 * 60 * 60 * 1000));
	}
	
	/**
	 * 根据年份 相差天数 确定当天的农历信息
	 * @param year 年份
	 * @param between 公历 农历之间天数差多少天
	 * @return LunarEntity
	 */
	@SuppressWarnings("rawtypes")
	private static LunarEntity getLunarByBetween(int year, int between){
		LunarEntity lunarEntity = new LunarEntity();
		int t=0, e=0, leapMonth=0;
		String m = "";
		if(between == 0){
			lunarEntity.setYear(year);//设置年
			lunarEntity.setLunarMonth("正月");//设置农历月
			lunarEntity.setLunarDay("初一");//设置农历日
			t=1;
			e=1;
		}else{
			year = between>0 ? year : year-1;
			ArrayList yearMonth = getLunarYearMonths(year);
			leapMonth = getLeapMonth(year);
			between = between>0?between:(getLunarYearDays(year)+between);
			for(int i=0;i<yearMonth.size();i++){
				if(between==(int)yearMonth.get(i)){
					t = i + 2;
					e = 1;
					break;
				}else if( between <(int)yearMonth.get(i)){
					t = i + 1;
					e = between - (i-1<0?0:(int)yearMonth.get(i-1))+1;
					break;
				}
			}
			m = (leapMonth!=0 && t == leapMonth+1)?("闰"+getCapitalNum(t-1,true)):getCapitalNum((leapMonth!=0&&leapMonth+1<t?(t-1):t),true);
			lunarEntity.setYear(year);//设置年
			lunarEntity.setLunarMonth(m);//设置农历月
			lunarEntity.setLunarDay(getCapitalNum(e, false));//设置农历日
		}
		lunarEntity.setMonth(t);//农历月 数字
		lunarEntity.setDay(e);//农历日 数字
		lunarEntity.setLunarSkyAndEarth(getLunarYearName(year));//设置天干地支
		lunarEntity.setZodiac(getYearZodiac(year));//设置生肖
		lunarEntity.setLeap(leapMonth);;//设置生肖
		return lunarEntity;
	}
	
	/**
	 * 获取某年 农历月份天数列表
	 * @param year
	 * @return ArrayList
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static ArrayList getLunarYearMonths(int year){
		ArrayList monthData = getLunarMonths(year);
		ArrayList res = new ArrayList();
		int temp = 0;
		for(int i=0;i<monthData.size();i++){
			temp = temp + (int)monthData.get(i);
			res.add(temp);
		}
		return res;
	}
	
	/**
	 * 获取公历之间（1月1号）相差的天数
	 * @param year
	 * @param month
	 * @param day
	 * @return int
	 */
	@SuppressWarnings({ "rawtypes" })
	private static int getDaysBetweenLunar(int year, int month, int day){
		ArrayList monthData = getLunarMonths(year);
		int res = 0;
		for(int i=0;i<month-1;i++){
			res += (int)monthData.get(i);
		}
		res += day-1;
		return res;
	}
	
	/**
	 * 获取当年农历没月份天数列表
	 * @param year
	 * @return ArrayList
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static ArrayList getLunarMonths(int year){
		int[] yearData = LUNAR_INFO[year-MIN_YEAR];
		int leap = yearData[0];
		int len = leap == 0?12:13;
		String bit = Integer.toBinaryString(yearData[3]);
		for(int i=0;i<16-Integer.toBinaryString(yearData[3]).length();i++){
			bit = "0"+bit;
		}
		
		ArrayList bits = new ArrayList();
		for(int i=0;i<len;i++){
			bits.add(Integer.valueOf(bit.substring(i, i+1))+29);
		}
		return bits;
	}
	
	/**
	 * 获取当年农历闰月的月份
	 * @param year
	 * @return int
	 */
	private static int getLeapMonth(int year){
		int[] yearData = LUNAR_INFO[year-MIN_YEAR];
		return yearData[0];
	}
	
	/**
	 * 获取农历当年的总天数
	 * @param year
	 * @return int
	 */
	@SuppressWarnings("rawtypes")
	private static int getLunarYearDays(int year){
		ArrayList monthData = getLunarYearMonths(year);
		int len = monthData.size();
		return (int)((0 == (int)monthData.get(len-1)) ? monthData.get(len-2) : monthData.get(len-1));
	}
	
	/**
	 * 根据数字返回字符串 主要用于获取农历月份 天数
	 * @param num 数字
	 * @param isMonth true 获取月份 false 获取天数 
	 * @return String
	 */
	private static String getCapitalNum(int num, boolean isMonth){
		
		String res="";
		if(isMonth){
			res = MONTH[num];
		}else{
			  if(num<=10){
	                res = "初"+DATE[num];
	            }else if(num>10&&num<20){
	                res = "十"+DATE[num-10];
	            }else if(num==20){
	                res = "二十";
	            }else if(num>20&&num<30){
	                res = "廿"+DATE[num-20];
	            }else if(num==30){
	                res = "三十";
	            }
		}
		return res;
	}
	
	/**
	 * 获取天干地支
	 * @param year
	 * @return String
	 */
	private static String getLunarYearName(int year){
		return SKY[year%SKY.length]+EARTH[year%EARTH.length];
	}
	
	/**
	 * 获取生肖			
	 * @param year
	 * @return String
	 */
	private static String getYearZodiac(int year){
		return ZODIAC[year%ZODIAC.length];
	}
	
	/**
	 * 通过年份获取是否是闰年
	 * @param year
	 * @return
	 */
	private static boolean isLeapYear(int year) {
		return ((year%4==0 && year%100 !=0) || (year%400==0))?true:false;
	}
	
	/**
	 * 获取最近生日的天数
	 * @param year
	 * @param month
	 * @param day 出生年月日 都是公历的日期 
	 * @param flag true 农历  false 阳历
	 * @return LunarEntity
	 * @throws ParseException 
	 */
	public static int getIntervalDaysOfBirthday(int year, int month, int day, boolean flag) throws ParseException {
		// 得到今天的日期 去除时分秒
		Date date = new Date();
		String dateString = dateToStr(date, 1);
		int nowyear = Integer.valueOf(dateString.substring(0, dateString.indexOf("-")));
		String nearBirthday = null;// 记录最近生日
		if (flag) {// 阴历
			LunarEntity le = Lunar.convertSolarToLunar(year, month, day);
			LunarEntity se = getNowbirthdayByBirthdayViaLunar(le);
			nearBirthday = se.getSolarYear() + "-" + se.getSolarMonth() + "-" + se.getSolarDay();
		} else {// 阳历
			if(month == 2 && day ==29){
				for(int i=0; i<5; i++){
					if(isLeapYear(nowyear) && (getDate(dateString).before(getDate(nowyear+"-02-29")) || dateString.equals(nowyear+"-02-29"))){
						break;
					}
					nowyear++;
				}
			}
			nearBirthday = nowyear + "-" + month + "-" + day;
			// 当前时间已经超过生日
			if (getDate(dateString).after(getDate(nearBirthday))) {
				nearBirthday = (nowyear + 1) + "-" + month + "-" + day;
			}
		}
		long intervalMilli = getDate(nearBirthday).getTime() - getDate(dateString).getTime();
		return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	}
	
	/**
	 * 通过获取农历获取最近农历生日
	 * 规则:1.非闰月生日只过闰月生日 2.闰月生日 若当前有相同闰月 过闰月 若无 过非闰月 3.若当前年份没有该农历日期 提前一天过 4.公历2-29 4年过一次生日
	 * @param le 
	 * @return LunarEntity
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	private static LunarEntity getNowbirthdayByBirthdayViaLunar(LunarEntity le) throws NumberFormatException, ParseException{
		String dateString = dateToStr(new Date(), 1);
		int year = Integer.valueOf(dateString.substring(0, dateString.indexOf("-")));
		//判断今年是否有闰月
		LunarEntity nowleap = Lunar.convertSolarToLunar(year, 8, 8);
		//判断出生那年是否有闰月 且就是闰月出生
		if(le.getLeap()>0 && le.getMonth() == le.getLeap()+1){
			if(nowleap.getLeap()>0 && nowleap.getLeap()<=le.getMonth()){
				nowleap = Lunar.convertLunarToSolar(year, le.getMonth(), le.getDay());
			}else{
				nowleap = Lunar.convertLunarToSolar(year, le.getMonth()-1, le.getDay());
			}
		}else{//非闰月出生 1.当前是否有闰月 2.出生时是否有闰月 3.出生月份与当前月份关系
			if(nowleap.getLeap()>0&&le.getLeap()==0&&nowleap.getLeap()<le.getMonth()){
				nowleap = Lunar.convertLunarToSolar(year, le.getMonth()+1, le.getDay());
			}else if((nowleap.getLeap()>0&&le.getLeap()>0&&nowleap.getLeap()>=le.getMonth())||
					(nowleap.getLeap()==0&&le.getLeap()>0 && le.getLeap()<le.getMonth())){
				nowleap = Lunar.convertLunarToSolar(year, le.getMonth()-1, le.getDay());
			}else{
				nowleap = Lunar.convertLunarToSolar(year, le.getMonth(), le.getDay());
			}
		}
		if(nowleap.getDay()!=le.getDay()){
			le.setDay(le.getDay()-1);
			nowleap = getNowbirthdayByBirthdayViaLunar(le);
		}
		return nowleap;
	}


	public static void main() throws ParseException{
		LunarEntity le = Lunar.convertSolarToLunar(2019, 1, 1);//公历转农历
		System.out.println(le.getLunarMonth());
		System.out.println(le.getLunarDay());
		System.out.println(le.getConstellation());
		System.out.println(le.getSolarYear());
		System.out.println(le.getSolarMonth());
		System.out.println(le.getSolarDay());
		System.out.println("-----------------");
		LunarEntity se = Lunar.convertLunarToSolar(2019, 1, 1);//农历转公历
		System.out.println(se.getLunarMonth());
		System.out.println(se.getLunarDay());
		System.out.println(se.getConstellation());
		System.out.println(se.getSolarYear());
		System.out.println(se.getSolarMonth());
		System.out.println(se.getSolarDay());
	}


	public String getNongLiByYl() throws ParseException {//通过农历获取阳历
		Calendar c = Calendar.getInstance();//
		int mYear = c.get(Calendar.YEAR)-1; // 获取当前年份
		int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
		int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
		LunarEntity se = Lunar.convertLunarToSolar(c.get(Calendar.YEAR)+1, 1, 1);//农历转公历
		System.out.println(se.getLunarMonth());
		System.out.println(se.getLunarDay());
		System.out.println(se.getConstellation());
		System.out.println(se.getSolarYear());
		System.out.println(se.getSolarMonth());
		System.out.println(se.getSolarDay());
		return  se.getSolarYear()+"-"+se.getSolarMonth()+"-"+se.getSolarDay();
	}

	public int Year() throws ParseException {//通过农历获取阳历
		Calendar c = Calendar.getInstance();//
		int mYear = c.get(Calendar.YEAR)-1; // 获取当前年份
		int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
		int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
		LunarEntity se = Lunar.convertLunarToSolar(c.get(Calendar.YEAR)+1, 1, 1);//农历转公历
		System.out.println(se.getLunarMonth());
		System.out.println(se.getLunarDay());
		System.out.println(se.getConstellation());
		System.out.println(se.getSolarYear());
		System.out.println(se.getSolarMonth());
		System.out.println(se.getSolarDay());
		return  se.getSolarYear();
	}
	public int Month() throws ParseException {//通过农历获取阳历
		Calendar c = Calendar.getInstance();//
		int mYear = c.get(Calendar.YEAR)-1; // 获取当前年份
		int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
		int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
		LunarEntity se = Lunar.convertLunarToSolar(c.get(Calendar.YEAR)+1, 1, 1);//农历转公历
		System.out.println(se.getLunarMonth());
		System.out.println(se.getLunarDay());
		System.out.println(se.getConstellation());
		System.out.println(se.getSolarYear());
		System.out.println(se.getSolarMonth());
		System.out.println(se.getSolarDay());
		return  se.getSolarMonth();
	}
	public int Day() throws ParseException {//通过农历获取阳历
		Calendar c = Calendar.getInstance();//
		int mYear = c.get(Calendar.YEAR)-1; // 获取当前年份
		int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
		int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
		LunarEntity se = Lunar.convertLunarToSolar(c.get(Calendar.YEAR)+1, 1, 1);//农历转公历
		System.out.println(se.getLunarMonth());
		System.out.println(se.getLunarDay());
		System.out.println(se.getConstellation());
		System.out.println(se.getSolarYear());
		System.out.println(se.getSolarMonth());
		System.out.println(se.getSolarDay());
		return  se.getSolarDay();
	}



}
