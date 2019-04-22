package com.github.evanquan.parsely.word;

import com.github.evanquan.parsely.util.FuncUtils;

import java.util.ArrayList;

/**
 * Represents a command that the player has issued to the game in a form of a
 * {@link String}. A player command is composed of an {@link ArrayList} of
 * {@link PlayerAction}s that are sequentially ordered from how they are ordered
 * in the input string.
 *
 * @author Evan Quan
 */
public class PlayerCommand {

    /**
     * Series of playerActions in the order they were issued.
     */
    ArrayList<PlayerAction> playerActions;
    /**
     * Represents the player command directly as a string, unaltered.
     */
    private String string;

    /**
     * Default constructor. Does not initially contain any {@link
     * PlayerAction}s.
     *
     * @param string input string representation of the command.
     */
    public PlayerCommand(String string) {
        this.string = string;
        playerActions = new ArrayList<>();
    }

    /**
     * Add a single playerAction to this command's list of playerActions.
     *
     * @param playerAction
     */
    public void addAction(PlayerAction playerAction) {
        this.playerActions.add(playerAction);
    }

    /**
     * @param playerAction to check
     * @return true if this command's {@link ArrayList} of {@link PlayerAction}s
     * contains the specified playerAction.
     */
    public boolean contains(PlayerAction playerAction) {
        return this.playerActions.contains(playerAction);
    }

    /**
     * @param other object to compare
     * @return true if the other object is a PlayerCommand that has the same
     * input string representation and same {@link PlayerAction}s.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof PlayerCommand) {
            return hasSameString((PlayerCommand) other)
                    && hasSameActions((PlayerCommand) other);
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
    public ArrayList<PlayerAction> getPlayerActions() {
        return this.playerActions;
    }

    public void setPlayerActions(ArrayList<PlayerAction> playerActions) {
        this.playerActions = playerActions;
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
     * Check if this command's {@link ArrayList} of {@link PlayerAction}s is not
     * empty.
     *
     * @return true if this command contains at least one action.
     */
    public boolean hasActions() {
        return !this.playerActions.isEmpty();
    }

    /**
     * @param other to compare
     * @return true if the other command has the same actions as this command.
     */
    public boolean hasSameActions(PlayerCommand other) {
        return FuncUtils.nullablesEqual(this.playerActions, other.getPlayerActions());
    }

    /**
     * @param other to compare
     * @return true if the other command has the same input string as this
     * command.
     */
    public boolean hasSameString(PlayerCommand other) {
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
            for (PlayerAction playerAction : this.playerActions) {
                actions.append("\t\t" + playerAction + System.lineSeparator());
            }
        }
        return "[string: " + string + System.lineSeparator() +
                "\tplayerActions[" + playerActions.size() + "]" + System.lineSeparator()
                + actions + "]";
    }
}
