package com.mycom.parser.http.utilities.json.buffer;


public class IndexBuffer
{

  public int[] position = null;

  public int[] length = null;


  // Usually there can be max 256 different token or element types (1 byte / type)
  public byte[] type = null;

  // the number of tokens / elements in the IndexBuffer.
  public int count = 0;



  public IndexBuffer ()
  {
  }



  public IndexBuffer (int capacity, boolean useTypeArray)
  {
    this.position = new int[capacity];
    this.length = new int[capacity];
    if (useTypeArray)
    {
      this.type = new byte[capacity];
    }
  }

  public void clean()
  {
    this.position = null;
    this.length = null;
    this.type = null;
  }
}
