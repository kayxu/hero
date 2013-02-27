package com.joymeng.game.domain.fight;
import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;
public class Battlefield extends ClientModuleBase {

	private int id;
	
	private String active;
	
	private String text;
	
	private String startTime;
	
	private String endTime;
	
	private String button;
	
	private int ui;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public int getUi() {
		return ui;
	}

	public void setUi(int ui) {
		this.ui = ui;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@Override
	public byte getModuleType() {
		// TODO Auto-generated method stub
		return NTC_BATTLE_FIELD_INFO;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(id);
		out.putPrefixedString(active,JoyBuffer.STRING_TYPE_SHORT);
		out.putPrefixedString(text,JoyBuffer.STRING_TYPE_SHORT);
		out.putInt(ui);

	}

	@Override
	public void deserialize(JoyBuffer in) {
		this.id = in.getInt();
		this.active = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.text = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.ui = in.getInt();

	}
	
	
	
}
