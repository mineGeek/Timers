package com.github.mineGeek.Timers.Structs;

import org.bukkit.Bukkit;

abstract class TimerBase implements ITimer, Runnable {

	public Integer start;
	public Integer interval;
	public Integer end;
	public Long timerStarted = null;
	public Long timerEnds	= null;
	
	public Integer taskId = null;
	public Integer endTaskId = null;
	
	
	public Integer getStart() {	return start; }

	public Integer getInterval() { return interval; }

	public Integer getEnd() { return end; }
	
	public void setTimerStart(Long start) { timerStarted = start; }	
	public void setTimerComplete(Long end) { timerEnds = end; }	
	public Long getLastStartTime() { return timerStarted; }
	
	public void setTaskId( Integer taskId ) { this.taskId = taskId; }
	public Integer getTaskId() { return this.taskId; }
	public void setEndTaskId( Integer taskId ) { this.endTaskId = taskId; }
	public Integer getEndTaskId() { return this.endTaskId; }
	

	
	public void run() {}
	

	
	public boolean isRunning() {
		
		if ( taskId != null && Bukkit.getScheduler().isCurrentlyRunning( taskId ) ) return true;
		if ( endTaskId != null && Bukkit.getScheduler().isCurrentlyRunning( endTaskId ) ) return true;
		
		return false;
	}
	
	


	
	public void ding( Object[] args ) {}
	public void preStart( Object[] args ) {}
	public void start( Object[] args ) {}
	public void postStart( Object[] args ) {}
	public void preStop( Object[] args ) {}
	public void stop( Object[] args) {}
	public void postStop( Object[] args ) {}
	public void reset( Object[] args ) {}
	public void paused( Object[] args ) {}
	public void resume( Object[] args ) {}
	public void close() {}
	
}
