package com.github.mineGeek.Timers.Events;


public class TimerEventStart extends TimerEvent {

	public ITimerEventHandler parent;
	public TimerEventStart( ITimerEventHandler parent ) { this.parent = parent; }
	
	@Override
	public void run() {
		if ( parent != null) parent.start( this.getTimeArgs() );
	}
	
}
