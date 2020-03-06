package com.zubergu.i8085emu.cpu.instructions.branchgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.instructions.JumpCondition;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.memory.Memory;

public class JConditionAddr implements Instruction {
	private Cpu cpu;
	private JumpCondition jumpCondition;
	
	public JConditionAddr (Cpu cpu, JumpCondition jumpCondition) {
		this.cpu = cpu;
		this.jumpCondition = jumpCondition;
	}

	@Override
	public int execute() {
		Register16Bit pc = cpu.get16BitRegisterForName(Register16BitName.PC);
		Memory memory = cpu.getMemory();
		int byte2 = memory.readByte(pc.getValue());
		pc.increment();
		int byte3 = memory.readByte(pc.getValue());
		pc.increment();
		
		boolean conditionIsMet = false;
		
		switch(jumpCondition) {
		case Z: 
			conditionIsMet = cpu.zeroFlagIsSet();
			break;
		case NZ: 
			conditionIsMet = !cpu.zeroFlagIsSet();
			break;
		case C: 
			conditionIsMet = cpu.carryFlagIsSet();
			break;
		case NC: 
			conditionIsMet = !cpu.carryFlagIsSet();
			break;
		case PE:
			conditionIsMet = cpu.parityFlagIsSet();
			break;
		case PO: 
			conditionIsMet = !cpu.parityFlagIsSet();
			break;
		case P:
			conditionIsMet = !cpu.signFlagIsSet();
			break;

		case M:
			conditionIsMet = cpu.signFlagIsSet();
			break;
		}
		
		if(conditionIsMet) {
			pc.setValue((byte3<<8)+byte2);
			return 10;
		}
		
		return 7;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
