package com.pacote.casaOnline;

public class Base64 {
    public static boolean teste=false;
    public static String usuario="";
	public static String chave="";
	public static String senha="";
	public static String nome="";
	public static String data="";
	public static String hora="";
	public static int quant=0;
	public static String[] dispositivos=null;
	
    private static final String base64code = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz" + "0123456789" + "+/";
 
    private static final int splitLinesAt = 76;
 
    public static byte[] zeroPad(int length, byte[] bytes) {
        byte[] padded = new byte[length]; // initialized to zero by JVM
        System.arraycopy(bytes, 0, padded, 0, bytes.length);
        return padded;
    }
 
    public static String encode(String string) {
 
        String encoded = "";
        byte[] stringArray;
        try {
            stringArray = string.getBytes("UTF-8");  // use appropriate encoding string!
        } catch (Exception ignored) {
            stringArray = string.getBytes();  // use locale default rather than croak
        }
        // determine how many padding bytes to add to the output
        int paddingCount = (3 - (stringArray.length % 3)) % 3;
        // add any necessary padding to the input
        stringArray = zeroPad(stringArray.length + paddingCount, stringArray);
        // process 3 bytes at a time, churning out 4 output bytes
        // worry about CRLF insertions later
        for (int i = 0; i < stringArray.length; i += 3) {
            int j = ((stringArray[i] & 0xff) << 16) +
                ((stringArray[i + 1] & 0xff) << 8) + 
                (stringArray[i + 2] & 0xff);
            encoded = encoded + base64code.charAt((j >> 18) & 0x3f) +
                base64code.charAt((j >> 12) & 0x3f) +
                base64code.charAt((j >> 6) & 0x3f) +
                base64code.charAt(j & 0x3f);
        }
        // replace encoded padding nulls with "="
        return splitLines(encoded.substring(0, encoded.length() -
            paddingCount) + "==".substring(0, paddingCount));
 
    }
    public static String splitLines(String string) {
 
        String lines = "";
        for (int i = 0; i < string.length(); i += splitLinesAt) {
 
            lines += string.substring(i, Math.min(string.length(), i + splitLinesAt));
            lines += "\r\n";
 
        }
        return lines;
 
    }
    public static String criptografa (String[] x_){
    	//String[] x_={"yasmmin199&000000&432014&cad","."};
    	String x_converted="";
    	
    	x_converted+=encode(""+x_[0]);
 
    	return x_converted;
    }/*
    public static void main(String[] args) {
    	String[] x_={"testes0123&999999&999999&cad","."};
    	System.out.println(criptografa(x_));       
 
    }*/
 
}