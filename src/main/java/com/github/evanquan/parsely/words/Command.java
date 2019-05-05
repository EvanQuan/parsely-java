package com.github.evanquan.parsely.words;

import com.github.evanquan.parsely.util.FuncUtils;

import java.util.ArrayList;

/**
 * Represents a command that the player has issued to the game in a form of a
 * {@link String}. A player command is composed of an {@link ArrayList} of
 * {@link Action}s that are sequentially ordered from how they are ordered
 * in the input string.
 *
 * @author Evan Quan
 */
public class Command {

    /**
     * Series of actions in the order they were issued.
     */
    ArrayList<Action> actions;
    /**
     * Represents the player command directly as a string, unaltered.
     */
    private String string;

    /**
     * Default constructor. Does not initially contain any {@link
     * Action}s.
     *
     * @param string input string representation of the command.
     */
    public Command(String string) {
        this.string = string;
        actions = new ArrayList<>();
    }

    /**
     * Add a single action to this command's list of actions.
     *
     * @param action
     */
    public void addAction(Action action) {
        this.actions.add(action);
    }

    /**
     * @param action to check
     * @return true if this command's {@link ArrayList} of {@link Action}s
     * contains the specified action.
     */
    public boolean contains(Action action) {
        return this.actions.contains(action);
    }

    /**
     * @param other object to compare
     * @return true if the other object is a Command that has the same
     * input string representation and same {@link Action}s.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Command) {
            return hasSameString((Command) other)
                    && hasSameActions((Command) other);
        }
        return false;
    }

    /**
     * @return true if this command represents an empty string.
     */
    public boolean isEmpty() {
        return this.string.isEmpty();
    }

    /**
     * @return all the actions that this command represents.
     */
    public ArrayList<Action> getActions() {
        return this.actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    /**
     * Get the input string that represents this command. This is different from
     * toString(), which gives a detailed representation of all components of
     * this command in terms of how it was parsed.
     *
     * @return the input string that represents this command.
     */
    public String getString() {
        return this.string;
    }

    /**
     * Check if this command's {@link ArrayList} of {@link Action}s is not
     * empty.
     *
     * @return true if this command contains at least one action.
     */
    public boolean hasActions() {
        return !this.actions.isEmpty();
    }

    /**
     * @param other to compare
     * @return true if the other command has the same actions as this command.
     */
    public boolean hasSameActions(Command other) {
        return FuncUtils.nullablesEqual(this.actions, other.getActions());
    }

    /**
     * @param other to compare
     * @return true if the other command has the same input string as this
     * command.
     */
    public boolean hasSameString(Command other) {
        return FuncUtils.nullablesEqual(this.string, other.getString());
    }

    /**
     * @return the string representation of this command in terms of all its
     * components.
     */
    @Override
    public String toString() {
        StringBuilder actions = new StringBuilder();
        if (hasActions()) {
            for (Action action : this.actions) {
                actions.append("\t\t" + action + System.lineSeparator());
            }
        }
        return "[string: " + string + System.lineSeparator() +
                "\tactions[" + this.actions.size() + "]" + System.lineSeparator()
                + actions + "]";
    }
}
