package com.github.mineGeek.Timers.Events;


public class TimerEventComplete extends TimerEvent {
	public ITimerEventHandler parent;
	
	public TimerEventComplete( ITimerEventHandler parent ) { this.parent = parent; }
	
	@Override
	public void run() {
		if ( parent!= null) parent.complete( this.getTimeArgs() );
	}
}
