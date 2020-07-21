package Core.Enums;

public enum BasicCommands {
    ban(0),
    admin(0);
    int power;
    BasicCommands(int power){
        this.power=power;
    }

    public int getPower() {
        return power;
    }
}
