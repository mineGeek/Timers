package com.github.mineGeek.Timers.Events;

import com.github.mineGeek.Timers.Structs.Timer;


public class TimerEvents implements ITimerEventHandler {

	public TimerEvent start = null;
	public TimerEvent complete = null;
	public TimerEvent interval = null;
	public Timer timer = null;
	
	
	public boolean offsetVars = false;
	
	
	public TimerEvents( Timer timer ) { this.timer = timer; }
	
	public void reset( int now ) {
		
		if ( start != null ) start.reset( now );
		if ( complete != null ) complete.reset( now );
		if ( interval != null ) interval.reset( now );
		
	}
	public void reset() {
		reset((int)(System.currentTimeMillis() / 1000 ));
	} 
	
	public void setVars( Integer startSecond, Integer intervalSecond, Integer stopSecond, Integer lengthSecond ) {
		
		if ( start != null ) 	{
			start.timer = this.timer;
			start.setVars(startSecond, intervalSecond, stopSecond, lengthSecond );
		}
		if ( complete != null ) {
			complete.timer = this.timer;
			complete.setVars(startSecond, intervalSecond, stopSecond, lengthSecond );
		}
		if ( interval != null ) {
			interval.timer = this.timer;
			interval.setVars(startSecond, intervalSecond, stopSecond, lengthSecond );
		}
		
	}
	
	public void close() {
		if ( start != null ) start.close();
		if ( complete != null) complete.close();
		if ( interval != null ) interval.close();
	}
	
	@Override
	public void start( Object[] args ) {
				
	}

	@Override
	public void complete( Object[] args ) {
		
	}


}
