package com.thrblock.poolable;

/** 
* 类:Poolable<br />
* 叙述:可池化对象接口<br />
* 注释日期:2015年4月10日<br />
* @author 三相板砖 thrblock badteeth@qq.com
*/
public interface Poolable {
	/** 
	* 叙述:可用标识<br />
	* @return boolean 代表该对象是否处于可用状态的布尔值
	*/
	public boolean isAvailable();
	/** 
	* 叙述:立即中断对象的启用状态<br />
	*/
	public void interrupt();
}
