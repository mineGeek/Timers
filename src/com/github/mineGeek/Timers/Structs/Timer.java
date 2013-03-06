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
	public List<BukkitTask> tasks = new ArrayList<BukkitTask>();
	
	public Integer start = null;
	public Integer resume = null;
	public Integer end = null;
	public Long lastStarted = null;	
	
	public Set<Integer> taskIds = new HashSet<Integer>();
	
	public void addTimer( ITimer timer ) {
		
		timerItems.add( timer );
		
	}
	
	
	public void start() {

		Integer toStart = start == null ? 0 : start;		
		lastStarted = System.currentTimeMillis();
		if ( resume != null ) {
			
			lastStarted -= resume;
			toStart -= resume;
		}
				
				
		for ( ITimer x : timerItems ) {
			
			BukkitTask task = null;
			
			if ( x.getInterval() != null ) {
				task = Bukkit.getScheduler().runTaskTimer( TimersRegistry.plugin, (Runnable) x, x.getStart() * 20 , x.getInterval() * 20 );
			} else {
				task = Bukkit.getScheduler().runTaskLater( TimersRegistry.plugin, (Runnable) x, (toStart == 0 ? 1 : toStart ) );
			}
			
			x.setTimerStart( lastStarted );
			
			tasks.add( task );
			
			
			Integer id = task.getTaskId();
			
			
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
					
					public void run() {	Bukkit.getScheduler().cancelTask( endTaskId ); }
					
				}, toEnd * 20 );
				
				tasks.add( endTask );			
				x.setTimerEnd( lastStarted + (toEnd * 1000 ) );
				
			}
			

		}
		

		resume = null;
		
	}
	
	public void stop() {
		
		if ( !tasks.isEmpty() ) for ( BukkitTask x : tasks ) x.cancel();
		
	}
	
	
	
}
