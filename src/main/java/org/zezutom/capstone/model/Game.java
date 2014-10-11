package org.zezutom.capstone.model;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by tom on 05/10/2014.
 */
public class Game implements Serializable {

    private Collection<GameSet> gameSets;

    private boolean finished;

}
