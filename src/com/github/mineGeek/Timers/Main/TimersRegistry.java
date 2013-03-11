package com.github.mineGeek.Timers.Main;

import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.mineGeek.Timers.Structs.SubTimer;

public class TimersRegistry {

	public static JavaPlugin plugin;
	public static Map<String, SubTimer> timers = new WeakHashMap<String, SubTimer>();
	
	
	
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
	
	
	
}
