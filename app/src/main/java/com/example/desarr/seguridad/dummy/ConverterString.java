package com.example.desarr.seguridad.dummy;

import java.io.UnsupportedEncodingException;

public class ConverterString {

    private final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
    private static ConverterString hexStringConverter = null;

    private ConverterString()
    {}

    public static ConverterString getHexStringConverterInstance()
    {
        if (hexStringConverter==null) hexStringConverter = new ConverterString();
        return hexStringConverter;
    }

    public String stringToCode(String input) throws UnsupportedEncodingException
    {
        if (input == null) throw new NullPointerException();
        return asCode(input.getBytes());
    }

    public String codeToString(String txtInHex)
    {
        byte [] txtInByte = new byte [txtInHex.length() / 2];
        int j = 0;
        for (int i = 0; i < txtInHex.length(); i += 2)
        {
            txtInByte[j++] = Byte.parseByte(txtInHex.substring(i, i + 2), 16);
        }
        return new String(txtInByte);
    }

    private String asCode(byte[] buf)
    {
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i)
        {
            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(chars);
    }
}
