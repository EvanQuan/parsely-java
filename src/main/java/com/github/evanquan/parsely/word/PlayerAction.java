package com.github.evanquan.parsely.word;

import com.github.evanquan.parsely.util.FuncUtils;

/**
 * Represents a single action for the player to do. A {@link PlayerCommand} may
 * be composed of multiple actions. Confirms to the following grammar:
 * <br><br>
 * VerbPhrase? ObjectPhrase? Preposition? ObjectPhrase?
 *
 * @author Evan Quan
 */
public class PlayerAction {

    private VerbPhrase verbPhrase;
    private ObjectPhrase directObjectPhrase;
    private String preposition;
    private ObjectPhrase indirectObjectPhrase;

    @Override
    public boolean equals(Object other) {
        if (other instanceof PlayerAction) {
            return hasSameVerbPhrase((PlayerAction) other)
                    && hasSameDirectObjectPhrase((PlayerAction) other)
                    && hasSamePreposition((PlayerAction) other)
                    && hasSameIndirectObjectPhrase((PlayerAction) other);
        }
        return false;
    }

    /**
     * @return the {@link ObjectPhrase} placed immediately after this action's
     * {@link VerbPhrase} (if it has one).
     */
    public ObjectPhrase getDirectObjectPhrase() {
        return this.directObjectPhrase;
    }

    /**
     * @param directObjectPhrase to set
     */
    public void setDirectObjectPhrase(ObjectPhrase directObjectPhrase) {
        this.directObjectPhrase = directObjectPhrase;
    }

    /**
     * @return the {@link ObjectPhrase} placed immediately after this action's
     * preposition (if it has one).
     */
    public ObjectPhrase getIndirectObjectPhrase() {
        return this.indirectObjectPhrase;
    }

    /**
     * @param indirectObjectPhrase to set
     */
    public void setIndirectObjectPhrase(ObjectPhrase indirectObjectPhrase) {
        this.indirectObjectPhrase = indirectObjectPhrase;
    }

    /**
     * @return the word positioned between this command's direct and indirect
     * {@link ObjectPhrase}s.
     */
    public String getPreposition() {
        return this.preposition;
    }

    /**
     * @param preposition to set
     */
    public void setPreposition(String preposition) {
        this.preposition = preposition;
    }

    /**
     * @return the {@link VerbPhrase} that is the beginning of this action.
     */
    public VerbPhrase getVerbPhrase() {
        return this.verbPhrase;
    }

    /**
     * @param verbPhrase to set
     */
    public void setVerbPhrase(VerbPhrase verbPhrase) {
        this.verbPhrase = verbPhrase;
    }

    /**
     * Set the verbPhrase to a new VerbPhrase with the corresponding verb.
     *
     * @param verb to set
     */
    public void setVerbPhrase(String verb) {
        this.verbPhrase = new VerbPhrase(verb);
    }

    /**
     * @return true if this command has a direct {@link ObjectPhrase}
     */
    public boolean hasDirectObjectPhrase() {
        return this.directObjectPhrase != null
                && !this.directObjectPhrase.isEmpty();
    }

    /**
     * @return true if this command has an indirect {@link ObjectPhrase}
     */
    public boolean hasIndirectObjectPhrase() {
        return this.indirectObjectPhrase != null && !this.indirectObjectPhrase.isEmpty();
    }

    /**
     * @return true if this action has a preposition.
     */
    public boolean hasPreposition() {
        return this.preposition != null;
    }

    /**
     * @param other to compare
     * @return true if both player actions have the same direct object phrase.
     */
    private boolean hasSameDirectObjectPhrase(PlayerAction other) {
        return FuncUtils.nullablesEqual(this.directObjectPhrase, other.getDirectObjectPhrase());
    }

    /**
     * @param other to compare
     * @return true if both player actions have the same indirect object phrase.
     */
    private boolean hasSameIndirectObjectPhrase(PlayerAction other) {
        return FuncUtils.nullablesEqual(this.indirectObjectPhrase, other.getIndirectObjectPhrase());
    }

    /**
     * @param other to compare
     * @return true if both player actions have the same preposition.
     */
    private boolean hasSamePreposition(PlayerAction other) {
        return FuncUtils.nullablesEqual(this.preposition, other.getPreposition());
    }

    /**
     * @param other to compare
     * @return true if both player actions have the same verb phrase.
     */
    private boolean hasSameVerbPhrase(PlayerAction other) {
        return FuncUtils.nullablesEqual(this.verbPhrase, other.getVerbPhrase());
    }

    /**
     * @return true if this command has a {@link VerbPhrase}
     */
    public boolean hasVerbPhrase() {
        return this.verbPhrase != null && !this.verbPhrase.isEmpty();
    }

    /**
     * @return true if all components are empty.
     */
    public boolean isEmpty() {
        return !hasVerbPhrase()
                && !hasDirectObjectPhrase()
                && !hasPreposition()
                && !hasIndirectObjectPhrase();
    }

    /**
     * @return the string representation of this action in terms of all its
     * components.
     */
    @Override
    public String toString() {
        return "[" + String.join(", ",
                (hasVerbPhrase() ? "verbPhrase: " + verbPhrase : ""),
                (hasDirectObjectPhrase() ?
                        "directObjectPhrase: " + directObjectPhrase : ""),
                (hasPreposition() ? "preposition: " + preposition : ""),
                (hasIndirectObjectPhrase() ?
                        "indirectObjectPhrase: " + indirectObjectPhrase : "")
        )
                + "]";
    }
}
