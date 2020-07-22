package Core.Enums;

public enum BotmanCommands {
    chatplayercolor(2),
    chunkreset(0),
    getskills(2),
    levelprefix(0),
    listplayerbed(2),
    listfriends(2),
    muteplayer(2),
    playerinfo(2),
    resetplayer(1),
    resetregions(0),
    spawnhorde(1),
    givexp(1),
    teleportplayerhome(2),
    give(2),
    antichext(0);
    int power;
    BotmanCommands(int power){
        this.power=power;
    }

    public int getPower() {
        return power;
    }
}
