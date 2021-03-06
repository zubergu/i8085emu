package com.zubergu.i8085emu.devices;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.zubergu.i8085emu.io.MemoryMappedIODevice;

public class MemoryMappedDisplayDevice implements MemoryMappedIODevice {

	private int startAddress;
	private int size;

	private int[] memory;
	private boolean[] touched;
	private boolean touchedInLastInstruction;

	private int pixelMultiplier = 2;
	private int charH = 13;
	private int charW = 9;
	private int displaySizeInCharsH = 16;
	private int displaySizeInCharsW = 64;

	private BufferedImage screen;

	private Color backgroundColor = Color.BLACK;
	private Color foregroundColor = Color.GREEN;

	private int[] characterRom = {

			/* 00 */0x00,0xFE,0x82,0x82,0x82,0x82,0x82,0x82,0x82,0xFE,0x00,0x00,0x00, // square
			/* 01 */0x00,0xFE,0x80,	0x80,0x80,0x80,0x80,0x80,0x80,0x80,0x00,0x00,0x00, // top-left angle
			/* 02 */0x00,0x10,0x10,0x10,0x10,0x10,0x10,0x10,0x10,0xFE,0x00,0x00,0x00, // inverted T
			/* 03 */0x00,0x02,0x02,0x02,0x02,0x02,0x02,0x02,0x02,0xFE,0x00,0x00,0x00, // bot-rgt angle
			/* 04 */0x00,
			0x40,
			0x20,
			0x10,
			0x08,
			0x7C,
			0x20,
			0x10,
			0x08,
			0x04,
			0x00,
			0x00,
			0x00, // lightning
			/* 05 */0x00,
			0xFE,
			0x82,
			0xC6,
			0xAA,
			0x92,
			0xAA,
			0xC6,
			0x82,
			0xFE,
			0x00,
			0x00,
			0x00, // x in a box
			/* 06 */0x00,
			0x00,
			0x02,
			0x04,
			0x08,
			0x90,
			0xA0,
			0xC0,
			0x80,
			0x00,
			0x00,
			0x00,
			0x00, // check
			/* 07 */0x00,
			0x38,
			0x44,
			0x82,
			0x82,
			0x82,
			0xFE,
			0x28,
			0x28,
			0xEE,
			0x00,
			0x00,
			0x00, // vert nand
			/* 08 */0x00,
			0x20,
			0x40,
			0xF8,
			0x44,
			0x22,
			0x02,
			0x02,
			0x02,
			0x02,
			0x00,
			0x00,
			0x00, // left turn
			/* 09 */0x00,
			0x00,
			0x10,
			0x08,
			0x04,
			0xFE,
			0x04,
			0x08,
			0x10,
			0x00,
			0x00,
			0x00,
			0x00, // rt arrow
			/* 0A */0x00,
			0xFE,
			0x00,
			0x00,
			0x00,
			0xFE,
			0x00,
			0x00,
			0x00,
			0xFE,
			0x00,
			0x00,
			0x00, // equivalence
			/* 0B */0x00,
			0x00,
			0x10,
			0x10,
			0x10,
			0x92,
			0x54,
			0x38,
			0x10,
			0x00,
			0x00,
			0x00,
			0x00, // dwn arrow
			/* 0C */0x00,
			0x10,
			0x10,
			0x54,
			0x38,
			0x10,
			0x92,
			0x54,
			0x38,
			0x10,
			0x00,
			0x00,
			0x00, // dbl dwn arrow
			/* 0D */0x00,
			0x00,
			0x10,
			0x20,
			0x40,
			0xFE,
			0x40,
			0x20,
			0x10,
			0x00,
			0x00,
			0x00,
			0x00, // left arrow
			/* 0E */0x00,
			0x38,
			0x44,
			0xC6,
			0xAA,
			0x92,
			0xAA,
			0xC6,
			0x44,
			0x38,
			0x00,
			0x00,
			0x00, // x in oval
			/* 0F */0x00,
			0x38,
			0x44,
			0x82,
			0x82,
			0x92,
			0x82,
			0x82,
			0x44,
			0x38,
			0x00,
			0x00,
			0x00, // . in oval

			/* 10 */0x00,
			0xFE,
			0x82,
			0x82,
			0x82,
			0xFE,
			0x82,
			0x82,
			0x82,
			0xFE,
			0x00,
			0x00,
			0x00, // 2-pane window
			/* 11 */0x00,
			0x38,
			0x54,
			0x92,
			0x92,
			0x9E,
			0x82,
			0x82,
			0x44,
			0x38,
			0x00,
			0x00,
			0x00, // 3:00 oval
			/* 12 */0x00,
			0x38,
			0x44,
			0x82,
			0x82,
			0x9E,
			0x92,
			0x92,
			0x54,
			0x38,
			0x00,
			0x00,
			0x00, // 3:30 oval
			/* 13 */0x00,
			0x38,
			0x44,
			0x82,
			0x82,
			0xF2,
			0x92,
			0x92,
			0x54,
			0x38,
			0x00,
			0x00,
			0x00, // 9:30 oval
			/* 14 */0x00,
			0x38,
			0x54,
			0x92,
			0x92,
			0xF2,
			0x82,
			0x82,
			0x44,
			0x38,
			0x00,
			0x00,
			0x00, // 9:00 oval
			/* 15 */0x00,
			0x00,
			0x22,
			0x14,
			0x08,
			0x94,
			0xA2,
			0xC0,
			0x80,
			0x00,
			0x00,
			0x00,
			0x00, // not check
			/* 16 */0x00,
			0x7C,
			0x44,
			0x44,
			0x44,
			0x44,
			0x44,
			0x44,
			0x44,
			0xC6,
			0x00,
			0x00,
			0x00, // croquet hoop
			/* 17 */0x00,
			0x02,
			0x02,
			0x02,
			0x02,
			0xFE,
			0x02,
			0x02,
			0x02,
			0x02,
			0x00,
			0x00,
			0x00, // 90-cw T
			/* 18 */0x00,
			0xFE,
			0x82,
			0x44,
			0x28,
			0x10,
			0x28,
			0x44,
			0x82,
			0xFE,
			0x00,
			0x00,
			0x00, // hourglass
			/* 19 */0x00,
			0x10,
			0x10,
			0x10,
			0x38,
			0x38,
			0x10,
			0x10,
			0x10,
			0x10,
			0x00,
			0x00,
			0x00, // pogo stick
			/* 1A */0x00,
			0x78,
			0x84,
			0x84,
			0x80,
			0x60,
			0x10,
			0x10,
			0x00,
			0x10,
			0x00,
			0x00,
			0x00, // mirror question mark
			/* 1B */0x00,
			0x38,
			0x44,
			0x82,
			0x82,
			0xFE,
			0x82,
			0x82,
			0x44,
			0x38,
			0x00,
			0x00,
			0x00, // 9:15 oval
			/* 1C */0x00,
			0xFE,
			0x92,
			0x92,
			0x92,
			0xF2,
			0x82,
			0x82,
			0x82,
			0xFE,
			0x00,
			0x00,
			0x00, // 9:00 square
			/* 1D */0x00,
			0xFE,
			0x82,
			0x82,
			0x82,
			0xF2,
			0x92,
			0x92,
			0x92,
			0xFE,
			0x00,
			0x00,
			0x00, // 9:30 square
			/* 1E */0x00,
			0xFE,
			0x82,
			0x82,
			0x82,
			0x9E,
			0x92,
			0x92,
			0x92,
			0xFE,
			0x00,
			0x00,
			0x00, // 3:30 square
			/* 1F */0x00,
			0xFE,
			0x92,
			0x92,
			0x92,
			0x9E,
			0x82,
			0x82,
			0x82,
			0xFE,
			0x00,
			0x00,
			0x00, // 3:00 square

			/* 20 */0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00, // space
			/* 21 */0x00,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x00,
			0x00,
			0x10,
			0x10,
			0x00,
			0x00,
			0x00, // !
			/* 22 */0x00,
			0x48,
			0x48,
			0x48,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00, // "
			/* 23 */0x00,
			0x28,
			0x28,
			0x28,
			0xFE,
			0x28,
			0xFE,
			0x28,
			0x28,
			0x28,
			0x00,
			0x00,
			0x00, // #
			/* 24 */0x00,
			0x10,
			0x7E,
			0x90,
			0x90,
			0x7C,
			0x12,
			0x12,
			0xFC,
			0x10,
			0x00,
			0x00,
			0x00, // $
			/* 25 */0x00,
			0x40,
			0xA2,
			0x44,
			0x08,
			0x10,
			0x20,
			0x44,
			0x8A,
			0x04,
			0x00,
			0x00,
			0x00, // %
			/* 26 */0x00,
			0x70,
			0x88,
			0x88,
			0x50,
			0x20,
			0x52,
			0x8C,
			0x8C,
			0x72,
			0x00,
			0x00,
			0x00, // &
			/* 27 */0x00,
			0x18,
			0x18,
			0x10,
			0x20,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00, // '
			/* 28 */0x00,
			0x08,
			0x10,
			0x20,
			0x20,
			0x20,
			0x20,
			0x20,
			0x10,
			0x08,
			0x00,
			0x00,
			0x00, // (
			/* 29 */0x00,
			0x20,
			0x10,
			0x08,
			0x08,
			0x08,
			0x08,
			0x08,
			0x10,
			0x20,
			0x00,
			0x00,
			0x00, // )
			/* 2A */0x00,
			0x00,
			0x10,
			0x92,
			0x54,
			0x38,
			0x54,
			0x92,
			0x10,
			0x00,
			0x00,
			0x00,
			0x00, // *
			/* 2B */0x00,
			0x00,
			0x10,
			0x10,
			0x10,
			0xFE,
			0x10,
			0x10,
			0x10,
			0x00,
			0x00,
			0x00,
			0x00, // +
			/* 2C */0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x30,
			0x30,
			0x20,
			0x40,
			0x00, // ,
			/* 2D */0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0xFE,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00, // -
			/* 2E */0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x30,
			0x30,
			0x00,
			0x00,
			0x00, // .
			/* 2F */0x00,
			0x00,
			0x02,
			0x04,
			0x08,
			0x10,
			0x20,
			0x40,
			0x80,
			0x00,
			0x00,
			0x00,
			0x00, // /

			/* 30 */0x00,
			0x7C,
			0x82,
			0x86,
			0x8A,
			0x92,
			0xA2,
			0xC2,
			0x82,
			0x7C,
			0x00,
			0x00,
			0x00, // 0
			/* 31 */0x00,
			0x10,
			0x30,
			0x50,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x7C,
			0x00,
			0x00,
			0x00, // 1
			/* 32 */0x00,
			0x7C,
			0x82,
			0x02,
			0x04,
			0x38,
			0x40,
			0x80,
			0x80,
			0xFE,
			0x00,
			0x00,
			0x00, // 2
			/* 33 */0x00,
			0x7C,
			0x82,
			0x02,
			0x02,
			0x3C,
			0x02,
			0x02,
			0x82,
			0x7C,
			0x00,
			0x00,
			0x00, // 3
			/* 34 */0x00,
			0x04,
			0x0C,
			0x14,
			0x24,
			0x44,
			0x84,
			0xFE,
			0x04,
			0x04,
			0x00,
			0x00,
			0x00, // 4
			/* 35 */0x00,
			0xFE,
			0x80,
			0x80,
			0xF8,
			0x04,
			0x02,
			0x02,
			0x84,
			0x78,
			0x00,
			0x00,
			0x00, // 5
			/* 36 */0x00,
			0x3C,
			0x40,
			0x80,
			0x80,
			0xFC,
			0x82,
			0x82,
			0x82,
			0x7C,
			0x00,
			0x00,
			0x00, // 6
			/* 37 */0x00,
			0xFE,
			0x82,
			0x04,
			0x08,
			0x10,
			0x20,
			0x20,
			0x20,
			0x20,
			0x00,
			0x00,
			0x00, // 7
			/* 38 */0x00,
			0x7C,
			0x82,
			0x82,
			0x82,
			0x7C,
			0x82,
			0x82,
			0x82,
			0x7C,
			0x00,
			0x00,
			0x00, // 8
			/* 39 */0x00,
			0x7C,
			0x82,
			0x82,
			0x82,
			0x7E,
			0x02,
			0x02,
			0x04,
			0x78,
			0x00,
			0x00,
			0x00, // 9
			/* 3A */0x00,
			0x00,
			0x00,
			0x00,
			0x30,
			0x30,
			0x00,
			0x00,
			0x30,
			0x30,
			0x00,
			0x00,
			0x00, // :
			/* 3B */0x00,
			0x00,
			0x00,
			0x00,
			0x30,
			0x30,
			0x00,
			0x00,
			0x30,
			0x30,
			0x20,
			0x40,
			0x00, // ;
			/* 3C */0x00,
			0x08,
			0x10,
			0x20,
			0x40,
			0x80,
			0x40,
			0x20,
			0x10,
			0x08,
			0x00,
			0x00,
			0x00, // <
			/* 3D */0x00,
			0x00,
			0x00,
			0x00,
			0x7C,
			0x00,
			0x7C,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00, // =
			/* 3E */0x00,
			0x20,
			0x10,
			0x08,
			0x04,
			0x02,
			0x04,
			0x08,
			0x10,
			0x20,
			0x00,
			0x00,
			0x00, // >
			/* 3F */0x00,
			0x3C,
			0x42,
			0x42,
			0x02,
			0x0C,
			0x10,
			0x10,
			0x00,
			0x10,
			0x00,
			0x00,
			0x00, // ?

			/* 40 */0x00,
			0x3C,
			0x42,
			0x9A,
			0xAA,
			0xAA,
			0xBC,
			0x80,
			0x40,
			0x3C,
			0x00,
			0x00,
			0x00, // @
			/* 41 */0x00,
			0x38,
			0x44,
			0x82,
			0x82,
			0x82,
			0xFE,
			0x82,
			0x82,
			0x82,
			0x00,
			0x00,
			0x00, // A
			/* 42 */0x00,
			0xFC,
			0x42,
			0x42,
			0x42,
			0x7C,
			0x42,
			0x42,
			0x42,
			0xFC,
			0x00,
			0x00,
			0x00, // B
			/* 43 */0x00,
			0x3C,
			0x42,
			0x80,
			0x80,
			0x80,
			0x80,
			0x80,
			0x42,
			0x3C,
			0x00,
			0x00,
			0x00, // C
			/* 44 */0x00,
			0xF8,
			0x44,
			0x42,
			0x42,
			0x42,
			0x42,
			0x42,
			0x44,
			0xF8,
			0x00,
			0x00,
			0x00, // D
			/* 45 */0x00,
			0xFE,
			0x80,
			0x80,
			0x80,
			0xF0,
			0x80,
			0x80,
			0x80,
			0xFE,
			0x00,
			0x00,
			0x00, // E
			/* 46 */0x00,
			0xFE,
			0x80,
			0x80,
			0x80,
			0xF0,
			0x80,
			0x80,
			0x80,
			0x80,
			0x00,
			0x00,
			0x00, // F
			/* 47 */0x00,
			0x3C,
			0x42,
			0x80,
			0x80,
			0x80,
			0x9E,
			0x82,
			0x42,
			0x3C,
			0x00,
			0x00,
			0x00, // G
			/* 48 */0x00,
			0x82,
			0x82,
			0x82,
			0x82,
			0xFE,
			0x82,
			0x82,
			0x82,
			0x82,
			0x00,
			0x00,
			0x00, // H
			/* 49 */0x00,
			0x7C,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x7C,
			0x00,
			0x00,
			0x00, // I
			/* 4A */0x00,
			0x3E,
			0x08,
			0x08,
			0x08,
			0x08,
			0x08,
			0x08,
			0x88,
			0x70,
			0x00,
			0x00,
			0x00, // J
			/* 4B */0x00,
			0x82,
			0x84,
			0x88,
			0x90,
			0xA0,
			0xD0,
			0x88,
			0x84,
			0x82,
			0x00,
			0x00,
			0x00, // K
			/* 4C */0x00,
			0x80,
			0x80,
			0x80,
			0x80,
			0x80,
			0x80,
			0x80,
			0x80,
			0xFE,
			0x00,
			0x00,
			0x00, // L
			/* 4D */0x00,
			0x82,
			0xC6,
			0xAA,
			0x92,
			0x92,
			0x82,
			0x82,
			0x82,
			0x82,
			0x00,
			0x00,
			0x00, // M
			/* 4E */0x00,
			0x82,
			0xC2,
			0xA2,
			0x92,
			0x8A,
			0x86,
			0x82,
			0x82,
			0x82,
			0x00,
			0x00,
			0x00, // N
			/* 4F */0x00,
			0x38,
			0x44,
			0x82,
			0x82,
			0x82,
			0x82,
			0x82,
			0x44,
			0x38,
			0x00,
			0x00,
			0x00, // O

			/* 50 */0x00,
			0xFC,
			0x82,
			0x82,
			0x82,
			0xFC,
			0x80,
			0x80,
			0x80,
			0x80,
			0x00,
			0x00,
			0x00, // P
			/* 51 */0x00,
			0x38,
			0x44,
			0x82,
			0x82,
			0x82,
			0x92,
			0x8A,
			0x44,
			0x3A,
			0x00,
			0x00,
			0x00, // Q
			/* 52 */0x00,
			0xFC,
			0x82,
			0x82,
			0x82,
			0xFC,
			0x90,
			0x88,
			0x84,
			0x82,
			0x00,
			0x00,
			0x00, // R
			/* 53 */0x00,
			0x7C,
			0x82,
			0x80,
			0x80,
			0x7C,
			0x02,
			0x02,
			0x82,
			0x7C,
			0x00,
			0x00,
			0x00, // S
			/* 54 */0x00,
			0xFE,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x00,
			0x00,
			0x00, // T
			/* 55 */0x00,
			0x82,
			0x82,
			0x82,
			0x82,
			0x82,
			0x82,
			0x82,
			0x82,
			0x7C,
			0x00,
			0x00,
			0x00, // U
			/* 56 */0x00,
			0x82,
			0x82,
			0x82,
			0x44,
			0x44,
			0x28,
			0x28,
			0x10,
			0x10,
			0x00,
			0x00,
			0x00, // V
			/* 57 */0x00,
			0x82,
			0x82,
			0x82,
			0x82,
			0x92,
			0x92,
			0xAA,
			0xC6,
			0x82,
			0x00,
			0x00,
			0x00, // W
			/* 58 */0x00,
			0x82,
			0x82,
			0x44,
			0x28,
			0x10,
			0x28,
			0x44,
			0x82,
			0x82,
			0x00,
			0x00,
			0x00, // X
			/* 59 */0x00,
			0x82,
			0x82,
			0x44,
			0x28,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x00,
			0x00,
			0x00, // Y
			/* 5A */0x00,
			0xFE,
			0x02,
			0x04,
			0x08,
			0x10,
			0x20,
			0x40,
			0x80,
			0xFE,
			0x00,
			0x00,
			0x00, // Z
			/* 5B */0x00,
			0x78,
			0x40,
			0x40,
			0x40,
			0x40,
			0x40,
			0x40,
			0x40,
			0x78,
			0x00,
			0x00,
			0x00, // [
			/* 5C */0x00,
			0x00,
			0x80,
			0x40,
			0x20,
			0x10,
			0x08,
			0x04,
			0x02,
			0x00,
			0x00,
			0x00,
			0x00, // \
			/* 5D */0x00,
			0x78,
			0x08,
			0x08,
			0x08,
			0x08,
			0x08,
			0x08,
			0x08,
			0x78,
			0x00,
			0x00,
			0x00, // ]
			/* 5E */0x00,
			0x10,
			0x28,
			0x44,
			0x82,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00, // ^
			/* 5F */0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0xFE,
			0x00,
			0x00,
			0x00, // _

			/* 60 */0x00,
			0x30,
			0x30,
			0x10,
			0x08,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00, // `
			/* 61 */0x00,
			0x00,
			0x00,
			0x00,
			0x78,
			0x04,
			0x7C,
			0x84,
			0x84,
			0x7A,
			0x00,
			0x00,
			0x00, // a
			/* 62 */0x00,
			0x80,
			0x80,
			0x80,
			0xB8,
			0xC4,
			0x84,
			0x84,
			0xC4,
			0xB8,
			0x00,
			0x00,
			0x00, // b
			/* 63 */0x00,
			0x00,
			0x00,
			0x00,
			0x78,
			0x84,
			0x80,
			0x80,
			0x84,
			0x78,
			0x00,
			0x00,
			0x00, // c
			/* 64 */0x00,
			0x04,
			0x04,
			0x04,
			0x74,
			0x8C,
			0x84,
			0x84,
			0x8C,
			0x74,
			0x00,
			0x00,
			0x00, // d
			/* 65 */0x00,
			0x00,
			0x00,
			0x00,
			0x78,
			0x84,
			0xFC,
			0x80,
			0x80,
			0x78,
			0x00,
			0x00,
			0x00, // e
			/* 66 */0x00,
			0x18,
			0x24,
			0x20,
			0x20,
			0xF8,
			0x20,
			0x20,
			0x20,
			0x20,
			0x00,
			0x00,
			0x00, // f
			/* 67 */0x00,
			0x00,
			0x00,
			0x00,
			0x74,
			0x8C,
			0x84,
			0x8C,
			0x74,
			0x04,
			0x04,
			0x84,
			0x78, // g
			/* 68 */0x00,
			0x80,
			0x80,
			0x80,
			0xB8,
			0xC4,
			0x84,
			0x84,
			0x84,
			0x84,
			0x00,
			0x00,
			0x00, // h
			/* 69 */0x00,
			0x00,
			0x10,
			0x00,
			0x30,
			0x10,
			0x10,
			0x10,
			0x10,
			0x38,
			0x00,
			0x00,
			0x00, // i
			/* 6A */0x00,
			0x00,
			0x00,
			0x00,
			0x0C,
			0x04,
			0x04,
			0x04,
			0x04,
			0x04,
			0x04,
			0x44,
			0x38, // j
			/* 6B */0x00,
			0x80,
			0x80,
			0x80,
			0x88,
			0x90,
			0xA0,
			0xD0,
			0x88,
			0x84,
			0x00,
			0x00,
			0x00, // k
			/* 6C */0x00,
			0x30,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x38,
			0x00,
			0x00,
			0x00, // l
			/* 6D */0x00,
			0x00,
			0x00,
			0x00,
			0xEC,
			0x92,
			0x92,
			0x92,
			0x92,
			0x92,
			0x00,
			0x00,
			0x00, // m
			/* 6E */0x00,
			0x00,
			0x00,
			0x00,
			0xB8,
			0xC4,
			0x84,
			0x84,
			0x84,
			0x84,
			0x00,
			0x00,
			0x00, // n
			/* 6F */0x00,
			0x00,
			0x00,
			0x00,
			0x78,
			0x84,
			0x84,
			0x84,
			0x84,
			0x78,
			0x00,
			0x00,
			0x00, // o

			/* 70 */0x00,
			0x00,
			0x00,
			0x00,
			0xB8,
			0xC4,
			0x84,
			0x84,
			0xC4,
			0xB8,
			0x80,
			0x80,
			0x80, // p
			/* 71 */0x00,
			0x00,
			0x00,
			0x00,
			0x74,
			0x8C,
			0x84,
			0x84,
			0x8C,
			0x74,
			0x04,
			0x04,
			0x04, // q
			/* 72 */0x00,
			0x00,
			0x00,
			0x00,
			0xB8,
			0xC4,
			0x80,
			0x80,
			0x80,
			0x80,
			0x00,
			0x00,
			0x00, // r
			/* 73 */0x00,
			0x00,
			0x00,
			0x00,
			0x78,
			0x84,
			0x60,
			0x18,
			0x84,
			0x78,
			0x00,
			0x00,
			0x00, // s
			/* 74 */0x00,
			0x00,
			0x20,
			0x20,
			0xF8,
			0x20,
			0x20,
			0x20,
			0x24,
			0x18,
			0x00,
			0x00,
			0x00, // t
			/* 75 */0x00, 0x00,
			0x00,
			0x00,
			0x84,
			0x84,
			0x84,
			0x84,
			0x8C,
			0x74,
			0x00,
			0x00,
			0x00, // u
			/* 76 */0x00, 0x00, 0x00,
			0x00,
			0x82,
			0x82,
			0x82,
			0x44,
			0x28,
			0x10,
			0x00,
			0x00,
			0x00, // v
			/* 77 */0x00, 0x00, 0x00, 0x00,
			0x82,
			0x92,
			0x92,
			0x92,
			0x92,
			0x6C,
			0x00,
			0x00,
			0x00, // w
			/* 78 */0x00, 0x00, 0x00, 0x00, 0x84,
			0x48,
			0x30,
			0x30,
			0x48,
			0x84,
			0x00,
			0x00,
			0x00, // x
			/* 79 */0x00, 0x00, 0x00, 0x00, 0x84, 0x84,
			0x84,
			0x84,
			0x8C,
			0x74,
			0x04,
			0x84,
			0x78, // y
			/* 7A */0x00, 0x00, 0x00, 0x00, 0xFC, 0x08, 0x10,
			0x20,
			0x40,
			0xFC,
			0x00,
			0x00,
			0x00, // z
			/* 7B */0x00, 0x1C, 0x20, 0x20, 0x20, 0x40, 0x20, 0x20,
			0x20,
			0x1C,
			0x00,
			0x00,
			0x00, // {
			/* 7C */0x00, 0x10, 0x10, 0x10, 0x00, 0x00, 0x10, 0x10, 0x10,
			0x00,
			0x00,
			0x00,
			0x00, // |
			/* 7D */0x00, 0x30, 0x08, 0x08, 0x08, 0x04, 0x08, 0x08, 0x08, 0x30,
			0x00,
			0x00,
			0x00, // }
			/* 7E */0x00, 0x60, 0x92, 0x0C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00,
			0x00, // ~
			/* 7F */0x00, 0x48, 0x92, 0x24, 0x48, 0x92, 0x24, 0x48, 0x92, 0x24,
			0x00, 0x00, 0x00, // checkerboard

	};

	int charset_6575[] = {

			/* 00 */0x00,
			0x88,
			0xC8,
			0xA8,
			0x98,
			0xAA,
			0x22,
			0x22,
			0x22,
			0x1C,
			0x00,
			0x00,
			0x00, // NU
			/* 01 */0x00,
			0x70,
			0x80,
			0x60,
			0x10,
			0xE2,
			0x22,
			0x3E,
			0x22,
			0x22,
			0x00,
			0x00,
			0x00, // SH
			/* 02 */0x00,
			0x70,
			0x80,
			0x60,
			0x10,
			0xE2,
			0x14,
			0x08,
			0x14,
			0x22,
			0x00,
			0x00,
			0x00, // SX
			/* 03 */0x00,
			0xF0,
			0x80,
			0xE0,
			0x80,
			0xF2,
			0x14,
			0x08,
			0x14,
			0x22,
			0x00,
			0x00,
			0x00, // EX
			/* 04 */0x00,
			0xF0,
			0x80,
			0xE0,
			0x80,
			0xF0,
			0x3E,
			0x08,
			0x08,
			0x08,
			0x00,
			0x00,
			0x00, // ET
			/* 05 */0x00,
			0xF0,
			0x80,
			0xE0,
			0x80,
			0xEC,
			0x12,
			0x12,
			0x16,
			0x0E,
			0x00,
			0x00,
			0x00, // EQ
			/* 06 */0x00,
			0x60,
			0x90,
			0xF0,
			0x90,
			0x12,
			0x14,
			0x18,
			0x14,
			0x12,
			0x00,
			0x00,
			0x00, // AK
			/* 07 */0x00,
			0xE0,
			0x90,
			0xE0,
			0x90,
			0xE0,
			0x10,
			0x10,
			0x10,
			0x1E,
			0x00,
			0x00,
			0x00, // BL
			/* 08 */0x00,
			0xE0,
			0x90,
			0xE0,
			0x90,
			0xEE,
			0x10,
			0x0C,
			0x02,
			0x1C,
			0x00,
			0x00,
			0x00, // BS
			/* 09 */0x00,
			0x90,
			0x90,
			0xF0,
			0x90,
			0x90,
			0x3E,
			0x08,
			0x08,
			0x08,
			0x00,
			0x00,
			0x00, // HT
			/* 0A */0x00,
			0x80,
			0x80,
			0x80,
			0xF0,
			0x1E,
			0x10,
			0x1C,
			0x10,
			0x10,
			0x00,
			0x00,
			0x00, // LF
			/* 0B */0x00,
			0x88,
			0x88,
			0x88,
			0x50,
			0x20,
			0x3E,
			0x08,
			0x08,
			0x08,
			0x00,
			0x00,
			0x00, // VT
			/* 0C */0x00,
			0xF0,
			0x80,
			0xE0,
			0x80,
			0x9E,
			0x10,
			0x1C,
			0x10,
			0x10,
			0x00,
			0x00,
			0x00, // FF
			/* 0D */0x00,
			0x70,
			0x80,
			0x80,
			0x80,
			0x7C,
			0x12,
			0x1C,
			0x14,
			0x12,
			0x00,
			0x00,
			0x00, // CR
			/* 0E */0x00,
			0x70,
			0x80,
			0x60,
			0x10,
			0xEC,
			0x12,
			0x12,
			0x12,
			0x0C,
			0x00,
			0x00,
			0x00, // SO
			/* 0F */0x00,
			0x70,
			0x80,
			0x60,
			0x10,
			0xEE,
			0x04,
			0x04,
			0x04,
			0x0E,
			0x00,
			0x00,
			0x00, // SI

			/* 10 */0x00,
			0xE0,
			0x90,
			0x90,
			0x90,
			0xE0,
			0x10,
			0x10,
			0x10,
			0x1E,
			0x00,
			0x00,
			0x00, // DL
			/* 11 */0x00,
			0xE0,
			0x90,
			0x90,
			0x90,
			0xE4,
			0x0C,
			0x04,
			0x04,
			0x0E,
			0x00,
			0x00,
			0x00, // D1
			/* 12 */0x00,
			0xE0,
			0x90,
			0x90,
			0x90,
			0xEC,
			0x12,
			0x04,
			0x08,
			0x1E,
			0x00,
			0x00,
			0x00, // D2
			/* 13 */0x00,
			0xE0,
			0x90,
			0x90,
			0x90,
			0xEC,
			0x02,
			0x0C,
			0x02,
			0x1C,
			0x00,
			0x00,
			0x00, // D3
			/* 14 */0x00,
			0xE0,
			0x90,
			0x90,
			0x90,
			0xE4,
			0x0C,
			0x14,
			0x3E,
			0x04,
			0x00,
			0x00,
			0x00, // D4
			/* 15 */0x00,
			0x90,
			0xD0,
			0xB0,
			0x90,
			0x92,
			0x14,
			0x18,
			0x14,
			0x12,
			0x00,
			0x00,
			0x00, // NK
			/* 16 */0x00,
			0x70,
			0x80,
			0x60,
			0x10,
			0xE2,
			0x14,
			0x08,
			0x08,
			0x08,
			0x00,
			0x00,
			0x00, // SY
			/* 17 */0x00,
			0xF0,
			0x80,
			0xE0,
			0x80,
			0xC,
			0x12,
			0x1C,
			0x12,
			0x1C,
			0x00,
			0x00,
			0x00, // EB
			/* 18 */0x00,
			0x70,
			0x80,
			0x80,
			0x80,
			0x70,
			0x12,
			0x1A,
			0x16,
			0x12,
			0x00,
			0x00,
			0x00, // CN
			/* 19 */0x00,
			0xF0,
			0x80,
			0xE0,
			0x80,
			0xF0,
			0x22,
			0x36,
			0x2A,
			0x22,
			0x00,
			0x00,
			0x00, // EM
			/* 1A */0x00,
			0x70,
			0x80,
			0x60,
			0x10,
			0xFC,
			0x12,
			0x1C,
			0x12,
			0x1C,
			0x00,
			0x00,
			0x00, // SB
			/* 1B */0x00,
			0xF0,
			0x80,
			0xE0,
			0x80,
			0xF0,
			0x0E,
			0x10,
			0x10,
			0x0E,
			0x00,
			0x00,
			0x00, // EC
			/* 1C */0x00,
			0xF0,
			0x80,
			0xE0,
			0x80,
			0x8E,
			0x10,
			0x0C,
			0x02,
			0x1C,
			0x00,
			0x00,
			0x00, // FS
			/* 1D */0x00,
			0x70,
			0x80,
			0xB0,
			0x90,
			0x6E,
			0x10,
			0x0C,
			0x02,
			0x1C,
			0x00,
			0x00,
			0x00, // GS
			/* 1E */0x00,
			0xE0,
			0x90,
			0xE0,
			0x90,
			0x8E,
			0x10,
			0x0C,
			0x02,
			0x1C,
			0x00,
			0x00,
			0x00, // RS
			/* 1F */0x00,
			0x90,
			0x90,
			0x90,
			0x90,
			0x6E,
			0x10,
			0x0C,
			0x02,
			0x1C,
			0x00,
			0x00,
			0x00, // US

			/* 20 */0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00, // space
			/* 21 */0x00,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x00,
			0x00,
			0x10,
			0x10,
			0x00,
			0x00,
			0x00, // !
			/* 22 */0x00,
			0x48,
			0x48,
			0x48,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00, // "
			/* 23 */0x00,
			0x28,
			0x28,
			0x28,
			0xFE,
			0x28,
			0xFE,
			0x28,
			0x28,
			0x28,
			0x00,
			0x00,
			0x00, // #
			/* 24 */0x00,
			0x10,
			0x7E,
			0x90,
			0x90,
			0x7C,
			0x12,
			0x12,
			0xFC,
			0x10,
			0x00,
			0x00,
			0x00, // $
			/* 25 */0x00,
			0x40,
			0xA2,
			0x44,
			0x08,
			0x10,
			0x20,
			0x44,
			0x8A,
			0x04,
			0x00,
			0x00,
			0x00, // %
			/* 26 */0x00,
			0x70,
			0x88,
			0x88,
			0x50,
			0x20,
			0x52,
			0x8C,
			0x8C,
			0x72,
			0x00,
			0x00,
			0x00, // &
			/* 27 */0x00,
			0x18,
			0x18,
			0x10,
			0x20,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00, // '
			/* 28 */0x00,
			0x08,
			0x10,
			0x20,
			0x20,
			0x20,
			0x20,
			0x20,
			0x10,
			0x08,
			0x00,
			0x00,
			0x00, // (
			/* 29 */0x00,
			0x20,
			0x10,
			0x08,
			0x08,
			0x08,
			0x08,
			0x08,
			0x10,
			0x20,
			0x00,
			0x00,
			0x00, // )
			/* 2A */0x00,
			0x00,
			0x10,
			0x92,
			0x54,
			0x38,
			0x54,
			0x92,
			0x10,
			0x00,
			0x00,
			0x00,
			0x00, // *
			/* 2B */0x00,
			0x00,
			0x10,
			0x10,
			0x10,
			0xFE,
			0x10,
			0x10,
			0x10,
			0x00,
			0x00,
			0x00,
			0x00, // +
			/* 2C */0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x30,
			0x30,
			0x20,
			0x40,
			0x00, // ,
			/* 2D */0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0xFE,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00, // -
			/* 2E */0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x30,
			0x30,
			0x00,
			0x00,
			0x00, // .
			/* 2F */0x00,
			0x00,
			0x02,
			0x04,
			0x08,
			0x10,
			0x20,
			0x40,
			0x80,
			0x00,
			0x00,
			0x00,
			0x00, // /

			/* 30 */0x00,
			0x7C,
			0x82,
			0x86,
			0x8A,
			0x92,
			0xA2,
			0xC2,
			0x82,
			0x7C,
			0x00,
			0x00,
			0x00, // 0
			/* 31 */0x00,
			0x10,
			0x30,
			0x50,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x7C,
			0x00,
			0x00,
			0x00, // 1
			/* 32 */0x00,
			0x7C,
			0x82,
			0x02,
			0x04,
			0x38,
			0x40,
			0x80,
			0x80,
			0xFE,
			0x00,
			0x00,
			0x00, // 2
			/* 33 */0x00,
			0x7C,
			0x82,
			0x02,
			0x02,
			0x3C,
			0x02,
			0x02,
			0x82,
			0x7C,
			0x00,
			0x00,
			0x00, // 3
			/* 34 */0x00,
			0x04,
			0x0C,
			0x14,
			0x24,
			0x44,
			0x84,
			0xFE,
			0x04,
			0x04,
			0x00,
			0x00,
			0x00, // 4
			/* 35 */0x00,
			0xFE,
			0x80,
			0x80,
			0xF8,
			0x04,
			0x02,
			0x02,
			0x84,
			0x78,
			0x00,
			0x00,
			0x00, // 5
			/* 36 */0x00,
			0x3C,
			0x40,
			0x80,
			0x80,
			0xFC,
			0x82,
			0x82,
			0x82,
			0x7C,
			0x00,
			0x00,
			0x00, // 6
			/* 37 */0x00,
			0xFE,
			0x82,
			0x04,
			0x08,
			0x10,
			0x20,
			0x20,
			0x20,
			0x20,
			0x00,
			0x00,
			0x00, // 7
			/* 38 */0x00,
			0x7C,
			0x82,
			0x82,
			0x82,
			0x7C,
			0x82,
			0x82,
			0x82,
			0x7C,
			0x00,
			0x00,
			0x00, // 8
			/* 39 */0x00,
			0x7C,
			0x82,
			0x82,
			0x82,
			0x7E,
			0x02,
			0x02,
			0x04,
			0x78,
			0x00,
			0x00,
			0x00, // 9
			/* 3A */0x00,
			0x00,
			0x00,
			0x00,
			0x30,
			0x30,
			0x00,
			0x00,
			0x30,
			0x30,
			0x00,
			0x00,
			0x00, // :
			/* 3B */0x00,
			0x00,
			0x00,
			0x00,
			0x30,
			0x30,
			0x00,
			0x00,
			0x30,
			0x30,
			0x20,
			0x40,
			0x00, // ;
			/* 3C */0x00,
			0x08,
			0x10,
			0x20,
			0x40,
			0x80,
			0x40,
			0x20,
			0x10,
			0x08,
			0x00,
			0x00,
			0x00, // <
			/* 3D */0x00,
			0x00,
			0x00,
			0x00,
			0x7C,
			0x00,
			0x7C,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00, // =
			/* 3E */0x00,
			0x20,
			0x10,
			0x08,
			0x04,
			0x02,
			0x04,
			0x08,
			0x10,
			0x20,
			0x00,
			0x00,
			0x00, // >
			/* 3F */0x00,
			0x3C,
			0x42,
			0x42,
			0x02,
			0x0C,
			0x10,
			0x10,
			0x00,
			0x10,
			0x00,
			0x00,
			0x00, // ?

			/* 40 */0x00,
			0x3C,
			0x42,
			0x9A,
			0xAA,
			0xAA,
			0xBC,
			0x80,
			0x40,
			0x3C,
			0x00,
			0x00,
			0x00, // @
			/* 41 */0x00,
			0x38,
			0x44,
			0x82,
			0x82,
			0x82,
			0xFE,
			0x82,
			0x82,
			0x82,
			0x00,
			0x00,
			0x00, // A
			/* 42 */0x00,
			0xFC,
			0x42,
			0x42,
			0x42,
			0x7C,
			0x42,
			0x42,
			0x42,
			0xFC,
			0x00,
			0x00,
			0x00, // B
			/* 43 */0x00,
			0x3C,
			0x42,
			0x80,
			0x80,
			0x80,
			0x80,
			0x80,
			0x42,
			0x3C,
			0x00,
			0x00,
			0x00, // C
			/* 44 */0x00,
			0xF8,
			0x44,
			0x42,
			0x42,
			0x42,
			0x42,
			0x42,
			0x44,
			0xF8,
			0x00,
			0x00,
			0x00, // D
			/* 45 */0x00,
			0xFE,
			0x80,
			0x80,
			0x80,
			0xF0,
			0x80,
			0x80,
			0x80,
			0xFE,
			0x00,
			0x00,
			0x00, // E
			/* 46 */0x00,
			0xFE,
			0x80,
			0x80,
			0x80,
			0xF0,
			0x80,
			0x80,
			0x80,
			0x80,
			0x00,
			0x00,
			0x00, // F
			/* 47 */0x00,
			0x3C,
			0x42,
			0x80,
			0x80,
			0x80,
			0x9E,
			0x82,
			0x42,
			0x3C,
			0x00,
			0x00,
			0x00, // G
			/* 48 */0x00,
			0x82,
			0x82,
			0x82,
			0x82,
			0xFE,
			0x82,
			0x82,
			0x82,
			0x82,
			0x00,
			0x00,
			0x00, // H
			/* 49 */0x00,
			0x7C,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x7C,
			0x00,
			0x00,
			0x00, // I
			/* 4A */0x00,
			0x3E,
			0x08,
			0x08,
			0x08,
			0x08,
			0x08,
			0x08,
			0x88,
			0x70,
			0x00,
			0x00,
			0x00, // J
			/* 4B */0x00,
			0x82,
			0x84,
			0x88,
			0x90,
			0xA0,
			0xD0,
			0x88,
			0x84,
			0x82,
			0x00,
			0x00,
			0x00, // K
			/* 4C */0x00,
			0x80,
			0x80,
			0x80,
			0x80,
			0x80,
			0x80,
			0x80,
			0x80,
			0xFE,
			0x00,
			0x00,
			0x00, // L
			/* 4D */0x00,
			0x82,
			0xC6,
			0xAA,
			0x92,
			0x92,
			0x82,
			0x82,
			0x82,
			0x82,
			0x00,
			0x00,
			0x00, // M
			/* 4E */0x00,
			0x82,
			0xC2,
			0xA2,
			0x92,
			0x8A,
			0x86,
			0x82,
			0x82,
			0x82,
			0x00,
			0x00,
			0x00, // N
			/* 4F */0x00,
			0x38,
			0x44,
			0x82,
			0x82,
			0x82,
			0x82,
			0x82,
			0x44,
			0x38,
			0x00,
			0x00,
			0x00, // O

			/* 50 */0x00,
			0xFC,
			0x82,
			0x82,
			0x82,
			0xFC,
			0x80,
			0x80,
			0x80,
			0x80,
			0x00,
			0x00,
			0x00, // P
			/* 51 */0x00,
			0x38,
			0x44,
			0x82,
			0x82,
			0x82,
			0x92,
			0x8A,
			0x44,
			0x3A,
			0x00,
			0x00,
			0x00, // Q
			/* 52 */0x00,
			0xFC,
			0x82,
			0x82,
			0x82,
			0xFC,
			0x90,
			0x88,
			0x84,
			0x82,
			0x00,
			0x00,
			0x00, // R
			/* 53 */0x00,
			0x7C,
			0x82,
			0x80,
			0x80,
			0x7C,
			0x02,
			0x02,
			0x82,
			0x7C,
			0x00,
			0x00,
			0x00, // S
			/* 54 */0x00,
			0xFE,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x00,
			0x00,
			0x00, // T
			/* 55 */0x00,
			0x82,
			0x82,
			0x82,
			0x82,
			0x82,
			0x82,
			0x82,
			0x82,
			0x7C,
			0x00,
			0x00,
			0x00, // U
			/* 56 */0x00,
			0x82,
			0x82,
			0x82,
			0x44,
			0x44,
			0x28,
			0x28,
			0x10,
			0x10,
			0x00,
			0x00,
			0x00, // V
			/* 57 */0x00,
			0x82,
			0x82,
			0x82,
			0x82,
			0x92,
			0x92,
			0xAA,
			0xC6,
			0x82,
			0x00,
			0x00,
			0x00, // W
			/* 58 */0x00,
			0x82,
			0x82,
			0x44,
			0x28,
			0x10,
			0x28,
			0x44,
			0x82,
			0x82,
			0x00,
			0x00,
			0x00, // X
			/* 59 */0x00,
			0x82,
			0x82,
			0x44,
			0x28,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x00,
			0x00,
			0x00, // Y
			/* 5A */0x00,
			0xFE,
			0x02,
			0x04,
			0x08,
			0x10,
			0x20,
			0x40,
			0x80,
			0xFE,
			0x00,
			0x00,
			0x00, // Z
			/* 5B */0x00,
			0x78,
			0x40,
			0x40,
			0x40,
			0x40,
			0x40,
			0x40,
			0x40,
			0x78,
			0x00,
			0x00,
			0x00, // [
			/* 5C */0x00,
			0x00,
			0x80,
			0x40,
			0x20,
			0x10,
			0x08,
			0x04,
			0x02,
			0x00,
			0x00,
			0x00,
			0x00, // \
			/* 5D */0x00,
			0x78,
			0x08,
			0x08,
			0x08,
			0x08,
			0x08,
			0x08,
			0x08,
			0x78,
			0x00,
			0x00,
			0x00, // ]
			/* 5E */0x00,
			0x10,
			0x28,
			0x44,
			0x82,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00, // ^
			/* 5F */0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0xFE,
			0x00,
			0x00,
			0x00, // _

			/* 60 */0x00,
			0x30,
			0x30,
			0x10,
			0x08,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00,
			0x00, // `
			/* 61 */0x00,
			0x00,
			0x00,
			0x00,
			0x78,
			0x04,
			0x7C,
			0x84,
			0x84,
			0x7A,
			0x00,
			0x00,
			0x00, // a
			/* 62 */0x00,
			0x80,
			0x80,
			0x80,
			0xB8,
			0xC4,
			0x84,
			0x84,
			0xC4,
			0xB8,
			0x00,
			0x00,
			0x00, // b
			/* 63 */0x00,
			0x00,
			0x00,
			0x00,
			0x78,
			0x84,
			0x80,
			0x80,
			0x84,
			0x78,
			0x00,
			0x00,
			0x00, // c
			/* 64 */0x00,
			0x04,
			0x04,
			0x04,
			0x74,
			0x8C,
			0x84,
			0x84,
			0x8C,
			0x74,
			0x00,
			0x00,
			0x00, // d
			/* 65 */0x00,
			0x00,
			0x00,
			0x00,
			0x78,
			0x84,
			0xFC,
			0x80,
			0x80,
			0x78,
			0x00,
			0x00,
			0x00, // e
			/* 66 */0x00,
			0x18,
			0x24,
			0x20,
			0x20,
			0xF8,
			0x20,
			0x20,
			0x20,
			0x20,
			0x00,
			0x00,
			0x00, // f
			/* 67 */0x00,
			0x00,
			0x00,
			0x00,
			0x74,
			0x8C,
			0x84,
			0x8C,
			0x74,
			0x04,
			0x04,
			0x84,
			0x78, // g
			/* 68 */0x00,
			0x80,
			0x80,
			0x80,
			0xB8,
			0xC4,
			0x84,
			0x84,
			0x84,
			0x84,
			0x00,
			0x00,
			0x00, // h
			/* 69 */0x00,
			0x00,
			0x10,
			0x00,
			0x30,
			0x10,
			0x10,
			0x10,
			0x10,
			0x38,
			0x00,
			0x00,
			0x00, // i
			/* 6A */0x00,
			0x00,
			0x00,
			0x00,
			0x0C,
			0x04,
			0x04,
			0x04,
			0x04,
			0x04,
			0x04,
			0x44,
			0x38, // j
			/* 6B */0x00,
			0x80,
			0x80,
			0x80,
			0x88,
			0x90,
			0xA0,
			0xD0,
			0x88,
			0x84,
			0x00,
			0x00,
			0x00, // k
			/* 6C */0x00,
			0x30,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x10,
			0x38,
			0x00,
			0x00,
			0x00, // l
			/* 6D */0x00,
			0x00,
			0x00,
			0x00,
			0xEC,
			0x92,
			0x92,
			0x92,
			0x92,
			0x92,
			0x00,
			0x00,
			0x00, // m
			/* 6E */0x00,
			0x00,
			0x00,
			0x00,
			0xB8,
			0xC4,
			0x84,
			0x84,
			0x84,
			0x84,
			0x00,
			0x00,
			0x00, // n
			/* 6F */0x00,
			0x00,
			0x00,
			0x00,
			0x78,
			0x84,
			0x84,
			0x84,
			0x84,
			0x78,
			0x00,
			0x00,
			0x00, // o

			/* 70 */0x00,
			0x00,
			0x00,
			0x00,
			0xB8,
			0xC4,
			0x84,
			0x84,
			0xC4,
			0xB8,
			0x80,
			0x80,
			0x80, // p
			/* 71 */0x00,0x00,0x00,0x00,0x74,0x8C,0x84,0x84,0x8C,0x74,0x04,0x04,0x04, // q
			/* 72 */0x00,0x00,0x00,0x00,0xB8,0xC4,0x80,0x80,0x80,0x80,0x00,0x00,0x00, // r
			/* 73 */0x00,0x00,0x00,0x00,0x78,0x84,0x60,0x18,0x84,0x78,0x00,0x00,0x00, // s
			/* 74 */0x00,0x00,0x20,0x20,0xF8,0x20,0x20,0x20,0x24,0x18,0x00,0x00,0x00, // t
			/* 75 */0x00, 0x00,0x00,0x00,0x84,0x84,0x84,0x84,0x8C,0x74,0x00,0x00,0x00, // u
			/* 76 */0x00, 0x00, 0x00,0x00,0x82,0x82,0x82,0x44,0x28,0x10,0x00,0x00,0x00, // v
			/* 77 */0x00, 0x00, 0x00, 0x00,0x82,0x92,0x92,0x92,0x92,0x6C,0x00,0x00,0x00, // w
			/* 78 */0x00, 0x00, 0x00, 0x00, 0x84,0x48,0x30,0x30,0x48,0x84,0x00,0x00,0x00, // x
			/* 79 */0x00, 0x00, 0x00, 0x00, 0x84, 0x84,0x84,0x84,0x8C,0x74,0x04,0x84,0x78, // y
			/* 7A */0x00, 0x00, 0x00, 0x00, 0xFC, 0x08, 0x10,0x20,0x40,0xFC,0x00,0x00,0x00, // z
			/* 7B */0x00, 0x1C, 0x20, 0x20, 0x20, 0x40, 0x20, 0x20,	0x20,0x1C,0x00,0x00,0x00, // {
			/* 7C */0x00, 0x10, 0x10, 0x10, 0x00, 0x00, 0x10, 0x10, 0x10,0x00,0x00,0x00,0x00, // |
			/* 7D */0x00, 0x30, 0x08, 0x08, 0x08, 0x04, 0x08, 0x08, 0x08, 0x30,0x00,0x00,0x00, // }
			/* 7E */0x00, 0x60, 0x92, 0x0C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00,0x00, // ~
			/* 7F */0x00, 0x48, 0x92, 0x24, 0x48, 0x92, 0x24, 0x48, 0x92, 0x24,0x00, 0x00, 0x00, // checkerboard

	};

	public MemoryMappedDisplayDevice(int startAddress, int size) {
		this.startAddress = startAddress;
		this.size = size;
		this.memory = new int[size];
		this.touched = new boolean[size];
		this.touchedInLastInstruction = true;
		for (int i = 0; i < size; i++) {
			touched[i] = true;
		}
		init();
	}

	@Override
	public void writeByte(int address, int data) {
		try {
			this.touchedInLastInstruction = true;
			memory[address - startAddress] = data & 0xFF;
			touched[address - startAddress] = true;
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.println("Crash on this address = 0x"
					+ Integer.toHexString(address) + " , data = 0x"
					+ Integer.toHexString(data));
			ex.printStackTrace();
			System.exit(1);
		}

	}

	@Override
	public int readByte(int address) {
		return memory[address - startAddress] & 0xFF;
	}

	@Override
	public int getStartAddress() {
		return startAddress;
	}

	@Override
	public int getMemorySize() {
		return size;
	}

	@Override
	public int getEndAddress() {
		return startAddress + size - 1;
	}

	@Override
	public String getDescription() {
		return "MM Display Device " + startAddress + " - " + getEndAddress();
	}

	@Override
	public void initialize(int address, int data) { // method for initializing
													// memory device state from
													// file
		writeByte(address, data);

	}

	@Override
	public void update() {
		if (touchedInLastInstruction) {
			drawScreen();
		}
	}

	private void init() {
		screen = new BufferedImage(displaySizeInCharsW * charW
				* pixelMultiplier, displaySizeInCharsH * charH
				* pixelMultiplier, BufferedImage.TYPE_BYTE_BINARY);
		drawScreen();
	}

	private void drawScreen() {
		for (int i = 0; i < size; i++) { // read memory byte by byte
			if (touched[i]) { // redraw character only if this memory was
								// written to in last iteration

				drawCharacter(memory[i], (i % 64) * pixelMultiplier * charW, i
						/ 64 * pixelMultiplier * charH);

			}

			touched[i] = false; // character was already written in this
								// iteration
		}

	}

	private void drawCharacter(int code, int x, int y) {

		int startY = y;
		int base = (code & 0x7F) * 13; // 13 = number of rows in character, 7
										// bits of code are addressing
		boolean invert = (code & 0x80) > 0; // MSB is inverting character colors

		for (int i = base; i < base + 13; i++) {
			int startX = x;
			int row = characterRom[i];

			if (invert) {
				row = ~row;
			}
			String bin = String.format("%16s", Integer.toBinaryString(row))
					.replace(' ', '0');

			for (char c : bin.substring(bin.length() - 9, bin.length() - 1)
					.toCharArray()) {
				if (c == '0') {
					putPixel(startX, startY, backgroundColor);
				} else {
					putPixel(startX, startY, foregroundColor);
				}
				startX += pixelMultiplier;
			}

			startY += pixelMultiplier;
		}
	}

	private void putPixel(int x, int y, Color color) {
		Graphics g = screen.createGraphics();
		for (int i = x; i < x + pixelMultiplier; i++) {
			for (int j = y; j < y + pixelMultiplier; j++) {
				screen.setRGB(i, j, color.getRGB());
			}
		}
	}

	public BufferedImage getFrame() {
		return screen;
	}
	
	public boolean wasTouchedInLastInstruction() {
		return touchedInLastInstruction;
	}
	
	public void setTouchedInLastInstruction(boolean touched) {
		touchedInLastInstruction = touched;
	}

}
