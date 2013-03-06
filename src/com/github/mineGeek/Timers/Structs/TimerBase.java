package com.github.mineGeek.Timers.Structs;

abstract class TimerBase implements ITimer, Runnable {

	public Integer start;
	public Integer interval;
	public Integer end;
	public Long timerStarted = null;
	public Long timerEnds	= null;
	
	public Integer getStart() {
		return start;
	}

	public Integer getInterval() {
		return interval;
	}

	public Integer getEnd() {
		return end;
	}
	
	public void run() {}
	
	public void setTimerStart(Long start) {
		timerStarted = start;
		
	}

	public void setTimerEnd(Long end) {
		timerEnds = end;
		
	}	
	
	
}
