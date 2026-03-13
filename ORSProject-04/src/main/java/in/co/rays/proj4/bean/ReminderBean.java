package in.co.rays.proj4.bean;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ReminderBean extends BaseBean {

	private Long reminderId;
	private String reminderCode;     // UNIQUE
	private String reminderTitle;
	private Timestamp reminderDate;
	private String reminderStatus;

	public Long getReminderId() {
		return reminderId;
	}

	public void setReminderId(Long reminderId) {
		this.reminderId = reminderId;
	}

	public String getReminderCode() {
		return reminderCode;
	}

	public void setReminderCode(String reminderCode) {
		this.reminderCode = reminderCode;
	}

	public String getReminderTitle() {
		return reminderTitle;
	}

	public void setReminderTitle(String reminderTitle) {
		this.reminderTitle = reminderTitle;
	}

	public Timestamp getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(Timestamp timestamp) {
		this.reminderDate = timestamp;
	}

	public String getReminderStatus() {
		return reminderStatus;
	}

	public void setReminderStatus(String reminderStatus) {
		this.reminderStatus = reminderStatus;
	}

	@Override
	public String getKey() {
		return String.valueOf(reminderId);
	}

	@Override
	public String getValue() {
		return reminderTitle;
	}
}