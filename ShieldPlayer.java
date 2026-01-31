public class ShieldPlayer extends Player {
    private RareShieldCollect shield;
    public static final String CHRISTMAS_PLAYER_IMAGE = "assets/elf.gif";

    public ShieldPlayer(int x, int y) {
        super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT, CHRISTMAS_PLAYER_IMAGE);
        this.shield = null;
    }

    public void setShield(RareShieldCollect shield) {
        this.shield = shield;
        System.out.println("Shield activated with strength: " + shield.getShieldStrength());
    }

    public boolean hasActiveShield() {
        return shield != null && shield.getShieldStrength() > 0;
    }

    @Override
    public boolean modifyHP(int delta) {
        // Don't modify HP for negative deltas (damage) if shield is active
        if (delta < 0 && hasActiveShield()) {
            shield.decreaseShieldStrength();
            System.out.println("Shield blocking avoid! Strength left: " + shield.getShieldStrength());
            if (shield.getShieldStrength() <= 0) {
                System.out.println("Shield depleted!");
                shield = null;
            }
            return true; // Player is still alive
        }
        return super.modifyHP(delta);
    }
}
