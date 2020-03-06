package com.zubergu.i8085emu.cpu.instructions;

import com.zubergu.i8085emu.cpu.core.Cpu;

/**
 * 
 */
public interface InstructionSet {
	
	public void initialize(Cpu cpu);
	public Instruction decode(int opcode) throws UnimplementedInstructionException;
}
