package com.l;

import java.util.ArrayList;

public class ElfType32 {
	
	public elf32_rel rel;
	public elf32_rela rela;
	public ArrayList<Elf32_Sym> symList = new ArrayList<Elf32_Sym>();
	public elf32_hdr hdr;//elfͷ����Ϣ
	public ArrayList<elf32_phdr> phdrList = new ArrayList<elf32_phdr>();//���ܻ��ж������ͷ
	public ArrayList<elf32_shdr> shdrList = new ArrayList<elf32_shdr>();//���ܻ��ж����ͷ
	public ArrayList<elf32_strtb> strtbList = new ArrayList<elf32_strtb>();//���ܻ��ж���ַ���ֵ
	
	public ElfType32() {
		rel = new elf32_rel();
		rela = new elf32_rela();
		hdr = new elf32_hdr();
	}
	
	/**
	 *  typedef struct elf32_rel {
		  Elf32_Addr	r_offset;
		  Elf32_Word	r_info;
		} Elf32_Rel;
	 *
	 */
	public class elf32_rel {
		public byte[] r_offset = new byte[4];
		public byte[] r_info = new byte[4];
		
		@Override
		public String toString(){
			return "r_offset:"+Utils.bytes2HexString(r_offset)+";r_info:"+Utils.bytes2HexString(r_info);
		}
	}
	
	/**
	 *  typedef struct elf32_rela{
		  Elf32_Addr	r_offset;
		  Elf32_Word	r_info;
		  Elf32_Sword	r_addend;
		} Elf32_Rela;
	 */
	public class elf32_rela{
		public byte[] r_offset = new byte[4];
		public byte[] r_info = new byte[4];
		public byte[] r_addend = new byte[4];
		
		@Override
		public String toString(){
			return "r_offset:"+Utils.bytes2HexString(r_offset)+";r_info:"+Utils.bytes2HexString(r_info)+";r_addend:"+Utils.bytes2HexString(r_info);
		}
	}
	
	/**
	 * typedef struct elf32_sym{
		  Elf32_Word	st_name;
		  Elf32_Addr	st_value;
		  Elf32_Word	st_size;
		  unsigned char	st_info;
		  unsigned char	st_other;
		  Elf32_Half	st_shndx;
		} Elf32_Sym;
	 */
	public static class Elf32_Sym{
		public byte[] st_name = new byte[4];//name
		public byte[] st_value = new byte[4];//symbol value
		public byte[] st_size = new byte[4];//symbol size
		public byte st_info;//type and binding
		public byte st_other;//0 no defind meaning
		public byte[] st_shndx = new byte[2];//section header index
		
		@Override
		public String toString(){
			return "st_name:"+Utils.bytes2HexString(st_name)
					+"\nst_value:"+Utils.bytes2HexString(st_value)
					+"\nst_size:"+Utils.bytes2HexString(st_size)
					+"\nst_info:"+(st_info/16)
					+"\nst_other:"+(((short)st_other) & 0xF)
					+"\nst_shndx:"+Utils.bytes2HexString(st_shndx);
		}
	}
	
	public void printSymList(){
		for(int i=0;i<symList.size();i++){
			System.out.println();
			System.out.println("The "+(i+1)+" Symbol Table:");
			System.out.println(symList.get(i).toString());
		}
	}
	
	//Bind�ֶ�==��st_info
	public static final int STB_LOCAL = 0;
	public static final int STB_GLOBAL = 1;
	public static final int STB_WEAK = 2;
	//Type�ֶ�==��st_other
	public static final int STT_NOTYPE = 0;
	public static final int STT_OBJECT = 1;
	public static final int STT_FUNC = 2;
	public static final int STT_SECTION = 3;
	public static final int STT_FILE = 4;
	/**
	 * ������Ҫע����ǻ���Ҫ��һ��ת��
	 *  #define ELF_ST_BIND(x)	((x) >> 4)
		#define ELF_ST_TYPE(x)	(((unsigned int) x) & 0xf)
	 */
	
	/**
	 * #define EI_NIDENT	16
	 * typedef struct elf32_hdr{
		  unsigned char	e_ident[EI_NIDENT];
		  Elf32_Half	e_type;
		  Elf32_Half	e_machine;
		  Elf32_Word	e_version;
		  Elf32_Addr	e_entry;  // Entry point
		  Elf32_Off	e_phoff;
		  Elf32_Off	e_shoff;
		  Elf32_Word	e_flags;
		  Elf32_Half	e_ehsize;
		  Elf32_Half	e_phentsize;
		  Elf32_Half	e_phnum;
		  Elf32_Half	e_shentsize;
		  Elf32_Half	e_shnum;
		  Elf32_Half	e_shstrndx;
		} Elf32_Ehdr;
	 */
	/**
	 * typedef uint32_t Elf32_Addr;
	 typedef uint16_t Elf32_Half;
	 typedef uint32_t Elf32_Off;
	 typedef int32_t  Elf32_Sword;
	 typedef uint32_t Elf32_Word;
	 */
	/**
	 * 1字节     uint8_t
	 2字节     uint16_t
	 4字节     uint32_t
	 8字节     uint64_t
	 1字节     unsigned char
	 */
	public class elf32_hdr{
		public byte[] e_ident = new byte[16];
		public byte[] e_type = new byte[2];
		public byte[] e_machine = new byte[2];
		public byte[] e_version = new byte[4];
		public byte[] e_entry = new byte[4];
		public byte[] e_phoff = new byte[4]; //program header 偏移值为程序头的开始位置
		public byte[] e_shoff = new byte[4]; //section header 偏移值为段头的开始位置
		public byte[] e_flags = new byte[4];//Processor-specific flags
		public byte[] e_ehsize = new byte[2];//elf header 大小
		public byte[] e_phentsize = new byte[2];//program header 子项大小
		public byte[] e_phnum = new byte[2]; //程序头数量
		public byte[] e_shentsize = new byte[2];//section header 子项大小
		public byte[] e_shnum = new byte[2]; //段头数量
		public byte[] e_shstrndx = new byte[2]; //string段在整个段中的索引值 用于定位String段的位置
		
		@Override
		public String toString(){
			return  "magic:"+ Utils.bytes2HexString(e_ident) 
					+"\ne_type:"+Utils.bytes2HexString(e_type)
					+"\ne_machine:"+Utils.bytes2HexString(e_machine)
					+"\ne_version:"+Utils.bytes2HexString(e_version)
					+"\ne_entry:"+Utils.bytes2HexString(e_entry)
					+"\ne_phoff:"+Utils.bytes2HexString(e_phoff)
					+"\ne_shoff:"+Utils.bytes2HexString(e_shoff)
					+"\ne_flags:"+Utils.bytes2HexString(e_flags)
					+"\ne_ehsize:"+Utils.bytes2HexString(e_ehsize)
					+"\ne_phentsize:"+Utils.bytes2HexString(e_phentsize)
					+"\ne_phnum:"+Utils.bytes2HexString(e_phnum)
					+"\ne_shentsize:"+Utils.bytes2HexString(e_shentsize)
					+"\ne_shnum:"+Utils.bytes2HexString(e_shnum)
					+"\ne_shstrndx:"+Utils.bytes2HexString(e_shstrndx);
		}
	}
	
	/**
	 * typedef struct elf32_phdr{
		  Elf32_Word	p_type;
		  Elf32_Off	p_offset;
		  Elf32_Addr	p_vaddr;
		  Elf32_Addr	p_paddr;
		  Elf32_Word	p_filesz;
		  Elf32_Word	p_memsz;
		  Elf32_Word	p_flags;
		  Elf32_Word	p_align;
		} Elf32_Phdr;
	 */
	public static class elf32_phdr{
		public byte[] p_type = new byte[4]; //segment type
		public byte[] p_offset = new byte[4]; //segment数据 偏移量
		public byte[] p_vaddr = new byte[4];//segment 虚拟地址
		public byte[] p_paddr = new byte[4];//segment 物理地址
		public byte[] p_filesz = new byte[4];//segment 在文件中的大小(这里是32*7==224)
		public byte[] p_memsz = new byte[4];//segment在内存中的大小 大小貌似同上
		public byte[] p_flags = new byte[4];//flags
		public byte[] p_align = new byte[4];//内存对齐
        //后面这里用010editor解析还有一个data块 数据偏移加上文件中大小就是它的大小
		
		@Override
		public String toString(){
			return "p_type:"+ Utils.bytes2HexString(p_type)
					+"\np_offset:"+Utils.bytes2HexString(p_offset)
					+"\np_vaddr:"+Utils.bytes2HexString(p_vaddr)
					+"\np_paddr:"+Utils.bytes2HexString(p_paddr)
					+"\np_filesz:"+Utils.bytes2HexString(p_filesz)
					+"\np_memsz:"+Utils.bytes2HexString(p_memsz)
					+"\np_flags:"+Utils.bytes2HexString(p_flags)
					+"\np_align:"+Utils.bytes2HexString(p_align);
		}
	}
	
	public void printPhdrList(){
		for(int i=0;i<phdrList.size();i++){
			System.out.println();
			System.out.println("The "+(i+1)+" Program Header:");
			System.out.println(phdrList.get(i).toString());
		}
	}
	
	/**
	 * typedef struct elf32_shdr {
		  Elf32_Word	sh_name;
		  Elf32_Word	sh_type;
		  Elf32_Word	sh_flags;
		  Elf32_Addr	sh_addr;
		  Elf32_Off	sh_offset;
		  Elf32_Word	sh_size;
		  Elf32_Word	sh_link;
		  Elf32_Word	sh_info;
		  Elf32_Word	sh_addralign;
		  Elf32_Word	sh_entsize;
		} Elf32_Shdr;
	 */
	public static class elf32_shdr{
		public byte[] sh_name = new byte[4];//name
		public byte[] sh_type = new byte[4];//type
		public byte[] sh_flags = new byte[4];//flag
		public byte[] sh_addr = new byte[4];//address
		public byte[] sh_offset = new byte[4];//file offset
		public byte[] sh_size = new byte[4];//setion size大小 //后面有一个data 这里的size是这个data的大小
		public byte[] sh_link = new byte[4];//table index link
		public byte[] sh_info = new byte[4];//额外的信息
		public byte[] sh_addralign = new byte[4];//地址对齐
		public byte[] sh_entsize = new byte[4];//section entry size//不同type有不同的size
		
		@Override
		public String toString(){
			return "sh_name:"+Utils.bytes2HexString(sh_name)/*Utils.byte2Int(sh_name)*/
					+"\nsh_type:"+Utils.bytes2HexString(sh_type)
					+"\nsh_flags:"+Utils.bytes2HexString(sh_flags)
					+"\nsh_add:"+Utils.bytes2HexString(sh_addr)
					+"\nsh_offset:"+Utils.bytes2HexString(sh_offset)
					+"\nsh_size:"+Utils.bytes2HexString(sh_size)
					+"\nsh_link:"+Utils.bytes2HexString(sh_link)
					+"\nsh_info:"+Utils.bytes2HexString(sh_info)
					+"\nsh_addralign:"+Utils.bytes2HexString(sh_addralign)
					+"\nsh_entsize:"+ Utils.bytes2HexString(sh_entsize);
		}
	}
	
	/****************sh_type********************/
	public static final int SHT_NULL = 0;
	public static final int SHT_PROGBITS = 1;
	public static final int SHT_SYMTAB = 2;
	public static final int SHT_STRTAB = 3;
	public static final int SHT_RELA = 4;
	public static final int SHT_HASH = 5;
	public static final int SHT_DYNAMIC = 6;
	public static final int SHT_NOTE = 7;
	public static final int SHT_NOBITS = 8;
	public static final int SHT_REL = 9;
	public static final int SHT_SHLIB = 10;
	public static final int SHT_DYNSYM = 11;
	public static final int SHT_NUM = 12;
	public static final int SHT_LOPROC = 0x70000000;
	public static final int SHT_HIPROC = 0x7fffffff;
	public static final int SHT_LOUSER = 0x80000000;
	public static final int SHT_HIUSER = 0xffffffff;
	public static final int SHT_MIPS_LIST = 0x70000000;
	public static final int SHT_MIPS_CONFLICT = 0x70000002;
	public static final int SHT_MIPS_GPTAB = 0x70000003;
	public static final int SHT_MIPS_UCODE = 0x70000004;
	
	/*****************sh_flag***********************/
	public static final int SHF_WRITE = 0x1;
	public static final int SHF_ALLOC = 0x2;
	public static final int SHF_EXECINSTR = 0x4;
	public static final int SHF_MASKPROC = 0xf0000000;
	public static final int SHF_MIPS_GPREL = 0x10000000;
	
	public void printShdrList(){
		for(int i=0;i<shdrList.size();i++){
			System.out.println();
			System.out.println("The "+(i+1)+" Section Header:");
			System.out.println(shdrList.get(i));
		}
	}
	
	
	public static class elf32_strtb{
		public byte[] str_name;
		public int len;
		
		@Override
		public String toString(){
			return "str_name:"+str_name
					+"len:"+len;
		}
	}
}
