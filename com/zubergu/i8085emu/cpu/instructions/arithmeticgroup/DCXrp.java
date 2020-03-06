package com.zubergu.i8085emu.cpu.instructions.arithmeticgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;

public class DCXrp implements Instruction {

	private Cpu cpu;
	private RegisterPairName registerPairName;
	private Register16BitName register16BitName;

	public DCXrp(Cpu cpu, RegisterPairName registerPairName) {
		this(cpu, registerPairName, null);
	}

	public DCXrp(Cpu cpu, Register16BitName register16BitName) {
		this(cpu, null, register16BitName);
	}

	private DCXrp(Cpu cpu, RegisterPairName registerPairName,
			Register16BitName register16BitName) {
		this.cpu = cpu;
		this.registerPairName = registerPairName;
		this.register16BitName = register16BitName;
	}

	@Override
	public int execute() {
		// SP is not really register pair in this implementation thus special
		if (registerPairName == null) { // register pair not defined meaning target is SP
			Register16Bit sp = cpu.get16BitRegisterForName(register16BitName);
			sp.decrement();
		} else {
			RegisterPair rp = cpu.getRegisterPairForName(registerPairName);
			rp.decrement();
		}
		return 6;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
