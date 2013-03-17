package com.github.mineGeek.Timers.Structs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.github.mineGeek.Timers.Events.TimerEvents;
import com.github.mineGeek.Timers.Main.TimersRegistry;

public class Timer implements ITimerOwner {

	public ITimerOwner parent = null;
	public Integer 		collectionLength = null;
	
	List<Timer>			subTimers		= new ArrayList<Timer>();
	
	public Integer 		secondStart		= null;
	public Integer 		secondInterval	= null;
	public Integer 		secondStop		= null;
	
	public TimerEvents	eventHandler	= new TimerEvents( this );
	
	public Integer		taskIdStart		= null;
	public Integer		taskIdEnd		= null;
	
	private Integer 	nextStart		= null;
	private Integer 	nextInterval	= null;
	private Integer 	nextStop		= null;
	
	public Timer() {}
	public Timer( ITimerOwner parent ) { this.parent = parent; }
	
	public void start() {
		
		eventHandler.reset();
		
		//Bukkit.broadcastMessage( "Starting : start" + nextStart + " " + " int: " + nextInterval + " stop: " + nextStop);
		if ( eventHandler.interval != null && nextStart != null && nextInterval != null ) {

			eventHandler.interval.handlerStart = eventHandler.start;
			taskIdStart = scheduleTimer( (Runnable) eventHandler.interval, nextStart, nextInterval );
			
		} else if ( eventHandler.start != null && nextStart != null ) {

			taskIdStart = schedule( (Runnable) eventHandler.start, nextStart );
				
		}
		
		if ( eventHandler.complete != null && nextStop != null ) {

			taskIdEnd = schedule( (Runnable) eventHandler.complete, nextStop );
		}
		
		for ( Timer x : subTimers ) x.start();
		
	}
	
	public void stop() {
		
		if ( taskIdStart != null) Bukkit.getScheduler().cancelTask( taskIdStart );
		if ( taskIdEnd != null ) Bukkit.getScheduler().cancelTask( taskIdEnd );
		
		taskIdStart = null;
		taskIdEnd = null;
		
		for ( Timer x : subTimers ) x.stop();
		
	}
	
	public void ini() {
		
		stop();		
		nextStart 		= getSecondsStart();
		nextInterval 	= getSecondsInterval();
		nextStop		= getSecondsStop();
		eventHandler.setVars( nextStart, nextInterval, nextStop, getLength() );
		for ( Timer t : subTimers ) t.ini();
		
	}
	
	public void addSubTimer( Timer timer ) {
		timer.parent = this;
		subTimers.add( timer );
	}
	
	@Override
	public Integer getLength() {
		return parent.getLength();
	}
	
	public Integer getSecondsStart() {
		
		Integer offset = getOffset();		
		if ( offset == null ) offset = 0;
		
 		if ( secondStart != null ) {
 			if ( offset > secondStart ) return null;
			return ( secondStart - offset );
		} else return null;
		
		
	}
	
	public Integer getSecondsStop() {
		
		Integer offset = getOffset();
		if ( offset == null ) offset = 0;
		
		if ( secondStop != null ) {
			return secondStop - offset ;
		} else {
			if ( parent!= null ) return parent.getLength() - offset;
		}
		return null;
	}
	
	public Integer getSecondsInterval() {
		if ( secondInterval != null ) return secondInterval;
		return null;
	}

	public void close() {
		
		stop();
		eventHandler.close();		
		for ( Timer t : subTimers ) t.close();
		subTimers.clear();
		parent = null;
		
	}
	
	@Override
	public Integer getOffset() {
		if ( parent != null ) return parent.getOffset();
		return null;
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
