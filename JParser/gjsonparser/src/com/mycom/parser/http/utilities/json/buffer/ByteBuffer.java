package com.mycom.parser.http.utilities.json.buffer;

public class ByteBuffer
{

  public byte[] data = null;

  public int length = 0;



  public ByteBuffer ()
  {
  }



  public ByteBuffer (byte[] data)
  {
    this.data = data;
  }



  public ByteBuffer (int capacity)
  {
    this.data = new byte[capacity];
  }

  public void clean()
  {
    this.data = null;
    this.length = 0;
  }
}
