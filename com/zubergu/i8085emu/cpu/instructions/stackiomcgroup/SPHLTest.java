package com.zubergu.i8085emu.cpu.instructions.stackiomcgroup;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

public class SPHLTest {
	
	@Test
	public void correctInstructionCarrySet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		RegisterPair hl = Mockito.mock(RegisterPair.class);
		Register16Bit sp = Mockito.mock(Register16Bit.class);
		
		when(cpu.getMemory()).thenReturn(m);
		when(cpu.get16BitRegisterForName(Register16BitName.SP)).thenReturn(sp);
		when(cpu.getRegisterPairForName(RegisterPairName.HL)).thenReturn(hl);
		
		when(hl.getValue()).thenReturn(0xFAFB);
		
		SPHL instruction = new SPHL(cpu);
		
		Assert.assertEquals(instruction.execute(), 6);
		verify(sp).setValue(0xFAFB);
		
		
		
	}

}
