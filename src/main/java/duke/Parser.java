package duke;

/**
 * Class that specifically deals with input from the user and calls the respective class and methods
 */
public class Parser {
    /**
     * Empty constructor for the Parser class
     */
    public Parser() {}

    /**
     * Method to take in the input that is passed by the user and makes sense of what to do from the input
     *
     * @param input String input by the user
     * @param taskList Current taskList
     * @return a boolean to check when to stop taking inputs from the user
     * @throws DukeException checks for any invalid instructions that have been inputted by the user
     */
    public boolean takeInput(String input, TaskList taskList) throws DukeException {
        if (input.equals("bye")) {
            System.out.println("~BYE!~ Come back to Duke anytime");
            return true;
        }

        //Check if input == list
        else if (input.equals("list")) {
            taskList.printList();
        }

        //Instruction to reset the arraylist
        else if (input.equals("reset")) {
            taskList.reset();
            System.out.println("List of tasks has been resetted");
        }

        //Check if input == unmark or delete or mark
        else if (input.contains("unmark") || input.contains("delete") || input.contains("mark")) {
            String[] splitString = input.split("\\s+");
            String instr = splitString[0];
            if (splitString.length < 2) {
                System.out.println("Did you miss out the index in your input?");
            } else {
                try {
                    int index = Integer.parseInt(splitString[1]);
                    if (instr.equals("unmark")) {
                        taskList.unmarkTask(index);
                    } else if (instr.equals("mark")) {
                        taskList.markTask(index);
                    } else if (instr.equals("delete")) {
                        taskList.deleteTask(index);
                    } else {
                        throw new DukeException("You have entered an invalid instruction");
                    }
                } catch (DukeException e) {
                    System.out.println(e);
                }
            }
        }

        //input is a new type of task
        else if (input.contains("todo") || input.contains("event") || input.contains("deadline")) {
            //identify type of task
            String[] stringArray = input.split(" ", 2);

            //task has no task detail/name
            if (stringArray.length < 2) {
                throw new DukeException("Description of task cannot be empty!");
            }

            String taskType = stringArray[0];
            String taskDetails = stringArray[1];

            Task newTask = new Task("");

            if (taskType.equals("todo")) {
                newTask = new Todo(taskDetails);
            } else if (taskType.equals("deadline")) {
                String[] stringSplit = taskDetails.split("/by");
                if (stringSplit.length < 2) {
                    throw new DukeException("Description of deadline must include a date/time! Did you miss out a /by?");
                }
                String details = stringSplit[0].trim();
                String dateTime = stringSplit[1].trim();
                newTask = new Deadline(details,dateTime);
            } else if (taskType.equals("event")) {
                String[] splitString = taskDetails.split("/at");
                if (splitString.length < 2) {
                    throw new DukeException("Description of event must include a date/time! Did you miss out a /at?");
                }
                String details = splitString[0].trim();
                String dateTime = splitString[1].trim();
                newTask = new Event(details,dateTime);
            }

            taskList.addTask(newTask);
        } else {
            throw new DukeException("no such task type");
        }
        Ui.printSeparator();
        return false;
    }
}
