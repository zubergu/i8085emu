package com.zubergu.i8085emu.cpu.instructions.stackiomcgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class POPrp implements Instruction {
	private Cpu cpu;
	private RegisterPairName rpName;
	
	public POPrp(Cpu cpu, RegisterPairName rpName) {
		this.cpu = cpu;
		this.rpName = rpName;
	}

	@Override
	public int execute() {
		RegisterPair rp = cpu.getRegisterPairForName(rpName);
		Register16Bit sp = cpu.get16BitRegisterForName(Register16BitName.SP);
		Memory memory = cpu.getMemory();
		
		Register8Bit rpH = rp.getRh();
		Register8Bit rpL = rp.getRl();
		
		rpL.setValue(memory.readByte(sp.getValue()));
		sp.increment();
		rpH.setValue(memory.readByte(sp.getValue()));
		sp.increment();
		
		return 10;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
}
