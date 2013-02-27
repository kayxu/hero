package com.joymeng.game.domain.world;

public class TipMessage implements Comparable<TipMessage>{
	private String message;// 消息内容
	private int type;// 类型
	private int p1;// 参数1
	private int p2;// 参数2
	private byte result;// 结果 0失败，1成功
	private int priotity;//优先级

	public TipMessage(String message, int type,byte result){
		this.message=message;
		this.type=type;
		this.result=result;
	}
	public  TipMessage(String message, int type,byte result,int p1){
		this.message=message;
		this.type=type;
		this.result=result;
		this.p1=p1;
	}
	public  TipMessage(String message, int type,byte result,int p1,int p2){
		this.message=message;
		this.type=type;
		this.result=result;
		this.p1=p1;
		this.p2=p2;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public byte getResult() {
		return result;
	}

	public void setResult(byte result) {
		this.result = result;
	}
	
	/**
	 * 是否成功
	 * @return
	 */
	public boolean getisSucc(){
		return result == 1;
	}
	public int getP1() {
		return p1;
	}
	public void setP1(int p1) {
		this.p1 = p1;
	}
	public int getP2() {
		return p2;
	}
	public void setP2(int p2) {
		this.p2 = p2;
	}
	public int getPriotity() {
		return priotity;
	}
	public void setPriotity(int priotity) {
		this.priotity = priotity;
	}
	@Override
	public int compareTo(TipMessage o) {
		 if(this.getPriotity() > o.getPriotity()){
             return 1;
		 }else if(this.getPriotity() == o.getPriotity()){
                 return 0;
		 }else{
         return -1;
		 }
	}


}
