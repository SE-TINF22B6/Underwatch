package de.dhbw.tinf22b6.components;

import de.dhbw.tinf22b6.gameobject.GameObject;

import java.util.ArrayList;

import com.badlogic.ashley.core.Component;

/**
 * InteractionComponent
 */
public class InteractionComponent implements Component{
    public ArrayList<GameObject> interactionTargets;
}
