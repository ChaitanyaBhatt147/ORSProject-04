package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * SeminarBean represents seminar information within the system. It includes
 * seminar details such as title, date, and speaker. This class extends
 * {@link BaseBean} to include common audit fields.
 *
 * author Chaitanya Bhatt
 * 
 * @version 1.0
 */
public class SeminarBean extends BaseBean {

	/** Unique ID of the seminar. */
	private Long seminarId;

	/** Title of the seminar. */
	private String seminarTitle;

	/** Date of the seminar. */
	private Date seminarDate;

	/** Speaker of the seminar. */
	private String speaker;

	/**
	 * Gets the seminar ID.
	 *
	 * @return seminarId
	 */
	public Long getSeminarId() {
		return seminarId;
	}

	/**
	 * Sets the seminar ID.
	 *
	 * @param seminarId the seminar ID
	 */
	public void setSeminarId(Long seminarId) {
		this.seminarId = seminarId;
	}

	/**
	 * Gets the seminar title.
	 *
	 * @return seminarTitle
	 */
	public String getSeminarTitle() {
		return seminarTitle;
	}

	/**
	 * Sets the seminar title.
	 *
	 * @param seminarTitle the seminar title
	 */
	public void setSeminarTitle(String seminarTitle) {
		this.seminarTitle = seminarTitle;
	}

	/**
	 * Gets the seminar date.
	 *
	 * @return seminarDate
	 */
	public Date getSeminarDate() {
		return seminarDate;
	}

	/**
	 * Sets the seminar date.
	 *
	 * @param seminarDate the seminar date
	 */
	public void setSeminarDate(Date seminarDate) {
		this.seminarDate = seminarDate;
	}

	/**
	 * Gets the seminar speaker.
	 *
	 * @return speaker
	 */
	public String getSpeaker() {
		return speaker;
	}

	/**
	 * Sets the seminar speaker.
	 *
	 * @param speaker the speaker name
	 */
	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	/**
	 * Returns the unique key (ID) as a string.
	 *
	 * @return the key
	 */
	@Override
	public String getKey() {
		return id + "";
	}

	/**
	 * Returns the display value of the seminar.
	 *
	 * @return seminarTitle
	 */
	@Override
	public String getValue() {
		return seminarTitle;
	}
}