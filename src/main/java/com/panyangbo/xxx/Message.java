package com.panyangbo.xxx;


/**
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/20 15:54
 * @Description Message
 */
public class Message {

	
	private String version;
	private String sequence;
	private String commandType;
	private String command;
	private String status;
	private String description;
	private Params params;
	
	public Params getParams() {
		return params;
	}

	public void setParams(Params params) {
		this.params = params;
	}

	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getSequence() {
		return sequence;
	}


	public void setSequence(String sequence) {
		this.sequence = sequence;
	}


	public String getCommandType() {
		return commandType;
	}


	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}


	public String getCommand() {
		return command;
	}


	public void setCommand(String command) {
		this.command = command;
	}

	@Override
	public String toString() {
		return "Message [version=" + version + ", sequence=" + sequence + ", commandType=" + commandType
				+ ", command=" + command + ", status=" + status + ", description=" + description + ", params=" + params
				+ "]";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
