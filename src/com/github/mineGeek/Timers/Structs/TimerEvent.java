package com.github.mineGeek.Timers.Structs;

public class TimerEvent implements Runnable, ITimer {

	public enum TimerEventType {BEFORESTART, START, AFTERSTART, BEFORESTOP, STOP, AFTERSTOP, BEFORERESUME, RESUME, AFTERRESUMT, BEFORERESTART, RESTART, AFTERRESTART}
	public TimerEventType type;
	
	@Override
	public void run() {
		
		
	}

	@Override
	public Integer getStart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getInterval() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getEnd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getTaskId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getEndTaskId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getLastStartTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEndTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTimerStart(Long start) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTimerEnd(Long end) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ding(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preStart(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postStart(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preEnd(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postEnd(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paused(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
	
}
