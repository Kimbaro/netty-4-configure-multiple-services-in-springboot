package com.template.netty.utils;

import java.nio.ByteBuffer;

public class HexUtil {
	public static final int[] HEX_CODE = {
		0,0xa,0xb,0xc,0xd,0xe,0xf,  0,
		0,  0,  0,  0,  0,  0,  0,  0,
		0,  1,  2,  3,  4,  5,  6,  7,
		8,  9,  0,  0,  0,  0,  0,  0};
	
    public static String toString(byte buf[])
    {
            StringBuffer strbuf = new StringBuffer(buf.length * 2);

            for(int i=0; i< buf.length; i++)
            {
                    if(((int) buf[i] & 0xff) < 0x10)
                            strbuf.append("0");

                    strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
                    
                    strbuf.append(" ");
            }
            return strbuf.toString().toUpperCase();
    }
    
    public static String toString(byte buf)
    {
            StringBuffer strbuf = new StringBuffer(1 * 2);
            
            if(((int) buf & 0xff) < 0x10)
                    strbuf.append("0");

            strbuf.append(Long.toString((int) buf & 0xff, 16));
            return strbuf.toString().toUpperCase();
    }
    
    public static String toString2(byte buf[])
    {
            StringBuffer strbuf = new StringBuffer(buf.length * 2);

            for(int i=0; i< buf.length; i++)
            {
                    if(((int) buf[i] & 0xff) < 0x10)
                            strbuf.append("0");

                    strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
            }
            return strbuf.toString().toUpperCase();
    }
    
    /*
     * String "10"  ==> Hex Bytes 31 30, 2byte
     */
    public static byte[] stringToBytes(String str){
		char[] chars = str.toCharArray();
		
		ByteBuffer buffer = ByteBuffer.allocate(chars.length);
		
		for(int i=0; i<chars.length; i++){
			String temp = Integer.toHexString(chars[i]);
			byte[] integerHexBytes = stringToHex(temp);
			buffer.put(integerHexBytes);
		}
		
		buffer.flip();
		
		byte[] returnBytes = new byte[buffer.remaining()];
		
		buffer.get(returnBytes); 
		
		return returnBytes;
    }
    
	// String "0A" ==> Hex Bytes 0A, 1byte
	public static byte[] stringToHex(String s) {
		if ((s.length()&1)!= 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("0");
			sb.append(s);
			s = sb.toString();
		}
	 
		if ((s.length()&1)!=0) throw new IllegalArgumentException();
	 
		char[] c = s.toCharArray();
		byte[] b = new byte[c.length>>1];
	 
		for(int i=0;i<b.length;++i)
			b[i] = (byte)(HEX_CODE[c[i<<1]&0x1f]<<4|HEX_CODE[c[(i<<1)+1]&0x1f]);


		return b;
	}
	
	public static String hexToString(byte[] buf){
        StringBuffer strbuf = new StringBuffer(buf.length * 2);

        for(int i=0; i< buf.length; i++)
        {
                if(((int) buf[i] & 0xff) < 0x10)
                        strbuf.append("0");

                strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
                
        }
        return strbuf.toString();
	}
	
	public static String hexToString(byte buf){
        StringBuffer strbuf = new StringBuffer(2);

        if(((int) buf & 0xff) < 0x10)
            strbuf.append("0");

        strbuf.append(Long.toString((int) buf & 0xff, 16));
        
        return strbuf.toString();
	}
	
	// 10진수를 n진수로
 	//jin : 진법, num : 변환할 수
	public static char[] TenToN(int jin, int num) {
		int i=0;
		char[] res = new char[100];
		char[] out;
		// 몫이 0일때까지 반복
		while( num != 0 ) {
			// 나머지가 0~9 사이이면  캐릭터 값을 배열에 저장
			if( (num % jin) < 10 ) {
				res[i] = (char)((num % jin) + 48);
			}

			// 나머지가 10 이상이면 해당하는 값의 알파벳을 저장
			else {
				res[i] = (char)((num % jin) + 55);
			}

			// 다음 루프에 지금의 몫을 이용해 나눠야 하기 때문에..
			// 나눈 몫을 num 변수에 저장
			num /= jin;
			i++;
		}
		out = new char[i+1];
		int j=0;
		// while 루프 부분은 거꾸로 읽는 역할

		while( i >= 0 ) {
			out[j++]= res[i];
			i--;
		}
		return out;
	}
	
	////////////////////////////////////////////
	// n진수를 10진수로
	//n : 진수, s : 변환할 수
	public static int NToTen(int n, String s) {
		int ten = 0;
		s = s.toUpperCase();
		for(int x = 0; x < s.length(); x++) {
			int digit = s.charAt(x) - '0';
			if(digit > 9) {
				digit = s.charAt(x) + 10 - 'A';
			}
   
			ten = ten + (int) Math.pow((double) n, (double) (s.length() - x - 1)) * digit;
		}
  
		return ten;
	}
 
	
    /**
     * array of bytes to a int
     */
    public static final long bytesToLong(byte[] ib) {
    	
        return ((unsigned(ib[0]) << 56) + (unsigned(ib[1]) << 48) +
                (unsigned(ib[2]) << 40) + (unsigned(ib[3]) << 32) +
                (unsigned(ib[4]) << 24) + (unsigned(ib[5]) << 16) +
                (unsigned(ib[6]) << 8) + (unsigned(ib[7]) << 0));
    }
    
	 public long byteToLong(byte[] value,int count)
	 {
	  long result =((value[0+count]&0xFF)<<56)|
	     ((value[1+count]&0xFF)<<48)|
	     ((value[2+count]&0xFF)<<40)|
	     ((value[3+count]&0xFF)<<32)|
	     ((value[4+count]&0xFF)<<24)|
	     ((value[5+count]&0xFF)<<16)|
	     ((value[6+count]&0xFF)<<8)|
	     (value[7+count]&0xFF);
	  return result;
	 }
	 
    /**
     * array of bytes to a int
     */
    public static final byte[] LongToByte(long value) {
    	byte[] bt = new byte[8];
    	bt[0] = (byte) (value >> 56);
    	bt[1] = (byte) (value >> 48);
    	bt[2] = (byte) (value >> 40);
    	bt[3] = (byte) (value >> 32);
    	bt[4] = (byte) (value >> 24);
    	bt[5] = (byte) (value >> 16);
    	bt[6] = (byte) (value >> 8);
    	bt[7] = (byte) (value >> 0);
    	
        return bt;
    }
    
    
    
	
    /**
     * array of bytes to a int
     */
    public static final int bytesToInt(byte[] ib) {
    	
        return ((unsigned(ib[0]) << 24) + (unsigned(ib[1]) << 16) +
                (unsigned(ib[2]) << 8) + (unsigned(ib[3]) << 0));
    }
    
    /**
     * array of bytes to a int
     */
    public static final int bytesToInt(byte ib) {
    	
        return (unsigned(ib) << 0);
    }
    
    /**
     * array of bytes to a int
     */
    public static final byte[] intToByte(int value) {
    	byte[] bt = new byte[4];
    	bt[0] = (byte) (value >> 24);
    	bt[1] = (byte) (value >> 16);
    	bt[2] = (byte) (value >> 8);
    	bt[3] = (byte) (value >> 0);
    	
        return bt;
    }
    
    
    /**
     * array of bytes to a int
     */
    public static final int bytesToInt(byte[] ib, int index) {
        return ((unsigned(ib[index]) << 24) + (unsigned(ib[index + 1]) << 16) +
                (unsigned(ib[index + 2]) << 8) + (unsigned(ib[index + 3]) << 0));
    }
      
    
    
    public static int unsigned(byte sb) {
        int val;

        if ((sb & 0x80) == 0x80) { // negative range
            val = sb & 0x7F;
            val += 0x80;
        } else { // positive range
            val = (int) sb;
        }

        return val;
    }	
    
    
    
    public static void main(String[] args) {
        
        //F099DE45B46AA88DACACCD11E7F706402F6920D9A108FC60F3836E50025B7E23F5EC60CB00CEC510D3D79084495D207D
        
        byte[] key = HexUtil.stringToHex("F099DE45B46AA88DACACCD11E7F706402F6920D9A108FC60F3836E50025B7E23F5EC60CB00CEC510D3D79084495D207D");
        System.out.println("stringToHex key:" + HexUtil.toString(key));
        
        String aaa = "0123456789ABCDEFFEDCBA9876543210";
        System.out.println("stringToHex key:" + HexUtil.toString(HexUtil.stringToBytes(aaa)));
        
    }
    
    
    
}
