package org.esupportail.bigbluebutton.domain.beans;

import java.util.Date;

/**
 * The class that represent recordings.
 * @author Franck Bordinat
 *
 */

public class Recording {
	
	/**
	 * Recording id
	 */
	private String recordID;
	
	/**
	 * Name of the recording.
	 */
	private String name;

	/**
	 * Description of the recording.
	 */
	private String description;
	
	/**
	 * start Time.
	 */
	private String startTime;
	
	/**
	 * publish status
	 */
	private String published;
	
	/**
	 * url of the playback.
	 */
	private String playback;
	
	/**
	 * Length in minutes of the recording.
	 */
	private String length;
	
	



	/* ****************** Constructors ************ */
	/**
	 * Bean constructor.
	 */
	public Recording() {
		super();
	}

	/**
	 * Bean constructor
	 * @param recordID
	 * @param name
	 * @param description
	 * @param startTime
	 * @param published
	 * @param playback
	 * @param length
	 */
	public Recording(String recordID, String name, String description,
			String startTime, String published, String playback, String length) {
		super();
		this.recordID = recordID;
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.published = published;
		this.playback = playback;
		this.length = length;
	}
	
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return "Recording#" + hashCode() + "[name=["
				+ name + "], description=[" + description + "], startTime=[" + startTime + "]" +
						", published=[" + published + "], playback=[" + playback + "], length=[" + length + "]]";
	}

	/* ******************* Accessors ******************* */
	
	public String getRecordID() {
		return recordID;
	}

	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getPlayback() {
		return playback;
	}

	public void setPlayback(String playback) {
		this.playback = playback;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
	

	
}
