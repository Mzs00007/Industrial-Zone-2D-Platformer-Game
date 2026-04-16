package ai;

/**
 * AIBehavior — the top-level behaviour contract for the reflective
 * AI system loaded by managers.SystemsContainer.
 *
 * The SimpleAction / SimpleBehavior / AIAction supporting types live in
 * their own files (ai.SimpleAction, ai.SimpleBehavior, ai.AIAction).
 */
public interface AIBehavior {
    void initialize();

    AIAction execute(AI.AIAgent agent, float deltaSec);

    boolean isComplete();

    void reset();

    void stop();

    String getName();
}
