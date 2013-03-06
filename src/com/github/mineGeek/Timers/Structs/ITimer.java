package com.github.mineGeek.Timers.Structs;

public interface ITimer {

	public Integer getStart();
	public Integer getInterval();
	public Integer getEnd();
	
	public void setTimerStart( Long start );
	public void setTimerEnd( Long end );
	
}
