package com.joymeng.game.domain.role;

/**
 * 姓名组合表对应实体类
 * @author madi
 *
 */
public class Username {

	private int id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 类型0.男，1.女
	 */
	private byte type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if(this == object){
			return true;
		}
		if(object instanceof Username){
			Username anOtherUsername = (Username) object;
			if(nickName != null && !"".equals(nickName)){//如果nickName不为空，则name 和 nickName任何一项相等则相等
				if(name.equals(anOtherUsername.getName()) || nickName.equals(anOtherUsername.getNickName())){
					return true;
				}
			}
			else{//如果nickName为空 则不比较nickName
				if(name.equals(anOtherUsername.getName())){
					return true;
				}
			}
			
		}
		return false;
	}
	
	
	
	
	
}
