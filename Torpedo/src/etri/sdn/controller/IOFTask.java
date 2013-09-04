package etri.sdn.controller;

import java.util.TimerTask;

/**
 * This is an interface for a task that asynchronously scheduled to be executed.
 * Normally, the object of IOFTask is passed to the 
 * {@link OFController#scheduleTask(IOFTask, long)} method as a first argument. 
 * 
 * @author bjlee
 *
 */
public interface IOFTask {

	/**
	 * Execute the given task object.
	 * This method is normally called by {@link TimerTask#run()}. 
	 * 
	 * @return a boolean value which indicates if this task is able to be re-scheduled.
	 */
	public boolean execute();
	
}
