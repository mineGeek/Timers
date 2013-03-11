package com.github.mineGeek.Timers.Structs;

public interface ITimer {

	public Integer getStart();
	public Integer getInterval();
	public Integer getEnd();
	public Integer getTaskId();
	public Integer getEndTaskId();
	
	public Long getLastStartTime();
	
	public boolean isRunning();
	
	public void setTaskId( Integer taskId );
	public void setEndTaskId( Integer taskId );
	public void setTimerStart( Integer start );
	public void setTimerComplete( Integer complete );
	public void setTimerInterval( Integer interval );
	public void setTotalSeconds( Integer totalSeconds );
	
	public void ding( Object args[] );
	public void preStart( Object args[] );
	public void start( Object args[] );
	public void postStart( Object args[] );
	public void preStop( Object[] args );
	public void stop( Object args[] );
	public void postStop( Object[] args );
	public void reset( Object args[] );
	public void paused( Object args[] );
	public void resume( Object args[] );
	
	public void close();
	
	
}
