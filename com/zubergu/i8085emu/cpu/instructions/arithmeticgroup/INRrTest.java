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

public class INRrTest {
	
	@Test
	public void correctInstructionExecution() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register8Bit b = Mockito.mock(Register8Bit.class);
		
		when(cpu.get8BitRegisterForName(Register8BitName.B)).thenReturn(b);
		when(b.getValue()).thenReturn(0x01).thenReturn(0x02);
		
		INRr instruction = new INRr(cpu, Register8BitName.B);
		
		Assert.assertEquals(instruction.execute(), 4);
		
		verify(b).increment();
		verify(cpu).setAuxiliaryCarryFlag(0x01, 0x01, false);
		verify(cpu).setZeroFlag(0x02);
		verify(cpu).setSignFlag(0x02);
		verify(cpu).setParityFlag(0x02);
		
	}

}

