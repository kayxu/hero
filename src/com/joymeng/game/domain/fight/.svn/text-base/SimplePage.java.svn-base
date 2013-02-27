package com.joymeng.game.domain.fight;

import java.sql.Timestamp;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

public class SimplePage extends ClientModuleBase{
	
	private int currentPage;
	
	private int totalPages;
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	@Override
	public byte getModuleType() {
		// TODO Auto-generated method stub
		return NTC_SIMPLE_PAGE;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(currentPage);
		out.putInt(totalPages);

	}

	@Override
	public void deserialize(JoyBuffer in) {
		this.currentPage = in.getInt();
		this.totalPages = in.getInt();

	}

}
