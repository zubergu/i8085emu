package com.zubergu.i8085emu.cpu.instructions.stackiomcgroup;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
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

public class XTHLTest {
	
	@Test
	public void correctInstructionCarrySet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		Register8Bit h = Mockito.mock(Register8Bit.class);
		Register8Bit l = Mockito.mock(Register8Bit.class);
		Register16Bit sp = Mockito.mock(Register16Bit.class);
		
		when(cpu.getMemory()).thenReturn(m);
		when(cpu.get16BitRegisterForName(Register16BitName.SP)).thenReturn(sp);
		when(cpu.get8BitRegisterForName(Register8BitName.H)).thenReturn(h);
		when(cpu.get8BitRegisterForName(Register8BitName.L)).thenReturn(l);
		
		when(sp.getValue()).thenReturn(0x00FA);
		
		when(m.readByte(0x00FA)).thenReturn(0xAB);
		when(m.readByte(0x00FB)).thenReturn(0xCD);
		
		
		XTHL instruction = new XTHL(cpu);
		
		Assert.assertEquals(instruction.execute(), 16);
		verify(h).setValue(0xCD);
		verify(l).setValue(0xAB);
		verify(sp, never()).setValue(any(Integer.class));
		
		
		
	}

}
