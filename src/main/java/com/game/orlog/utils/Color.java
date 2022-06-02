package com.game.orlog.utils;

public enum Color {
	WHITE((short) 0xFF, (short) 0xFF, (short) 0xFF),
	BLACK((short) 0x00, (short) 0x00, (short) 0x00),
	RED((short) 0xFF, (short) 0x00, (short) 0x00),
	GREEN((short) 0x00, (short) 0xFF, (short) 0x00),
	BLUE((short) 0x00, (short) 0x00, (short) 0xFF);
	
	private short red;
	private short green;
	private short blue;
	
	private Color(short red, short green, short blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public short[] getColor() {
		return new short[]{this.red, this.green, this.blue};
	}
}
