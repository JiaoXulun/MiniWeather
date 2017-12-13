package cn.edu.pku.jiaoxulun.bean;

import java.lang.ref.SoftReference;

/**
 * Created by jiaoxulun on 2017/10/16.
 */

public class TodayWeather {
    private String city;
    private String updatetime;
    private String wendu;
    private String shidu;
    private String pm25;
    private String quality;
    private String fengxiang;
    private String fengli;
    private String date;
    private String high;
    private String low;
    private String type;

    private String date1;
    private String date2;
    private String date3;
    private String date4;
    private String date5;

    private String type1;
    private String type2;
    private String type3;
    private String type4;
    private String type5;

    private String high1;
    private String high2;
    private String high3;
    private String high4;
    private String high5;

    private String low1;
    private String low2;
    private String low3;
    private String low4;
    private String low5;

    public String getCity() {
        return city;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public String getWendu() {
        return wendu;
    }

    public String getShidu() {
        return shidu;
    }

    public String getPm25() {
        return pm25;
    }

    public String getQuality() {
        return quality;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public String getFengli() {
        return fengli;
    }

    public String getDate() {
        return date;
    }

    public String getHigh() {
        return high.substring(2);
    }

    public String getLow() {
        return low.substring(2);
    }

    public String getType() {
        return type;
    }

    public String getDate1() {
        return date1;
    }

    public String getDate2() {
        return date2;
    }

    public String getDate3() {
        return date3;
    }

    public String getDate4() {
        return date4;
    }

    public String getDate5() {
        return date5;
    }

    public String getHigh1() {
        return high1.substring(2);
    }

    public String getType1() {
        return type1;
    }

    public String getHigh2() {
        return high2.substring(2);
    }

    public String getType2() {
        return type2;
    }

    public String getHigh3() {
        return high3.substring(2);
    }

    public String getHigh4() {
        return high4.substring(2);
    }

    public String getType3() {
        return type3;
    }

    public String getHigh5() {
        return high5.substring(2);
    }

    public String getType4() {
        return type4;
    }

    public String getType5() {
        return type5;
    }

    public String getLow1() {
        return low1.substring(2);
    }

    public String getLow2() {
        return low2.substring(2);
    }

    public String getLow3() {
        return low3.substring(2);
    }

    public String getLow4() {
        return low4.substring(2);
    }

    public String getLow5() {
        return low5.substring(2);
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public void setDate3(String date3) {
        this.date3 = date3;
    }

    public void setDate4(String date4) {
        this.date4 = date4;
    }

    public void setDate5(String date5) {
        this.date5 = date5;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public void setHigh1(String high1) {
        this.high1 = high1;
    }

    public void setHigh2(String high2) {
        this.high2 = high2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public void setType3(String type3) {
        this.type3 = type3;
    }

    public void setType4(String type4) {
        this.type4 = type4;
    }

    public void setHigh3(String high3) {
        this.high3 = high3;
    }

    public void setHigh4(String high4) {
        this.high4 = high4;
    }

    public void setHigh5(String high5) {
        this.high5 = high5;
    }

    public void setType5(String type5) {
        this.type5 = type5;
    }

    public void setLow1(String low1) {
        this.low1 = low1;
    }

    public void setLow2(String low2) {
        this.low2 = low2;
    }

    public void setLow3(String low3) {
        this.low3 = low3;
    }

    public void setLow4(String low4) {
        this.low4 = low4;
    }

    public void setLow5(String low5) {
        this.low5 = low5;
    }


    @Override
    public String toString() {
        return "TodayWeather{" +
                "city='" + city + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", wendu='" + wendu + '\'' +
                ", shidu='" + shidu + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", quality='" + quality + '\'' +
                ", fengxiang='" + fengxiang + '\'' +
                ", fengli='" + fengli + '\'' +
                ", date='" + date + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", type='" + type + '\'' +
                '}';


    }

    @Override
    public Object clone() {
        TodayWeather cloneObj = null;
        try{
            cloneObj = (TodayWeather) super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return cloneObj;
    }
}
