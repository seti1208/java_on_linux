public class Cli {
    // how to draw board cells
    protected char emptyOrUnknownCellChar = '.';
    protected char shipCellChar = '@';
    protected char hitCellChar = 'X';
    protected char missedCellChar = '~';
    
    // action delay for human comfortability
    protected long actionDelayMillis = 1000;
    
    protected Console console;
    protected PrintStream out;
    protected GameContext gameContext;
    protected AiFuncs aiFuncs;
    
    protected Pattern coordPattern
            = Pattern.compile("([A-Za-z])\\s*(\\d+)");
    
    public void setConsole(Console console) {
        this.console = console;
    }
    
    public void setOut(PrintStream out) {
        this.out = out;
    }
    
    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }
    
    public void setAiFuncs(AiFuncs aiFuncs) {
        this.aiFuncs = aiFuncs;
    }
    
    public void init() {
        out.println("####### Battleship CLI Game #######\n");
    }
    
    public String repeatChar(char c, int count) {
        String str = "";
        
        for (int i = 0; i < count; ++i) {
            str += c;
        }
        
        return str;
    }
    
    public String xLabelToStr(int x) {
        char c = 'A';
        
        c += x;
        
        return String.valueOf(c);
    }
    
    public String yLabelToStr(int y) {
        y += 1;
        
        return String.valueOf(y / 10) + String.valueOf(y % 10);
    }
    
    public int coordToX(Field field, Matcher matcher) {
        int sideSize = field.getSideSize();
        
        if (!matcher.matches()) {
            return -1;
        }
        
        char c = matcher.group(1).toUpperCase().charAt(0);
        
        int x = c - 'A';
        
        if (x < 0 || x >= sideSize) {
            return -1;
        }
        
        return x;
    }
    
    public int coordToY(Field field, Matcher matcher) {
        int sideSize = field.getSideSize();
        
        if (!matcher.matches()) {
            return -1;
        }
        
        int y = Integer.valueOf(matcher.group(2)) - 1;
        
        if (y < 0 || y >= sideSize) {
            return -1;
        }
        
        return y;
    }
    
    public char getCellChar(
            ArrayList<ShipStruct> ships,
            Field field,
            int x,
            int y,
            boolean isUncovered) {
        FieldCellStateConsts fieldCellState = field.getFieldCellState(x, y);
        
        switch (fieldCellState) {
            case HIT:
                return hitCellChar;
            case MISSED:
                return missedCellChar;
        }
        
        if (!isUncovered) {
            return emptyOrUnknownCellChar;
        }
        
        ShipLayoutFuncs shipLayoutFuncs = gameContext.getShipLayoutFuncs();
        
        for (int i = 0; i < ships.size(); ++i) {
            ShipStruct ship = ships.get(i);
            
            if (shipLayoutFuncs.isShipHere(ship, x, y)) {
                return shipCellChar;
            }
        }
        
        return emptyOrUnknownCellChar;
    }
    
    public String getFleetStatStr(ArrayList<ShipStruct> ships) {
        int fourDeckers = 0;
        int threeDeckers = 0;
        int twoDeckers = 0;
        int oneDeckers = 0;
        
        for (int i = 0; i < ships.size(); ++i) {
            ShipStruct ship = ships.get(i);
            
            if (ship.isSunken) {
                continue;
            }
            
            switch (ship.decks) {
                case 4:
                    ++fourDeckers;
                    break;
                case 3:
                    ++threeDeckers;
                    break;
                case 2:
                    ++twoDeckers;
                    break;
                case 1:
                    ++oneDeckers;
                    break;
            }
        }
        
        return fourDeckers + " four-deckers, "
                + threeDeckers + " three-deckers, "
                + twoDeckers + " two-deckers, "
                + oneDeckers + " one-deckers";
    }
    
    public void drawBoard(boolean isUncovered) {
        ArrayList<ShipStruct> yourShips = gameContext.getYourShips();
        Field yourField = gameContext.getYourField();
        ArrayList<ShipStruct> enemysShips = gameContext.getEnemysShips();
        Field enemysField = gameContext.getEnemysField();
        
        String line;
        
        // drowing board labels and decoration
        
        out.println(repeatChar(' ', 7)
                + "Your Field"
                + repeatChar(' ', yourField.getSideSize() * 2 - 3)
                + "Enemy's Field\n"
        );
        
        line = repeatChar(' ', 4); // spaces from ^ to A
        
        for (int i = 0; i < yourField.getSideSize(); ++i) {
            if (i != 0) {
                line += ' ';
            }
            
            line += xLabelToStr(i);
        }
        
        line += repeatChar(' ', 8); // from J to A
        
        for (int i = 0; i < enemysField.getSideSize(); ++i) {
            if (i != 0) {
                line += ' ';
            }
            
            line += xLabelToStr(i);
        }
        
        out.println(line);
        
        // drowing board row by row
        
        int sideSize = Math.max(yourField.getSideSize(), enemysField.getSideSize());
        
        for (int i = 0; i < sideSize; ++i) {
            // your field
            
            line = yLabelToStr(i) + " |";
            
            for (int j = 0; j < yourField.getSideSize(); ++j) {
                if (j != 0) {
                    line += ' ';
                }
                
                if (i < yourField.getSideSize()) {
                    line += getCellChar(yourShips, yourField, j, i, true);
                } else {
                    line += ' ';
                }
            }
            
            // enemy's field
            
            line += '|' + repeatChar(' ', 3) + yLabelToStr(i) + " |";
            
            for (int j = 0; j < enemysField.getSideSize(); ++j) {
                if (j != 0) {
                    line += ' ';
                }
                
                if (i < enemysField.getSideSize()) {
                    line += getCellChar(enemysShips, enemysField, j, i, isUncovered);
                } else {
                    line += ' ';
                }
            }
            
            line += '|';
            
            out.println(line);
        }
        
        // fleets status
        
        out.println(
                "\nYour Fleet: "
                + getFleetStatStr(yourShips)
                + "\n"
        );
        out.println(
                "Enemy's Fleet: "
                + getFleetStatStr(enemysShips)
                + "\n"
        );
    }
    
    public void actionDelay() {
        try {
            Thread.sleep(actionDelayMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void preLobbyHandler(GamePhasesConsts prevGamePhase) {
        out.println(
                "\n" + repeatChar('=', 60) + 
                "\n\n### You Are In Lobby ###"
        );
        
        gameContext.randomizeYourShips();
        gameContext.randomizeEnemysShips();
    }
    
    public void preGameHandler(GamePhasesConsts prevGamePhase) {
        out.println(
                "\n" + repeatChar('=', 60) + 
                "\n\n### The Game Has Started! ###"
        );
    }
    
    public void preFinalHandler(GamePhasesConsts prevGamePhase) {
        out.println(
                "\n" + repeatChar('=', 60) + 
                "\n\n### The Game Is Over ###"
        );
    }
     
    public boolean doLobbyIteration() {
        out.println(
                "\n" + repeatChar('-', 60)
                + "\n\n[Lobby]\n"
        );
        
        drawBoard(false);
        
        out.println("available commands: begin, mov, new, quit\n");
        
        out.print("lobby command> ");
        String input = console.readLine();
        
        switch (input) {
            case "begin":
                gameContext.beginGame();
                break;
//            case "mov":
//                doLobbyMov();
//                break;
            case "new":
                gameContext.randomizeYourShips();
                break;
            case "quit":
                return false;
            default:
                out.println("\nwrong command\n");
                
                actionDelay();
        }
        
        return true;
    }
    
    public boolean doGameIteration(boolean isYourTurn) {
        if (isYourTurn) {
            out.println(
                    "\n" + repeatChar('-', 60)
                    + "\n\n[Game. Turn #" + gameContext.getTurnNum() + ". Your Turn]\n"
            );
        } else {
            out.println(
                    "\n" + repeatChar('-', 60)
                    + "\n\n[Game. Turn #" + gameContext.getTurnNum() + ". Enemy's Turn]\n"
            );
        }
        
        drawBoard(false);
        
        ArrayList<ShipStruct> ships;
        Field field;
        int x;
        int y;
        
        if (isYourTurn) {
            ships = gameContext.getEnemysShips();
            field = gameContext.getEnemysField();
            
            out.println("available commands: <type-coordinates-here>, new, quit\n");
            
            out.print("game command> ");
            String input = console.readLine();
            
            switch (input) {
                case "new":
                    gameContext.newGame();
                    return true;
                case "quit":
                    return false;
            }
            
            Matcher coordMatcher = coordPattern.matcher(input);
            x = coordToX(field, coordMatcher);
            y = coordToY(field, coordMatcher);
            
            if (x == -1 || y == -1) {
                out.println("\nwrong coordinates format or command\n");
                
                actionDelay();
                
                return true;
            }
        } else {
            ships = gameContext.getYourShips();
            field = gameContext.getYourField();
            
            int[] pair = aiFuncs.guess(field);
            
            x = pair[0];
            y = pair[1];
            
            out.println("aim: " + xLabelToStr(x) + yLabelToStr(y));
        }
        
        boolean isHit = gameContext.shoot(ships, field, x, y);
        
        if (isHit) {
            out.println("\nhit!\n");
            
            if (!isYourTurn) {
                actionDelay();
            }
        } else {
            out.println("\nmiss!\n");
            
            actionDelay();
        }
        
        return true;
    }
    
    public boolean runFinal(boolean isYouWon) {
        if (isYouWon) {
            out.println(
                    "\n" + repeatChar('-', 60)
                    + "\n\n[Game Over. " + gameContext.getTurnNum() + " Turns. You Won]\n"
            );
        } else {
            out.println(
                    "\n" + repeatChar('-', 60)
                    + "\n\n[Game Over. " + gameContext.getTurnNum() + " Turns. You Lose]\n"
            );
        }
        
        drawBoard(true);
        
        out.println("available commands: new, quit\n");
        
        out.print("final command> ");
        String input = console.readLine();
        
        switch (input) {
            case "new":
                gameContext.newGame();
                return true;
            case "quit":
                return false;
            default:
                out.println("\nwrong command\n");
                
                actionDelay();
        }
        
        return true;
    }
    
    public GamePhasesConsts run(GamePhasesConsts savedGamePhase) {
        GamePhasesConsts prevGamePhase = savedGamePhase;
        boolean doContinue;
        
        do {
            GamePhasesConsts gamePhase = gameContext.getGamePhase();
            
            if (gamePhase == GamePhasesConsts.LOBBY) {
                if (prevGamePhase != GamePhasesConsts.LOBBY) {
                    preLobbyHandler(prevGamePhase);
                }
                
                doContinue = doLobbyIteration();
            } else if (gamePhase.isGame()) {
                if (!prevGamePhase.isGame()) {
                    preGameHandler(prevGamePhase);
                }
                
                boolean isYourTurn = gamePhase == GamePhasesConsts.YOUR_TURN;
                
                doContinue = doGameIteration(isYourTurn);
            } else if (gamePhase.isDone()) {
                if (!prevGamePhase.isDone()) {
                    preFinalHandler(prevGamePhase);
                }
                
                boolean isYouWon = gamePhase == GamePhasesConsts.YOU_WON;
                
                doContinue = runFinal(isYouWon);
            } else {
                doContinue = false;
            }
            
            prevGamePhase = gamePhase;
        } while (doContinue);
        
        return prevGamePhase;
    }
}
