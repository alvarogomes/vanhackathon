/*
 * StringUtils.java
 *
 * Created on 27 de Maio de 2007, 16:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vanhack.jobmatch.infra.rotines;

import java.util.StringTokenizer;
import java.util.Vector;

public class StringUtils
{

    public static final int REPLACE_ALL = 0;
    public static final int REPLACE_FIRST = 1;
    public static final int REPLACE_LAST = 2;
    
    public StringUtils()
    {
    }

    public static boolean  isNumeric(String s) {  
        return s.matches("[-+]?\\d*\\.?\\d+");  
    } 
    public static String replace(String s, String s1, String s2, int i)
        throws NullPointerException
    {
        int j = 0;
        boolean flag = false;
        StringBuffer stringbuffer = new StringBuffer(s);
        Object obj = null;
        boolean flag1 = true;
        do
        {
            if(j < 0 || !flag1)
                break;
            switch(i)
            {
            case 0: // '\0'
                j = stringbuffer.toString().indexOf(s1);
                break;

            case 1: // '\001'
                j = stringbuffer.toString().indexOf(s1);
                flag1 = false;
                break;

            case 2: // '\002'
                j = stringbuffer.toString().lastIndexOf(s1);
                flag1 = false;
                break;

            default:
                j = -1;
                break;
            }
            if(j >= 0)
            {
                int k = j + s1.length();
                stringbuffer.replace(j, k, s2);
            }
        } while(true);
        return stringbuffer.toString();
    }

    public static String changeWord(String s, String s1, String s2)
    {
        String s3 = null;
        if(s2 != null)
        {
            s3 = "";
            for(int i = s2.indexOf(s); i != -1; i = s2.indexOf(s))
            {
                s3 = s3 + s2.substring(0, i) + s1;
                s2 = s2.substring(i + s.length());
            }

            if(s2 != null)
                s3 = s3 + s2;
        }
        return s3;
    }

    public static String replace(String s, String as[], String as1[], int i)
        throws NullPointerException, ArrayIndexOutOfBoundsException
    {
        String s1 = s;
        int j = as.length <= as1.length ? as1.length : as.length;
        for(int k = 0; k < j; k++)
            s1 = replace(s1, as[k], as1[k], i);

        return s1;
    }

    public static String[] split(String s, String s1)
        throws NullPointerException
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s, s1);
        Vector vector = new Vector();
        for(; stringtokenizer.hasMoreTokens(); vector.add(stringtokenizer.nextToken()));
        String as[] = new String[vector.size()];
        for(int i = 0; i < vector.size(); i++)
            as[i] = (String)vector.elementAt(i);

        return as;
    }

    public static String nameOfClass(Class class1)
        throws NullPointerException
    {
        String s = class1.getName();
        int i = s.lastIndexOf('.') + 1;
        if(i != -1)
            return s.substring(i, s.length());
        else
            return s;
    }

    public static String nameOfClass(String s)
        throws NullPointerException
    {
        int i = s.lastIndexOf('.') + 1;
        if(i != -1)
            return s.substring(i, s.length());
        else
            return s;
    }

    public static String removeAccents(String s)
    {
        if(s == null)
            return s;
        char ac[] = s.toCharArray();
        for(int i = 0; i < s.length(); i++)
            if(ac[i] > 'z')
                switch(ac[i])
                {
                case 192: 
                case 193: 
                case 194: 
                case 195: 
                case 196: 
                    ac[i] = 'A';
                    break;

                case 224: 
                case 225: 
                case 226: 
                case 227: 
                case 228: 
                    ac[i] = 'a';
                    break;

                case 200: 
                case 201: 
                case 202: 
                case 203: 
                    ac[i] = 'E';
                    break;

                case 232: 
                case 233: 
                case 234: 
                case 235: 
                    ac[i] = 'e';
                    break;

                case 204: 
                case 205: 
                case 206: 
                case 207: 
                    ac[i] = 'I';
                    break;

                case 236: 
                case 237: 
                case 238: 
                case 239: 
                    ac[i] = 'i';
                    break;

                case 210: 
                case 211: 
                case 212: 
                case 213: 
                case 214: 
                    ac[i] = 'O';
                    break;

                case 242: 
                case 243: 
                case 244: 
                case 245: 
                case 246: 
                    ac[i] = 'o';
                    break;

                case 217: 
                case 218: 
                case 219: 
                case 220: 
                    ac[i] = 'U';
                    break;

                case 249: 
                case 250: 
                case 251: 
                case 252: 
                    ac[i] = 'u';
                    break;

                case 199: 
                    ac[i] = 'C';
                    break;

                case 231: 
                    ac[i] = 'c';
                    break;

                case 209: 
                    ac[i] = 'N';
                    break;

                case 241: 
                    ac[i] = 'n';
                    break;
                }

        return new String(ac);
    }

    public static boolean isEmpty(String s)
    {
        return s == null || s.trim().length() == 0;
    }

    public static boolean contains(String s, String s1)
    {
        return s != null && s1 != null && s.indexOf(s1) >= 0;
    }


}
