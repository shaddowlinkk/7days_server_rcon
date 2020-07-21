package Core.Enums;

public enum BotmanCommands {
    chatplayercolor(2),
    chunkreset(1),
    getskills(2),
    help(2),
    levelpredix(0),
    listplayerbed(1),
    listfriends(1),
    muteplayer(1),
    playerinfo(2),
    remove(1),
    resetplayer(1),
    resetregions(0),
    spawnhorde(1),
    teleportplayerhome(1),

    antichext(0);
    int power;
    BotmanCommands(int power){
        this.power=power;
    }

    public int getPower() {
        return power;
    }
}
