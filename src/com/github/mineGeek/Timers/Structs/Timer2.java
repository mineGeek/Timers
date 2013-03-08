package com.github.mineGeek.Timers.Structs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.github.mineGeek.Timers.Main.TimersRegistry;

public class Timer2 {

	public List<Timer2> subTimers 	= new ArrayList<Timer2>();
	public ITimer		handler 	= null;
	public Integer 		secondsDelay= null;
	public Integer 		secondsInto = null;
	public Integer 		secondsLong = null;
	public Integer		secondsRepeat = null;
	public Long 		lastStarted = null;
	
	public Integer		timerStartTaskId 	= null;
	public Integer		timerEndTaskId 		= null;
	public ITimer		startHandler= null;
	public ITimer		endHandler 	= null;
	
	public Timer2( ITimer handler ) { this.handler = handler; }
	
	public void addSubTimer( Timer2 timer ) {
		subTimers.add( timer );
	}
		
	public void start() {
	
		Long startTime = System.currentTimeMillis();
		
		Integer toStart = secondsDelay == null ? 0 : secondsDelay;		
		lastStarted 	= startTime;
		
		if ( secondsInto != null ) {
			
			lastStarted -= secondsInto;
			toStart 	-= secondsInto;
		}		
		
		Object[] args = { 	lastStarted == null ? 0 : lastStarted, 
				startTime - ( lastStarted == null ? startTime : lastStarted ), 
				startTime + ( this.secondsLong != null ? this.secondsLong * 1000 : 0 ) };		
		
		
		handler.preStart( args );
		
		if ( startHandler != null ) {
			if ( secondsRepeat == null && toStart > 0 ) {			
				timerStartTaskId = schedule( (Runnable)startHandler, toStart );
			} else if ( secondsRepeat != null ) {
				timerStartTaskId = scheduleTimer( (Runnable)startHandler, toStart, secondsRepeat );
			}
		}
		
		if ( endHandler != null && secondsLong != null ) timerEndTaskId = schedule( (Runnable)endHandler, secondsLong );
		
		handler.start(args);
		
		
		for ( Timer2 x : subTimers ) {
			
			x.start();

		}
		
		handler.postStart(args);

		
	}
	
	public void stop() {
		
		Long stopTime = System.currentTimeMillis();
		
		Object[] args = { 	lastStarted == null ? 0 : lastStarted, 
				stopTime - ( lastStarted == null ? stopTime : lastStarted ), 
				stopTime + ( this.secondsLong * 1000 ) };		
		
		handler.preEnd(args);
		
		for ( Timer2 x : subTimers ) {
			x.stop();
		}
		
		handler.end(args);
		if ( this.timerStartTaskId != null) Bukkit.getScheduler().cancelTask( this.timerStartTaskId );
		if ( this.timerEndTaskId != null ) Bukkit.getScheduler().cancelTask( this.timerEndTaskId );
		handler.postEnd(args);
		
	}
	
	
	private Integer scheduleTimer( Runnable runnable, Integer startSeconds, Integer intervalSeconds ) {
		BukkitTask task = Bukkit.getScheduler().runTaskTimer( TimersRegistry.plugin, runnable, startSeconds * 20 , intervalSeconds * 20 );
		return task.getTaskId();
	}
	
	private Integer schedule( Runnable runnable, Integer seconds) {
		BukkitTask task = Bukkit.getScheduler().runTaskLater( TimersRegistry.plugin, runnable, seconds * 20 );
		return task.getTaskId();
	}
	
}
