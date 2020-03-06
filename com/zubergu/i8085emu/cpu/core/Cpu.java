package com.zubergu.i8085emu.cpu.core;

import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.instructions.InstructionSet;
import com.zubergu.i8085emu.cpu.instructions.InstructionSet8085;
import com.zubergu.i8085emu.cpu.instructions.UnimplementedInstructionException;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

/**
 * Class that implements the cpu state and available activities.
 * 
 * @author zubergu
 *
 */

public class Cpu {
	/*
	 * flag masks
	 */
	private static final int ZERO_FLAG_MASK = 0x40;
	private static final int ZERO_FLAG_ON_MASK = 0x40;
	private static final int ZERO_FLAG_OFF_MASK = 0xBF;
	private static final int PARITY_FLAG_MASK = 0x04;
	private static final int PARITY_FLAG_ON_MASK = 0x04;
	private static final int PARITY_FLAG_OFF_MASK = 0xFB;
	private static final int CARRY_FLAG_MASK = 0x01;
	private static final int CARRY_FLAG_ON_MASK = 0x01;
	private static final int CARRY_FLAG_OFF_MASK = 0xFE;
	private static final int AUXILIARY_CARRY_FLAG_MASK = 0x10;
	private static final int AUXILIARY_CARRY_FLAG_ON_MASK = 0x10;
	private static final int AUXILIARY_CARRY_FLAG_OFF_MASK = 0xEF;
	private static final int SIGN_FLAG_MASK = 0x80;
	private static final int SIGN_FLAG_ON_MASK = 0x80;
	private static final int SIGN_FLAG_OFF_MASK = 0x7F;
	

	/*
	 * 8 bit registers
	 */
	private Register8Bit registerA = new Register8Bit();
	private Register8Bit registerPSW = new Register8Bit();
	private Register8Bit registerB = new Register8Bit();
	private Register8Bit registerC = new Register8Bit();
	private Register8Bit registerD = new Register8Bit();
	private Register8Bit registerE = new Register8Bit();
	private Register8Bit registerH = new Register8Bit();
	private Register8Bit registerL = new Register8Bit();

	/*
	 * Register Pairs
	 */
	private RegisterPair registerPairAPWD = new RegisterPair(registerA, registerPSW);
	private RegisterPair registerPairBC = new RegisterPair(registerB, registerC);
	private RegisterPair registerPairDE = new RegisterPair(registerD, registerE);
	private RegisterPair registerPairHL = new RegisterPair(registerH, registerL);

	/*
	 * 16 bit registers
	 */
	private Register16Bit registerPC = new Register16Bit();
	private Register16Bit registerSP = new Register16Bit();
	
	/*
	 * mask, interrupt, serial i/o flip-flops
	 */
	private boolean interruptsEnabled = true;
	private boolean haltState = false;
	
	private boolean serialInputDataBit = false;
	private boolean serialOutputDataBit = false;
	
	private boolean interrupt5_5mask = false;
	private boolean interrupt6_5mask = false;
	private boolean interrupt7_5mask = false;
	
	private boolean interrupt5_5pending = false;
	private boolean interrupt6_5pending = false;
	private boolean interrupt7_5pending = false;

	/*
	 * Instruction set for this cpu
	 */
	private InstructionSet instructionSet = new InstructionSet8085(this);
	
	/* DEVICES EXTERNAL TO CPU  */
	private Memory memory;
	

	public int  executeNextInstruction() {
		int opcode = memory.readByte(registerPC.getValue()); // fetch
		registerPC.increment(); // increment
		Instruction instruction;
		try {
			instruction = instructionSet.decode(opcode); // decode
			return instruction.execute(); // execute
			
		} catch (UnimplementedInstructionException e) {
			System.out.println("Unimplemented instruction 0x" + Integer.toHexString(opcode));
			e.printStackTrace();
			System.exit(1);
		}
		return 0;

	}

	public Register8Bit get8BitRegisterForName(Register8BitName name) {
		switch (name) {
		case A:
			return registerA;
		case B:
			return registerB;
		case C:
			return registerC;
		case D:
			return registerD;
		case E:
			return registerE;
		case H:
			return registerH;
		case L:
			return registerL;
		case PSW:
			return registerPSW;
		default:
			throw new RuntimeException(
					"Can't match 8 bit register for given name");
		}
	}

	public Register16Bit get16BitRegisterForName(Register16BitName name) {
		switch (name) {
		case PC:
			return registerPC;
		case SP:
			return registerSP;
		default:
			throw new RuntimeException(
					"Can't match 16 bit register for given name");
		}
	}

	public RegisterPair getRegisterPairForName(RegisterPairName name) {
		switch (name) {
		case APSW:
			return registerPairAPWD;
		case BC:
			return registerPairBC;
		case DE:
			return registerPairDE;
		case HL:
			return registerPairHL;
		default:
			throw new RuntimeException(
					"Can't match register pair for given name");
		}
	}
	
	public void connectMemory(Memory memory) {
		this.memory = memory;
	}

	public Memory getMemory() {
		return memory;
	}
	
	public void setZeroFlag(int data) {
		int oldValue = registerPSW.getValue();
		boolean on = data == 0x00;
		int newValue = 0;
		if(on) {
			newValue = oldValue | ZERO_FLAG_ON_MASK;
		} else {
			newValue = oldValue & ZERO_FLAG_OFF_MASK;
		}
		registerPSW.setValue(newValue);
	}
	
	public void setCarryFlag(int operandA, int operandB, boolean subtraction) {
		int oldValue = registerPSW.getValue();
		int newValue = 0;
		int result = 0;
		boolean on = false;
		if(subtraction) {
			result = operandA - operandB;
			if ( result < 0) {
				on = true;
			}
		} else {
			result = operandA + operandB;
			if(result > 255) {
				on = true;
			}
		}
		if(on) {
			newValue = oldValue | CARRY_FLAG_ON_MASK;
		} else {
			newValue = oldValue & CARRY_FLAG_OFF_MASK;
		}
		registerPSW.setValue(newValue);
	}
	
	public void setAuxiliaryCarryFlag(int operandA, int operandB, boolean subtraction) { 
		int oldValue = registerPSW.getValue();
		int newValue = 0;
		int result = 0;
		int lowNibbleOperandA = operandA & 0x0F;
		int lowNibbleOperandB = operandB & 0x0F;
		boolean on = false;
		if(subtraction) {
			result = lowNibbleOperandA - lowNibbleOperandB;
			if ( result < 0) {
				on = true;
			}
		} else {
			result = lowNibbleOperandA + lowNibbleOperandB;
			if(result > 15) {
				on = true;
			}
		}
		if(on) {
			newValue = oldValue | AUXILIARY_CARRY_FLAG_ON_MASK;
		} else {
			newValue = oldValue & AUXILIARY_CARRY_FLAG_OFF_MASK;
		}
		registerPSW.setValue(newValue);
	}
	
	public void setParityFlag(int data) {
		int oldValue = registerPSW.getValue();
		int newValue = 0;
		if(dataHasEvenParity(data)) {
			newValue = oldValue | PARITY_FLAG_ON_MASK;
		} else {
			newValue = oldValue & PARITY_FLAG_OFF_MASK;
		}
		registerPSW.setValue(newValue);
	}
	
	public void setSignFlag(int data) {
		int oldValue = registerPSW.getValue();
		int newValue = 0;
		boolean on = (data & 0x80) > 0;
		if(on) {
			newValue = oldValue | SIGN_FLAG_ON_MASK;
		} else {
			newValue = oldValue & SIGN_FLAG_OFF_MASK;
		}
		registerPSW.setValue(newValue);
	}
	
	
	public boolean parityFlagIsSet() {
		return (registerPSW.getValue() & PARITY_FLAG_MASK) > 0;
	}
	public boolean zeroFlagIsSet() {
		return (registerPSW.getValue() & ZERO_FLAG_MASK) > 0;
	}
	public boolean carryFlagIsSet() {
		return (registerPSW.getValue() & CARRY_FLAG_MASK) > 0;
	}
	public boolean auxiliaryCarryFlagIsSet() {
		return (registerPSW.getValue() & AUXILIARY_CARRY_FLAG_MASK) > 0;
	}
	public boolean signFlagIsSet() {
		return (registerPSW.getValue() & SIGN_FLAG_MASK) > 0;
	}
	
	public boolean dataHasEvenParity(int data) {
		int ones = Integer.toBinaryString(data).replaceAll("0", "").length();	
		return ones%2 == 0;
	}

	public void setInterruptsEnabled(boolean interruptsEnabled) {
		this.interruptsEnabled = interruptsEnabled;
		
	}
	
	public boolean getInterruptsEnabled() {
		return interruptsEnabled;
	}
	
	public void setHaltState(boolean haltState) {
		this.haltState = haltState;
	}
	
	public boolean getHaltState() {
		return haltState;
	}
	
	public void setSerialInputDataBit(boolean sid) {
		this.serialInputDataBit = sid;
	}
	
	public boolean getSerialInputDataBit() {
		return serialInputDataBit;
	}
	
	public void setSerialOutputDataBit(boolean sod) {
		this.serialOutputDataBit = sod;
	}
	
	public boolean getSerialOutputDataBit() {
		return serialOutputDataBit;
	}

	public boolean getInterrupt5_5mask() {
		return interrupt5_5mask;
	}

	public void setInterrupt5_5mask(boolean interrupt5_5mask) {
		this.interrupt5_5mask = interrupt5_5mask;
	}

	public boolean getInterrupt6_5mask() {
		return interrupt6_5mask;
	}

	public void setInterrupt6_5mask(boolean interrupt6_5mask) {
		this.interrupt6_5mask = interrupt6_5mask;
	}

	public boolean getInterrupt7_5mask() {
		return interrupt7_5mask;
	}

	public void setInterrupt7_5mask(boolean interrupt7_5mask) {
		this.interrupt7_5mask = interrupt7_5mask;
	}

	public boolean getInterrupt5_5pending() {
		return interrupt5_5pending;
	}

	public void setInterrupt5_5pending(boolean interrupt5_5pending) {
		this.interrupt5_5pending = interrupt5_5pending;
	}

	public boolean getInterrupt6_5pending() {
		return interrupt6_5pending;
	}

	public void setInterrupt6_5pending(boolean interrupt6_5pending) {
		this.interrupt6_5pending = interrupt6_5pending;
	}

	public boolean getInterrupt7_5pending() {
		return interrupt7_5pending;
	}

	public void setInterrupt7_5pending(boolean interrupt7_5pending) {
		this.interrupt7_5pending = interrupt7_5pending;
	}

}
