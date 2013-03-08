package com.github.mineGeek.Timers.Structs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.github.mineGeek.Timers.Main.TimersRegistry;

public class Timer {

	public List<ITimer> timerItems = new ArrayList<ITimer>();
	public ITimer 	timerHandler = null;
	public Integer 	start = null;
	public Integer 	resume = null;
	public Integer 	end = null;
	public Long 	lastStarted = null;
	public Integer	timerTaskId = null;
	public Integer	timerEndTaskId = null;
	
	public Set<Integer> taskIds = new HashSet<Integer>();
	
	public Timer( ITimer handler ) { this.timerHandler = handler; }
	
	public void addTimer( ITimer timer ) {
		
		timerItems.add( timer );
		
	}
	
	
	private Integer scheduleTimer( Runnable runnable, Integer startSeconds, Integer intervalSeconds ) {
		BukkitTask task = Bukkit.getScheduler().runTaskTimer( TimersRegistry.plugin, runnable, startSeconds * 20 , intervalSeconds * 20 );
		return task.getTaskId();
	}
	
	private Integer schedule( Runnable runnable, Integer seconds) {
		BukkitTask task = Bukkit.getScheduler().runTaskLater( TimersRegistry.plugin, runnable, seconds * 20 );
		return task.getTaskId();
	}
	
	public void start() {

		Integer toStart = start == null ? 0 : start;		
		lastStarted = System.currentTimeMillis();
		if ( resume != null ) {
			
			lastStarted -= resume;
			toStart -= resume;
		}
				
		Object[] args = { 	lastStarted == null ? 0 : lastStarted, 
							System.currentTimeMillis() - ( lastStarted == null ? System.currentTimeMillis() : lastStarted ), 
							System.currentTimeMillis() + ( this.end * 1000 ) };
		
		timerHandler.preStart( args );
		
		for ( ITimer x : timerItems ) {
			
			Integer id = null;
			int startTimer = x.getStart() == null ? 0 : x.getStart();
			Object[] subArgs = { args[0], args[1], ( x.getEnd() == null ? 0 : x.getEnd() )};
			x.preStart( subArgs );
			
			if ( x.getInterval() != null ) {
				// Ticking timer
				id = scheduleTimer( (Runnable) x, startTimer, x.getInterval() );
				
			} else if ( startTimer > 0 && x.getEnd() != null ){
				
				//One off set in future or that contains an end?
				id = schedule( (Runnable) x, ( startTimer == 0 ? 1 : startTimer ) );
				
			} else if ( startTimer == 0 ) {
				
				//on off message. Just raise event
				x.start( subArgs);
				x.setTimerStart( lastStarted );
				x.postStart( subArgs );
				resume = null;
				continue;
			}
			
			
			x.start( subArgs );
			x.setTimerStart( lastStarted );
			x.setTaskId( id );
			
			final int endTaskId = new Integer(id);
			
			if ( x.getEnd() != null ) {
				
				Integer toEnd = x.getEnd();
				
				if ( resume != null ) {
					if ( toEnd < resume ) {
						toEnd = resume - toEnd;
						
					} else {
						toEnd -= resume;
					}
				}
				
				schedule( new Runnable() {
					
					public void run() {	
						
						Bukkit.getScheduler().cancelTask( endTaskId ); 
						
					}
					
				}, toEnd * 20 );
				
				x.setEndTaskId( endTaskId );
				x.setTimerEnd( lastStarted + (toEnd * 1000 ) );
				
			}
			
			x.postStart(subArgs);

		}
		

		resume = null;		
		timerHandler.start(args);
		timerHandler.postStart(args);
	}
	
	public void stop( Integer taskId ) {
		
		Object[] args = { lastStarted, System.currentTimeMillis() - lastStarted, 0};
		timerHandler.preEnd( args );
		
		for ( ITimer x : timerItems ) {
			
			if ( x.getTaskId() == taskId || ( x.getEndTaskId() != null && x.getEndTaskId() == taskId ) ) {			
				x.preEnd(args);
				x.end( args );
				Bukkit.getScheduler().cancelTask( taskId );
				if ( x.getEndTaskId() != null ) Bukkit.getScheduler().cancelTask( x.getEndTaskId() );
				x.postEnd(args);
				return;
			}
		}
		timerHandler.end( args );
		timerHandler.postEnd(args);		
		
	}
	
	public void stop() {
		
		Object[] args = { lastStarted == null ? 0 : lastStarted, System.currentTimeMillis() - ( lastStarted == null ? 0 : lastStarted ), System.currentTimeMillis() };
		timerHandler.preEnd( args );
		
		for ( ITimer x : timerItems ) {
			x.preEnd(args);
			x.end( args );			
			if ( x.getTaskId() != null ) Bukkit.getScheduler().cancelTask( x.getTaskId() );
			if ( x.getEndTaskId() != null ) Bukkit.getScheduler().cancelTask( x.getEndTaskId() );
			x.postEnd(args);
		}
		
		
		timerHandler.end( args );
		timerHandler.postEnd(args);
		
	}
	
	public void close() {
		
		stop();
		for ( ITimer x : timerItems ) { x.close(); }
		
	}
	
	
	
}
