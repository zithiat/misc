package com.mycom.parser.http.utilities.json.buffer;


public class CharBuffer
{

  public char[] data = null;

  public int length = 0;



  public CharBuffer ()
  {
  }



  public CharBuffer (char[] data)
  {
    this.data = data;
  }



  public CharBuffer (int capacity)
  {
    this.data = new char[capacity];
  }

  public void clean()
  {
    this.data = null;
  }
}
