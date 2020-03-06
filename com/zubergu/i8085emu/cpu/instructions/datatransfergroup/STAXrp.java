package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class STAXrp implements Instruction {
	
	private Cpu cpu;
	private RegisterPairName rpName;
	
	public STAXrp(Cpu cpu, RegisterPairName rpName) {
		this.cpu = cpu;
		this.rpName  = rpName;
	}

	@Override
	public int execute() {
		Memory memory = cpu.getMemory();
		RegisterPair rp = cpu.getRegisterPairForName(rpName);
		Register8Bit accumulator = cpu.get8BitRegisterForName(Register8BitName.A);
		memory.writeByte(rp.getValue(), accumulator.getValue());
		return 7;
	}


	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
