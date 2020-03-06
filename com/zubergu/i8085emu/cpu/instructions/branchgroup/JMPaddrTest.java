package com.zubergu.i8085emu.cpu.instructions.branchgroup;

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

public class JMPaddrTest {
	
	@Test
	public void correctInstructionCarrySet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		Register16Bit pc = Mockito.mock(Register16Bit.class);

		
		when(cpu.getMemory()).thenReturn(m);
		when(cpu.get16BitRegisterForName(Register16BitName.PC)).thenReturn(pc);
		
		when(pc.getValue()).thenReturn(0xFF0A).thenReturn(0xFF0B);
		when(m.readByte(0xFF0A)).thenReturn(0x03);
		when(m.readByte(0xFF0B)).thenReturn(0x04);
		
		JMPaddr instruction = new JMPaddr(cpu);
		
		Assert.assertEquals(instruction.execute(), 10);
		
		verify(pc).increment();
		verify(pc).setValue(0x0403);
		
	}

}
