package com.mycom.parser.http.utilities.json;

import com.mycom.parser.http.utilities.json.buffer.CharBuffer;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * This class is only read data from file to ByteBuffer
 */
public class ReaderUtil
{

  public static CharBuffer readFile (String fileName, int bufferLength) throws IOException
  {
    FileReader reader = new FileReader (fileName);
    CharBuffer charBuffer = new CharBuffer (bufferLength); // Default: 8192, 65536
    charBuffer.length = reader.read (charBuffer.data);
    reader.close ();
    return charBuffer;
  }

  public static CharBuffer readStream(Reader in, int bufferLength) throws IOException
  {
    CharBuffer charBuffer = new CharBuffer (bufferLength); // Default: 8192, 65536
    charBuffer.length = in.read (charBuffer.data);
    in.close ();
    return charBuffer;
  }
}
