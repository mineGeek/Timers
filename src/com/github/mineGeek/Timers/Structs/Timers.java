package com.github.mineGeek.Timers.Structs;

import com.github.mineGeek.Timers.Main.TimersRegistry;

public class Timers implements ITimerOwner {

	public 	String tag = null;
	public 	Integer length;
	public	Integer	offset;
	public	Integer lastOffset = null;
	public  Long lastStart;
	public	Long lastStop;
	public	Timer clock = new Timer( this );
	
	public Timers( String tag ) {
		this.tag = tag;
		TimersRegistry.addTimers( this );
	}
	
	@Override
	public Integer getLength() {
		return length == null ? 0 : length;
	}
	
	public void ini() 	{
		if ( offset != null && lastOffset == null ) lastOffset = offset;
		clock.secondStop = length;
		clock.ini(); 
	}
	
	public void addSubTimer( Timer timer ) {
		clock.addSubTimer( timer );
	}
	
	public void stop() 	{
		lastStop = System.currentTimeMillis();		
		clock.stop();
		if ( lastOffset != null ) resetOffset();
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
		TimersRegistry.timers.remove( this );
		clock.close();
		clock = null;
	}

	public Integer getCurrentOffset() {
		Integer i = (int) (lastStop - lastStart) /1000;
		if ( length > i ) return length - i;
		return null;
	}
	
	@Override
	public Integer getOffset() {
		return this.offset == null ? 0 : this.offset;
	}
	
	public void resetOffset() {
		
		if ( lastOffset != null ) {
		
			this.offset = null;
			clock.ini();
			this.lastOffset = null;
		}
		
	}
	
}
