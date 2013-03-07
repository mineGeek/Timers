package com.github.mineGeek.Timers.Main;

import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.mineGeek.Timers.Structs.Timer;

public class TimersRegistry {

	public static JavaPlugin plugin;
	public static Map<String, Timer> timers = new WeakHashMap<String, Timer>();
	
	
	public static void cancelTask( Integer taskId ) {
		
		for ( Timer t : timers.values() ) {
			t.stop( taskId );
		}	
	}
	
	
	
}
