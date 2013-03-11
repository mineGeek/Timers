package com.github.mineGeek.Timers.Structs;

import com.github.mineGeek.Timers.Main.TimersRegistry;

public class TimerEvent implements Runnable {

	public SubTimer timer;	
	public TimerEvent handlerStart = null;
	public TimerEvent handlerComplete = null;
	
	public Integer secondStart = null;
	public Integer secondInterval = null;
	public Integer secondComplete = null;
	public Integer secondTotal = null;
	
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
		realStart = (int)(System.currentTimeMillis()/1000);
	}
	
	
	private String getTimeRemaining( int now ) {
		if ( secondTotal != null) return TimersRegistry.getTimeStampAsString(  secondTotal - now );
		return "";
	}
	
	private String getTimeElapsed( int now ) {
		return TimersRegistry.getTimeStampAsString( now );
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
	}
	
	@Override
	public void run() {
		

		
		
	}

}
