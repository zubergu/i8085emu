package com.zubergu.i8085emu.cpu.instructions.stackiomcgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;

public class RIM implements Instruction {
	
	private Cpu cpu;
	
	public RIM(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		Register8Bit accumulator = cpu.get8BitRegisterForName(Register8BitName.A);
		int value = 0;
		
		if(cpu.getInterrupt5_5mask()) {
			value +=1; 
		}
		
		if(cpu.getInterrupt6_5mask()) {
			value += 2;
		}
		
		if(cpu.getInterrupt7_5mask()) {
			value +=4;
		}
		
		if(cpu.getInterruptsEnabled()) {
			value +=8;
		}
		
		if(cpu.getInterrupt5_5pending()) {
			value +=16; 
		}
		
		if(cpu.getInterrupt6_5pending()) {
			value += 32;
		}
		
		if(cpu.getInterrupt7_5pending()) {
			value +=64;
		}
		
		if(cpu.getSerialInputDataBit()) {
			value +=128;
		}
		
		accumulator.setValue(value);
		
		return 4;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
