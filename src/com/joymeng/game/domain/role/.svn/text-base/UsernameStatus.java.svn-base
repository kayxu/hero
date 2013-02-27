package com.joymeng.game.domain.role;

public class UsernameStatus {
	
	/** Cache the hash code for the UsernameStatus */
    private int hash; // Default to 0

	private int id;
	
	/**
	 * 全名
	 */
	private String fullName;
	
	/**
	 * 0.男
	 * 1.女
	 */
	private int sex;
	
	/**
	 * 0.不可用
	 * 1.可用
	 */
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		char[] value = fullName.toCharArray();
		 int h = hash;
	        if (h == 0 && value.length > 0) {
	            char val[] = value;

	            for (int i = 0; i < value.length; i++) {
	                h = 31 * h + val[i];
	            }
	            hash = h;
	        }
	        return h;
	}

	@Override
	public boolean equals(Object object) {
		if(this == object){
			return true;
		}
		if(object instanceof UsernameStatus){
			UsernameStatus anOtherUsernameStatus = (UsernameStatus) object;
			if(fullName.equals(anOtherUsernameStatus.getFullName())){
				return true;
			}
		}
		return false;
	}
	
	
	
	
}
