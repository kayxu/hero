package com.joymeng.core.log;

/**
 * 
 */


/**
 * log日志记录事件数据缓冲区
 * 
 * @author gejing
 */
public class LogBuffer {

	public static final char DATA_SEPERATE_CHAR = '|';
	public static final char ITEMS_PREFIX_CHAR = '[';
	public static final char SPECIAL_CHAR = ':';
	public static final char ITEM_NUM_CHAR = '*';
	public static final char SEPERATE_CHAR = ',';// 多个同类型数据间隔，比如多个物品，多个怪物ID
	public static final char ITEMS_SUFIX_CHAR = ']';

	StringBuffer buff;

	public LogBuffer() {
		buff = new StringBuffer(128);
	}

	public LogBuffer add(Object obj) {
		buff.append(obj.toString()).append(DATA_SEPERATE_CHAR);
		return this;
	}

	public LogBuffer add(double obj) {
		buff.append(obj).append(DATA_SEPERATE_CHAR);
		return this;
	}

	public LogBuffer add(long obj) {
		buff.append(obj).append(DATA_SEPERATE_CHAR);
		return this;
	}

	public LogBuffer add(float obj) {
		buff.append(obj).append(DATA_SEPERATE_CHAR);
		return this;
	}

	public LogBuffer add(int obj) {
		buff.append(obj).append(DATA_SEPERATE_CHAR);
		return this;
	}

	public LogBuffer add(char obj) {
		buff.append(obj).append(DATA_SEPERATE_CHAR);
		return this;
	}

	public LogBuffer add(short obj) {
		buff.append(obj).append(DATA_SEPERATE_CHAR);
		return this;
	}

	public LogBuffer add(byte obj) {
		buff.append(obj).append(DATA_SEPERATE_CHAR);
		return this;
	}

	public LogBuffer add(int[] arr) {
		for (int v : arr) {
			buff.append(v).append(DATA_SEPERATE_CHAR);
		}
		return this;
	}

	/**
	 * 记录多个物品
	 * 
	 * @param item
	 * @param num
	 * @return
	 */
	// public LogBuffer add(List<StackItem> items)
	// {
	// if (items == null)
	// {
	// return this;
	// }
	// for (StackItem si : items)
	// {
	// add(si.getItem(), si.getNum());
	// }
	// return this;
	// }

	/**
	 * 记录中只有一个可以用这个方法。 多个物品则调用 {@link #add(List)}
	 * 
	 * @param item
	 * @param num
	 * @return
	 */
	// public LogBuffer add(StackItem si)
	// {
	// if (si == null)
	// {
	// return this;
	// }
	// return add(si.getItem(), si.getNum());
	// }
	/**
	 * 记录中只有一个可以用这个方法。 多个物品则调用 {@link #add(List)}
	 * 
	 * @param item
	 * @param num
	 * @return
	 */
	// public LogBuffer add(Item item, int num)
	// {
	// if (item == null)
	// {
	// return this;
	// }
	// int level = 0;
	// int color = 0;
	// String daoStr = item.toDAOString();
	//
	// if (!item.isProp())
	// {
	// Equipment eq = (Equipment) item;
	// if (eq.isTalisman())
	// {
	// level = ((Talisman) eq).getTalismanLvl();
	// }
	// else
	// {
	// level = eq.getEnchantLevel();
	// color = eq.getColor();
	// }
	// }
	// return
	// add(num).add(item.getType()).add(item.getPrototypeId()).add(level).add(color).add(daoStr);
	// }

	/**
	 * 增加是/否得到（邮寄）标记
	 * 
	 * @param item
	 * @param num
	 * @param isMailed
	 * @return
	 */
	// public LogBuffer add(Item item, int num, boolean flag)
	// {
	// return add(item, num);
	// }
	//
	// public LogBuffer add(Item item)
	// {
	// return add(item, 1);
	// }

	// public LogBuffer add(EmailPrototype email){
	//
	// //<<日志
	// add(email.getId());
	// add(email.getSenderId());
	// add(email.getReceiverId());
	// add(email.getType().ordinal());
	// add(email.getGiftMoney());
	// if (email.hasAppendidx()) {
	// List<StackItem> items = new ArrayList<StackItem>();
	// for (Appendidx app : email.getAppendidxs()) {
	// if (app != null) {//存在则添加
	// StackItem drop = new StackItem();
	// drop.setItem(app.getItem());
	// drop.setNum(app.getItemNum());
	// items.add(drop);
	// }
	// }
	// add(items);
	// } else {
	// add(-1);
	// }
	//
	// return this;
	// }
	/**
	 * bool型，写进日志对应1:0
	 * 
	 * @param obj
	 * @return
	 */
	public LogBuffer add(boolean obj) {
		buff.append(obj ? 1 : 0).append(DATA_SEPERATE_CHAR);
		return this;
	}

	public LogBuffer add(String obj) {
		buff.append(obj).append(DATA_SEPERATE_CHAR);
		return this;
	}

	public LogBuffer add(StringBuffer obj) {
		buff.append(obj).append(DATA_SEPERATE_CHAR);
		return this;
	}

	public StringBuffer getStringBuffer() {
		return buff;
	}

	@Override
	public String toString() {
		return buff.toString();
	}

	// /**
	// * @return
	// */
	// public LogEvent getEvent()
	// {
	// return event;
	// }
	//
	// public void setEvent(LogEvent event)
	// {
	// this.event = event;
	// }
}
