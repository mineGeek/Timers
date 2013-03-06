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
	public void setTimerEnd(Long end) { timerEnds = end; }	
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
	
	


	
	public void ding( Long timeSinceStart, Long timeElapsed, Long timeToEnd ) {}
	public void start( Long timeToEnd ) {}
	public void end( Long timeElapsed ) {}
	public void reset( Long timeSinceStart, Long timeElapsed, Long timeToEnd ) {}
	public void paused( Long timeSinceStart, Long timeElapsed, Long timeToEnd ) {}
	public void resume( Long timeSinceStart, Long timeElapsed, Long timeToEnd ) {}
	
	
}
