package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class LXIrpData16 implements Instruction {
	
	private String description = "";
	private Cpu cpu;
	private RegisterPairName rpName;
	private Register16BitName register16bitName;
	
	public LXIrpData16(Cpu cpu, RegisterPairName rpName) {
		this(cpu, rpName, null);
	}
	
	public LXIrpData16(Cpu cpu, Register16BitName register16bitName) {
		this(cpu, null, register16bitName);
	}
	
	
	private LXIrpData16(Cpu cpu, RegisterPairName rpName, Register16BitName register16bitName) {
		this.cpu = cpu;
		this.rpName = rpName;
		this.register16bitName = register16bitName;
	}

	@Override
	public int execute() {
		// SP is not really register pair in this implementation thus special treatment
		Memory memory = cpu.getMemory();
		Register16Bit pc = cpu.get16BitRegisterForName(Register16BitName.PC);
		
		int byte2 = memory.readByte(pc.getValue());
		pc.increment();
		int byte3 = memory.readByte(pc.getValue());
		pc.increment();
		
		if(rpName == null) { // register pair not defined meaning target is SP
			Register16Bit sp = cpu.get16BitRegisterForName(register16bitName);
			sp.setValue((byte3<<8) | byte2);
		} else {
			RegisterPair rp = cpu.getRegisterPairForName(rpName);
			rp.getRl().setValue(byte2);
			rp.getRh().setValue(byte3);
		}
		return 10;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}
	
	

}
