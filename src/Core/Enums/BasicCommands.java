package Core.Enums;

public enum BasicCommands {
    ban(2),
    kick(2),
    say(2),
    help(2),
    lp(2),
    is(2),
    tele(2),
    teleportplayer(2),
    kill(0),
    traderarea(2),
    admin(0);

    int power;
    BasicCommands(int power){
        this.power=power;
    }

    public int getPower() {
        return power;
    }
}
