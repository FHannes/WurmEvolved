package com.wurmemu.server.game.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FaceStyle {

    @Column(name = "upperFace", nullable = false)
    private int upperFace;

    @Column(name = "eyeType", nullable = false)
    private int eyeType;

    @Column(name = "complexion", nullable = false)
    private int complexion;

    @Column(name = "lowerFace", nullable = false)
    private int lowerFace;

    @Column(name = "hair", nullable = false)
    private int hair;

    @Column(name = "nose", nullable = false)
    private int nose;

    @Column(name = "facialHair", nullable = false)
    private int facialHair;

    @Column(name = "eyeColor", nullable = false)
    private int eyeColor;

    @Column(name = "hairColor", nullable = false)
    private int hairColor;

    @Column(name = "skinColor", nullable = false)
    private int skinColor;

    public int getUpperFace() {
        return upperFace;
    }

    public void setUpperFace(int upperFace) {
        this.upperFace = upperFace;
    }

    public int getEyeType() {
        return eyeType;
    }

    public void setEyeType(int eyeType) {
        this.eyeType = eyeType;
    }

    public int getComplexion() {
        return complexion;
    }

    public void setComplexion(int complexion) {
        this.complexion = complexion;
    }

    public int getLowerFace() {
        return lowerFace;
    }

    public void setLowerFace(int lowerFace) {
        this.lowerFace = lowerFace;
    }

    public int getHair() {
        return hair;
    }

    public void setHair(int hair) {
        this.hair = hair;
    }

    public int getNose() {
        return nose;
    }

    public void setNose(int nose) {
        this.nose = nose;
    }

    public int getFacialHair() {
        return facialHair;
    }

    public void setFacialHair(int facialHair) {
        this.facialHair = facialHair;
    }

    public int getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(int eyeColor) {
        this.eyeColor = eyeColor;
    }

    public int getHairColor() {
        return hairColor;
    }

    public void setHairColor(int hairColor) {
        this.hairColor = hairColor;
    }

    public int getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(int skinColor) {
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
        this.upperFace = ((int)data & 0x7);
        this.eyeType = ((int)(data >> 3) & 0x7);
        this.complexion = ((int)(data >> 6) & 0x7);
        this.lowerFace = ((int)(data >> 9) & 0x7);
        this.hair = ((int)(data >> 12) & 0x7);
        this.nose = ((int)(data >> 15) & 0x7);
        this.facialHair = ((int)(data >> 18) & 0x7);
        this.eyeColor = ((int)(data >> 21) & 0x7);
        this.hairColor = ((int)(data >> 24) & 0x7);
        this.skinColor = ((int)(data >> 27) & 0x7);
    }

}
