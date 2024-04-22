/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *   http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.dhbw.tinf22b6.world.tiled;

import com.badlogic.gdx.utils.Array;

/**
 * A node for a {@link FlatTiledGraph}.
 *
 * @author davebaol
 */
public class FlatTiledNode extends TiledNode<FlatTiledNode> {
    private final int sizeY;

    public FlatTiledNode(int x, int y, int type, int connectionCapacity, int sizeY) {
        super(x, y, type, new Array<>(connectionCapacity));
        this.sizeY = sizeY;
    }

    @Override
    public int getIndex() {
        return x * sizeY + y;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + type;
    }
}
