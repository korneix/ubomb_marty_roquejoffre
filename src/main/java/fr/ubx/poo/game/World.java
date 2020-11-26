/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.game;

import fr.ubx.poo.model.decor.Decor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class World {
    private final Map<Position, Decor> grid;
    private final WorldEntity[][] raw;
    public final Dimension dimension;
    public boolean smthHasChanged;

    public World(WorldEntity[][] raw) {
        this.raw = raw;
        dimension = new Dimension(raw.length, raw[0].length);
        grid = WorldBuilder.build(raw, dimension);
        smthHasChanged = true;
    }

    public Position findPlayer() throws PositionNotFoundException {
        for (int x = 0; x < dimension.width; x++) {
            for (int y = 0; y < dimension.height; y++) {
                if (raw[y][x] == WorldEntity.Player) {
                    return new Position(x, y);
                }
            }
        }
        throw new PositionNotFoundException("Player");
    }
//MONSTERSREQ
    public List<Position> findMonsters() throws PositionNotFoundException {
        List<Position> monstersList = new ArrayList<>();
        for(int x=0; x < dimension.width; x++){
            for(int y=0; y < dimension.height; y++){
                if(raw[y][x] == WorldEntity.Monster){
                    monstersList.add( new Position(x,y) );
                }
            }
        }
        return monstersList;
    }
//END

    public Decor get(Position position) {
        return grid.get(position);
    }

    public void set(Position position, Decor decor) {
        grid.put(position, decor);
    }

    public void clear(Position position) {
        grid.remove(position);
    }

    public void forEach(BiConsumer<Position, Decor> fn) {
        grid.forEach(fn);
    }

    public Collection<Decor> values() {
        return grid.values();
    }

    public boolean isInside(Position position) {

        return position.inside(dimension); // to update
    }

    public boolean getChanges(){ return smthHasChanged; }
    public void setChanges(boolean b){ this.smthHasChanged = b; };

    public boolean isEmpty(Position position) {
        return grid.get(position) == null;
    }
}
