import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class Heba extends StarterGame {
    public static final String NEW_BACK_GROUND = "assets/darkchristmasbg.gif";
    public static final String NEW_SPLASH_SCREEN = "assets/newsplash.gif";
    public static final String WIN_SPLASH = "assets/winsplash2.gif";
    public static final String LOSE_SPLASH = "assets/losesplash2.gif";
    private ShieldPlayer shieldedPlayer;
    private int lastScoreCheck = 0;
    private boolean hardModeActivated = false;

    @Override
    protected void pregame() {
        setSplashImage(NEW_SPLASH_SCREEN);
        this.setBgImage(NEW_BACK_GROUND);
        this.score = 0;
        lastScoreCheck = 0;
        hardModeActivated = false;
        shieldedPlayer = new ShieldPlayer(STARTING_PLAYER_X, STARTING_PLAYER_Y);
        displayList.add(shieldedPlayer);
        entities.add(shieldedPlayer);
        player = shieldedPlayer;
    }

    @Override
    protected void updateGame() {
        super.updateGame();
        updateSpeed();
    }



    private void updateSpeed() {
        if (this.score > lastScoreCheck) {
            setGameSpeed((int)(getGameSpeed() * 1.05));
            lastScoreCheck = this.score;
            System.out.println("Speed increased by 5%! Current speed: " + getGameSpeed());
        }
        if (!hardModeActivated && this.score >= 200) {
            activateHardMode();
        }
    }

    private void activateHardMode(){
        hardModeActivated = true;
        setGameSpeed((int)(getGameSpeed() * 1.15));
        System.out.println("Hard mode activated! Speed boosted by 15%!");
    }


    @Override
    protected void postgame(){
        if (player.getHP() <= 0){
            setSplashImage(LOSE_SPLASH);
            setTitleText("GAME OVER - You Lost, try again!");
        }
        else if (this.score >= 300){
            setSplashImage(WIN_SPLASH);
            setTitleText("GAME OVER - You Won, Congratulations!");
        }
    }

    @Override
    protected boolean isGameOver() {
        return (player.getHP() <= 0) || (this.score >= 300);
    }


        // Christmas-themed entity classes
    private class ChristmasCollect extends Collect {
        public ChristmasCollect(int x, int y) {
            super(x, y, COLLECT_WIDTH, COLLECT_HEIGHT, "assets/ornament.gif");
        }
    }

    private class ChristmasRareCollect extends RareCollect {
        public ChristmasRareCollect(int x, int y) {
            super(x, y, RCOLLECT_WIDTH, RCOLLECT_HEIGHT, "assets/rarecollectcandy.gif");
        }
    }

    private class ChristmasAvoid extends Avoid {
        public ChristmasAvoid(int x, int y) {
            super(x, y, AVOID_WIDTH, AVOID_HEIGHT, "assets/avoidbrokenornament.gif");
        }
    }

    private class ChristmasRareAvoid extends RareAvoid {
        public ChristmasRareAvoid(int x, int y) {
            super(x, y, RAVOID_WIDTH, RAVOID_HEIGHT, "assets/rareavoid2.gif");
        }
    }

    @Override
    protected void spawnEntities() {
        int numberOfEntitiesToAdd;
        if (hardModeActivated) {
            numberOfEntitiesToAdd = rand.nextInt(5) + 2;
        } else {
            numberOfEntitiesToAdd = rand.nextInt(5) + 1;
        }

        for (int i = 0; i < numberOfEntitiesToAdd; i++) {
            int randomY = rand.nextInt(getWindowHeight() - Avoid.AVOID_HEIGHT);
            Entity entityToAdd;
            int chance = rand.nextInt(100);

            if (hardModeActivated) {
                if (chance < 60) {
                    entityToAdd = new ChristmasAvoid(getWindowWidth(), randomY);
                }
                else if (chance < 70) {
                    entityToAdd = new ChristmasRareAvoid(getWindowWidth(), randomY);
                }
                else if (chance < 85) {
                    entityToAdd = new ChristmasCollect(getWindowWidth(), randomY);
                }
                else if (chance < 95) {
                    entityToAdd = new ChristmasRareCollect(getWindowWidth(), randomY);
                }
                else {
                    entityToAdd = new RareShieldCollect(getWindowWidth(), randomY);
                }
            }
            else {
                if (chance < 50){
                    entityToAdd = new ChristmasAvoid(getWindowWidth(), randomY);
                }
                else if (chance < 80){
                    entityToAdd = new ChristmasCollect(getWindowWidth(), randomY);
                }
                else if (chance < 90){
                    entityToAdd = new ChristmasRareAvoid(getWindowWidth(), randomY);
                }
                else if (chance < 95){
                    entityToAdd = new ChristmasRareCollect(getWindowWidth(), randomY);
                }
                else {
                    entityToAdd = new RareShieldCollect(getWindowWidth(), randomY);
                }
            }

            if (getAllCollisions(entityToAdd).isEmpty()){
                displayList.add(entityToAdd);
                entities.add(entityToAdd);
            }
        }
    }

    @Override
    protected void collidedWithPlayer(Consumable collidedWith){
        Entity entity = ((Entity) collidedWith);
        if (!entity.isFlaggedForGC()) {
            if (collidedWith instanceof RareShieldCollect){
                shieldedPlayer.setShield((RareShieldCollect)collidedWith);
                this.score += collidedWith.getScoreModifier();
            }
            else{
                // Always modify HP (shield logic is now in modifyHP)
                player.modifyHP(collidedWith.getHPModifier());
                this.score += collidedWith.getScoreModifier();
            }
            entity.setGCFlag(true);
        }
    }
}
