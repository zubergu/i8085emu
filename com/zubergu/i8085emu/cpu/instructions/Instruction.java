package com.zubergu.i8085emu.cpu.instructions;

public interface Instruction {
	/**
	 * Complete instruction implementation goes into this method.
	 * 
	 * @return Number of cycles that current instruction execution consumes
	 */
	public int execute();
	
	/**
	 * 
	 * @return Instruction description for logging or other purpose.
	 */
	public String getDescription();
}
