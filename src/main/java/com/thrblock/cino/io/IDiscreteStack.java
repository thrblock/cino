package com.thrblock.cino.io;


/** 
* 类: IDiscreteStack <br />
* 叙述:二值离散式输入设备 如键盘、触屏固定位置的映射等<br />
* @author 三向板砖 thrblock badteeth@qq.com
* 注释日期 2016年3月20日 下午8:23:53 
*/
public interface IDiscreteStack {
	public void pushDiscreteListener(IDiscreteListener discreteListener);
	public IDiscreteListener popDiscreteListener();
	/**
	 * @param code 各个离散输入的唯一标识
	 * @return 代表对于位的二值是否处于激活状态
	 */
	public boolean isDiscreteActivate(int code);
}
