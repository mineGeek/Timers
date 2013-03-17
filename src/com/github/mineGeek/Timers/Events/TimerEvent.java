package com.github.mineGeek.Timers.Events;

import com.github.mineGeek.Timers.Main.TimersRegistry;
import com.github.mineGeek.Timers.Structs.Timer;

public class TimerEvent implements Runnable {

	public Timer timer;	
	public TimerEvent handlerStart = null;
	public TimerEvent handlerComplete = null;
	
	public Integer secondStart = null;
	public Integer secondInterval = null;
	public Integer secondComplete = null;
	public Integer secondTotal = null;
	
	private Integer offset = null;
	
	private int realStart = (int)(System.currentTimeMillis() / 1000);
	
	public Object[] getTimeArgs() {
		
		int now  = (int)(System.currentTimeMillis()/1000)-realStart;
		
		Object[] args = { 	
							getTimeElapsed( now ),
							getTimeRemaining( now ),
							getTimeToComplete( now ),
				 		};
		
		return args;
	}
	
	public void reset( int now ) {
		realStart = now;
		offset = timer != null ? timer.getOffset() : null;
	}
	public void reset() {
		reset((int)(System.currentTimeMillis() / 1000 ));
	}
	
	public void setVars( Integer start, Integer interval, Integer complete, Integer total, TimerEvent startHandler, TimerEvent completeHandler ) {
		
		this.handlerStart = startHandler;
		this.handlerComplete = completeHandler;
		setVars( start, interval, complete, total );
		
	}
	
	public void setVars( Integer start, Integer interval, Integer complete, Integer total ) {
		secondStart = start;
		secondInterval = interval;
		secondComplete = complete;
		secondTotal = total;
		reset((int)(System.currentTimeMillis()/1000));
	}
	
	
	private String getTimeRemaining( int now ) {
		int offset = this.offset == null ? 0 : this.offset;
		if ( secondTotal != null) return TimersRegistry.getTimeStampAsString(  ( secondTotal-offset) - now );
		return "";
	}
	
	private String getTimeElapsed( int now ) {
		int offset = this.offset == null ? 0 : this.offset;
		return TimersRegistry.getTimeStampAsString( now + offset );
	}	
	
	private String getTimeToComplete( int now ) {
		if ( secondComplete != null ) {
			return TimersRegistry.getTimeStampAsString( secondComplete - now ); 
		} 
		return "";
	}
	
	public void close() {
		if ( handlerStart != null ) handlerStart.close();
		if ( handlerComplete != null ) handlerComplete.close();
		handlerStart = null;
		handlerComplete = null;
		timer = null;
	}
	
	@Override
	public void run() {
		

		
		
	}

}
