package com.zubergu.i8085emu.cpu.instructions.arithmeticgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;

public class DAA implements Instruction {
	private Cpu cpu;
	
	public DAA(Cpu cpu) {
		this.cpu = cpu;
	}
	

	@Override
	public int execute() {
		Register8Bit accumulator = cpu.get8BitRegisterForName(Register8BitName.A);
		int value = accumulator.getValue();
		boolean auxiliaryCarryFlagSet = cpu.auxiliaryCarryFlagIsSet();
		int accLowNibble =  value & 0x0F;
		
		
		if(accLowNibble > 9 || auxiliaryCarryFlagSet) {
			accumulator.setValue(value+6);
			cpu.setAuxiliaryCarryFlag(accLowNibble, 0x06, false);
			cpu.setCarryFlag(value, 6, false);
		}
		
		value = accumulator.getValue(); // new accumulator value
		int accHighNibble = (value >> 4) & 0x0F;
		boolean carryFlagSet = cpu.carryFlagIsSet();
		if(accHighNibble > 9 || carryFlagSet) {
			accHighNibble +=6;
			cpu.setCarryFlag(accHighNibble << 4, 6, false);
		}
		accLowNibble = value & 0x0F;
		accHighNibble = accHighNibble << 4;
		accumulator.setValue(accHighNibble+accLowNibble);
		cpu.setZeroFlag(accumulator.getValue());
		cpu.setParityFlag(accumulator.getValue());
		cpu.setSignFlag(accumulator.getValue());
		
		return 4;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
