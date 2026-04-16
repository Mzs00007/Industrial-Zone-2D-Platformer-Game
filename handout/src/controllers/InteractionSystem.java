package controllers;

import entities.Card;
import entities.InteractiveBox;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * InteractionSystem - Handles E-key interactions (chests, checkpoints, etc)
 * Manages:
 *   - Detection of interactive objects near player
 *   - Processing interaction requests
 *   - Spawning cards from chests
 *   - Generating interaction prompts
 */
public class InteractionSystem {
    
    private List<InteractiveBox> interactiveBoxes = new ArrayList<>();
    private List<Card> spawnedCards = new ArrayList<>();
    
    private boolean eKeyPressed = false;
    private boolean eKeyProcessed = false;  // Prevent repeated triggers
    
    public InteractionSystem() {
    }
    
    // ===== REGISTRATION =====
    public void registerInteractiveBox(InteractiveBox box) {
        interactiveBoxes.add(box);
    }
    
    public void registerCard(Card card) {
        spawnedCards.add(card);
    }
    
    // ===== UPDATE =====
    public void update(float delta, float playerX, float playerY) {
        // Update all interactive objects
        for (InteractiveBox box : interactiveBoxes) {
            box.update(delta);
        }
        
        // Update cards
        for (Card card : spawnedCards) {
            card.update(delta);
        }
        
        // Remove dead cards
        spawnedCards.removeIf(card -> !card.isAlive());
        
        // Handle E-key interaction
        if (eKeyPressed && !eKeyProcessed) {
            eKeyProcessed = true;
            processInteraction(playerX, playerY);
        }
        
        if (!eKeyPressed) {
            eKeyProcessed = false;
        }
    }
    
    private void processInteraction(float playerX, float playerY) {
        // Find closest interactive box within range
        InteractiveBox closestBox = null;
        float closestDist = Float.MAX_VALUE;
        
        for (InteractiveBox box : interactiveBoxes) {
            if (box.isOpened()) continue;  // Skip opened boxes
            
            float dx = playerX - box.getX();
            float dy = playerY - box.getY();
            float dist = (float)Math.sqrt(dx*dx + dy*dy);
            
            if (dist < box.getInteractRange() && dist < closestDist) {
                closestBox = box;
                closestDist = dist;
            }
        }
        
        // Interact with closest box
        if (closestBox != null) {
            closestBox.interact();
        }
    }
    
    // ===== RENDERING =====
    public void render(Graphics2D g, int camX, int camY, float playerX, float playerY) {
        // Draw interaction prompts
        for (InteractiveBox box : interactiveBoxes) {
            box.drawInteractPrompt(g, playerX, playerY, camX, camY);
        }
    }
    
    // ===== INPUT =====
    public void onKeyDown(int keyCode) {
        if (keyCode == java.awt.event.KeyEvent.VK_E) {
            eKeyPressed = true;
        }
    }
    
    public void onKeyUp(int keyCode) {
        if (keyCode == java.awt.event.KeyEvent.VK_E) {
            eKeyPressed = false;
        }
    }
    
    // ===== CARD COLLECTION =====
    public int collectNearbyCards(float playerX, float playerY) {
        int collected = 0;
        for (Card card : spawnedCards) {
            if (card.canCollect(playerX, playerY)) {
                card.collect();
                collected++;
            }
        }
        return collected;
    }
    
    public int getCardCount() {
        int count = 0;
        for (Card card : spawnedCards) {
            if (!card.isCollected()) count++;
        }
        return count;
    }
    
    // ===== GETTERS =====
    public List<Card> getCards() {
        return spawnedCards;
    }
    
    public List<InteractiveBox> getBoxes() {
        return interactiveBoxes;
    }
}
