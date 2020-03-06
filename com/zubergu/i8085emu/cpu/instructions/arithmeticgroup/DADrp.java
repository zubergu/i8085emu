package com.zubergu.i8085emu.cpu.instructions.arithmeticgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;

public class DADrp implements Instruction {
	private Cpu cpu;
	private RegisterPairName registerPairName;
	private Register16BitName register16BitName;

	public DADrp(Cpu cpu, RegisterPairName registerPairName) {
		this(cpu, registerPairName, null);
	}

	public DADrp(Cpu cpu, Register16BitName register16BitName) {
		this(cpu, null, register16BitName);
	}

	private DADrp(Cpu cpu, RegisterPairName registerPairName,
			Register16BitName register16BitName) {
		this.cpu = cpu;
		this.registerPairName = registerPairName;
		this.register16BitName = register16BitName;
	}

	@Override
	public int execute() {
		int value = 0;
		RegisterPair hl = cpu.getRegisterPairForName(RegisterPairName.HL);
		value = hl.getValue();
		// SP is not really register pair in this implementation thus special
		if (registerPairName == null) { // register pair not defined meaning target is SP
			Register16Bit sp = cpu.get16BitRegisterForName(register16BitName);
			value = value + sp.getValue();
		} else {
			RegisterPair rp = cpu.getRegisterPairForName(registerPairName);
			value = value + rp.getValue();
		}
		if(value > 0xFFFF) {
			cpu.setCarryFlag(0xFF, 0x01, false);
		} else {
			cpu.setCarryFlag(0x00, 0x01, false);
		}
		hl.setValue(value);
		
		return 10;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
