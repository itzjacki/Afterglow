package com.itzjacki.afterglow.models;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Note {
    public void draw(ShapeRenderer shape);
    public boolean update(float dt);
    public int getState();
}
