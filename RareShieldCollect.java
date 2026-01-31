public class RareShieldCollect extends RareCollect{
    private int shieldStrength;

    private static final int SHIELD_COLLECT_HP_MODIFIER = 0;
    private static final int SHIELD_COLLECT_SCORE_MODIFIER = 20;
    private static final String SHIELD_COLLECT_IMAGE = "assets/giphyshield.gif";

    public RareShieldCollect(int x, int y){
        super(x, y, RCOLLECT_WIDTH, RCOLLECT_HEIGHT, SHIELD_COLLECT_IMAGE);
        this.shieldStrength = 5; // Shield can protect against up to 5 avoids
    }

    @Override
    public int getHPModifier() {
        return SHIELD_COLLECT_HP_MODIFIER;
    }

    @Override
    public int getScoreModifier() {
        return SHIELD_COLLECT_SCORE_MODIFIER;
    }


    public int getShieldStrength(){
        return shieldStrength;
    }

    public void decreaseShieldStrength(){
        if (shieldStrength > 0) {
            shieldStrength--;
        }
    }
}
