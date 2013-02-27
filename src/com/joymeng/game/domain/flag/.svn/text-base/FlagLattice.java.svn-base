package com.joymeng.game.domain.flag;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.services.core.buffer.JoyBuffer;

/**
 * 格子
 * @author Administrator
 *
 */
public class FlagLattice extends  ClientModuleBase{
	FlagManager fgr =FlagManager.getInstance();
	private byte point;//坐标
	private int userid;//用户
	private int heroid;//武将
	private String heroinfo ="";//武将信息
	private String soinfo ="";//士兵
	private int buff;//buff
	private byte flagId;//是否有军旗
	private boolean isSee;//是否可见
	private int offTime;//移动时间

	
	public boolean isSee() {
		return isSee;
	}
	public void setSee(boolean isSee) {
		this.isSee = isSee;
	}
	public int getOffTime() {
		return offTime;
	}
	public void setOffTime(int offTime) {
		this.offTime = offTime;
	}
	public byte getPoint() {
		return point;
	}
	public void setPoint(byte point) {
		this.point = point;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getHeroid() {
		return heroid;
	}
	public void setHeroid(int heroid) {
		this.heroid = heroid;
	}
	public String getHeroinfo() {
		return heroinfo;
	}
	public void setHeroinfo(String heroinfo) {
		this.heroinfo = heroinfo;
	}
	public String getSoinfo() {
		return soinfo;
	}
	public void setSoinfo(String soinfo) {
		this.soinfo = soinfo;
	}
	public int getBuff() {
		return buff;
	}
	public void setBuff(int buff) {
		this.buff = buff;
	}
	
	public byte getFlagId() {
		return flagId;
	}
	public void setFlagId(byte flagId) {
		this.flagId = flagId;
	}
	@Override
	public byte getModuleType() {
		return NTC_FLAG;
	}
	@Override
	public void _serialize(JoyBuffer out) {
		out.put(point);
		out.putInt(userid);
		out.putInt(heroid);
		out.putPrefixedString(heroinfo, (byte)2);
		out.putPrefixedString(soinfo, (byte)2);
		out.putInt(buff);
		out.put(flagId);
		if(isSee)
			out.put((byte)1);
		else
			out.put((byte)0);
	}
	/**
	 * 拷贝数据copy
	 * @param copy
	 */
	public void copy(FlagLattice copy,String soinfo,PlayerCharacter[] ps){
		setUserid(copy.getUserid());
		setHeroid(copy.getHeroid());
		setHeroinfo(copy.getHeroinfo());
		if(this.getBuff() <= 0){
			setBuff(copy.getBuff());
		}
		setFlagId(copy.getFlagId());
		copy.clean(ps);
		motifySolider(soinfo, ps);
	}
	/**
	 * 新建FlagLattice
	 * @param userid
	 * @param heroid
	 * @param soinfo
	 * @return
	 */
	public TipUtil creat(int userid,int heroid,String soinfo,PlayerCharacter[] ps){
		TipUtil tip = new TipUtil(ProcotolType.FLAG_RESP);
		//PlayerCharacter player = World.getInstance().getOnlineRole(userid);
		//if(player != null){
			//PlayerHero ph = player.getPlayerHeroManager().getHero(heroid);
			//if(ph != null){
				setUserid(userid);
				setHeroid(heroid);
				//setHeroinfo(GameUtils.heroInfo(ph));
//				setSoinfo(soinfo);
				motifySolider(soinfo, ps);
				tip.setSuccTip("");
//			}else{
//				tip.setFailTip("将领不存在");
//			}
//		}else{
//			tip.setFailTip("用户不存在");
//		}
		fgr.sendOne(this, ps);
		
		return tip;
	}
	
	/**
	 * 修改驻防兵力
	 * @param msg
	 * @param ps
	 */
	public void motifySolider(String msg,PlayerCharacter[] ps){
		//驻防士兵为
		setSoinfo(msg);
		System.out.println("用户："+userid+"地点："+point+"|将领："+getHeroid()+"|士兵修改为："+msg);
		print();
		fgr.sendOne(this, ps);
	}
	
	/**
	 * 移动   就是 格子复制
	 */
	public void clean(PlayerCharacter[] ps){
		setUserid(0);
		setHeroid(0);
		setHeroinfo("");
		setSoinfo("");
		setBuff(0);
		setFlagId((byte)0);
		fgr.sendOne(this, ps);
	}
	/**
	 * 夺旗
	 * @param ps
	 */
	public void captureFlag(PlayerCharacter[] ps,byte homeid){
		setFlagId(homeid);
		fgr.sendOne(this, ps);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String print() {
		return this.getPoint()+"士兵"+this.getSoinfo();
	}

}
