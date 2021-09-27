package fr.spse.master_gacha_reroller.rerollable;

/**
 * An interface for anything that can be rerolled
 */
public interface Rerollable {


    /**
     * Call before the reroll execution.
     * Should be called on the UI thread, for the implementation to make preparations
     * that aren't directly related to the reroll itself.
     */
    void onPreReroll();

    /**
     * Call when the reroll must be executed.
     * Any implementation MUST call either onRerollSuccess on onRerollFail
     * @return Whether or not the reroll succeeded
     */
    boolean reroll();

    /**
     * Call after the reroll execution, regardless of the success state.
     * Usually used to do cleanup, no need to call it on the UI thread
     */
    void onPostReroll(boolean success);

    /**
     * Call when the reroll succeeded. Do what you want but inform the user.
     * Calling on the UI thread is advised
     */
    void onRerollSuccess();


    /**
     * Call when the reroll failed. Do what you want but inform the user.
     * Calling on the UI thread is advised
     */
    void onRerollFail();


}
