package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * TrainingBean represents training information within the system.
 * It includes details such as training code, training name,
 * trainer name, and training date.
 * This class extends {@link BaseBean} to include common audit fields.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class TrainingBean extends BaseBean {

    /** Unique code of the training. */
    private String trainingCode;

    /** Name of the training. */
    private String trainingName;

    /** Name of the trainer. */
    private String trainerName;

    /** Date of the training. */
    private Date trainingDate;

    /**
     * Gets the training code.
     *
     * @return trainingCode
     */
    public String getTrainingCode() {
        return trainingCode;
    }

    /**
     * Sets the training code.
     *
     * @param trainingCode the training code
     */
    public void setTrainingCode(String trainingCode) {
        this.trainingCode = trainingCode;
    }

    /**
     * Gets the training name.
     *
     * @return trainingName
     */
    public String getTrainingName() {
        return trainingName;
    }

    /**
     * Sets the training name.
     *
     * @param trainingName the training name
     */
    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    /**
     * Gets the trainer name.
     *
     * @return trainerName
     */
    public String getTrainerName() {
        return trainerName;
    }

    /**
     * Sets the trainer name.
     *
     * @param trainerName the trainer name
     */
    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    /**
     * Gets the training date.
     *
     * @return trainingDate
     */
    public Date getTrainingDate() {
        return trainingDate;
    }

    /**
     * Sets the training date.
     *
     * @param trainingDate the training date
     */
    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
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
     * Returns the display value of the training,
     * typically the training name.
     *
     * @return trainingName
     */
    @Override
    public String getValue() {
        return trainingName;
    }
}