package com.zubergu.i8085emu.cpu.instructions;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.ACIdata;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.ADCM;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.ADCr;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.ADDM;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.ADDr;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.ADIdata;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.DAA;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.DADrp;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.DCRM;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.DCRr;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.DCXrp;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.INRM;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.INRr;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.INXrp;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.SBBM;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.SBBr;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.SBIdata;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.SUBM;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.SUBr;
import com.zubergu.i8085emu.cpu.instructions.arithmeticgroup.SUIdata;
import com.zubergu.i8085emu.cpu.instructions.branchgroup.CALLaddr;
import com.zubergu.i8085emu.cpu.instructions.branchgroup.CConditionAddr;
import com.zubergu.i8085emu.cpu.instructions.branchgroup.JConditionAddr;
import com.zubergu.i8085emu.cpu.instructions.branchgroup.JMPaddr;
import com.zubergu.i8085emu.cpu.instructions.branchgroup.PCHL;
import com.zubergu.i8085emu.cpu.instructions.branchgroup.RET;
import com.zubergu.i8085emu.cpu.instructions.branchgroup.RSTn;
import com.zubergu.i8085emu.cpu.instructions.branchgroup.Rcondition;
import com.zubergu.i8085emu.cpu.instructions.datatransfergroup.LDAXrp;
import com.zubergu.i8085emu.cpu.instructions.datatransfergroup.LDAaddress16;
import com.zubergu.i8085emu.cpu.instructions.datatransfergroup.LHLDaddress16;
import com.zubergu.i8085emu.cpu.instructions.datatransfergroup.LXIrpData16;
import com.zubergu.i8085emu.cpu.instructions.datatransfergroup.MOVMRs;
import com.zubergu.i8085emu.cpu.instructions.datatransfergroup.MOVRdM;
import com.zubergu.i8085emu.cpu.instructions.datatransfergroup.MOVRdRs;
import com.zubergu.i8085emu.cpu.instructions.datatransfergroup.MVIMdata;
import com.zubergu.i8085emu.cpu.instructions.datatransfergroup.MVIRdData;
import com.zubergu.i8085emu.cpu.instructions.datatransfergroup.SHLDaddress16;
import com.zubergu.i8085emu.cpu.instructions.datatransfergroup.STAXrp;
import com.zubergu.i8085emu.cpu.instructions.datatransfergroup.STAaddress16;
import com.zubergu.i8085emu.cpu.instructions.datatransfergroup.XCHG;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.ANAM;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.ANAr;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.ANIdata;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.CMA;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.CMC;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.CMPM;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.CMPr;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.CPIdata;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.ORAM;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.ORAr;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.ORIdata;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.RAL;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.RAR;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.RLC;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.RRC;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.STC;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.XRAM;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.XRAr;
import com.zubergu.i8085emu.cpu.instructions.logicalgroup.XRIdata;
import com.zubergu.i8085emu.cpu.instructions.stackiomcgroup.DI;
import com.zubergu.i8085emu.cpu.instructions.stackiomcgroup.EI;
import com.zubergu.i8085emu.cpu.instructions.stackiomcgroup.HLT;
import com.zubergu.i8085emu.cpu.instructions.stackiomcgroup.INport;
import com.zubergu.i8085emu.cpu.instructions.stackiomcgroup.NOP;
import com.zubergu.i8085emu.cpu.instructions.stackiomcgroup.OUTport;
import com.zubergu.i8085emu.cpu.instructions.stackiomcgroup.POPrp;
import com.zubergu.i8085emu.cpu.instructions.stackiomcgroup.PUSHrp;
import com.zubergu.i8085emu.cpu.instructions.stackiomcgroup.RIM;
import com.zubergu.i8085emu.cpu.instructions.stackiomcgroup.SIM;
import com.zubergu.i8085emu.cpu.instructions.stackiomcgroup.SPHL;
import com.zubergu.i8085emu.cpu.instructions.stackiomcgroup.XTHL;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;

/**
 * Class initializing instructions for i8085 processor and matching opcodes with
 * instructions.
 * 
 * @author zubergu
 *
 */
public class InstructionSet8085 implements InstructionSet {

	private Instruction[] instructions = new Instruction[256];
	
	public InstructionSet8085(Cpu cpu) {
		initialize(cpu);
	}

	@Override
	public void initialize(Cpu cpu) {
		instructions[0x00] = new NOP(cpu);
		instructions[0x01] = new LXIrpData16(cpu, RegisterPairName.BC);            //0x01  LXI BC,data16
		instructions[0x02] = new STAXrp(cpu, RegisterPairName.BC);                 //0x02  STAX BC
		instructions[0x03] = new INXrp(cpu, RegisterPairName.BC);
		instructions[0x04] = new INRr(cpu, Register8BitName.B);
		instructions[0x05] = new DCRr(cpu, Register8BitName.B);
		instructions[0x06] = new MVIRdData(cpu, Register8BitName.B);              //0x06  MVI B,data
		instructions[0x07] = new RLC(cpu);
		instructions[0x08] = null;
		instructions[0x09] = new DADrp(cpu, RegisterPairName.BC);
		instructions[0x0A] = new LDAXrp(cpu, RegisterPairName.BC);                 //0x0A  LDAX BC
		instructions[0x0B] = new DCXrp(cpu, RegisterPairName.BC);
		instructions[0x0C] = new INRr(cpu, Register8BitName.C);
		instructions[0x0D] = new DCRr(cpu, Register8BitName.C);
		instructions[0x0E] = new MVIRdData(cpu, Register8BitName.C);              //0x0E  MVI C,data
		instructions[0x0F] = new RRC(cpu);
		instructions[0x10] = null;
		instructions[0x11] = new LXIrpData16(cpu, RegisterPairName.DE);            //0x11  LXI DE,data16
		instructions[0x12] = new STAXrp(cpu, RegisterPairName.DE);                 //0x02  STAX DE
		instructions[0x13] = new INXrp(cpu, RegisterPairName.DE);
		instructions[0x14] = new INRr(cpu, Register8BitName.D);
		instructions[0x15] = new DCRr(cpu, Register8BitName.D);
		instructions[0x16] = new MVIRdData(cpu, Register8BitName.D);              //0x16  MVI D,data
		instructions[0x17] = new RAL(cpu);
		instructions[0x18] = null;
		instructions[0x19] = new DADrp(cpu, RegisterPairName.DE);
		instructions[0x1A] = new LDAXrp(cpu, RegisterPairName.DE);                 //0x1a  LDAX DE
		instructions[0x1B] = new DCXrp(cpu, RegisterPairName.DE);
		instructions[0x1C] = new INRr(cpu, Register8BitName.E);
		instructions[0x1D] = new DCRr(cpu, Register8BitName.E);
		instructions[0x1E] = new MVIRdData(cpu, Register8BitName.E);              //0x1E  MVI E,data
		instructions[0x1F] = new RAR(cpu);
		instructions[0x20] = new RIM(cpu);
		instructions[0x21] = new LXIrpData16(cpu, RegisterPairName.HL);            //0x21  LXI HL,data16
		instructions[0x22] = new SHLDaddress16(cpu);                           //0x22  SHLD adddress16
		instructions[0x23] = new INXrp(cpu, RegisterPairName.HL);
		instructions[0x24] = new INRr(cpu, Register8BitName.H);
		instructions[0x25] = new DCRr(cpu, Register8BitName.H);
		instructions[0x26] = new MVIRdData(cpu, Register8BitName.H);              //0x26  MVI H,data
		instructions[0x27] = new DAA(cpu);
		instructions[0x28] = null;
		instructions[0x29] = new DADrp(cpu, RegisterPairName.HL);
		instructions[0x2A] = new LHLDaddress16(cpu);                           //0x2A LHLD address16
		instructions[0x2B] = new DCXrp(cpu, RegisterPairName.HL);
		instructions[0x2C] = new INRr(cpu, Register8BitName.L);
		instructions[0x2D] = new DCRr(cpu, Register8BitName.L);
		instructions[0x2E] = new MVIRdData(cpu, Register8BitName.L);              //0x06  MVI L,data
		instructions[0x2F] = new CMA(cpu);
		instructions[0x30] = new SIM(cpu);
		instructions[0x31] = new LXIrpData16(cpu, Register16BitName.SP);            //0x31  LXI SP,data16
		instructions[0x32] = new STAaddress16(cpu);                             //0x32  STA address16
		instructions[0x33] = new INXrp(cpu, Register16BitName.SP);
		instructions[0x34] = new INRM(cpu);
		instructions[0x35] = new DCRM(cpu);
		instructions[0x36] = new MVIMdata(cpu);                                //0x36  MVI M,data
		instructions[0x37] = new STC(cpu);
		instructions[0x38] = null;
		instructions[0x39] = new DADrp(cpu, Register16BitName.SP);
		instructions[0x3A] = new LDAaddress16(cpu);                            //0x3A  LDA address16
		instructions[0x3B] = new DCXrp(cpu, Register16BitName.SP);
		instructions[0x3C] = new INRr(cpu, Register8BitName.A);
		instructions[0x3D] = new DCRr(cpu, Register8BitName.A);
		instructions[0x3E] = new MVIRdData(cpu, Register8BitName.A);              //0x3E  MVI A,data
		instructions[0x3F] = new CMC(cpu);
		instructions[0x40] = new MOVRdRs(cpu, Register8BitName.B, Register8BitName.B); //0x40  MOV B,B
		instructions[0x41] = new MOVRdRs(cpu, Register8BitName.B, Register8BitName.C); //0x41  MOV B,C
		instructions[0x42] = new MOVRdRs(cpu, Register8BitName.B, Register8BitName.D); //0x42  MOV B,D
		instructions[0x43] = new MOVRdRs(cpu, Register8BitName.B, Register8BitName.E); //0x43  MOV B,E
		instructions[0x44] = new MOVRdRs(cpu, Register8BitName.B, Register8BitName.H); //0x44  MOV B,H
		instructions[0x45] = new MOVRdRs(cpu, Register8BitName.B, Register8BitName.L); //0x45  MOV B,L
		instructions[0x46] = new MOVRdM(cpu, Register8BitName.B);                      //0x46  MOV B,M
		instructions[0x47] = new MOVRdRs(cpu, Register8BitName.B, Register8BitName.A); //0x47  MOV B,A
		instructions[0x48] = new MOVRdRs(cpu, Register8BitName.C, Register8BitName.B); //0x48  MOV C,B
		instructions[0x49] = new MOVRdRs(cpu, Register8BitName.C, Register8BitName.C); //0x49  MOV C,C
		instructions[0x4A] = new MOVRdRs(cpu, Register8BitName.C, Register8BitName.D); //0x4A  MOV C,D
		instructions[0x4B] = new MOVRdRs(cpu, Register8BitName.C, Register8BitName.E); //0x4B  MOV C,E
		instructions[0x4C] = new MOVRdRs(cpu, Register8BitName.C, Register8BitName.H); //0x4C  MOV C,H
		instructions[0x4D] = new MOVRdRs(cpu, Register8BitName.C, Register8BitName.L); //0x4D  MOV C,L
		instructions[0x4E] = new MOVRdM(cpu, Register8BitName.C);                  //0x4E  MOV C,M
		instructions[0x4F] = new MOVRdRs(cpu, Register8BitName.C, Register8BitName.A); //0x4F  MOV C,A
		instructions[0x50] = new MOVRdRs(cpu, Register8BitName.D, Register8BitName.B); //0x50  MOV D,B
		instructions[0x51] = new MOVRdRs(cpu, Register8BitName.D, Register8BitName.C); //0x51  MOV D,C
		instructions[0x52] = new MOVRdRs(cpu, Register8BitName.D, Register8BitName.D); //0x52  MOV D,D
		instructions[0x53] = new MOVRdRs(cpu, Register8BitName.D, Register8BitName.E); //0x53  MOV D,E
		instructions[0x54] = new MOVRdRs(cpu, Register8BitName.D, Register8BitName.H); //0x54  MOV D,H
		instructions[0x55] = new MOVRdRs(cpu, Register8BitName.D, Register8BitName.L); //0x55  MOV D,L
		instructions[0x56] = new MOVRdM(cpu, Register8BitName.D);                  //0x5E  MOV D,M
		instructions[0x57] = new MOVRdRs(cpu, Register8BitName.D, Register8BitName.A); //0x57  MOV D,A
		instructions[0x58] = new MOVRdRs(cpu, Register8BitName.E, Register8BitName.B); //0x58  MOV E,B
		instructions[0x59] = new MOVRdRs(cpu, Register8BitName.E, Register8BitName.C); //0x59  MOV E,C
		instructions[0x5A] = new MOVRdRs(cpu, Register8BitName.E, Register8BitName.D); //0x5A  MOV E,D
		instructions[0x5B] = new MOVRdRs(cpu, Register8BitName.E, Register8BitName.E); //0x5B  MOV E,E
		instructions[0x5C] = new MOVRdRs(cpu, Register8BitName.E, Register8BitName.H); //0x5C  MOV E,H
		instructions[0x5D] = new MOVRdRs(cpu, Register8BitName.E, Register8BitName.L); //0x5D  MOV E,L
		instructions[0x5E] = new MOVRdM(cpu, Register8BitName.E);                  //0x5E  MOV E,M
		instructions[0x5F] = new MOVRdRs(cpu, Register8BitName.E, Register8BitName.A); //0x5F  MOV E,A
		instructions[0x60] = new MOVRdRs(cpu, Register8BitName.H, Register8BitName.B); //0x60  MOV H,B
		instructions[0x61] = new MOVRdRs(cpu, Register8BitName.H, Register8BitName.C); //0x61  MOV H,C
		instructions[0x62] = new MOVRdRs(cpu, Register8BitName.H, Register8BitName.D); //0x62  MOV H,D
		instructions[0x63] = new MOVRdRs(cpu, Register8BitName.H, Register8BitName.E); //0x63  MOV H,E
		instructions[0x64] = new MOVRdRs(cpu, Register8BitName.H, Register8BitName.H); //0x64  MOV H,H
		instructions[0x65] = new MOVRdRs(cpu, Register8BitName.H, Register8BitName.L); //0x65  MOV H,L
		instructions[0x66] = new MOVRdM(cpu, Register8BitName.H);                  //0x5E  MOV H,M
		instructions[0x67] = new MOVRdRs(cpu, Register8BitName.H, Register8BitName.A); //0x67  MOV H,A
		instructions[0x68] = new MOVRdRs(cpu, Register8BitName.L, Register8BitName.B); //0x68  MOV L,B
		instructions[0x69] = new MOVRdRs(cpu, Register8BitName.L, Register8BitName.C); //0x69  MOV L,C
		instructions[0x6A] = new MOVRdRs(cpu, Register8BitName.L, Register8BitName.D); //0x6A  MOV L,D
		instructions[0x6B] = new MOVRdRs(cpu, Register8BitName.L, Register8BitName.E); //0x6B  MOV L,E
		instructions[0x6C] = new MOVRdRs(cpu, Register8BitName.L, Register8BitName.H); //0x6C  MOV L,H
		instructions[0x6D] = new MOVRdRs(cpu, Register8BitName.L, Register8BitName.L); //0x6D  MOV L,L
		instructions[0x6E] = new MOVRdM(cpu, Register8BitName.L);                      //0x5E  MOV L,M
		instructions[0x6F] = new MOVRdRs(cpu, Register8BitName.L, Register8BitName.A); //0x6F  MOV L,A
		instructions[0x70] = new MOVMRs(cpu, Register8BitName.B); //0x70 MOV M, B
		instructions[0x71] = new MOVMRs(cpu, Register8BitName.C); //0x71 MOV M, C
		instructions[0x72] = new MOVMRs(cpu, Register8BitName.D); //0x72 MOV M, D
		instructions[0x73] = new MOVMRs(cpu, Register8BitName.E); //0x73 MOV M, E
		instructions[0x74] = new MOVMRs(cpu, Register8BitName.H); //0x74 MOV M, H
		instructions[0x75] = new MOVMRs(cpu, Register8BitName.L); //0x75 MOV M, L
		instructions[0x76] = new HLT(cpu); //0x76 
		instructions[0x77] = new MOVMRs(cpu, Register8BitName.A); //0x77 MOV M, A
		instructions[0x78] = new MOVRdRs(cpu, Register8BitName.A, Register8BitName.B); //0x78  MOV A,B
		instructions[0x79] = new MOVRdRs(cpu, Register8BitName.A, Register8BitName.C); //0x79  MOV A,C
		instructions[0x7A] = new MOVRdRs(cpu, Register8BitName.A, Register8BitName.D); //0x7A  MOV A,D
		instructions[0x7B] = new MOVRdRs(cpu, Register8BitName.A, Register8BitName.E); //0x7B  MOV A,E
		instructions[0x7C] = new MOVRdRs(cpu, Register8BitName.A, Register8BitName.H); //0x7C  MOV A,H
		instructions[0x7D] = new MOVRdRs(cpu, Register8BitName.A, Register8BitName.L); //0x7D  MOV A,L
		instructions[0x7E] = new MOVRdM(cpu, Register8BitName.A);                      //0x7E  MOV A,M
		instructions[0x7F] = new MOVRdRs(cpu, Register8BitName.A, Register8BitName.A); //0x7F  MOV A,A
		instructions[0x80] = new ADDr(cpu, Register8BitName.B);
		instructions[0x81] = new ADDr(cpu, Register8BitName.C);
		instructions[0x82] = new ADDr(cpu, Register8BitName.D);
		instructions[0x83] = new ADDr(cpu, Register8BitName.E);
		instructions[0x84] = new ADDr(cpu, Register8BitName.H);
		instructions[0x85] = new ADDr(cpu, Register8BitName.L);
		instructions[0x86] = new ADDM(cpu);
		instructions[0x87] = new ADDr(cpu, Register8BitName.A);
		instructions[0x88] = new ADCr(cpu, Register8BitName.B);
		instructions[0x89] = new ADCr(cpu, Register8BitName.C);
		instructions[0x8A] = new ADCr(cpu, Register8BitName.D);
		instructions[0x8B] = new ADCr(cpu, Register8BitName.E);
		instructions[0x8C] = new ADCr(cpu, Register8BitName.H);
		instructions[0x8D] = new ADCr(cpu, Register8BitName.L);
		instructions[0x8E] = new ADCM(cpu);
		instructions[0x8F] = new ADCr(cpu, Register8BitName.A);
		instructions[0x90] = new SUBr(cpu, Register8BitName.B);
		instructions[0x91] = new SUBr(cpu, Register8BitName.C);
		instructions[0x92] = new SUBr(cpu, Register8BitName.D);
		instructions[0x93] = new SUBr(cpu, Register8BitName.E);
		instructions[0x94] = new SUBr(cpu, Register8BitName.H);
		instructions[0x95] = new SUBr(cpu, Register8BitName.L);
		instructions[0x96] = new SUBM(cpu);
		instructions[0x97] = new SUBr(cpu, Register8BitName.A);
		instructions[0x98] = new SBBr(cpu, Register8BitName.B);
		instructions[0x99] = new SBBr(cpu, Register8BitName.C);
		instructions[0x9A] = new SBBr(cpu, Register8BitName.D);
		instructions[0x9B] = new SBBr(cpu, Register8BitName.E);
		instructions[0x9C] = new SBBr(cpu, Register8BitName.H);
		instructions[0x9D] = new SBBr(cpu, Register8BitName.L);
		instructions[0x9E] = new SBBM(cpu);
		instructions[0x9F] = new SBBr(cpu, Register8BitName.A);
		instructions[0xA0] = new ANAr(cpu, Register8BitName.B);
		instructions[0xA1] = new ANAr(cpu, Register8BitName.C);
		instructions[0xA2] = new ANAr(cpu, Register8BitName.D);
		instructions[0xA3] = new ANAr(cpu, Register8BitName.E);
		instructions[0xA4] = new ANAr(cpu, Register8BitName.H);
		instructions[0xA5] = new ANAr(cpu, Register8BitName.L);
		instructions[0xA6] = new ANAM(cpu);
		instructions[0xA7] = new ANAr(cpu, Register8BitName.A);
		instructions[0xA8] = new XRAr(cpu, Register8BitName.B);
		instructions[0xA9] = new XRAr(cpu, Register8BitName.C);
		instructions[0xAA] = new XRAr(cpu, Register8BitName.D);
		instructions[0xAB] = new XRAr(cpu, Register8BitName.E);
		instructions[0xAC] = new XRAr(cpu, Register8BitName.H);
		instructions[0xAD] = new XRAr(cpu, Register8BitName.L);
		instructions[0xAE] = new XRAM(cpu);
		instructions[0xAF] = new XRAr(cpu, Register8BitName.A);
		instructions[0xB0] = new ORAr(cpu, Register8BitName.B);
		instructions[0xB1] = new ORAr(cpu, Register8BitName.C);
		instructions[0xB2] = new ORAr(cpu, Register8BitName.D);
		instructions[0xB3] = new ORAr(cpu, Register8BitName.E);
		instructions[0xB4] = new ORAr(cpu, Register8BitName.H);
		instructions[0xB5] = new ORAr(cpu, Register8BitName.L);
		instructions[0xB6] = new ORAM(cpu);
		instructions[0xB7] = new ORAr(cpu, Register8BitName.A);
		instructions[0xB8] = new CMPr(cpu, Register8BitName.B);
		instructions[0xB9] = new CMPr(cpu, Register8BitName.C);
		instructions[0xBA] = new CMPr(cpu, Register8BitName.D);
		instructions[0xBB] = new CMPr(cpu, Register8BitName.E);
		instructions[0xBC] = new CMPr(cpu, Register8BitName.H);
		instructions[0xBD] = new CMPr(cpu, Register8BitName.L);
		instructions[0xBE] = new CMPM(cpu);
		instructions[0xBF] = new CMPr(cpu, Register8BitName.A);
		instructions[0xC0] = new Rcondition(cpu, JumpCondition.NZ);
		instructions[0xC1] = new POPrp(cpu, RegisterPairName.BC);
		instructions[0xC2] = new JConditionAddr(cpu, JumpCondition.NZ);
		instructions[0xC3] = new JMPaddr(cpu);
		instructions[0xC4] = new CConditionAddr(cpu, JumpCondition.NZ);
		instructions[0xC5] = new PUSHrp(cpu, RegisterPairName.BC);
		instructions[0xC6] = new ADIdata(cpu);
		instructions[0xC7] = new RSTn(cpu, 0);
		instructions[0xC8] = new Rcondition(cpu, JumpCondition.Z);
		instructions[0xC9] = new RET(cpu);
		instructions[0xCA] = new JConditionAddr(cpu, JumpCondition.Z);
		instructions[0xCB] = null;
		instructions[0xCC] = new CConditionAddr(cpu, JumpCondition.Z);
		instructions[0xCD] = new CALLaddr(cpu);
		instructions[0xCE] = new ACIdata(cpu);
		instructions[0xCF] = new RSTn(cpu, 1);
		instructions[0xD0] = new Rcondition(cpu, JumpCondition.NC);
		instructions[0xD1] = new POPrp(cpu, RegisterPairName.DE);
		instructions[0xD2] = new JConditionAddr(cpu, JumpCondition.NC);
		instructions[0xD3] = new OUTport(cpu);
		instructions[0xD4] = new CConditionAddr(cpu, JumpCondition.NC);
		instructions[0xD5] = new PUSHrp(cpu, RegisterPairName.DE);
		instructions[0xD6] = new SUIdata(cpu);
		instructions[0xD7] = new RSTn(cpu, 2);
		instructions[0xD8] = new Rcondition(cpu, JumpCondition.C);
		instructions[0xD9] = null;
		instructions[0xDA] = new JConditionAddr(cpu, JumpCondition.C);
		instructions[0xDB] = new INport(cpu);
		instructions[0xDC] = new CConditionAddr(cpu, JumpCondition.C);
		instructions[0xDD] = null;
		instructions[0xDE] = new SBIdata(cpu);
		instructions[0xDF] = new RSTn(cpu, 3);
		instructions[0xE0] = new Rcondition(cpu, JumpCondition.PO);
		instructions[0xE1] = new POPrp(cpu, RegisterPairName.HL);
		instructions[0xE2] = new JConditionAddr(cpu, JumpCondition.PO);
		instructions[0xE3] = new XTHL(cpu);
		instructions[0xE4] = new CConditionAddr(cpu, JumpCondition.PO);
		instructions[0xE5] = new PUSHrp(cpu, RegisterPairName.HL);
		instructions[0xE6] = new ANIdata(cpu);
		instructions[0xE7] = new RSTn(cpu, 4);
		instructions[0xE8] = new Rcondition(cpu, JumpCondition.PE);
		instructions[0xE9] = new PCHL(cpu);
		instructions[0xEA] = new JConditionAddr(cpu, JumpCondition.PE);
		instructions[0xEB] = new XCHG(cpu);
		instructions[0xEC] = new CConditionAddr(cpu, JumpCondition.PE);
		instructions[0xED] = null;
		instructions[0xEE] = new XRIdata(cpu);
		instructions[0xEF] = new RSTn(cpu, 5);
		instructions[0xF0] = new Rcondition(cpu, JumpCondition.P);
		instructions[0xF1] = new POPrp(cpu, RegisterPairName.APSW);
		instructions[0xF2] = new JConditionAddr(cpu, JumpCondition.P);
		instructions[0xF3] = new DI(cpu);
		instructions[0xF4] = new CConditionAddr(cpu, JumpCondition.P);
		instructions[0xF5] = new PUSHrp(cpu, RegisterPairName.APSW);
		instructions[0xF6] = new ORIdata(cpu);
		instructions[0xF7] = new RSTn(cpu, 6);
		instructions[0xF8] = new Rcondition(cpu, JumpCondition.M);
		instructions[0xF9] = new SPHL(cpu);
		instructions[0xFA] = new JConditionAddr(cpu, JumpCondition.M);
		instructions[0xFB] = new EI(cpu);
		instructions[0xFC] = new CConditionAddr(cpu, JumpCondition.M);
		instructions[0xFD] = null;
		instructions[0xFE] = new CPIdata(cpu);
		instructions[0xFF] = new RSTn(cpu, 7);

	}

	@Override
	public Instruction decode(int opcode) throws UnimplementedInstructionException {
		Instruction instruction = instructions[opcode];

		if (instruction == null) {
			throw new UnimplementedInstructionException();
		}

		return instruction;
	}

}
