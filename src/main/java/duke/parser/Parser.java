package duke.parser;

import duke.command.AddCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.ErrorCommand;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.exception.InvalidDeadlineException;
import duke.exception.InvalidEventException;
import duke.exception.InvalidInputException;
import duke.exception.InvalidRequestException;
import duke.exception.InvalidTodoException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

/**
 * Represents a Parser class and consists of methods related to parsing user commands.
 */
public class Parser {

    /**
     * Returns a command object by parsing the user command.
     *
     * @param userCommand The user command.
     * @return The command for Duke to execute.
     */
    public static Command parse(String userCommand) {
        try {
            String[] wordArray = userCommand.split(" ");
            int noOfWords = wordArray.length;
            if (noOfWords == 0) {
                throw new InvalidInputException("I don't know what you need "
                        + "me to do since the command is empty!");
            }
            if (userCommand.equals("bye")) {
                return new ExitCommand();
            }
            if (userCommand.equals("list")) {
                return new ListCommand();
            }
            if (wordArray[0].equals("done")) {
                return parseDone(wordArray);
            }
            if (wordArray[0].equals("delete")) {
                return parseDeletion(wordArray);
            }
            if (wordArray[0].equals("find")) {
                return parseFind(wordArray);
            }
            if (wordArray[0].equals("todo")) {
                return parseTodo(userCommand);
            }
            if (wordArray[0].equals("deadline")) {
                return parseDeadline(userCommand);
            }
            if (wordArray[0].equals("event")) {
                return parseEvent(userCommand);
            }
            throw new InvalidInputException("I cannot understand your command! "
                    + "Please ensure your command follows the rules.");
        } catch (InvalidInputException e) {
            return new ErrorCommand(e.getMessage());
        }
    }

    public static Command parseDone(String[] wordArray) {
        int noOfWords = wordArray.length;
        try {
            if (noOfWords == 1) {
                throw new InvalidRequestException("Please tell me which task you want "
                        + "to be marked as done.");
            }
            if (noOfWords > 2) {
                throw new InvalidRequestException("I can only handle one request to "
                        + "mark a task as DONE once! Please check your command.");
            }
            Integer index = Integer.parseInt(wordArray[1]);
            return new DoneCommand(index);
        } catch (InvalidInputException e) {
            return new ErrorCommand(e.getMessage());
        }
    }

    public static Command parseDeletion(String[] wordArray) {
        int noOfWords = wordArray.length;
        try {
            if (noOfWords == 1) {
                throw new InvalidRequestException("Please tell me which task you want "
                        + "to delete!");
            }
            if (noOfWords > 2) {
                throw new InvalidRequestException("I can only handle one request to "
                        + "delete a task at once! Please check your command.");
            }
            Integer index = Integer.parseInt(wordArray[1]);
            return new DeleteCommand(index);
        } catch (InvalidInputException e) {
            return new ErrorCommand(e.getMessage());
        }
    }

    public static Command parseFind(String[] wordArray) {
        int noOfWords = wordArray.length;
        try {
            if (noOfWords == 1) {
                throw new InvalidRequestException("Please tell me the word you want "
                        + "to find!");
            }
            if (noOfWords > 2) {
                throw new InvalidRequestException("I can only handle one keyword!");
            }
            return new FindCommand(wordArray[1]);
        } catch (InvalidInputException e) {
            return new ErrorCommand(e.getMessage());
        }
    }

    public static Command parseTodo(String userCommand) {
        String[] wordArray = userCommand.split(" ");
        int noOfWords = wordArray.length;
        try {
            if (noOfWords == 1) {
                throw new InvalidTodoException("Please tell me the name "
                        + "of the todo task! Or else I cannot add it into the list.");
            }
            String taskName = userCommand.split(" ", 2)[1];
            Task newTask = new Todo(taskName);
            return new AddCommand(newTask);
        } catch (InvalidInputException e) {
            return new ErrorCommand(e.getMessage());
        }
    }

    public static Command parseDeadline(String userCommand) {
        String[] wordArray = userCommand.split(" ");
        int noOfWords = wordArray.length;
        try {
            if (noOfWords == 1) {
                throw new InvalidDeadlineException("Please tell me the name "
                        + "and the time due of the deadline task! Or else I "
                        + "cannot add it into the list.");
            }
            String body = userCommand.split(" ", 2)[1];
            if (body.split(" /by").length < 2) {
                throw new InvalidDeadlineException("Please tell me both the name and"
                        + " the time due of the deadline task in the correct form! "
                        + "Don't forget to include the time by using /by.");
            }
            String taskName = body.split(" /by ")[0];
            String time = body.split(" /by ")[1];
            Task newTask = new Deadline(taskName, time);
            return new AddCommand(newTask);
        } catch (InvalidInputException e) {
            return new ErrorCommand(e.getMessage());
        }
    }

    public static Command parseEvent(String userCommand) {
        String[] wordArray = userCommand.split(" ");
        int noOfWords = wordArray.length;
        try {
            if (noOfWords == 1) {
                throw new InvalidEventException("Please tell me the name and time period"
                        + " of the event task! Or else I cannot add it into the list.");
            }
            String body = userCommand.split(" ", 2)[1];
            if (body.split(" /at").length < 2) {
                throw new InvalidEventException("Please tell me both the name and "
                        + "the time period of the event task in the correct form! "
                        + "Don't forget to include the time by using /at.");
            }
            String taskName = body.split(" /at ")[0];
            String time = body.split(" /at ")[1];
            Task newTask = new Event(taskName, time);
            return new AddCommand(newTask);
        } catch (InvalidInputException e) {
            return new ErrorCommand(e.getMessage());
        }
    }
}

