/*
 * Copyright (C) 2014 GG-Net GmbH - Oliver GÃ¼nther
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package beta.server.entity;

import java.io.Serializable;
import java.util.Optional;

/**
 * Specifies a way of communication.
 * <p>
 * @author oliver.guenther
 */
public class Communication implements Serializable {

    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

    /**
     * ISO5008 + Microsoft Phone Pattern, see
     * {@linkplain https://de.wikipedia.org/wiki/Rufnummer#Schreibweisen}.
     */
    public static final String PHONE_PATTERN
            = "^((\\+|00)[1-9]\\d+) ([1-9]\\d+) ([1-9]\\d+)(\\-\\d+){0,1}$" // ISO: +49 123 123456-78 o. 0049 123 123456-78
            + "|^(\\+[1-9]\\d+) \\(([1-9]\\d+)\\) ([1-9]\\d+)(\\-\\d+){0,1}$" // MS: +49 (123) 132456-78
            + "|^(0[1-9]\\d+) ([1-9]\\d+)(\\-\\d+){0,1}$"; // ISO: 040 123456

    /**
     * Represents some of the common ways to communicate nowadays.
     */
    public enum Type {

        PHONE(PHONE_PATTERN), MOBILE(PHONE_PATTERN), FAX(PHONE_PATTERN),
        ICQ, SKYPE, EMAIL(EMAIL_PATTERN), FACEBOOK;

        private final String validPattern;

        private Type() {
            this(null);
        }

        private Type(String validPattern) {
            this.validPattern = validPattern;
        }

        public Optional<String> getValidPattern() {
            return Optional.ofNullable(validPattern);
        }

    }

    private long id;

    /**
     * The type of communication.
     */
    private Type type;

    /**
     * The value for the {@link Type} of communication. I.e. Phone: 0123 456789,
     * E-Mail: max.mustermann@mustermail.de
     */
    private String identifier;

    public Communication() {
    }

    /**
     * Tryout constructor, do not use in productive.
     *
     * @param id
     */
    public Communication(long id) {
        this.id = id;
    }

    public Communication(Type type, String identifier) {
        this.type = type;
        this.identifier = identifier;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public long getId() {
        return id;
    }

    /**
     * Html representation of the class.
     *
     * @return a html string
     */
    public String toHtml() {
        return type + ":&nbsp;" + identifier;
    }

    /**
     * Returns null, if the Communication is valid. Rules are:
     * <ul>
     * <li>The identifier pattern must match the Communication type</li>
     * </ul>
     *
     * @return null if instance is valid, else a string representing the
     * invalidation.
     */
    public String getViolationMessage() {
        if (type == null) {
            return "Type is null";
        }
        if (StringUtils.isBlank(identifier)) {
            return type + " Identifier is blank";
        }
        return type.getValidPattern()
                .map(pattern -> identifier.matches(pattern))
                .map(b -> (b ? null : "Identifier " + identifier + " vom Type: " + type + " passt nicht auf pattern " + type.getValidPattern().get()))
                .orElse(null);
    }

    public String toSingleLineString() {
        String toSingleLine = type.name() + " " + identifier;
        return toSingleLine;
    }

}