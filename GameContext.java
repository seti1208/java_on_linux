public class GameContext {
    protected ShipStructFactory shipStructFactory = new ShipStructFactory();
    protected ShipLayoutFuncs shipLayoutFuncs = new ShipLayoutFuncs();
    
    // current game phase
    protected GamePhasesConsts gamePhase;
    // number of current turn
    protected int turnNum;
    
    // your ships
    protected ArrayList<ShipStruct> yourShips;
    // your field (the field with your ships)
    protected Field yourField;
    
    // enemy's ships
    protected ArrayList<ShipStruct> enemysShips;
    // enemy's field (the field with enemy's ships)
    protected Field enemysField;
    
    // factory function for making instances of Field.
    // it can be overridden for debug/test purposes
    protected Field makeField() {
        Field field = new Field();
        
        field.init();
        
        return field;
    }
    
    // factory function for making instances of Random.
    // it can be overridden for debug/test purposes
    protected Random makeRandom() {
        return new Random();
    }
    
    public ShipLayoutFuncs getShipLayoutFuncs() {
        return shipLayoutFuncs;
    }
    
    public void init() {
        newGame();
    }
    
    public GamePhasesConsts getGamePhase() {
        return gamePhase;
    }
    
    public int getTurnNum() {
        return turnNum;
    }
    
    public ArrayList<ShipStruct> getYourShips() {
        return yourShips;
    }
    
    public Field getYourField() {
        return yourField;
    }
    
    public ArrayList<ShipStruct> getEnemysShips() {
        return enemysShips;
    }
    
    public Field getEnemysField() {
        return enemysField;
    }
    
    public void randomizeYourShips() {
        ArrayList<ShipStruct> ships = new ArrayList<>();
        
        shipLayoutFuncs.makeRandomShipLayout(ships, yourField, shipStructFactory);
        
        yourShips = ships;
    }
    
    public void randomizeEnemysShips() {
        ArrayList<ShipStruct> ships = new ArrayList<>();
        
        shipLayoutFuncs.makeRandomShipLayout(ships, enemysField, shipStructFactory);
        
        enemysShips = ships;
    }
    
    public void newGame() {
        gamePhase = GamePhasesConsts.LOBBY;
        turnNum = 0;
        
        yourShips = new ArrayList<>();
        yourField = makeField();
        
        enemysShips = new ArrayList<>();
        enemysField = makeField();
    }
    
    public void beginGame() {
        Random rnd = makeRandom();
        
        if (rnd.nextBoolean()) {
            gamePhase = GamePhasesConsts.YOUR_TURN;
        } else {
            gamePhase = GamePhasesConsts.ENEMYS_TURN;
        }
        ++turnNum;
    }
    
    public void switchTurn() {
        switch (gamePhase) {
            case YOUR_TURN:
                gamePhase = GamePhasesConsts.ENEMYS_TURN;
                break;
            case ENEMYS_TURN:
                gamePhase = GamePhasesConsts.YOUR_TURN;
                break;
        }
    }
    
    public void endGame() {
        switch (gamePhase) {
            case YOUR_TURN:
                gamePhase = GamePhasesConsts.YOU_WON;
                break;
            case ENEMYS_TURN:
                gamePhase = GamePhasesConsts.YOU_LOSE;
                break;
        }
    }
    
    // to make shot.
    // returning true means hit
    public boolean shoot(
            ArrayList<ShipStruct> ships,
            Field field,
            int x,
            int y
    ) {
        int sideSize = field.getSideSize();
        FieldCellStateConsts fieldCellState = field.getFieldCellState(x, y);
        
        if (fieldCellState.hasShot()) {
            // you can not hit twice at the same cell
            
            switchTurn();
            ++turnNum;
            
            return false;
        }
        
        for (int i = 0; i < ships.size(); ++i) {
            ShipStruct ship = ships.get(i);
            
            if (shipLayoutFuncs.isShipHere(ship, x, y)) {
                field.setFieldCellState(x, y, FieldCellStateConsts.HIT);
                
                ++ship.hits;
                
                if (ship.hits >= ship.decks) {
                    ship.isSunken = true;
                    
                    // marking rectangle around the sunken ship
                    
                    int missRectX = ship.x - 1;
                    int missRectY = ship.y - 1;
                    int missRectTailX = shipLayoutFuncs.getShipTailX(ship) + 1;
                    int missRectTailY = shipLayoutFuncs.getShipTailY(ship) + 1;
                    
                    for (int mx = missRectX; mx <= missRectTailX; ++mx) {
                        if (mx < 0 || mx >= sideSize) {
                            continue;
                        }
                        
                        for (int my = missRectY; my <= missRectTailY; ++my) {
                            if (my < 0 || my >= sideSize) {
                                continue;
                            }
                            
                            FieldCellStateConsts missFieldCellState = field.getFieldCellState(mx, my);
                            
                            if (!missFieldCellState.hasShot()) {
                                field.setFieldCellState(
                                        mx,
                                        my,
                                        FieldCellStateConsts.MISSED
                                );
                            }
                        }
                    }
                    
                    // checking maybe somebody has won
                    
                    int aliveCount = 0;
                    
                    for (int j = 0; j < ships.size(); ++j) {
                        ShipStruct otherShip = ships.get(j);
                        
                        if (!otherShip.isSunken) {
                            ++aliveCount;
                        }
                    }
                    
                    if (aliveCount == 0) {
                        // yes! somebody has won. changing game phase
                        
                        endGame();
                    } else {
                        ++turnNum;
                    }
                } else {
                    ++turnNum;
                }
                
                return true;
            }
        }
        
        field.setFieldCellState(x, y, FieldCellStateConsts.MISSED);
        
        switchTurn();
        ++turnNum;
        
        return false;
    }
}
