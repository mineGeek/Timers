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

	public Integer start = null;
	public Integer resume = null;
	public Integer end = null;
	public Long lastStarted = null;	
	
	public Set<Integer> taskIds = new HashSet<Integer>();
	
	public void addTimer( ITimer timer ) {
		
		timerItems.add( timer );
		
	}
	
	
	public void start() {

		stop();
		Integer toStart = start == null ? 0 : start;		
		lastStarted = System.currentTimeMillis();
		if ( resume != null ) {
			
			lastStarted -= resume;
			toStart -= resume;
		}
				
				
		for ( ITimer x : timerItems ) {
			
			BukkitTask task = null;
			int startTimer = x.getStart() == null ? 0 : x.getStart();
			Object[] args = { lastStarted, 0, ( x.getEnd() == null ? 0 : x.getEnd() )};
			
			if ( x.getInterval() != null ) {
				// Ticking timer
				task = Bukkit.getScheduler().runTaskTimer( TimersRegistry.plugin, (Runnable) x, startTimer * 20 , x.getInterval() * 20 );
				
			} else if ( startTimer > 0 && x.getEnd() != null ){
				
				//One off set in future or that contains an end?
				task = Bukkit.getScheduler().runTaskLater( TimersRegistry.plugin, (Runnable) x, ( startTimer == 0 ? 1 : startTimer ) );
				
			} else if ( startTimer == 0 ) {
				
				//on off message. Just raise event
				x.start(args);
				x.setTimerStart( lastStarted );
				resume = null;
				continue;
			}
			
			
			x.start( args );
			x.setTimerStart( lastStarted );
			
			
			Integer id = task.getTaskId();
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
				
				BukkitTask endTask = Bukkit.getScheduler().runTaskLater( TimersRegistry.plugin, new Runnable() {
					
					public void run() {	
						
						Bukkit.getScheduler().cancelTask( endTaskId ); 
						
					}
					
				}, toEnd * 20 );
				
				x.setEndTaskId( endTask.getTaskId() );
				x.setTimerEnd( lastStarted + (toEnd * 1000 ) );
				
			}
			

		}
		

		resume = null;
		
	}
	
	public void stop( Integer taskId ) {
		
		for ( ITimer x : timerItems ) {
			if ( x.getTaskId() == taskId || ( x.getEndTaskId() != null && x.getEndTaskId() == taskId ) ) {
				
				Object[] args = { lastStarted, System.currentTimeMillis() - lastStarted, 0};
				x.end( args );
				Bukkit.getScheduler().cancelTask( taskId );
				if ( x.getEndTaskId() != null ) Bukkit.getScheduler().cancelTask( x.getEndTaskId() );
				return;
			}
		}
		
	}
	
	public void stop() {
		
		for ( ITimer x : timerItems ) {
			Object[] args = { lastStarted, System.currentTimeMillis() - lastStarted, 0};
			x.end( args );
			if ( x.getTaskId() != null ) Bukkit.getScheduler().cancelTask( x.getTaskId() );
			if ( x.getEndTaskId() != null ) Bukkit.getScheduler().cancelTask( x.getEndTaskId() );
		}
		
	}
	
	
	
}
