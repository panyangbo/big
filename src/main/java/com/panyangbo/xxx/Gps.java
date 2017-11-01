package com.panyangbo.xxx;

/**
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/20 14:35
 * @Description
 */
public class Gps {

	private String Version;
	private String Protocol;
	private Params4 Params;
	
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public String getProtocol() {
		return Protocol;
	}
	public void setProtocol(String protocol) {
		Protocol = protocol;
	}
	public Params4 getParams() {
		return Params;
	}
	public void setParams(Params4 params) {
		Params = params;
	}
	@Override
	public String toString() {
		return "Gps [Version=" + Version + ", Protocol=" + Protocol + ", Params=" + Params + "]";
	}
	
}
