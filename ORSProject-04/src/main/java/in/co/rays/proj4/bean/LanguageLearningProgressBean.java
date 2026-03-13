package in.co.rays.proj4.bean;

/**
 * LanguageLearningProgressBean represents the progress of a student
 * in learning a particular language. It includes details such as
 * student name, language being learned, proficiency level,
 * and completion status.
 *
 * This class extends {@link BaseBean} to include common audit fields.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class LanguageLearningProgressBean extends BaseBean {

    /** Name of the student. */
    private String studentName;

    /** Language being learned. */
    private String language;

    /** Level of learning (Beginner, Intermediate, Advanced). */
    private String level;

    /** Completion status of the course. */
    private String completionStatus;

    /**
     * Gets the student name.
     *
     * @return studentName
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Sets the student name.
     *
     * @param studentName the student name
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * Gets the language being learned.
     *
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the language being learned.
     *
     * @param language the language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets the learning level.
     *
     * @return level
     */
    public String getLevel() {
        return level;
    }

    /**
     * Sets the learning level.
     *
     * @param level the level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * Gets the completion status.
     *
     * @return completionStatus
     */
    public String getCompletionStatus() {
        return completionStatus;
    }

    /**
     * Sets the completion status.
     *
     * @param completionStatus the completion status
     */
    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }

    /**
     * Returns the unique key (ID) as a string.
     *
     * @return id
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns the display value of the record,
     * typically the student name.
     *
     * @return studentName
     */
    @Override
    public String getValue() {
        return studentName;
    }
}