package com.github.mineGeek.Timers.Structs;

public class TimerMessage extends TimerBase {

	public String startMessage = null;
	public String endMessage = null;
	public String stopMessage = null;
	public String resumeMessage = null;
	public String incrimentMessage = null;
	public String message = null;
	public String resetMessage = null;
	public String pauseMessage = null;
	
	@Override
	public void run() {
		
		//ding
		Object[] args = { this.getLastStartTime(), System.currentTimeMillis() - this.getLastStartTime(), ( this.end != null ? this.getLastStartTime() +(this.end * 1000 ) : 0)};
		ding( args );
		
	}
	
	@Override	
	public void ding( Object args[] ) {
		broadCast( incrimentMessage, args );
	}
	
	@Override
	public void start( Object args[] ) {
		broadCast( startMessage, args );
	}
	
	@Override
	public void end( Object args[] ) {
		broadCast( endMessage, args );
	}
	
	@Override
	public void reset( Object args[] ) {
		broadCast( resetMessage, args );
	}
	
	@Override
	public void paused( Object args[] ) {
		broadCast( pauseMessage, args );
	}
	
	@Override
	public void resume( Object args[] ) {
		broadCast( resumeMessage, args );
	}

	public void broadCast( String message, Object[] ars ) {
		
		if ( message == null ) return;
		
	}

	
}
