package com.panyangbo.xxx;

/**
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/20 15:54
 * @Description Params4
 */
public class Params4 {

	private String DepartmentId;
	private String DepartmentName;
	private String Phone;
	private String MacAddress;
	private String MobileIp;
	private String Name;
	private String UserId;
	private String Longitude;
	private String Latitude;
	private String Remark;
	private String type;
	private String RealName;
	private String ReportTime;
	
	public String getDepartmentId() {
		return DepartmentId;
	}
	public void setDepartmentId(String departmentId) {
		DepartmentId = departmentId;
	}
	public String getDepartmentName() {
		return DepartmentName;
	}
	public void setDepartmentName(String departmentName) {
		DepartmentName = departmentName;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getMacAddress() {
		return MacAddress;
	}
	public void setMacAddress(String macAddress) {
		MacAddress = macAddress;
	}
	public String getMobileIp() {
		return MobileIp;
	}
	public void setMobileIp(String mobileIp) {
		MobileIp = mobileIp;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the reportTime
	 */
	public String getReportTime() {
		return ReportTime;
	}
	/**
	 * @param reportTime the reportTime to set
	 */
	public void setReportTime(String reportTime) {
		ReportTime = reportTime;
	}
	/**
	 * @return the realName
	 */
	public String getRealName() {
		return RealName;
	}
	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		RealName = realName;
	}
	@Override
	public String toString() {
		return "Params4 [DepartmentId=" + DepartmentId + ", DepartmentName=" + DepartmentName + ", Phone=" + Phone
				+ ", MacAddress=" + MacAddress + ", MobileIp=" + MobileIp + ", Name=" + Name + ", UserId=" + UserId
				+ ", Longitude=" + Longitude + ", Latitude=" + Latitude + ", Remark=" + Remark + ", type=" + type
				+ ", RealName=" + RealName + ", ReportTime=" + ReportTime + "]";
	}
	
	
}
