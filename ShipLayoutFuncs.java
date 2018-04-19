public class ShipLayoutFuncs {
    // attempt count before giving up finding a suitable place for a ship
    protected int findAttempts = 100;
    
    // parameters for making a new random ship layout
    protected int makeFourDeckers = 1;
    protected int makeThreeDeckers = 2;
    protected int makeTwoDeckers = 3;
    protected int makeOneDeckers = 4;
    
    // factory function for making instances of Random.
    // it can be overridden for debug/test purposes
    protected Random makeRandom() {
        return new Random();
    }
    
    public int getShipTailX(ShipStruct ship) {
        if (ship.rot == ShipRotatedConsts.HORIZONTAL) {
            return ship.x + ship.decks - 1;
        } else {
            return ship.x;
        }
    }
    
    public int getShipTailY(ShipStruct ship) {
        if (ship.rot == ShipRotatedConsts.VERTICAL) {
            return ship.y + ship.decks - 1;
        } else {
            return ship.y;
        }
    }
    
    // is the ship here?
    public boolean isShipHere(ShipStruct ship, int x, int y) {
        return x >= ship.x && x <= getShipTailX(ship)
                && y >= ship.y && y <= getShipTailY(ship);
    }
    
    // to check can we put a new ship in the field among other ships
    public boolean canShipPutHere(
            ArrayList<ShipStruct> ships,
            Field field,
            ShipStruct nextShip) {
        int sideSize = field.getSideSize();
        int nextShipTailX = getShipTailX(nextShip);
        int nextShipTailY = getShipTailY(nextShip);
        
        // checking for field's borders
        
        if (nextShip.x < 0 || nextShip.y < 0
                || nextShipTailX >= sideSize || nextShipTailY >= sideSize) {
            // border check hasn't passed
            
            return false;
        }
        
        // checking for overlapping with every other ship
        
        int rectX = nextShip.x - 1;
        int rectY = nextShip.y - 1;
        int rectTailX = nextShipTailX + 1;
        int rectTailY = nextShipTailY + 1;
        
        for (int i = 0; i < ships.size(); ++i) {
            ShipStruct otherShip = ships.get(i);
            
            if (otherShip == nextShip) {
                continue;
            }
            
            int otherShipTailX = getShipTailX(otherShip);
            int otherShipTailY = getShipTailY(otherShip);
            
            if (!(rectX > otherShipTailX
                    || otherShip.x > rectTailX
                    || rectY > otherShipTailY
                    || otherShip.y > rectTailY)) {
                // there is overlapping
                
                return false;
            }
        }
        
        // all checks have successfully passed!
        
        return true;
    }
    
    // to find a random suitable place for a ship among other ships in the field.
    // function modifies given ship structure instance coordinates in place
    public boolean findPlaceForShip(
            ArrayList<ShipStruct> ships,
            Field field,
            ShipStruct nextShip) {
        Random rnd = makeRandom();
        int sideSize = field.getSideSize();
        
        for (int i = 0; i < findAttempts; ++i) {
            // generation random coordinates
            
            nextShip.x = rnd.nextInt(sideSize);
            nextShip.y = rnd.nextInt(sideSize);
            
            if (rnd.nextBoolean()) {
                nextShip.rot = ShipRotatedConsts.HORIZONTAL;
            } else {
                nextShip.rot = ShipRotatedConsts.VERTICAL;
            }
            
            // checking for suitability
            
            if (canShipPutHere(ships, field, nextShip)) {
                // we've found!
                
                return true;
            }
        }
        
        // we haven't anything found a suitable one
        return false;
    }
    
    // to make a new random ship layout.
    // function modifies given ship list in place
    public void makeRandomShipLayout(
            ArrayList<ShipStruct> ships,
            Field field,
            ShipStructFactory shipStructFactory
    ) {
        for (;;) {
            // firstly, we're checking what the situation is
            
            int fourDeckers = 0;
            int threeDeckers = 0;
            int twoDeckers = 0;
            int oneDeckers = 0;
            
            for (int i = 0; i < ships.size(); ++i) {
                ShipStruct ship = ships.get(i);
            
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
            
            // is ship count already enough?
            
            ShipStruct nextShip;
            
            if (fourDeckers < makeFourDeckers) {
                nextShip = shipStructFactory.makeShipStruct();
                
                nextShip.decks = 4;
            } else if (threeDeckers < makeThreeDeckers) {
                nextShip = shipStructFactory.makeShipStruct();
                
                nextShip.decks = 3;
            } else if (twoDeckers < makeTwoDeckers) {
                nextShip = shipStructFactory.makeShipStruct();
                
                nextShip.decks = 2;
            } else if (oneDeckers < makeOneDeckers) {
                nextShip = shipStructFactory.makeShipStruct();
                
                nextShip.decks = 1;
            } else {
                // everything is enough. doing exit
                
                break;
            }
            
            // the ship count isn't enough.
            // we need to add a new ship
            
            boolean isFound = findPlaceForShip(ships, field, nextShip);
            
            if (isFound) {
                // a suitable place for the new ship has successfully found!
                // adding a new ship to other ships and retrying iteration
                
                ships.add(nextShip);
            } else {
                // finding a suitable place has finished with failure.
                // we should remove some other ship before retrying iteration
                
                if (!ships.isEmpty()) {
                    ships.remove(ships.size() - 1);
                }
            }
        }
    }
}
