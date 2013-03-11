package com.github.mineGeek.Timers.Structs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.github.mineGeek.Timers.Main.TimersRegistry;

public class SubTimer implements ITimerParent {

	public ITimerParent parent = null;
	public Integer 		collectionLength = null;
	
	List<SubTimer>		subTimers		= new ArrayList<SubTimer>();
	
	public Integer 		secondStart		= null;
	public Integer 		secondInterval	= null;
	public Integer 		secondStop		= null;
	
	public TimerEvent	handlerStart	= null;
	public TimerEvent	handlerInterval	= null;
	public TimerEvent	handlerComplete	= null;
	
	public Integer		taskIdStart		= null;
	public Integer		taskIdEnd		= null;
	
	private Integer 	nextStart		= null;
	private Integer 	nextInterval	= null;
	private Integer 	nextStop		= null;
	
	public SubTimer() {}
	public SubTimer( ITimerParent parent ) { this.parent = parent; }
	
	public void start() {
				
		
		if ( handlerInterval != null ) {
			
			handlerInterval.handlerStart = handlerStart;
			taskIdStart = scheduleTimer( (Runnable) handlerInterval, nextStart, nextInterval );
			
		} else if ( handlerStart != null ) {
				 
			taskIdStart = schedule( (Runnable) handlerStart, nextStart );
				
		}
		
		if ( handlerComplete != null ) taskIdEnd = schedule( (Runnable) handlerComplete, nextStop );
		
		for ( SubTimer x : subTimers ) x.start();
		
	}
	
	public void stop() {
		
		if ( taskIdStart != null) Bukkit.getScheduler().cancelTask( taskIdStart );
		if ( taskIdEnd != null ) Bukkit.getScheduler().cancelTask( taskIdEnd );
		
		taskIdStart = null;
		taskIdEnd = null;
		
		for ( SubTimer x : subTimers ) x.stop();
		
	}
	
	public void ini() {
		
		stop();
		setHandlers();
		for ( SubTimer t : subTimers ) t.ini();
		
	}
	
	public void addSubTimer( SubTimer timer ) {
		timer.parent = this;
		subTimers.add( timer );
	}
	
	@Override
	public Integer getLength() {
		return parent.getLength();
	}
	
	private Integer getSecondsStart() {
		
		Integer result = getOffset();		
		if ( result != null ) return result;
		
 		if ( secondStart != null ) {
			return ( getOffset() - secondStart );
		}
		
		return null;
		
	}
	
	private Integer getSecondsStop() {
		
		Integer offset = getOffset();
		if ( offset == null ) offset = 0;
		
		if ( secondStop != null ) return secondStop - offset ;
		return null;
	}
	
	private Integer getSecondsInterval() {
		if ( secondInterval != null ) return secondInterval;
		return null;
	}
	
	private void setHandlers(  ) {
		
		nextStart 		= getSecondsStart();
		nextInterval 	= getSecondsInterval();
		nextStop		= getSecondsStop();		
		
		setHandler( handlerStart, nextStart, nextInterval, nextStop );
		setHandler( handlerInterval, nextStart, nextInterval, nextStop );
		setHandler( handlerComplete, nextStart, nextInterval, nextStop );
		
	}
	
	private void setHandler( TimerEvent handler, Integer start, Integer interval, Integer stop ) {
		
		if ( handler != null ) handler.setVars(start, interval, stop, getLength() );
		
	}

	public void close() {
		
		stop();
		if ( handlerStart != null ) handlerStart.close();
		if ( handlerComplete != null ) handlerComplete.close();
		if ( handlerInterval != null ) handlerInterval.close();
		
		handlerStart = null;
		handlerComplete = null;
		handlerInterval = null;
		
		for ( SubTimer t : subTimers ) t.close();
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
