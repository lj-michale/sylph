/*
 * Copyright (C) 2018 The Sylph Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.harbby.sylph.parser.tree;

import com.github.harbby.gadtry.collection.ImmutableList;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.github.harbby.gadtry.base.MoreObjects.checkState;

public class Identifier
        extends Expression
{
    private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z_]([a-zA-Z0-9_:@])*");

    private final String value;
    private final boolean delimited;

    public Identifier(String value, boolean delimited)
    {
        this(null, value, delimited);
    }

    public Identifier(String value)
    {
        this(null, value, !NAME_PATTERN.matcher(value).matches());
    }

    public Identifier(NodeLocation location, String value, boolean delimited)
    {
        super(location);
        this.value = value;
        this.delimited = delimited;

        checkState(delimited || NAME_PATTERN.matcher(value).matches(), "value contains illegal characters: " + value);
    }

    public String getValue()
    {
        return value;
    }

    public boolean isDelimited()
    {
        return delimited;
    }

    @Override
    public List<Node> getChildren()
    {
        return ImmutableList.of();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Identifier that = (Identifier) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode()
    {
        return value.hashCode();
    }

    @Override
    public String toString()
    {
        if (!this.isDelimited()) {
            return this.getValue();
        }
        else {
            return '"' + this.getValue().replace("\"", "\"\"") + '"';
        }
    }
}
