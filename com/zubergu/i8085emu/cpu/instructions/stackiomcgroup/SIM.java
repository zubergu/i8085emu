package com.zubergu.i8085emu.cpu.instructions.stackiomcgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;

public class SIM implements Instruction {
	private Cpu cpu;
	
	public SIM(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		Register8Bit accumulator = cpu.get8BitRegisterForName(Register8BitName.A);
		int value = accumulator.getValue();
		
		
		boolean serialDataEnabled = (value & 0x40) > 0;
		if(serialDataEnabled) {
			boolean sod = (value & 0x80) > 0;
			cpu.setSerialOutputDataBit(sod);
		}
		
		boolean r75 = (value & 0x10) > 0; // should r75 flip flop be reset to OFF?
		if(r75) {
			cpu.setInterrupt7_5pending(false);
		}
		boolean maskSetEnabled = (value & 0x08) > 0;
		if(maskSetEnabled) {
			boolean m75 = (value & 0x04) > 0;
			cpu.setInterrupt7_5mask(m75);
			boolean m65 = (value & 0x02) > 0;
			cpu.setInterrupt6_5mask(m65);
			boolean m55 = (value & 0x01) > 0;
			cpu.setInterrupt5_5mask(m55);
		}
		
		return 4;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
