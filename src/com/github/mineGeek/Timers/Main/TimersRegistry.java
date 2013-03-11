package com.github.mineGeek.Timers.Main;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.mineGeek.Persistence.FileStore;
import com.github.mineGeek.Timers.Structs.Timers;


public class TimersRegistry {

	public static JavaPlugin plugin;
	
	public 	static Set<Timers> timers = new HashSet<Timers>();
	private static FileStore fs = null;
	public 	static boolean persistTimerData = false;
	private static boolean timerDataFetched = false;
	
	public static Integer getTimersOffset( String tag ) {
		
		Integer result = null;
		
		if ( persistTimerData ) {
			
			if ( !timerDataFetched ) {
				
				fs = new FileStore( plugin.getDataFolder().toString() + File.separator + "timers", "data.bin" );
				fs.load();
			}
			
			result = fs.getAsInteger( tag, null );
			
		}
		return result;
	}
	
	public static void saveTimersOffset() {
		
		if ( persistTimerData ) {
				
			fs = new FileStore( plugin.getDataFolder().toString() + File.separator + "timers", "data.bin" );
			Integer offset = null;

			for ( Timers t : timers ) {
				
				offset = (int) (t.lastStop - t.lastStart)/1000;				
				if ( offset < 1 ) offset = null;
				fs.set( t.tag, offset );
			}
			
			fs.save();
			
		}		
		
	}
	
	public static void addTimers( Timers timersObject ) {
		
		timersObject.offset = getTimersOffset( timersObject.tag );
		timers.add( timersObject );
		
		
	}
	
	public static String getTimeStampAsString( Integer timeStamp ) {
		
		if ( timeStamp == null || timeStamp == 0 ) return "";
		
		int sec = 	( timeStamp % 60 );
		int min = 	( timeStamp / 60) % 60;
		int hours = ( timeStamp / (60*60 ) ) % 24;
		int days = 	( timeStamp / (60*60*24) ) % 7;
		int weeks = ( timeStamp / (60*60*24*7) ); 
  
		String result = "";
		if ( weeks > 0 ) 	result = weeks + " week" + ( weeks == 1 ? "" : "s");
		if ( days > 0) 		result += ( result.length() > 0 ? ", " : "" ) + days + " day"  	+ ( days == 1 ? "" : "s");
		if ( hours > 0) 	result += ( result.length() > 0 ? ", " : "" ) + hours + " hour"  + ( hours == 1 ? "" : "s");
		if ( min > 0) 		result += ( result.length() > 0 ? ", " : "" ) + min + " minute" 	+ ( min == 1 ? "" : "s");
		if ( sec > 0) 		result += ( result.length() > 0 ? ", " : "" ) + sec + " second" 	+ ( sec == 1 ? "" : "s");
		
		return result;
		
	}	
	
	public static void close() {
		Bukkit.getScheduler().cancelTasks( plugin );
		if ( timers != null && !timers.isEmpty() ) for ( Timers t : timers ) t.stop();
		saveTimersOffset();
		if ( timers != null && !timers.isEmpty() ) for ( Timers t : timers ) t.close();
		plugin = null;
	}
	
}
