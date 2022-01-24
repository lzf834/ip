package duke;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Event Class that is a subclass of task, has additional date and time attributes
 */
public class Event extends Task{
    LocalDate date;
    LocalTime time = null;

    /**
     * Constructor for Event Class
     * Input is taken as {DATE TIME}, where date is in YYYY-MM-DD format and time is in HH:mm format
     *
     * @param taskName the details/name of the task
     * @param dateTime the date and time in string format
     * @throws DukeException checks for any invalid input into date and time
     */
    public Event(String taskName, String dateTime) throws DukeException {
        super(taskName);

        dateTime = dateTime.trim();
        String[] splitString = dateTime.split(" ");
        if (splitString.length == 1) {
            try {
                this.date = LocalDate.parse(splitString[0]);
            } catch (Exception e) {
                throw new DukeException("Invalid input into date");
            }
        } else if (splitString.length == 2) {
            try {
                this.date = LocalDate.parse(splitString[0]);
                this.time = LocalTime.parse(splitString[1]);
            } catch (Exception e) {
                throw new DukeException("Invalid input into date/time");
            }
        } else {
            throw new DukeException("Please input for a date (optional: time)");
        }
    }

    /**
     * formats and prints the date into MMM d YYYY format
     */
    public void printDate() {
        System.out.print("(at: ");
        System.out.print(this.date.format(DateTimeFormatter.ofPattern("MMM d yyyy")));
        printTime();
        System.out.println(")");
    }

    /**
     * formats and prints the time into HHmm format
     */
    public void printTime() {
        if (this.time != null) {
            System.out.print(" " + this.time.format(DateTimeFormatter.ofPattern(("HH:mm"))));
        }
    }

    /**
     * Method to print the Deadline task out, overrides the method in the superclass
     */
    @Override
    public void printTask() {
        //print task type
        System.out.print("[E]");
        //print task done symbol
        if (this.done) {
            System.out.print("[X] " + this.taskName + " ");
        } else {
            System.out.print("[ ] " + this.taskName + " ");
        }
        printDate();
    }

    /**
     * Overrides the toString method, used for JUnit testing ensuring the correct output is printed out
     * @return String that goes into the output
     */
    @Override
    public String toString() {
        String result = "";
        result += "[E]";
        if (this.done) {
            result += "[X]";
        } else {
            result += "[ ]";
        }
        result += this.taskName + " (at: " + this.date.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        if (this.time != null) {
            result += " " + this.time.format(DateTimeFormatter.ofPattern(("HH:mm")));
        }
        result += ")";
        return result;
    }
}
