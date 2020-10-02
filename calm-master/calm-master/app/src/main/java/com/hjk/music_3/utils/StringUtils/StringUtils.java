package com.hjk.music_3.utils.StringUtils;


public class StringUtils {

    private StringUtils(){

    }


    public static String[] str_split(String str){

        String[] arr=str.split(",");

        return arr;
    }

    public static int getIndex(String[] str,String s){
        int length=str.length;
        int index=-1;
        for(int i=0; i<length; i++){
            if(str[i].equals(s)){
                index=i;
            }
        }
        return index;
    }

    public static String[] remove(String[] arr, int index){
        int length=arr.length;
        String[] array=new String[length-1];
        int j=0;
        for(int i=0; i<length; i++){
            if(i!=index){
                array[j]=arr[i];
                j++;
            }
        }
        return array;
    }

    public static String merge(String[] arr){
        String str="";
        for(int i=0; i<arr.length; i++){

            str+=arr[i]+",";
        }
        return str;
    }


    public static boolean isEmpty(final CharSequence s){
        return s==null || s.length()==0;
    }

    public static boolean isTrimEmpty(final String s){
        return (s==null || s.trim().length()==0);
    }

    public static boolean isSpace(final String s){
        if(s==null) return true;
        for(int i=0, len=s.length(); i<len; i++){
            if(!Character.isWhitespace(s.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static boolean equals(final CharSequence s1, final CharSequence s2){
        if(s1==s2) return true;
        int length;
        if(s1!=null && s2!=null && (length= s1.length())==s2.length()){
            if(s1 instanceof String && s2 instanceof String){
                return s1.equals(s2);
            }else{
                for(int i=0; i<length; i++){
                    if(s1.charAt(i)!=s2.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    public static String upperFirstLetter(final String s){
        if(s==null || s.length()==0) return "";
        if(!Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char)(s.charAt(0)-32))+s.substring(1);
    }

    public static String lowerFistLetter(final String s){
        if(s==null || s.length()==0) return "";
        if(!Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char)(s.charAt(0)+32))+ s.substring(1);
    }

    public static String reverse(final String s){
        if(s==null) return "";
        int len=s.length();
        if(len<=1) return s;
        int mid=len>>1;
        char[] chars=s.toCharArray();
        char c;

        for(int i=0; i<mid; i++){
            c=chars[i];
            chars[i]=chars[len-i-1];
            chars[len-i-1]=c;
        }
        return new String(chars);
    }


}
