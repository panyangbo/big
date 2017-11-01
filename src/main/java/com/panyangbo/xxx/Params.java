package com.panyangbo.xxx;

import java.math.BigDecimal;


/**
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/20 15:54
 * @Description Params
 */
public class Params {

	//已处理为1
	private String handleFlag;
	
	private String deviceID;
	private String timeZone;
	private String time;
	private String divisionEW;//N：北半球；S：南半球
	private String longitude;//纬度=实际度*3600*100+实际分*60*100+实际秒*100
	private String divisionNS;
	private String latitude;
	private String direction;
	private String speed;
	private String satellites;
	private String precision;
	private String height;
	private String retransFlag;
	private String needsResponse;
	private String remark;
	
	//无人机字段
	private String type;
	private String rollAngle;
	private String pitchAngle;
	private String yawAngle;
	private String relativeHeight;
	private String verticalSpeed;
	private String batteryRemaining;
	
	//告警
	private String action;
	private String videoChannel;
	private String alarmInChannel;
	private String diskNumber;
	
	//附加字段为了兼容以往版本82-metis
	private String devicename;
	private String deviceType;
	  
	public String getDeviceID() {
		return deviceID;
	}
	
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
		this.devicename = deviceID;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDivisionEW() {
		return divisionEW;
	}
	public void setDivisionEW(String divisionEW) {
		this.divisionEW = divisionEW;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		if ("1".equals(handleFlag)) {
			this.longitude = longitude;
		}else {
			String tempCoord="";
			if(!"".equals(longitude)&& null!= longitude){
				double _num = Double.parseDouble(longitude)/(3600*100);
				BigDecimal bg = new BigDecimal(_num);
				double _d = bg.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
				tempCoord = String.valueOf(_d);
			} 
			this.longitude = tempCoord;
		}
	}
	public String getDivisionNS() {
		return divisionNS;
	}
	public void setDivisionNS(String divisionNS) {
		this.divisionNS = divisionNS;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		if ("1".equals(handleFlag)) {
			this.latitude = latitude;
		}else {
			String tempCoord="";
			if(!"".equals(latitude)&& null!= latitude){
				double _num = Double.parseDouble(latitude)/(3600*100);
				BigDecimal bg = new BigDecimal(_num);
				double _d = bg.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
				tempCoord = String.valueOf(_d);
			} 
			this.latitude = tempCoord;
		}
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		if ("1".equals(handleFlag)) {
			this.direction = direction;
		}else {
			String tempDir="0";
			if(!"".equals(direction)&& null!= direction){
				double d = Double.parseDouble(direction);
			 	int _num = Integer.parseInt(new java.text.DecimalFormat("0").format(d));
			 	_num %= 36000;
			 	if (_num<0) {
			 		_num += 36000;
				}
			 	tempDir = String.valueOf(_num/100);
			} 
			this.direction = tempDir;
		}
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		if ("1".equals(handleFlag)) {
			this.speed = speed;
		}else {
			String tempspeed="0";
			if(!"".equals(speed)&& null!= speed){
				int d = Integer.parseInt(speed);
				d /= 100*1000;
				tempspeed = String.valueOf(d);
			} 
			this.speed = tempspeed;
		}
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getSatellites() {
		return satellites;
	}
	public void setSatellites(String satellites) {
		this.satellites = satellites;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getRetransFlag() {
		return retransFlag;
	}
	public void setRetransFlag(String retransFlag) {
		this.retransFlag = retransFlag;
	}
	public String getNeedsResponse() {
		return needsResponse;
	}
	public void setNeedsResponse(String needsResponse) {
		this.needsResponse = needsResponse;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
		this.deviceType = type;
	}
	public String getRollAngle() {
		return rollAngle;
	}
	public void setRollAngle(String rollAngle) {
		this.rollAngle = rollAngle;
	}
	public String getPitchAngle() {
		return pitchAngle;
	}
	public void setPitchAngle(String pitchAngle) {
		this.pitchAngle = pitchAngle;
	}
	public String getYawAngle() {
		return yawAngle;
	}
	public void setYawAngle(String yawAngle) {
		this.yawAngle = yawAngle;
	}
	public String getAltitudeHeight() {
		return relativeHeight;
	}
	public void setAltitudeHeight(String altitudeHeight) {
		this.relativeHeight = altitudeHeight;
	}
	public String getVerticalSpeed() {
		return verticalSpeed;
	}
	public void setVerticalSpeed(String verticalSpeed) {
		this.verticalSpeed = verticalSpeed;
	}
	public String getBatteryRemaining() {
		return batteryRemaining;
	}
	public void setBatteryRemaining(String batteryRemaining) {
		this.batteryRemaining = batteryRemaining;
	}
	@Override
	public String toString() {
		return "Params [deviceID=" + deviceID + ", timeZone=" + timeZone + ", time=" + time + ", divisionEW="
				+ divisionEW + ", longitude=" + longitude + ", divisionNS=" + divisionNS + ", latitude=" + latitude
				+ ", direction=" + direction + ", speed=" + speed + ", satellites=" + satellites + ", precision="
				+ precision + ", height=" + height + ", retransFlag=" + retransFlag + ", needsResponse="
				+ needsResponse + ", remark=" + remark + ", type=" + type + ", rollAngle=" + rollAngle
				+ ", pitchAngle=" + pitchAngle + ", yawAngle=" + yawAngle + ", relativeHeight=" + relativeHeight
				+ ", verticalSpeed=" + verticalSpeed + ", batteryRemaining=" + batteryRemaining + "]";
	}
	public String getRelativeHeight() {
		return relativeHeight;
	}
	public void setRelativeHeight(String relativeHeight) {
		this.relativeHeight = relativeHeight;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getVideoChannel() {
		return videoChannel;
	}
	public void setVideoChannel(String videoChannel) {
		this.videoChannel = videoChannel;
	}
	public String getAlarmInChannel() {
		return alarmInChannel;
	}
	public void setAlarmInChannel(String alarmInChannel) {
		this.alarmInChannel = alarmInChannel;
	}
	public String getDiskNumber() {
		return diskNumber;
	}
	public void setDiskNumber(String diskNumber) {
		this.diskNumber = diskNumber;
	}
	public String getDeviceName() {
		return devicename;
	}
	public void setDeviceName(String deviceName) {
		this.devicename = deviceID;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = type;
	}

	/**
	 * @return the handleFlag
	 */
	public String getHandleFlag() {
		return handleFlag;
	}

	/**
	 * @param handleFlag the handleFlag to set
	 */
	public void setHandleFlag(String handleFlag) {
		this.handleFlag = handleFlag;
	}
}
