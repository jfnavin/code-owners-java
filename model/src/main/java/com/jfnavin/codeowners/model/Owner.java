package com.jfnavin.codeowners.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Information about code ownership
 *
 * @param ids The id(s) of the owner(s)
 *            These may be anything that makes sense for your environment - emails, team IDs, group IDs etc.
 */
public record Owner(String[] ids) {

    @Override
    public String toString() {
        return "Owner{" +
                "ids=" + Arrays.toString(ids) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final var owner = (Owner) o;
        return Objects.deepEquals(ids, owner.ids);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(ids);
    }
}
