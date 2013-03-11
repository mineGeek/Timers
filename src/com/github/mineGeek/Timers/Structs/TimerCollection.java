package com.github.mineGeek.Timers.Structs;

public class TimerCollection implements ITimerParent {

	public 	Integer length;
	public	Integer	offset;
	private Long lastStart;
	private Long lastStop;
	public	SubTimer clock = new SubTimer( this );
	
	@Override
	public Integer getLength() {
		return length == null ? 0 : length;
	}
	
	public void ini() 	{ clock.secondStop = length; clock.ini(); }
	
	public void addSubTimer( SubTimer timer ) {
		clock.addSubTimer( timer );
	}
	
	public void stop() 	{
		
		lastStop = System.currentTimeMillis();
		int seconds = length - (int) ((lastStop-lastStart)/1000);
		
		if ( seconds > 0 ) {
			offset = seconds;
		}
		
		clock.stop(); 
	}
	
	public void start() { 
		lastStart = System.currentTimeMillis();
		clock.start();
		offset = null;
	}
	
	
	public void restart() { 
		ini();		
		start();
	}
	
	public void close() {
		stop();
		clock.close();
		clock = null;
	}

	@Override
	public Integer getOffset() {
		return offset == null ? 0 : offset;
	}
	
}
