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
            if (userCommand.equals("bye")) {
                return new ExitCommand();
            } else {
                if (userCommand.equals("list")) {
                    return new ListCommand();
                } else {
                    String[] wordArray = userCommand.split(" ");
                    int noOfWords = wordArray.length;
                    if (noOfWords == 0) {
                        throw new InvalidInputException("I don't know what you need "
                                + "me to do since the command is empty!");
                    }
                    if (wordArray[0].equals("done")) {
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
                    } else if (wordArray[0].equals("delete")) {
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
                    } else if (wordArray[0].equals("find")) {
                        if (noOfWords == 1) {
                            throw new InvalidRequestException("Please tell me the word you want "
                                    + "to find!");
                        }
                        if (noOfWords > 2) {
                            throw new InvalidRequestException("I can only handle one keyword!");
                        }
                        return new FindCommand(wordArray[1]);
                    } else {
                        Task newTask;
                        if (wordArray[0].equals("todo")) {
                            if (noOfWords == 1) {
                                throw new InvalidTodoException("Please tell me the name "
                                        + "of the todo task! Or else I cannot add it into the list.");
                            }
                            String taskName = userCommand.split(" ", 2)[1];
                            newTask = new Todo(taskName);
                        } else if (wordArray[0].equals("deadline")) {
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
                            newTask = new Deadline(taskName, time);
                        } else if (wordArray[0].equals("event")) {
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
                            newTask = new Event(taskName, time);
                        } else {
                            throw new InvalidInputException("I cannot understand your command! "
                                    + "Please ensure your command follows the rules.");
                        }
                        return new AddCommand(newTask);
                    }
                }
            }
        } catch (InvalidInputException e) {
            return new ErrorCommand(e.getMessage());
        }
    }


}
