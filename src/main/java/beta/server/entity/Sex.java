/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.server.entity;

/**
 *
 * @author jens.papenhagen
 */
public enum Sex {
    MALE("m"),
    FEMALE("w");

    private final String sign;

    private Sex(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }
}
