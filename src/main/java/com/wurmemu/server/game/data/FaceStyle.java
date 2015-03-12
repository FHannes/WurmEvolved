package com.wurmemu.server.game.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FaceStyle {

    @Column(name = "upperFace", nullable = false)
    private byte upperFace;

    @Column(name = "eyeType", nullable = false)
    private byte eyeType;

    @Column(name = "complexion", nullable = false)
    private byte complexion;

    @Column(name = "lowerFace", nullable = false)
    private byte lowerFace;

    @Column(name = "hair", nullable = false)
    private byte hair;

    @Column(name = "nose", nullable = false)
    private byte nose;

    @Column(name = "facialHair", nullable = false)
    private byte facialHair;

    @Column(name = "eyeColor", nullable = false)
    private byte eyeColor;

    @Column(name = "hairColor", nullable = false)
    private byte hairColor;

    @Column(name = "skinColor", nullable = false)
    private byte skinColor;

    public byte getUpperFace() {
        return upperFace;
    }

    public void setUpperFace(byte upperFace) {
        this.upperFace = upperFace;
    }

    public byte getEyeType() {
        return eyeType;
    }

    public void setEyeType(byte eyeType) {
        this.eyeType = eyeType;
    }

    public byte getComplexion() {
        return complexion;
    }

    public void setComplexion(byte complexion) {
        this.complexion = complexion;
    }

    public byte getLowerFace() {
        return lowerFace;
    }

    public void setLowerFace(byte lowerFace) {
        this.lowerFace = lowerFace;
    }

    public byte getHair() {
        return hair;
    }

    public void setHair(byte hair) {
        this.hair = hair;
    }

    public byte getNose() {
        return nose;
    }

    public void setNose(byte nose) {
        this.nose = nose;
    }

    public byte getFacialHair() {
        return facialHair;
    }

    public void setFacialHair(byte facialHair) {
        this.facialHair = facialHair;
    }

    public byte getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(byte eyeColor) {
        this.eyeColor = eyeColor;
    }

    public byte getHairColor() {
        return hairColor;
    }

    public void setHairColor(byte hairColor) {
        this.hairColor = hairColor;
    }

    public byte getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(byte skinColor) {
        this.skinColor = skinColor;
    }

    public long toLong() {
        long data = this.skinColor & 0x7;
        data = (data << 3) | (this.hairColor & 0x7);
        data = (data << 3) | (this.eyeColor & 0x7);
        data = (data << 3) | (this.facialHair & 0x7);
        data = (data << 3) | (this.nose & 0x7);
        data = (data << 3) | (this.hair & 0x7);
        data = (data << 3) | (this.lowerFace & 0x7);
        data = (data << 3) | (this.complexion & 0x7);
        data = (data << 3) | (this.eyeType & 0x7);
        data = (data << 3) | (this.upperFace & 0x7);
        return data;
    }

    public void fromLong(long data) {
        this.upperFace = (byte) ((int)data & 0x7);
        this.eyeType = (byte) ((int)(data >> 3) & 0x7);
        this.complexion = (byte) ((int)(data >> 6) & 0x7);
        this.lowerFace = (byte) ((int)(data >> 9) & 0x7);
        this.hair = (byte) ((int)(data >> 12) & 0x7);
        this.nose = (byte) ((int)(data >> 15) & 0x7);
        this.facialHair = (byte) ((int)(data >> 18) & 0x7);
        this.eyeColor = (byte) ((int)(data >> 21) & 0x7);
        this.hairColor = (byte) ((int)(data >> 24) & 0x7);
        this.skinColor = (byte) ((int)(data >> 27) & 0x7);
    }

}
