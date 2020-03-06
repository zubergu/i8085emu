package com.zubergu.i8085emu.cpu.instructions.arithmeticgroup;


import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class DCRMTest {
	
	@Test
	public void correctInstructionExecution() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		RegisterPair hl = Mockito.mock(RegisterPair.class);
		
		when(cpu.getRegisterPairForName(RegisterPairName.HL)).thenReturn(hl);
		when(cpu.getMemory()).thenReturn(m);
		when(hl.getValue()).thenReturn(0xFAFB);
		when(m.readByte(0xFAFB)).thenReturn(0xFF).thenReturn(0xFE);
		
		
		DCRM instruction = new DCRM(cpu);
		
		Assert.assertEquals(instruction.execute(), 10);
		
		verify(m).writeByte(0xFAFB, 0xFE);
		verify(cpu).setAuxiliaryCarryFlag(0xFF, 1, true);
		verify(cpu).setZeroFlag(0xFE);
		verify(cpu).setSignFlag(0xFE);
		verify(cpu).setParityFlag(0xFE);
		
		
		
	}

}

