public class AiFuncs {
    // factory function for making instances of Random.
    // it can be overridden for debug/test purposes
    protected Random makeRandom() {
        return new Random();
    }
    
    public boolean hasBestNeighCell(
            Field field,
            int x, int y, int[] res,
            Random rnd) {
        int neighCount = 4;
        int sideSize = field.getSideSize();
        
        // we want to check every neighbor but in random order
        int checkOrder = rnd.nextInt(neighCount);
        
        // in this case: we're tring to find "The Best" neighbor
        
        for (int i = 0; i < neighCount; ++i) {
            // neighbor coordinates
            int nx = x;
            int ny = y;
            
            // opposite coordinates
            int ox = x;
            int oy = y;
            
            switch (checkOrder) {
                case 0:
                    // look at right
                    ++nx;
                    --ox;
                    break;
                case 1:
                    // look at left
                    --nx;
                    ++ox;
                    break;
                case 2:
                    // look at down
                    ++ny;
                    --oy;
                    break;
                case 3:
                    // look at up
                    --ny;
                    ++oy;
                    break;
            }
            
            if (nx >= 0 && nx < sideSize && ny >= 0 && ny < sideSize
                    && ox >= 0 && ox < sideSize && oy >= 0 && oy < sideSize) {
                FieldCellStateConsts nFieldCellState = field.getFieldCellState(nx, ny);
                FieldCellStateConsts oFieldCellState = field.getFieldCellState(ox, oy);
                
                if (!nFieldCellState.hasShot()
                        && oFieldCellState == FieldCellStateConsts.HIT) {
                    res[0] = nx;
                    res[1] = ny;
                    
                    return true;
                }
            }
            
            ++checkOrder;
            
            while (checkOrder >= neighCount) {
                checkOrder -= neighCount;
            }
        }
        
        return false;
    }
    
    public boolean hasGoodNeighCell(
            Field field,
            int x, int y, int[] res,
            Random rnd) {
        int neighCount = 4;
        int sideSize = field.getSideSize();
        
        // we want to check every neighbor but in random order
        int checkOrder = rnd.nextInt(neighCount);
        
        // in this case: we're tring to find "Just Good" neighbor. more simple case
        
        for (int i = 0; i < neighCount; ++i) {
            // neighbor coordinates
            int nx = x;
            int ny = y;
            
            // we don't look at opposite coordinates in this case
            
            switch (checkOrder) {
                case 0:
                    // look at right
                    ++nx;
                    break;
                case 1:
                    // look at left
                    --nx;
                    break;
                case 2:
                    // look at down
                    ++ny;
                    break;
                case 3:
                    // look at up
                    --ny;
                    break;
            }
            
            if (nx >= 0 && nx < sideSize && ny >= 0 && ny < sideSize) {
                FieldCellStateConsts nFieldCellState = field.getFieldCellState(nx, ny);
                
                if (!nFieldCellState.hasShot()) {
                    res[0] = nx;
                    res[1] = ny;
                    
                    return true;
                }
            }
            
            ++checkOrder;
            
            while (checkOrder >= neighCount) {
                checkOrder -= neighCount;
            }
        }
        
        return false;
    }
    
    // to guess where shoot at
    public int[] guess(Field field) {
        Random rnd = makeRandom();
        
        int sideSize = field.getSideSize();
        
        int hitCount = 0;
        int unknCount = 0;
        
        // collecting statistic
        
        for (int x = 0; x < sideSize; ++x) {
            for (int y = 0; y < sideSize; ++y) {
                FieldCellStateConsts fieldCellState = field.getFieldCellState(x, y);
                
                if (fieldCellState == FieldCellStateConsts.HIT) {
                    ++hitCount;
                }
                
                if (!fieldCellState.hasShot()) {
                    ++unknCount;
                }
            }
        }
        
        // finding not sunken ships
        
        if (hitCount > 0) {
            int hitPointer = rnd.nextInt(hitCount);
            int[] res = new int[2];
            
            // the first pass for "The Best" case
            
            for (int x = 0; x < sideSize; ++x) {
                for (int y = 0; y < sideSize; ++y) {
                    FieldCellStateConsts fieldCellState = field.getFieldCellState(x, y);
                    
                    if (fieldCellState != FieldCellStateConsts.HIT) {
                        continue;
                    }
                    
                    if (hitPointer > 0) {
                        --hitPointer;
                        
                        continue;
                    }
                    
                    if (hasBestNeighCell(field, x, y, res, rnd)) {
                        return res;
                    }
                }
            }
            
            // the second pass for "The Best" case
            
            for (int x = 0; x < sideSize; ++x) {
                for (int y = 0; y < sideSize; ++y) {
                    FieldCellStateConsts fieldCellState = field.getFieldCellState(x, y);
                    
                    if (fieldCellState != FieldCellStateConsts.HIT) {
                        continue;
                    }
                    
                    if (hasBestNeighCell(field, x, y, res, rnd)) {
                        return res;
                    }
                }
            }
            
            // the first pass for "Just Good" case
            
            for (int x = 0; x < sideSize; ++x) {
                for (int y = 0; y < sideSize; ++y) {
                    FieldCellStateConsts fieldCellState = field.getFieldCellState(x, y);
                    
                    if (fieldCellState != FieldCellStateConsts.HIT) {
                        continue;
                    }
                    
                    if (hitPointer > 0) {
                        --hitPointer;
                        
                        continue;
                    }
                    
                    if (hasGoodNeighCell(field, x, y, res, rnd)) {
                        return res;
                    }
                }
            }
            
            // the second pass for "Just Good" case
            
            for (int x = 0; x < sideSize; ++x) {
                for (int y = 0; y < sideSize; ++y) {
                    FieldCellStateConsts fieldCellState = field.getFieldCellState(x, y);
                    
                    if (fieldCellState != FieldCellStateConsts.HIT) {
                        continue;
                    }
                    
                    if (hasGoodNeighCell(field, x, y, res, rnd)) {
                        return res;
                    }
                }
            }
        }
        
        // plan B. selecting just random one from unknown cells
        
        int unknPointer = rnd.nextInt(unknCount);
        
        for (int x = 0; x < sideSize; ++x) {
            for (int y = 0; y < sideSize; ++y) {
                FieldCellStateConsts fieldCellState = field.getFieldCellState(x, y);
                
                if (fieldCellState.hasShot()) {
                    continue;
                }
                
                if (unknPointer <= 0) {
                    return new int[]{x, y};
                }
                
                --unknPointer;
            }
        }
        
        throw new IllegalStateException();
    }
}
